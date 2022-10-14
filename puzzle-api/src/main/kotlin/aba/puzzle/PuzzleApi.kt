package aba.puzzle

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class PuzzleApi

fun main(args: Array<String>) {
	runApplication<PuzzleApi>(*args)
}
