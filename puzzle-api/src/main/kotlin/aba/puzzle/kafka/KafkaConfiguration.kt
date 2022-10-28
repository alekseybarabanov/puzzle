package aba.puzzle.kafka

import aba.puzzle.domain.dto.NewTaskDto
import aba.puzzle.domain.dto.PuzzleStateDto
import io.confluent.kafka.serializers.KafkaJsonSerializer
import mu.KotlinLogging
import org.apache.kafka.clients.admin.AdminClientConfig
import org.apache.kafka.clients.producer.ProducerConfig
import org.apache.kafka.common.serialization.StringSerializer
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.kafka.core.DefaultKafkaProducerFactory
import org.springframework.kafka.core.KafkaAdmin
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.kafka.core.ProducerFactory


@Configuration
class KafkaConfiguration {
    private val log = KotlinLogging.logger {}

    @Value("\${app.kafka.bootstrap}")
    lateinit var bootstrapAddress: String

    @Bean
    fun kafkaAdmin() = KafkaAdmin(mapOf(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG to bootstrapAddress))

    @Bean("producerFactory")
    fun producerFactory(): ProducerFactory<String, PuzzleStateDto> {
        log.info { "kafka bootstrap $bootstrapAddress" }
        val configProps: MutableMap<String, Any> = HashMap()
        configProps[ProducerConfig.BOOTSTRAP_SERVERS_CONFIG] = bootstrapAddress
        configProps[ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG] = StringSerializer::class.java
        configProps[ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG] = KafkaJsonSerializer::class.java
        return DefaultKafkaProducerFactory(configProps)
    }

    @Bean
    fun kafkaTemplate(@Autowired @Qualifier("producerFactory") factory: ProducerFactory<String, PuzzleStateDto>): KafkaTemplate<String, PuzzleStateDto> {
        return KafkaTemplate(factory)
    }

    @Bean("producerFactoryNewTopic")
    fun producerFactoryNewTopic(): ProducerFactory<String, NewTaskDto> {
        log.info { "kafka bootstrap $bootstrapAddress" }
        val configProps: MutableMap<String, Any> = HashMap()
        configProps[ProducerConfig.BOOTSTRAP_SERVERS_CONFIG] = bootstrapAddress
        configProps[ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG] = StringSerializer::class.java
        configProps[ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG] = KafkaJsonSerializer::class.java
        return DefaultKafkaProducerFactory(configProps)
    }

    @Bean
    fun kafkaTemplateNewTopic(@Autowired @Qualifier("producerFactoryNewTopic") factory: ProducerFactory<String, NewTaskDto>): KafkaTemplate<String, NewTaskDto> {
        return KafkaTemplate(factory)
    }
}
