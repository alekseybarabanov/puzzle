package aba.puzzle.domain.rest.mapstruct.dto

data class PuzzleConfigDto(
    var id: Int? = null,
    var extId: String? = null,
    var puzzleMap: PuzzleMapDto = PuzzleMapDto(),
    var puzzleDetails: Collection<PuzzleDetailDto> = mutableListOf()
)