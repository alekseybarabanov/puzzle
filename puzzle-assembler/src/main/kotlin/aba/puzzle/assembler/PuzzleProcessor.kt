package aba.puzzle.assembler

import aba.puzzle.domain.PuzzleConfig
import aba.puzzle.domain.PuzzleState
import aba.puzzle.domain.rest.mapstruct.dto.PuzzleConfigDto
import aba.puzzle.domain.rest.mapstruct.dto.PuzzleStateDto
import aba.puzzle.domain.rest.mapstruct.mapper.MapStructMapper
import org.apache.commons.logging.Log
import org.apache.commons.logging.LogFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.stereotype.Component

interface PuzzleProcessor {
    fun process(topic: String, currentPuzzleState: PuzzleState, puzzleConfig: PuzzleConfig)
}

@Component
class PuzzleProcessorImpl(
    @Autowired val puzzleAssembler: PuzzleAssembler,
    @Autowired val kafkaProducer: KafkaTemplate<String, Any>,
    @Autowired private val mapper: MapStructMapper
) : PuzzleProcessor {
    private val log: Log = LogFactory.getLog(PuzzleProcessorImpl::class.java)

    override fun process(topic: String, currentPuzzleState: PuzzleState, puzzleConfig: PuzzleConfig) {
        puzzleConfig.puzzleMap.puzzleFields.find {
            !currentPuzzleState.positionedDetails.containsKey(it)
        }?.let { nextPuzzleField ->
            val usedIds = currentPuzzleState.positionedDetails.map { it.value.detail.id }
            puzzleConfig.puzzleDetails.filter { !usedIds.contains(it.id) }.forEach {
                with(puzzleAssembler.addDetail(currentPuzzleState, it, nextPuzzleField, puzzleConfig)) {
                    this.incompleted.forEach { puzzleState ->
                        sendPuzzles(topic, puzzleState)
                    }
                    this.completed.forEach { puzzleState ->
                        sendCompletedPuzzle("$topic-assembled", puzzleState, puzzleConfig)
                        log.info { "Completed puzzle: $it" }
                    }
                }
            }
        }
    }

    private fun sendPuzzles(topic: String, puzzleState: PuzzleState) {
        val puzzleFuture = kafkaProducer.send(topic, mapper.puzzleStateToPuzzleStateDto(puzzleState))
        puzzleFuture.addCallback({
            log.info { "message sent to $topic" }
        }, {
            log.info { "Exception in sending to kafka $it" }
        })
    }

    private fun sendCompletedPuzzle(topic: String, puzzleState: PuzzleState, config: PuzzleConfig) {
        val puzzleFuture = kafkaProducer.send(
            topic, PuzzleCompleted(
                puzzleState = mapper.puzzleStateToPuzzleStateDto(puzzleState),
                puzzleConfigDto = mapper.puzzleConfigToPuzzleConfigDto(config)
            )
        )
        puzzleFuture.addCallback({
            log.info { "message sent to $topic" }
        }, {
            log.info { "Exception in sending to kafka $it" }
        })
    }
}

data class PuzzleCompleted(
    val puzzleState: PuzzleStateDto,
    val puzzleConfigDto: PuzzleConfigDto
)