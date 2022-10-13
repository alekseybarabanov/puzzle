package aba.puzzle.domain.dto

import aba.puzzle.domain.PuzzleConfig

data class PuzzleConfigVO(
    val puzzleMap: PuzzleMapVO = PuzzleMapVO(),
    val puzzleDetails: Collection<DetailVO> = listOf()
) {
    companion object {
        fun fromPuzzleConfig(puzzleConfig: PuzzleConfig): PuzzleConfigVO {
            return PuzzleConfigVO(puzzleMap = PuzzleMapVO.fromPuzzleMap(puzzleConfig.puzzleMap),
                puzzleDetails = puzzleConfig.puzzleDetails.map { DetailVO.fromDetail(it) })
        }

        fun toPuzzleConfig(puzzleConfigVO: PuzzleConfigVO): PuzzleConfig {
            return PuzzleConfig(puzzleMap = PuzzleMapVO.toPuzzleMap(puzzleConfigVO.puzzleMap),
                puzzleDetails = puzzleConfigVO.puzzleDetails.map { DetailVO.toDetail(it) })
        }
    }
}