package aba.puzzle.generator

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class PuzzleGeneratorApplication

fun main(args: Array<String>) {
    runApplication<PuzzleGeneratorApplication>(*args)
}
