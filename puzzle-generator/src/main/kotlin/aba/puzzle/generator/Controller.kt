package aba.puzzle.generator

import aba.puzzle.domain.rest.mapstruct.dto.PuzzleConfigDto
import aba.puzzle.domain.rest.mapstruct.dto.PuzzleMapDto
import aba.puzzle.domain.rest.mapstruct.mapper.MapStructMapper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.*
import reactor.core.publisher.Mono

@RestController
class Controller(
    @Autowired private val puzzleGeneratorService: PuzzleGeneratorService,
    @Autowired private val mapper: MapStructMapper
) {
    @PostMapping("/generate", consumes = [MediaType.APPLICATION_JSON_VALUE], produces = [MediaType.APPLICATION_JSON_VALUE])
    fun generate(@RequestBody puzzleMapDto: PuzzleMapDto): Mono<PuzzleConfigDto> {
        return Mono.just(mapper.puzzleMapDtoToPuzzleMap(puzzleMapDto)).map {
            puzzleGeneratorService.generate(it)
        }.map { mapper.puzzleConfigToPuzzleConfigDto(it) }
    }
}