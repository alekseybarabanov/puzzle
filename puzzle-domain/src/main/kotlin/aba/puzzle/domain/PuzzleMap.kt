package aba.puzzle.domain

data class PuzzleMap(
    val puzzleFields: List<PuzzleField>
)

data class PuzzleField(
    var id: Int? = null,
    var shiftX: Int = 0,
    var shiftY: Int = 0
)
