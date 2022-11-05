package aba.puzzle.assembler.kafka


import aba.puzzle.domain.rest.mapstruct.dto.NewTaskDto
import aba.puzzle.domain.rest.mapstruct.mapper.MapStructMapper
import com.fasterxml.jackson.databind.ObjectMapper
import org.apache.commons.logging.Log
import org.apache.commons.logging.LogFactory
import org.apache.kafka.clients.consumer.ConsumerConfig
import org.apache.kafka.clients.consumer.ConsumerRecord
import org.apache.kafka.common.errors.SerializationException
import org.apache.kafka.common.serialization.Deserializer
import org.apache.kafka.common.serialization.StringDeserializer
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Lazy
import org.springframework.context.annotation.PropertySource
import org.springframework.kafka.annotation.EnableKafka
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory
import org.springframework.kafka.config.KafkaListenerContainerFactory
import org.springframework.kafka.core.ConsumerFactory
import org.springframework.kafka.core.DefaultKafkaConsumerFactory
import org.springframework.kafka.listener.ConcurrentMessageListenerContainer
import org.springframework.stereotype.Component


@PropertySource("classpath:application.yaml")
@Component
class NewTopicListener(
    @Autowired val listenerRegistrar: CustomKafkaListenerRegistrar,
    @Autowired private val mapper: MapStructMapper
) {
    private val log: Log = LogFactory.getLog(NewTopicListener::class.java)

    @KafkaListener(
        id = "#{ systemEnvironment['POD_NAME'] }",
        topics = ["\${app.kafka.newTaskTopic}"],
        clientIdPrefix = "topicsClient"
    )
    fun listen(data: ConsumerRecord<String, NewTaskDto>) {
        log.info { "Read from new topic: $data" }
        val (topic, puzzleConfigVO) = data.value()
        val puzzleConfig = mapper.puzzleConfigDtoToPuzzleConfig(puzzleConfigVO)
        listenerRegistrar.registerCustomKafkaListener(topic, topic, "defaultGroup", puzzleConfig, true)
    }
}

@Configuration
@EnableKafka
class KafkaConfig {
    private val log: Log = LogFactory.getLog(KafkaConfig::class.java)

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

class CustomNewTopicDeserializer : Deserializer<NewTaskDto?> {
    private val objectMapper = ObjectMapper()
    private val log: Log = LogFactory.getLog(CustomNewTopicDeserializer::class.java)

    override fun configure(configs: Map<String?, *>?, isKey: Boolean) {}
    override fun deserialize(topic: String?, data: ByteArray?): NewTaskDto? {
        return try {
            if (data == null) {
                log.warn { "Null received at deserializing" }
                return null
            }
            log.debug { "Deserializing..." }
            objectMapper.readValue(String(data), NewTaskDto::class.java)
        } catch (e: Exception) {
            throw SerializationException("Error when deserializing byte[] to NewTaskVO")
        }
    }

    override fun close() {}
}
