package aba.puzzle.assembler.kafka

import aba.puzzle.assembler.PuzzleProcessor
import aba.puzzle.domain.PuzzleConfig
import aba.puzzle.domain.rest.mapstruct.dto.PuzzleStateDto
import aba.puzzle.domain.rest.mapstruct.mapper.MapStructMapper
import com.fasterxml.jackson.databind.ObjectMapper
import mu.KotlinLogging
import org.apache.kafka.clients.consumer.ConsumerConfig
import org.apache.kafka.clients.consumer.ConsumerRecord
import org.apache.kafka.common.errors.SerializationException
import org.apache.kafka.common.serialization.Deserializer
import org.springframework.beans.factory.BeanFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.config.ConfigurableBeanFactory
import org.springframework.context.annotation.Scope
import org.springframework.kafka.config.KafkaListenerContainerFactory
import org.springframework.kafka.config.KafkaListenerEndpoint
import org.springframework.kafka.config.KafkaListenerEndpointRegistry
import org.springframework.kafka.config.MethodKafkaListenerEndpoint
import org.springframework.kafka.listener.MessageListener
import org.springframework.messaging.handler.annotation.support.DefaultMessageHandlerMethodFactory
import org.springframework.stereotype.Component
import java.util.*

private class PuzzleStateListener(
    val puzzleProcessor: PuzzleProcessor,
    val topic: String,
    val puzzleConfig: PuzzleConfig,
    private val mapper: MapStructMapper
) :
    MessageListener<String, PuzzleStateDto> {
    private val log = KotlinLogging.logger {}
    override fun onMessage(record: ConsumerRecord<String, PuzzleStateDto>) {
        log.debug { "read ${record.value()} record, launch new task" }
        val puzzleState = mapper.puzzleStateDtoToPuzzleState(record.value())
        puzzleProcessor.process(topic, puzzleState, puzzleConfig)
    }
}

@Component
class CustomKafkaListenerRegistrar {
    private val log = KotlinLogging.logger {}

    @Autowired
    private lateinit var beanFactory: BeanFactory

    @Autowired
    private lateinit var kafkaListenerEndpointRegistry: KafkaListenerEndpointRegistry

    @Autowired
    private lateinit var kafkaListenerContainerFactory: KafkaListenerContainerFactory<*>

    @Autowired
    private lateinit var puzzleProcessor: PuzzleProcessor

    @Autowired
    private lateinit var mapper: MapStructMapper


    fun registerCustomKafkaListener(
        name: String,
        topic: String,
        groupId: String,
        puzzleConfig: PuzzleConfig,
        startImmediately: Boolean
    ) {
        kotlin.runCatching {
            val listenerClass = PuzzleTopicListener::class.java
            val puzzleTopicListener =
                beanFactory.getBean(
                    Class.forName(listenerClass.canonicalName),
                    puzzleProcessor,
                    puzzleConfig,
                    mapper
                ) as PuzzleTopicListener
            kafkaListenerEndpointRegistry.registerListenerContainer(
                puzzleTopicListener.createKafkaListenerEndpoint(name, topic, groupId),
                kafkaListenerContainerFactory, startImmediately
            )
        }.onFailure {
            log.error(it) { "Exception in bean creation" }
        }
    }
}


@Component
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
class PuzzleTopicListener(
    val puzzleProcessor: PuzzleProcessor,
    val puzzleConfig: PuzzleConfig,
    val mapper: MapStructMapper
) {

    fun createKafkaListenerEndpoint(name: String, topic: String, groupId: String): KafkaListenerEndpoint {
        val kafkaListenerEndpoint = createDefaultMethodKafkaListenerEndpoint(name, topic, groupId)
        kafkaListenerEndpoint.bean = PuzzleStateListener(puzzleProcessor, topic, puzzleConfig, mapper)
        kafkaListenerEndpoint.method = PuzzleStateListener::class.java.getMethod(
            "onMessage", ConsumerRecord::class.java
        )
        return kafkaListenerEndpoint
    }

    private fun createDefaultMethodKafkaListenerEndpoint(
        name: String,
        topic: String,
        consumerGroupId: String
    ): MethodKafkaListenerEndpoint<String, String> {
        val props = Properties()
        props[ConsumerConfig.AUTO_OFFSET_RESET_CONFIG] = "earliest"
        props[ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG] = CustomPuzzleStateDeserializer::class.java
        return with(MethodKafkaListenerEndpoint<String, String>()) {
            setId(getConsumerId(name))
            setGroupId(consumerGroupId)
            setAutoStartup(true)
            setTopics(topic)
            setConsumerProperties(props)
            setMessageHandlerMethodFactory(DefaultMessageHandlerMethodFactory())
            this
        }
    }

    private fun getConsumerId(name: String): String {
        return if (isBlank(name)) {
            PuzzleTopicListener::class.java.canonicalName + "#" + NUMBER_OF_LISTENERS++
        } else {
            name
        }
    }

    private fun isBlank(string: String?): Boolean {
        return string.isNullOrBlank()
    }

    companion object {
        private var NUMBER_OF_LISTENERS = 0
    }
}

class CustomPuzzleStateDeserializer : Deserializer<PuzzleStateDto?> {
    private val objectMapper = ObjectMapper()
    private val log = KotlinLogging.logger {}

    override fun configure(configs: Map<String?, *>?, isKey: Boolean) {}
    override fun deserialize(topic: String?, data: ByteArray?): PuzzleStateDto? {
        return try {
            if (data == null) {
                log.warn { "Null received at deserializing" }
                return null
            }
            log.debug { "Deserializing..." }
            objectMapper.readValue(String(data), PuzzleStateDto::class.java)
        } catch (e: Exception) {
            throw SerializationException("Error when deserializing byte[] to PuzzleStateVO")
        }
    }

    override fun close() {}
}
