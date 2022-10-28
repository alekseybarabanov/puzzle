package aba.puzzle.assembler.kafka

import io.confluent.kafka.serializers.KafkaJsonSerializer
import mu.KotlinLogging
import org.apache.kafka.clients.producer.ProducerConfig
import org.apache.kafka.common.serialization.StringSerializer
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.kafka.core.DefaultKafkaProducerFactory
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.kafka.core.ProducerFactory

@Configuration
class KafkaConfiguration {
    private val log = KotlinLogging.logger {}

    @Value("\${app.kafka.bootstrap}")
    lateinit var bootstrapAddress: String

    @Bean("puzzleStateProducerFactory")
    fun producerFactory(): ProducerFactory<String, Any> {
        log.info { "kafka bootstrap $bootstrapAddress" }
        val configProps: MutableMap<String, Any> = HashMap()
        configProps[ProducerConfig.BOOTSTRAP_SERVERS_CONFIG] = bootstrapAddress
        configProps[ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG] = StringSerializer::class.java
        configProps[ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG] = KafkaJsonSerializer::class.java
        return DefaultKafkaProducerFactory(configProps)
    }

    @Bean
    fun kafkaTemplate(@Autowired factory: ProducerFactory<String, Any>): KafkaTemplate<String, Any> {
        return KafkaTemplate(factory)
    }
}