package aba.puzzle.generator

import aba.puzzle.domain.*
import mu.KotlinLogging
import org.springframework.stereotype.Component
import java.lang.Integer.max

interface PuzzleGeneratorService {
    fun generate(puzzleMap: PuzzleMap): PuzzleConfig
}

@Component
class PuzzleGeneratorServiceImpl : PuzzleGeneratorService {
    private val log = KotlinLogging.logger {}

    override fun generate(puzzleMap: PuzzleMap): PuzzleConfig {
        val draftPuzzle = createDraft(puzzleMap)
        puzzleMap.puzzleFields.forEach {
            fillSomeDetail(it, draftPuzzle)
        }
        return PuzzleConfig(puzzleMap, draftPuzzle.positionedDetails.values.map { it.detail })
    }

    private fun fillSomeDetail(nextPuzzleField: PuzzleField, draftPuzzle: PuzzleState) {
        val preparedDetails = draftPuzzle.positionedDetails.filter { !it.value.detail.isDraft }
        val currentDetail = draftPuzzle.positionedDetails.getValue(nextPuzzleField)
        preparedDetails.forEach { that ->
            if (getPuzzleFieldDistance(that.key, nextPuzzleField) == 1) {
                val detail = that.value.detail
                if (that.key.shiftX < nextPuzzleField.shiftX) {
                    //check right side for puzzle from "it" and left side of detailWithRotation
                    currentDetail.detail.setSide(DetailSide.left, detail.getBallSide(DetailSide.right).complement)
                } else if (that.key.shiftX > nextPuzzleField.shiftX) {
                    //check left side for puzzle from "it" and right side of detailWithRotation
                    currentDetail.detail.setSide(DetailSide.right, detail.getBallSide(DetailSide.left).complement)
                } else if (that.key.shiftY < nextPuzzleField.shiftY) {
                    //check upper side for puzzle from "it" and bottom side of detailWithRotation
                    currentDetail.detail.setSide(DetailSide.up, detail.getBallSide(DetailSide.down).complement)
                } else {
                    //check bottom side for puzzle from "it" and upper side of detailWithRotation
                    currentDetail.detail.setSide(DetailSide.down, detail.getBallSide(DetailSide.up).complement)
                }
            }
        }
        DetailSide.values().filter { !currentDetail.detail.filledSides.contains(it) }.forEach {
            currentDetail.detail.setSide(it, randomBallSide())
        }
    }

    private fun randomBallSide(): BallSide {
        return BallSide(Color.values().random(), BallPart.values().random())
    }

    private fun createDraft(puzzleMap: PuzzleMap): PuzzleState {
        val maxDimension =
            puzzleMap.puzzleFields.stream().map { max(it.shiftX, it.shiftX)+1 }.max(Integer::compare).orElse(0)
        return puzzleMap.puzzleFields.stream().map { puzzleField ->
            puzzleField to Detail.newDraftDetail(puzzleField.shiftX * maxDimension + puzzleField.shiftY).also {
                log.info { "PuzzleField: $puzzleField, id: ${it.id}" }
            }
        }.collect({
            PuzzleState()
        }, { state, pair ->
            state.positionedDetails[pair.first] = DetailWithRotation(pair.second, 0)
        }, { state1, state2 ->
            state1.positionedDetails.putAll(state2.positionedDetails)
        })
    }

    private fun getPuzzleFieldDistance(puzzleField1: PuzzleField, puzzleField2: PuzzleField): Int {
        return Math.abs(puzzleField1.shiftX - puzzleField2.shiftX) + Math.abs(puzzleField1.shiftY - puzzleField2.shiftY)
    }

}