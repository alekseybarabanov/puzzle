package aba.puzzle.service

import aba.puzzle.domain.Detail
import aba.puzzle.domain.PuzzlePosition
import aba.puzzle.domain.PuzzleState
import aba.puzzle.domain.dto.DetailVO
import aba.puzzle.domain.dto.NewTaskVO
import aba.puzzle.domain.dto.PuzzleStateVO
import aba.puzzle.kafka.CustomKafkaListenerRegistrar
import mu.KotlinLogging
import org.apache.kafka.clients.admin.AdminClient
import org.springframework.beans.factory.InitializingBean
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.MediaType
import org.springframework.kafka.config.TopicBuilder
import org.springframework.kafka.core.KafkaAdmin
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.client.WebClient

interface LaunchService {
    fun launch(topic: String): Boolean
}

@Component
class LaunchServiceImpl(
    @Value("\${app.repositoryUrl}") val repositoryUrl: String,
    @Value("\${app.taskTopicsTopic}") val taskTopicsTopic: String,
    @Autowired val puzzleStateService: PuzzleStateService,
    @Autowired val kafkaProducer: KafkaTemplate<String, PuzzleStateVO>,
    @Autowired val kafkaTaskTopicsProducer: KafkaTemplate<String, NewTaskVO>,
    @Autowired val kafkaAdmin: KafkaAdmin,
    @Autowired val listenerRegistrar: CustomKafkaListenerRegistrar,
    @Autowired val detailsService: DetailsService
) : LaunchService {
    private val log = KotlinLogging.logger {}

    override fun launch(topic: String): Boolean {
        //check that the task is not already running. Make call to repository for the answer
        if (!createTopic(topic)) {
            return false
        }

        //publish topic for consumers to subscribe
        sendNewTopic(topic)

        //Take each detail and put it on the left upper corner of the puzzle with all possible rotations (4).
        detailsService.getDetails().forEach {
            puzzleStateService.addDetail(PuzzleState(), it, PuzzlePosition.left_upper).forEach { puzzleState ->
                sendPuzzles(topic, puzzleState)
            }
        }
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


    private fun sendNewTopic(topic: String) {
        val taskVO = NewTaskVO()
        taskVO.topic = topic
        taskVO.allDetails = detailsService.getDetails().map { DetailVO.fromDetail(it) }
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
