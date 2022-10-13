package aba.puzzle.kafka

import aba.puzzle.domain.dto.PuzzleStateVO
import io.confluent.kafka.serializers.KafkaJsonSerializer
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
    @Value("\${app.kafka.bootstrap}")
    lateinit var bootstrapAddress: String

    @Bean
    fun kafkaAdmin() = KafkaAdmin(mapOf(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG to bootstrapAddress))

    @Bean("producerFactory")
    fun producerFactory(): ProducerFactory<String, PuzzleStateVO> {
        println("kafka bootstrap $bootstrapAddress")
        val configProps: MutableMap<String, Any> = HashMap()
        configProps[ProducerConfig.BOOTSTRAP_SERVERS_CONFIG] = bootstrapAddress
        configProps[ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG] = StringSerializer::class.java
        configProps[ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG] = KafkaJsonSerializer::class.java
        return DefaultKafkaProducerFactory(configProps)
    }

    @Bean
    fun kafkaTemplate(@Autowired @Qualifier("producerFactory") factory: ProducerFactory<String, PuzzleStateVO>): KafkaTemplate<String, PuzzleStateVO> {
        return KafkaTemplate(factory)
    }
    @Bean("producerFactoryString")
    fun producerFactoryString(): ProducerFactory<String, String> {
        println("kafka bootstrap $bootstrapAddress")
        val configProps: MutableMap<String, Any> = HashMap()
        configProps[ProducerConfig.BOOTSTRAP_SERVERS_CONFIG] = bootstrapAddress
        configProps[ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG] = StringSerializer::class.java
        configProps[ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG] = StringSerializer::class.java
        return DefaultKafkaProducerFactory(configProps)
    }

    @Bean
    fun kafkaTemplateString(@Autowired @Qualifier("producerFactoryString") factory: ProducerFactory<String, String>): KafkaTemplate<String, String> {
        return KafkaTemplate(factory)
    }
}