package aba.puzzle

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class PuzzleRestApplication

fun main(args: Array<String>) {
	runApplication<PuzzleRestApplication>(*args)
}
