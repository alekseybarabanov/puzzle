package aba.puzzle.generator

import aba.puzzle.domain.dto.PuzzleConfigDto
import aba.puzzle.domain.dto.PuzzleMapDto
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.*
import reactor.core.publisher.Mono

@RestController
class Controller(
    @Autowired private val puzzleGeneratorService: PuzzleGeneratorService
) {
    @PostMapping("/generate", consumes = [MediaType.APPLICATION_JSON_VALUE], produces = [MediaType.APPLICATION_JSON_VALUE])
    fun generate(@RequestBody puzzleMapDto: PuzzleMapDto): Mono<PuzzleConfigDto> {
        return Mono.just(PuzzleMapDto.toPuzzleMap(puzzleMapDto)).map {
            puzzleGeneratorService.generate(it)
        }.map { PuzzleConfigDto.fromPuzzleConfig(it) }
    }
}