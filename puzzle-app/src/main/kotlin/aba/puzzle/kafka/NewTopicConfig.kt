package aba.puzzle.kafka

import aba.puzzle.domain.dto.NewTaskVO
import com.fasterxml.jackson.databind.ObjectMapper
import io.confluent.kafka.serializers.KafkaJsonDeserializer
import mu.KotlinLogging
import org.apache.kafka.clients.consumer.ConsumerConfig
import org.apache.kafka.common.errors.SerializationException
import org.apache.kafka.common.serialization.Deserializer
import org.apache.kafka.common.serialization.StringDeserializer
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.kafka.annotation.EnableKafka
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory
import org.springframework.kafka.config.KafkaListenerContainerFactory
import org.springframework.kafka.core.ConsumerFactory
import org.springframework.kafka.core.DefaultKafkaConsumerFactory
import org.springframework.kafka.listener.ConcurrentMessageListenerContainer

@Configuration
@EnableKafka
class KafkaConfig {
    private val log = KotlinLogging.logger {}

    @Value("\${app.kafka.bootstrap}")
    lateinit var bootstrapAddress: String

    @Bean
    fun kafkaListenerContainerFactory(): KafkaListenerContainerFactory<ConcurrentMessageListenerContainer<String, String>> {
        val factory = ConcurrentKafkaListenerContainerFactory<String, String>();
        factory.setConsumerFactory(consumerFactory());
        factory.setConcurrency(3);
        factory.getContainerProperties().setPollTimeout(3000);
        return factory;
    }

    @Bean
    fun consumerFactory(): ConsumerFactory<String, String> {
        return DefaultKafkaConsumerFactory<String, String>(consumerConfigs());
    }

    @Bean
    fun consumerConfigs(): Map<String, Any> {
        log.info { "Consumer bootstrap servers $bootstrapAddress" }
        return mapOf(
            ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG to bootstrapAddress,
            ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG to CustomNewTopicDeserializer::class.java,
            ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG to StringDeserializer::class.java,
            ConsumerConfig.AUTO_OFFSET_RESET_CONFIG to "earliest"
        )
    }
}


class CustomNewTopicDeserializer : Deserializer<NewTaskVO?> {
    private val objectMapper = ObjectMapper()
    private val log = KotlinLogging.logger {}

    override fun configure(configs: Map<String?, *>?, isKey: Boolean) {}
    override fun deserialize(topic: String?, data: ByteArray?): NewTaskVO? {
        return try {
            if (data == null) {
                log.warn { "Null received at deserializing" }
                return null
            }
            log.debug {"Deserializing..."}
            objectMapper.readValue(String(data), NewTaskVO::class.java)
        } catch (e: Exception) {
            throw SerializationException("Error when deserializing byte[] to NewTaskVO")
        }
    }

    override fun close() {}
}