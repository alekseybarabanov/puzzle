package aba.puzzle.domain

data class PuzzleConfig(
    var id: Int? = null,
    var extId: String? = null,
    var puzzleMap: PuzzleMap,
    var puzzleDetails: Collection<Detail>
)
