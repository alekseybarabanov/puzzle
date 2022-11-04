package aba.puzzle

import aba.puzzle.domain.rest.mapstruct.mapper.MapStructMapper
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@SpringBootApplication
class PuzzleApi

fun main(args: Array<String>) {
    runApplication<PuzzleApi>(*args)
}

@Configuration
class ModuleBeans {
    @Bean
    fun getDomainMapper(): MapStructMapper = MapStructMapper.INSTANCE
}
