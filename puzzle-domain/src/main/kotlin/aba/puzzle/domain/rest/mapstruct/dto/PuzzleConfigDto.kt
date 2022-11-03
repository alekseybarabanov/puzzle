package aba.puzzle.domain.rest.mapstruct.dto

import aba.puzzle.domain.PuzzleConfig

data class PuzzleConfigDto(
    var puzzleMap: PuzzleMapDto = PuzzleMapDto(),
    var puzzleDetails: Collection<PuzzleDetailDto> = mutableListOf()
)