package aba.kover.rest

import aba.kover.service.LaunchService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*


@RestController
class Controller(@Autowired val launchService: LaunchService) {

    @GetMapping("/info")
    fun info() : String {
        return "test"
    }

    @PostMapping("/run")
    fun run() {
        if (launchService.launch()) {
            return
        } else {
            throw AlreadyRunningException()
        }
    }
}

class AlreadyRunningException: RuntimeException()

@ControllerAdvice
class AlreadyRunningExceptionController {
    @ExceptionHandler(value = [AlreadyRunningException::class])
    fun exception(exception: AlreadyRunningException): ResponseEntity<Any> {
        return ResponseEntity("Task is already running", HttpStatus.INTERNAL_SERVER_ERROR)
    }
}