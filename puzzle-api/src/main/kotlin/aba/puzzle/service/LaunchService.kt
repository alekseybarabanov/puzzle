package aba.puzzle.service

import aba.puzzle.domain.DetailWithRotation
import aba.puzzle.domain.PuzzleConfig
import aba.puzzle.domain.PuzzleState
import aba.puzzle.domain.rest.mapstruct.dto.NewTaskDto
import aba.puzzle.domain.rest.mapstruct.dto.PuzzleStateDto
import aba.puzzle.domain.rest.mapstruct.mapper.MapStructMapper
import org.apache.commons.logging.Log
import org.apache.commons.logging.LogFactory
import org.apache.kafka.clients.admin.AdminClient
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.kafka.config.TopicBuilder
import org.springframework.kafka.core.KafkaAdmin
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.stereotype.Component
import java.util.*
import java.util.stream.IntStream
import kotlin.math.max

interface LaunchService {
    fun launch(topic: String, puzzleConfig: PuzzleConfig): Boolean
}

@Component
class LaunchServiceImpl(
    @Value("\${app.taskTopicsTopic}") val taskTopicsTopic: String,
    @Autowired val kafkaProducer: KafkaTemplate<String, PuzzleStateDto>,
    @Autowired val kafkaTaskTopicsProducer: KafkaTemplate<String, NewTaskDto>,
    @Autowired val kafkaAdmin: KafkaAdmin,
    @Autowired private val mapper: MapStructMapper
) : LaunchService {
    private val log: Log = LogFactory.getLog(LaunchServiceImpl::class.java)

    override fun launch(topic: String, puzzleConfig: PuzzleConfig): Boolean {
        //check that the task is not already running. Make call to repository for the answer
        if (!createTopic(topic)) {
            return false
        }

        //restrict puzzle rotations
        restrictPuzzleRotations(puzzleConfig)

        //publish topic for consumers to subscribe
        sendNewTopic(topic, puzzleConfig)

        //initiate puzzle assembling
        sendPuzzles(topic, PuzzleState(puzzleConfig))
        return true
    }

    private fun restrictPuzzleRotations(puzzleConfig: PuzzleConfig) {
        // if detail looks identically with two different rotations, consider rotations equivalent.
        puzzleConfig.puzzleDetails.forEach { detail ->
            IntStream.range(0, 4)
                .mapToObj { rotation -> DetailWithRotation(detail, rotation) }
                .forEach { detailWithRotation -> detail.allowedRotations.add(detailWithRotation.rotation) }
        }

        //take the most diverse detail and fix it
        val mostDiverseDetail =
            puzzleConfig.puzzleDetails.stream().max { d1, d2 -> max(d1.detailDiversity, d2.detailDiversity) }.get()
        mostDiverseDetail.allowedRotations = Collections.singletonList(0)
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
        val taskVO = NewTaskDto(topic, mapper.puzzleConfigToPuzzleConfigDto(puzzleConfig))
        val taskTopicsFuture = kafkaTaskTopicsProducer.send(taskTopicsTopic, taskVO)
        taskTopicsFuture.addCallback({
            log.info { "message sent to $taskTopicsTopic" }
        }, {
            log.info { "Exception in sending to kafka topic $taskTopicsTopic $it" }
        })
    }

    private fun sendPuzzles(topic: String, puzzleState: PuzzleState) {
        val puzzleFuture = kafkaProducer.send(topic, mapper.puzzleStateToPuzzleStateDto(puzzleState))
        puzzleFuture.addCallback({
            log.info { "message sent to $topic" }
        }, {
            log.info { "Exception in sending to kafka $it" }
        })
    }

}
