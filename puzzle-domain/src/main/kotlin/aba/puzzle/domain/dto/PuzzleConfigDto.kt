package aba.puzzle.domain.dto

import aba.puzzle.domain.PuzzleConfig

data class PuzzleConfigDto(
    val puzzleMap: PuzzleMapDto = PuzzleMapDto(),
    val puzzleDetails: Collection<PuzzleDetailDto> = listOf()
) {
    companion object {
        fun fromPuzzleConfig(puzzleConfig: PuzzleConfig): PuzzleConfigDto {
            return PuzzleConfigDto(puzzleMap = PuzzleMapDto.fromPuzzleMap(puzzleConfig.puzzleMap),
                puzzleDetails = puzzleConfig.puzzleDetails.map { PuzzleDetailDto.fromDetail(it) })
        }

        fun toPuzzleConfig(puzzleConfigDto: PuzzleConfigDto): PuzzleConfig {
            return PuzzleConfig(puzzleMap = PuzzleMapDto.toPuzzleMap(puzzleConfigDto.puzzleMap),
                puzzleDetails = puzzleConfigDto.puzzleDetails.map { PuzzleDetailDto.toDetail(it) })
        }
    }
}