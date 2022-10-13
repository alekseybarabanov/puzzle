package aba.puzzle.domain.dto

import aba.puzzle.domain.PuzzleField
import aba.puzzle.domain.PuzzleMap

data class PuzzleMapVO(
    val puzzleFields: List<PuzzleFieldVO> = listOf()
) {
    companion object {
        fun fromPuzzleMap(puzzleMap: PuzzleMap): PuzzleMapVO {
            return PuzzleMapVO(puzzleFields = puzzleMap.puzzleFields.map { PuzzleFieldVO.fromPuzzleField(it) })
        }
        fun toPuzzleMap(puzzleMapVO: PuzzleMapVO): PuzzleMap {
            return PuzzleMap(puzzleFields = puzzleMapVO.puzzleFields.map { PuzzleFieldVO.toPuzzleField(it) })
        }
    }
}

data class PuzzleFieldVO(
    val shiftX: Int = 0,
    val shiftY: Int = 0
) {
    companion object {
        fun fromPuzzleField(puzzleField: PuzzleField): PuzzleFieldVO {
            return PuzzleFieldVO(shiftX = puzzleField.shiftX, shiftY = puzzleField.shiftY)
        }
        fun toPuzzleField(puzzleFieldVO: PuzzleFieldVO): PuzzleField {
            return PuzzleField(shiftX=puzzleFieldVO.shiftX, shiftY = puzzleFieldVO.shiftY)
        }
    }
}
