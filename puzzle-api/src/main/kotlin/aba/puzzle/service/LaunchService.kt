package aba.puzzle.service

import aba.puzzle.domain.PuzzleConfig
import aba.puzzle.domain.PuzzleState
import aba.puzzle.domain.dto.NewTaskVO
import aba.puzzle.domain.dto.PuzzleConfigVO
import aba.puzzle.domain.dto.PuzzleStateVO
import mu.KotlinLogging
import org.apache.kafka.clients.admin.AdminClient
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.kafka.config.TopicBuilder
import org.springframework.kafka.core.KafkaAdmin
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.stereotype.Component

interface LaunchService {
    fun launch(topic: String, puzzleConfig: PuzzleConfig): Boolean
}

@Component
class LaunchServiceImpl(
    @Value("\${app.taskTopicsTopic}") val taskTopicsTopic: String,
    @Autowired val kafkaProducer: KafkaTemplate<String, PuzzleStateVO>,
    @Autowired val kafkaTaskTopicsProducer: KafkaTemplate<String, NewTaskVO>,
    @Autowired val kafkaAdmin: KafkaAdmin
) : LaunchService {
    private val log = KotlinLogging.logger {}

    override fun launch(topic: String, puzzleConfig: PuzzleConfig): Boolean {
        //check that the task is not already running. Make call to repository for the answer
        if (!createTopic(topic)) {
            return false
        }

        //publish topic for consumers to subscribe
        sendNewTopic(topic, puzzleConfig)

        //initiate puzzle assembling
        sendPuzzles(topic, PuzzleState())
        return true
    }

    private fun createTopic(topic: String): Boolean =
        kotlin.runCatching {
            val topicDescription = kafkaAdmin.describeTopics(topic)
            topicDescription[topic] == null
        }.getOrElse {
            val client = AdminClient.create(kafkaAdmin.configurationProperties)
            client.use {
                it.createTopics(listOf(TopicBuilder.name(topic).replicas(1).build()))
            }
            true
        }


    private fun sendNewTopic(topic: String, puzzleConfig: PuzzleConfig) {
        val taskVO = NewTaskVO(topic,PuzzleConfigVO.fromPuzzleConfig(puzzleConfig))
        val taskTopicsFuture = kafkaTaskTopicsProducer.send(taskTopicsTopic, taskVO)
        taskTopicsFuture.addCallback({
            log.info { "message sent to $taskTopicsTopic" }
        }, {
            log.info { "Exception in sending to kafka topic $taskTopicsTopic $it" }
        })
    }

    private fun sendPuzzles(topic: String, puzzleState: PuzzleState) {
        val puzzleFuture = kafkaProducer.send(topic, PuzzleStateVO.fromPuzzleState(puzzleState))
        puzzleFuture.addCallback({
            log.info {"message sent to $topic"}
        }, {
            log.info {"Exception in sending to kafka $it"}
        })
    }

}
