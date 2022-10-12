package aba.kover.rest

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class Controller {

    @GetMapping("/info")
    fun info() : String {
        return "test"
    }

    @PostMapping("/run")
    fun run() : String {
        return "run"
    }
}