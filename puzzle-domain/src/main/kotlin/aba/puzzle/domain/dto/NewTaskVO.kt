package aba.puzzle.domain.dto

data class NewTaskVO (
    var topic: String = "",
    var puzzleConfig: PuzzleConfigVO = PuzzleConfigVO()
)