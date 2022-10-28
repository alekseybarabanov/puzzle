package aba.puzzle.domain.dto

import aba.puzzle.domain.PuzzleField
import aba.puzzle.domain.PuzzleMap

data class PuzzleMapDto(
    val puzzleFields: List<PuzzleFieldDto> = listOf()
) {
    companion object {
        fun fromPuzzleMap(puzzleMap: PuzzleMap): PuzzleMapDto {
            return PuzzleMapDto(puzzleFields = puzzleMap.puzzleFields.map { PuzzleFieldDto.fromPuzzleField(it) })
        }
        fun toPuzzleMap(puzzleMapDto: PuzzleMapDto): PuzzleMap {
            return PuzzleMap(puzzleFields = puzzleMapDto.puzzleFields.map { PuzzleFieldDto.toPuzzleField(it) })
        }
    }
}

data class PuzzleFieldDto(
    val shiftX: Int = 0,
    val shiftY: Int = 0
) {
    companion object {
        fun fromPuzzleField(puzzleField: PuzzleField): PuzzleFieldDto {
            return PuzzleFieldDto(shiftX = puzzleField.shiftX, shiftY = puzzleField.shiftY)
        }
        fun toPuzzleField(puzzleFieldDto: PuzzleFieldDto): PuzzleField {
            return PuzzleField(shiftX=puzzleFieldDto.shiftX, shiftY = puzzleFieldDto.shiftY)
        }
    }
}
