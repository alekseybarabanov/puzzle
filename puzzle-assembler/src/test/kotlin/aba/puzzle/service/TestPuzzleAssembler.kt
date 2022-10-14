package aba.puzzle.service

import aba.puzzle.assembler.PuzzleAssemblerImpl
import aba.puzzle.domain.*
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import java.lang.reflect.Method

class TestPuzzleAssembler {
    @Test
    fun testIsCompletedOK() {
        val ps = PuzzleState()
        ps.positionedDetails = mapOf(
            PuzzleField(0, 0) to DetailWithRotation(null, 0),
            PuzzleField(0, 1) to DetailWithRotation(null, 0),
        )
        val puzzleMap = PuzzleMap(
            listOf(
                PuzzleField(0, 0),
                PuzzleField(0, 1)
            )
        )
        val returnValue = getIsCompletedMethod().invoke(PuzzleAssemblerImpl(), ps, puzzleMap) as Boolean
        assertTrue(returnValue)
    }

    @Test
    fun testIsCompletedFail() {
        val ps = PuzzleState()
        ps.positionedDetails = mapOf(
            PuzzleField(0, 0) to DetailWithRotation(null, 0),
            PuzzleField(0, 1) to DetailWithRotation(null, 0),
        )
        val puzzleMap = PuzzleMap(
            listOf(
                PuzzleField(0, 0),
                PuzzleField(0, 1),
                PuzzleField(1, 1)
            )
        )
        val returnValue = getIsCompletedMethod().invoke(PuzzleAssemblerImpl(), ps, puzzleMap) as Boolean
        assertFalse(returnValue)
    }

    @Test
    fun testPuzzleFieldDistance() {
        val getPuzzleFieldDistanceMethod: Method = PuzzleAssemblerImpl::class.java.getDeclaredMethod(
            "getPuzzleFieldDistance",
            PuzzleField::class.java,
            PuzzleField::class.java
        )
        getPuzzleFieldDistanceMethod.isAccessible = true

        val dist1 =
            getPuzzleFieldDistanceMethod.invoke(PuzzleAssemblerImpl(), PuzzleField(0, 0), PuzzleField(0, 1)) as Int
        assertEquals(dist1, 1)
        val dist2 =
            getPuzzleFieldDistanceMethod.invoke(PuzzleAssemblerImpl(), PuzzleField(1, 0), PuzzleField(0, 1)) as Int
        assertEquals(dist2, 2)
        val dist3 =
            getPuzzleFieldDistanceMethod.invoke(PuzzleAssemblerImpl(), PuzzleField(1, 1), PuzzleField(1, 1)) as Int
        assertEquals(dist3, 0)
    }

    @Test
    fun testIsMatch() {
        val isMatchMethod: Method =
            PuzzleAssemblerImpl::class.java.getDeclaredMethod("isMatch", BallSide::class.java, BallSide::class.java)
        isMatchMethod.isAccessible = true
        val match1 = isMatchMethod.invoke(
            PuzzleAssemblerImpl(),
            BallSide(Color.green, BallPart.one_third),
            BallSide(Color.green, BallPart.one_third)
        ) as Boolean
        assertFalse(match1)
        val match2 = isMatchMethod.invoke(
            PuzzleAssemblerImpl(),
            BallSide(Color.green, BallPart.one_third),
            BallSide(Color.green, BallPart.two_thirds)
        ) as Boolean
        assertTrue(match2)
        val match3 = isMatchMethod.invoke(
            PuzzleAssemblerImpl(),
            BallSide(Color.green, BallPart.one_third),
            BallSide(Color.yellow, BallPart.two_thirds)
        ) as Boolean
        assertFalse(match3)
    }

    @Test
    fun testCheckPuzzleState() {
        val checkPuzzleStateMethod: Method = PuzzleAssemblerImpl::class.java.getDeclaredMethod(
            "checkPuzzleState",
            PuzzleState::class.java,
            PuzzleField::class.java,
            DetailWithRotation::class.java
        )
        checkPuzzleStateMethod.isAccessible = true

        val detail00 = DetailWithRotation(
            Detail(
                0,
                BallSide(Color.white, BallPart.one_third),
                BallSide(Color.yellow, BallPart.one_third),
                BallSide(Color.green, BallPart.one_third),
                BallSide(Color.red, BallPart.one_third)
            ), 0
        )
        val detail11 = DetailWithRotation(
            Detail(
                0,
                BallSide(Color.white, BallPart.one_third),
                BallSide(Color.yellow, BallPart.one_third),
                BallSide(Color.green, BallPart.two_thirds),
                BallSide(Color.red, BallPart.one_third)
            ), 1
        )
        val check = { rotation: Int ->
            val detail01 = DetailWithRotation(
                Detail(
                    0,
                    BallSide(Color.green, BallPart.two_thirds),
                    BallSide(Color.yellow, BallPart.two_thirds),
                    BallSide(Color.green, BallPart.two_thirds),
                    BallSide(Color.green, BallPart.one_third)
                ), rotation
            )
            val candidate1 = PuzzleState(
                mapOf(
                    PuzzleField(0, 0) to detail00,
                    PuzzleField(0, 1) to detail01,
                    PuzzleField(1, 1) to detail11
                )
            )
            checkPuzzleStateMethod.invoke(PuzzleAssemblerImpl(), candidate1, PuzzleField(0, 1), detail01) as Boolean
        }
        assertFalse(check(0))
        assertFalse(check(1))
        assertTrue(check(2))
        assertFalse(check(3))
    }

    @Test
    fun testAddDetailToPuzzle() {
        val addDetailToPuzzleMethod: Method = PuzzleAssemblerImpl::class.java.getDeclaredMethod(
            "addDetailToPuzzle",
            PuzzleState::class.java,
            PuzzleField::class.java,
            DetailWithRotation::class.java
        )
        addDetailToPuzzleMethod.isAccessible = true

        val detail00 = DetailWithRotation(
            Detail(
                0,
                BallSide(Color.white, BallPart.one_third),
                BallSide(Color.yellow, BallPart.one_third),
                BallSide(Color.green, BallPart.one_third),
                BallSide(Color.red, BallPart.one_third)
            ), 0
        )
        val detail11 = DetailWithRotation(
            Detail(
                1,
                BallSide(Color.white, BallPart.one_third),
                BallSide(Color.yellow, BallPart.one_third),
                BallSide(Color.green, BallPart.two_thirds),
                BallSide(Color.red, BallPart.one_third)
            ), 1
        )
        val detail01 = DetailWithRotation(
            Detail(
                2,
                BallSide(Color.green, BallPart.two_thirds),
                BallSide(Color.yellow, BallPart.two_thirds),
                BallSide(Color.green, BallPart.two_thirds),
                BallSide(Color.green, BallPart.one_third)
            ), 0
        )
        val curState = PuzzleState(
            mapOf(
                PuzzleField(0, 0) to detail00,
                PuzzleField(0, 1) to detail01
            )
        )
        val candidate = addDetailToPuzzleMethod.invoke(PuzzleAssemblerImpl(), curState, PuzzleField(1, 1), detail11) as PuzzleState
        assertEquals(candidate.positionedDetails.size, 3)
        assertEquals(candidate.positionedDetails[PuzzleField(1,1)]!!.detail.id, 1)
    }

    private fun getIsCompletedMethod(): Method {
        val isCompletedMethod: Method = PuzzleAssemblerImpl::class.java.getDeclaredMethod(
            "isCompleted",
            PuzzleState::class.java,
            PuzzleMap::class.java
        )
        isCompletedMethod.isAccessible = true
        return isCompletedMethod
    }
}