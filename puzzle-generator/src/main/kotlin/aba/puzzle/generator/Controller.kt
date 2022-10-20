package aba.puzzle.generator

import aba.puzzle.domain.dto.PuzzleConfigVO
import aba.puzzle.domain.dto.PuzzleMapVO
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.*
import reactor.core.publisher.Mono

@RestController
class Controller(
    @Autowired private val puzzleGeneratorService: PuzzleGeneratorService
) {
    @PostMapping("/generate", consumes = [MediaType.APPLICATION_JSON_VALUE], produces = [MediaType.APPLICATION_JSON_VALUE])
    fun generate(@RequestBody puzzleMapVO: PuzzleMapVO): Mono<PuzzleConfigVO> {
        return Mono.just(PuzzleMapVO.toPuzzleMap(puzzleMapVO)).map {
            puzzleGeneratorService.generate(it)
        }.map { PuzzleConfigVO.fromPuzzleConfig(it) }
    }
}