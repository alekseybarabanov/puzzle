package aba.kover

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class KoverRestApplication

fun main(args: Array<String>) {
	runApplication<KoverRestApplication>(*args)
}
