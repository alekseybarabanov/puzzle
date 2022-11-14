package aba.puzzle.service

import aba.puzzle.domain.DetailWithRotation
import aba.puzzle.domain.PuzzleConfig
import aba.puzzle.domain.PuzzleState
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
    fun launch(puzzleConfig: PuzzleConfig)
}

@Component
class LaunchServiceImpl(
    @Value("\${app.puzzleStateTopic}") val stateTopic: String,
    @Autowired val kafkaProducer: KafkaTemplate<String, PuzzleStateDto>,
    @Autowired val kafkaAdmin: KafkaAdmin,
    @Autowired private val mapper: MapStructMapper
) : LaunchService {
    private val log: Log = LogFactory.getLog(LaunchServiceImpl::class.java)

    override fun launch(puzzleConfig: PuzzleConfig) {
        //restrict puzzle rotations
        restrictPuzzleRotations(puzzleConfig)

        //initiate puzzle assembling
        sendPuzzles(stateTopic, PuzzleState(puzzleConfig))
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

    private fun sendPuzzles(topic: String, puzzleState: PuzzleState) {
        val puzzleFuture = kafkaProducer.send(topic, mapper.puzzleStateToPuzzleStateDto(puzzleState))
        puzzleFuture.addCallback({
            log.info { "message sent to $topic" }
        }, {
            log.info { "Exception in sending to kafka $it" }
        })
    }

}
