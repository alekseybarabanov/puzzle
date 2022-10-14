package aba.puzzle.assembler

import aba.puzzle.domain.*
import org.springframework.stereotype.Component

interface PuzzleAssembler {
    fun addDetail(
        currentState: PuzzleState,
        detail: Detail,
        puzzleField: PuzzleField,
        puzzleConfig: PuzzleConfig
    ): PuzzleStatus
}

@Component
class PuzzleAssemblerImpl : PuzzleAssembler {
    override fun addDetail(
        currentState: PuzzleState,
        detail: Detail,
        puzzleField: PuzzleField,
        puzzleConfig: PuzzleConfig
    ): PuzzleStatus {
        val completed = mutableListOf<PuzzleState>()
        val incomplete = mutableListOf<PuzzleState>()
        for (rotation in 0..3) {
            val detailWithRotation = DetailWithRotation(detail, rotation)
            val candidateState = addDetailToPuzzle(currentState, puzzleField, detailWithRotation)
            if (checkPuzzleState(candidateState, puzzleField, detailWithRotation)) {
                if (isCompleted(candidateState, puzzleConfig.puzzleMap)) {
                    completed.add(candidateState)
                } else {
                    incomplete.add(candidateState)
                }
            }
        }
        return PuzzleStatus(completed, incomplete)
    }

    private fun addDetailToPuzzle(
        currentState: PuzzleState,
        puzzleField: PuzzleField,
        detailWithRotation: DetailWithRotation
    ): PuzzleState {
        return PuzzleState(currentState, puzzleField, detailWithRotation)
    }

    private fun checkPuzzleState(
        candidatePuzzleState: PuzzleState,
        puzzleField: PuzzleField,
        detailWithRotation: DetailWithRotation
    ): Boolean {
        return candidatePuzzleState.positionedDetails.all {
            val that = it.key
            if (getPuzzleFieldDistance(that, puzzleField) == 1) {
                if (that.shiftX < puzzleField.shiftX) {
                    //check right side for puzzle from "it" and left side of detailWithRotation
                    isMatch(it.value.getBallSide(DetailSide.right), detailWithRotation.getBallSide(DetailSide.left))
                } else if (that.shiftX > puzzleField.shiftX) {
                    //check left side for puzzle from "it" and right side of detailWithRotation
                    isMatch(it.value.getBallSide(DetailSide.left), detailWithRotation.getBallSide(DetailSide.right))
                } else if (that.shiftY < puzzleField.shiftY) {
                    //check upper side for puzzle from "it" and bottom side of detailWithRotation
                    isMatch(it.value.getBallSide(DetailSide.down), detailWithRotation.getBallSide(DetailSide.up))
                } else {
                    //check bottom side for puzzle from "it" and upper side of detailWithRotation
                    isMatch(it.value.getBallSide(DetailSide.up), detailWithRotation.getBallSide(DetailSide.down))
                }
            } else {
                true
            }
        }
    }

    private fun isMatch(side1: BallSide, side2: BallSide): Boolean {
        return side1.color == side2.color && BallPart.isFull(side1.ballPart, side2.ballPart)
    }

    private fun getPuzzleFieldDistance(puzzleField1: PuzzleField, puzzleField2: PuzzleField): Int {
        return Math.abs(puzzleField1.shiftX - puzzleField2.shiftX) + Math.abs(puzzleField1.shiftY - puzzleField2.shiftY)
    }

    private fun isCompleted(puzzleState: PuzzleState, puzzleMap: PuzzleMap): Boolean =
         puzzleMap.puzzleFields.size == puzzleState.positionedDetails.size

}

data class PuzzleStatus(
    val completed: List<PuzzleState>,
    val incompleted: List<PuzzleState>
)