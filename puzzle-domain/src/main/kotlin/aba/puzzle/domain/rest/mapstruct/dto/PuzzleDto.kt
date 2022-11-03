package aba.puzzle.domain.rest.mapstruct.dto

import aba.puzzle.domain.PuzzleField
import aba.puzzle.domain.PuzzleMap

data class PuzzleMapDto(
    val puzzleFields: List<PuzzleFieldDto> = mutableListOf()
)

data class PuzzleFieldDto(
    var shiftX: Int = 0,
    var shiftY: Int = 0
)
