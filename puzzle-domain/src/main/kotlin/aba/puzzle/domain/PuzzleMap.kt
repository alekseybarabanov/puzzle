package aba.puzzle.domain

data class PuzzleMap(
    val puzzleFields: List<PuzzleField>
)

data class PuzzleField(
    val shiftX: Int = 0,
    val shiftY: Int = 0
)
