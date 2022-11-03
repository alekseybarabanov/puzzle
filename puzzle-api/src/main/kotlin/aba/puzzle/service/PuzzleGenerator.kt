package aba.puzzle.service

import aba.puzzle.domain.PuzzleConfig
import aba.puzzle.domain.PuzzleField
import aba.puzzle.domain.PuzzleMap
import aba.puzzle.domain.rest.mapstruct.dto.PuzzleConfigDto
import aba.puzzle.domain.rest.mapstruct.dto.PuzzleMapDto
import aba.puzzle.domain.rest.mapstruct.mapper.MapStructMapper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.MediaType
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.BodyInserters
import org.springframework.web.reactive.function.client.WebClient
import reactor.core.publisher.Mono

interface PuzzleGenerator {
    fun generatePuzzleConfig(sizeX: Int, sizeY: Int): Mono<PuzzleConfig>
}

@Component
class PuzzleGeneratorImpl(@Value("\${app.puzzleGeneratorUrl}") val puzzleGeneratorUrl: String,
@Autowired private val mapper: MapStructMapper) : PuzzleGenerator {

    override fun generatePuzzleConfig(sizeX: Int, sizeY: Int): Mono<PuzzleConfig> {
        validateParams(sizeX, sizeY)

        val puzzleMap = generatePuzzleMap(sizeX, sizeY)
        return generatePuzzleDetails(puzzleMap)
    }

    private fun generatePuzzleDetails(puzzleMap: PuzzleMap): Mono<PuzzleConfig> {
        return WebClient.create(puzzleGeneratorUrl).post().uri("/generate")
            .body(BodyInserters.fromValue(mapper.puzzleMapToPuzzleMapDto(puzzleMap)))
            .accept(MediaType.APPLICATION_JSON).retrieve().bodyToMono(PuzzleConfigDto::class.java)
            .map { mapper.puzzleConfigDtoToPuzzleConfig(it) }
    }

    private fun generatePuzzleMap(sizeX: Int, sizeY: Int) : PuzzleMap{
        val fieldMap = mutableListOf<PuzzleField>()
        for (x in 1..sizeX) {
            for (y in 1..sizeY) {
                fieldMap.add(PuzzleField(x-1, y-1))
            }
        }
        return PuzzleMap(fieldMap)
    }

    private fun validateParams(sizeX: Int, sizeY: Int) {
        if (sizeX<=0 || sizeY <= 0) {
            throw RuntimeException("Cannot generate puzzle config with negative X or Y parameter.")
        }
    }
}