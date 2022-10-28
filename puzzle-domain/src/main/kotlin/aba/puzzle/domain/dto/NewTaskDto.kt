package aba.puzzle.domain.dto

data class NewTaskDto (
    var topic: String = "",
    var puzzleConfig: PuzzleConfigDto = PuzzleConfigDto()
)