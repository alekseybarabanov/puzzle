package aba.puzzle.rest

import aba.puzzle.service.LaunchService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*


@RestController
class Controller(@Autowired val launchService: LaunchService) {

    @GetMapping("/info")
    fun info(): String {
        return "test"
    }

    @PostMapping("/run", consumes = [MediaType.APPLICATION_JSON_VALUE], produces = [MediaType.APPLICATION_JSON_VALUE])
    fun run(@RequestBody launchRequest: LaunchRequest): LaunchResponse {
        val topicName = "puzzle-${launchRequest.testId}"
        if (launchService.launch(topicName)) {
            return LaunchResponse(launched = true, topicName = topicName)
        } else {
            throw AlreadyRunningException(topicName=topicName)
        }
    }
}

class AlreadyRunningException(val topicName: String) : RuntimeException()

@ControllerAdvice
class AlreadyRunningExceptionController {
    @ExceptionHandler(value = [AlreadyRunningException::class])
    fun exception(exception: AlreadyRunningException): ResponseEntity<LaunchResponse> {
        return ResponseEntity(LaunchResponse(launched = false, topicName = exception.topicName), HttpStatus.INTERNAL_SERVER_ERROR)
    }
}

class LaunchRequest {
    val testId: String = "defaultTest"
}

data class LaunchResponse(
    val launched: Boolean,
    val topicName: String
)