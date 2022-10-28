package aba.puzzle.rest

import aba.puzzle.domain.dto.PuzzleConfigDto
import aba.puzzle.service.LaunchService
import aba.puzzle.service.PuzzleGenerator
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import reactor.core.publisher.Mono


@RestController
class Controller(
    @Autowired private val launchService: LaunchService,
    @Autowired private val puzzleGenerator: PuzzleGenerator
) {

    @PostMapping("/run", consumes = [MediaType.APPLICATION_JSON_VALUE], produces = [MediaType.APPLICATION_JSON_VALUE])
    fun run(@RequestBody launchRequest: LaunchRequest, @RequestHeader("Idempotency-Key") idempotenceKey: String): LaunchResponse {
        val topicName = "puzzle-$idempotenceKey"
        val puzzleConfig = launchRequest.puzzleConfig!!.let { PuzzleConfigDto.toPuzzleConfig(it)}
        if (launchService.launch(topicName, puzzleConfig)) {
            return LaunchResponse(launched = true, topicName = topicName)
        } else {
            throw AlreadyRunningException(topicName = topicName)
        }
    }

    @GetMapping("/generate", produces = [MediaType.APPLICATION_JSON_VALUE])
    fun generate(@RequestParam("sizeX") sizeX: Int, @RequestParam("sizeY") sizeY: Int): Mono<PuzzleConfigDto> {
        return puzzleGenerator.generatePuzzleConfig(sizeX, sizeY).map { PuzzleConfigDto.fromPuzzleConfig(it) }
    }

}

class AlreadyRunningException(val topicName: String) : RuntimeException()

@ControllerAdvice
class AlreadyRunningExceptionController {
    @ExceptionHandler(value = [AlreadyRunningException::class])
    fun exception(exception: AlreadyRunningException): ResponseEntity<LaunchResponse> {
        return ResponseEntity(
            LaunchResponse(launched = false, topicName = exception.topicName),
            HttpStatus.INTERNAL_SERVER_ERROR
        )
    }
}

class LaunchRequest {
    val puzzleConfig: PuzzleConfigDto? = null
}

data class LaunchResponse(
    val launched: Boolean,
    val topicName: String
)