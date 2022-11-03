package aba.puzzle.domain

data class PuzzleMap(
    val puzzleFields: List<PuzzleField>
)

data class PuzzleField(
    var shiftX: Int = 0,
    var shiftY: Int = 0
)
