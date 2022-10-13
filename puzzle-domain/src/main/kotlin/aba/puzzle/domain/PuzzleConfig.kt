package aba.puzzle.domain

data class PuzzleConfig(
    val puzzleMap: PuzzleMap,
    val puzzleDetails: Collection<Detail>
)
