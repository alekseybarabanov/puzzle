package aba.puzzle.domain.rest.mapstruct.dto

data class PuzzleMapDto(
    val puzzleFields: List<PuzzleFieldDto> = mutableListOf()
)

data class PuzzleFieldDto(
    var id: Int? = null,
    var shiftX: Int = 0,
    var shiftY: Int = 0
)
