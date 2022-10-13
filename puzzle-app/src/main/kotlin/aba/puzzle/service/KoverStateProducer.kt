package aba.puzzle.service

import aba.puzzle.domain.*
import org.springframework.stereotype.Component

interface PuzzleStateService {
    fun addDetail(currentState: PuzzleState, detail: Detail, position: PuzzlePosition): List<PuzzleState>
}

@Component
class PuzzleStateServiceImpl : PuzzleStateService {
    override fun addDetail(currentState: PuzzleState, detail: Detail, position: PuzzlePosition): List<PuzzleState> {
        val dependentSides = getDependentSides(currentState, position)
        val result: MutableList<PuzzleState> = ArrayList()
        for (rotation in 0..3) {
            val detailWithRotation = DetailWithRotation(detail, rotation)
            if (detailMatcher.isMatch(dependentSides, detailWithRotation)) {
                result.add(PuzzleState(currentState, position, detailWithRotation))
            }
        }
        return result
    }

    private fun getDependentSides(currentState: PuzzleState, position: PuzzlePosition): List<SideWithPosition> {
        val dependentPositions = PuzzlePosition.getDependentPositions(position)
        val result: MutableList<SideWithPosition> = ArrayList()
        dependentPositions.forEach { (key, value) ->
            val detailWithRotation = currentState.positionedDetails[key]
            if (detailWithRotation != null) {
                result.add(SideWithPosition(detailWithRotation.getBallSide(value), key, value))
            }
        }
        return result
    }

    private class DetailMatcher {
        private val ballSidesMatcher = BallSidesMatcher()
        fun isMatch(restrictions: List<SideWithPosition>, detailWithRotation: DetailWithRotation): Boolean {
            for (sideWithPosition in restrictions) {
                val detailWithRotationBallSide =
                    detailWithRotation.getBallSide(DetailSide.getMatchingSide(sideWithPosition.detailSide))
                if (!ballSidesMatcher.isMatch(sideWithPosition.ballSide, detailWithRotationBallSide)) {
                    return false
                }
            }
            return true
        }
    }

    private class SideWithPosition constructor(
        val ballSide: BallSide,
        private val positions: PuzzlePosition,
        val detailSide: DetailSide
    )

    private class BallSidesMatcher {
        fun isMatch(side1: BallSide, side2: BallSide): Boolean {
            return if (side1.color == side2.color && BallPart.isFull(side1.ballPart, side2.ballPart)) {
                true
            } else {
                false
            }
        }
    }

    companion object {
        private val detailMatcher = DetailMatcher()
    }

}