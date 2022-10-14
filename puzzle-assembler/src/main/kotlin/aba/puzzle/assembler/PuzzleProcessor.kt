package aba.puzzle.assembler

import aba.puzzle.domain.PuzzleConfig
import aba.puzzle.domain.PuzzleState
import aba.puzzle.domain.dto.PuzzleConfigVO
import aba.puzzle.domain.dto.PuzzleStateVO
import mu.KotlinLogging
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
) : PuzzleProcessor {
    private val log = KotlinLogging.logger {}

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
                    this.completed.forEach {puzzleState->
                        sendCompletedPuzzle("$topic-assembled", puzzleState, puzzleConfig)
                        log.info { "Completed puzzle: $it" }
                    }
                }
            }
        }
    }

    private fun sendPuzzles(topic: String, puzzleState: PuzzleState) {
        val puzzleFuture = kafkaProducer.send(topic, PuzzleStateVO.fromPuzzleState(puzzleState))
        puzzleFuture.addCallback({
            log.info {"message sent to $topic"}
        }, {
            log.info {"Exception in sending to kafka $it"}
        })
    }
    private fun sendCompletedPuzzle(topic: String, puzzleState: PuzzleState, config: PuzzleConfig) {
        val puzzleFuture = kafkaProducer.send(topic, PuzzleCompleted(puzzleState = PuzzleStateVO.fromPuzzleState(puzzleState),
            puzzleConfigVO = PuzzleConfigVO.fromPuzzleConfig(config)))
        puzzleFuture.addCallback({
            log.info {"message sent to $topic"}
        }, {
            log.info {"Exception in sending to kafka $it"}
        })
    }
}

data class PuzzleCompleted(
    val puzzleState: PuzzleStateVO,
    val puzzleConfigVO: PuzzleConfigVO
)