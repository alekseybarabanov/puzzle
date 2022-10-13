package aba.kover.service

import aba.kover.domain.*
import org.springframework.stereotype.Component

interface KoverStateService {
    fun addDetail(currentState: KoverState, detail: Detail, position: KoverPosition): List<KoverState>
}

@Component
class KoverStateServiceImpl : KoverStateService {
    override fun addDetail(currentState: KoverState, detail: Detail, position: KoverPosition): List<KoverState> {
        val dependentSides = getDependentSides(currentState, position)
        val result: MutableList<KoverState> = ArrayList()
        for (rotation in 0..3) {
            val detailWithRotation = DetailWithRotation(detail, rotation)
            if (detailMatcher.isMatch(dependentSides, detailWithRotation)) {
                result.add(KoverState(currentState, position, detailWithRotation))
            }
        }
        return result
    }

    private fun getDependentSides(currentState: KoverState, position: KoverPosition): List<SideWithPosition> {
        val dependentPositions = KoverPosition.getDependentPositions(position)
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
        private val positions: KoverPosition,
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