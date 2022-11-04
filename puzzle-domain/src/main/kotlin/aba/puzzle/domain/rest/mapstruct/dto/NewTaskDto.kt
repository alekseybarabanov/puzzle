package aba.puzzle.domain.rest.mapstruct.dto

data class NewTaskDto(
    var topic: String = "",
    var puzzleConfig: PuzzleConfigDto = PuzzleConfigDto()
)