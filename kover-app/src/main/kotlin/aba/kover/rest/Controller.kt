package aba.kover.rest

import aba.kover.service.LaunchService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class Controller(@Autowired val launchService: LaunchService) {

    @GetMapping("/info")
    fun info() : String {
        return "test"
    }

    @PostMapping("/run")
    fun run() : String {
        return launchService.launch()
    }
}