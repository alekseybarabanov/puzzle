package aba.puzzle.assembler

import aba.puzzle.domain.PuzzleState
import aba.puzzle.domain.rest.mapstruct.mapper.MapStructMapper
import org.apache.commons.logging.Log
import org.apache.commons.logging.LogFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.stereotype.Component

interface PuzzleProcessor {
    fun process(currentPuzzleState: PuzzleState)
}

@Component
class PuzzleProcessorImpl(
    @Autowired val puzzleAssembler: PuzzleAssembler,
    @Autowired val kafkaProducer: KafkaTemplate<String, Any>,
    @Autowired private val mapper: MapStructMapper,
    @Value("\${app.kafka.puzzleStateTopic}") private val topic: String
) : PuzzleProcessor {
    private val log: Log = LogFactory.getLog(PuzzleProcessorImpl::class.java)

    override fun process(currentPuzzleState: PuzzleState) {
        val puzzleConfig = currentPuzzleState.puzzleConfig
        puzzleConfig.puzzleMap.puzzleFields.find {
            !currentPuzzleState.positionedDetails.containsKey(it)
        }?.let { nextPuzzleField ->
            val usedIds = currentPuzzleState.positionedDetails.map { it.value.detail.id }
            puzzleConfig.puzzleDetails.filter { !usedIds.contains(it.id) }.forEach {
                with(puzzleAssembler.addDetail(currentPuzzleState, it, nextPuzzleField, puzzleConfig)) {
                    this.forEach { puzzleState ->
                        sendPuzzles(puzzleState)
                        if (puzzleState.isCompleted) {
                            log.info("Puzzle solution: " + puzzleState.toString())
                        }
                    }
                }
            }
        }
    }

    private fun sendPuzzles(puzzleState: PuzzleState) {
        val puzzleFuture = kafkaProducer.send(topic, mapper.puzzleStateToPuzzleStateDto(puzzleState))
        puzzleFuture.addCallback({
            log.debug("message ${it.toString()} sent to $topic")
            if (puzzleState.isCompleted) {
                log.info(
                    "Done: meta: ${it?.recordMetadata?.offset()}, completed: ${
                        mapper.puzzleStateToPuzzleStateDto(
                            puzzleState
                        ).isCompleted
                    }"
                )
            }
        }, {
            log.error("Exception in sending to kafka $it")
        })
    }
}
