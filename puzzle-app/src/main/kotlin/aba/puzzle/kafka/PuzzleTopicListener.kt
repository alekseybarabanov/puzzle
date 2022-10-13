package aba.puzzle.kafka

import aba.puzzle.domain.PuzzleConfig
import aba.puzzle.domain.dto.PuzzleStateVO
import aba.puzzle.service.LaunchService
import com.fasterxml.jackson.databind.ObjectMapper
import io.confluent.kafka.serializers.KafkaJsonDeserializer
import mu.KotlinLogging
import org.apache.kafka.clients.consumer.ConsumerConfig
import org.apache.kafka.clients.consumer.ConsumerRecord
import org.apache.kafka.common.errors.SerializationException
import org.apache.kafka.common.serialization.Deserializer
import org.springframework.beans.factory.config.ConfigurableBeanFactory
import org.springframework.context.annotation.Scope
import org.springframework.kafka.config.KafkaListenerEndpoint
import org.springframework.kafka.config.MethodKafkaListenerEndpoint
import org.springframework.kafka.listener.MessageListener
import org.springframework.messaging.handler.annotation.support.DefaultMessageHandlerMethodFactory
import org.springframework.stereotype.Component
import java.util.*

@Component
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
class PuzzleTopicListener(
    val launchService: LaunchService,
    val puzzleConfig: PuzzleConfig
) : CustomMessageListener() {


    override fun createKafkaListenerEndpoint(name: String, topic: String, groupId: String): KafkaListenerEndpoint {
        val kafkaListenerEndpoint = createDefaultMethodKafkaListenerEndpoint(name, topic, groupId)
        kafkaListenerEndpoint.bean = MyMessageListener(launchService, topic, puzzleConfig)
        kafkaListenerEndpoint.method = MyMessageListener::class.java.getMethod(
            "onMessage", ConsumerRecord::class.java
        )
        return kafkaListenerEndpoint
    }

    private class MyMessageListener(val launchService: LaunchService, val topic: String, val puzzleConfig: PuzzleConfig) : MessageListener<String, PuzzleStateVO> {
        private val log = KotlinLogging.logger {}
        override fun onMessage(record: ConsumerRecord<String, PuzzleStateVO>) {
            log.debug{ "read ${record.value()} record, launch new task" }
            val puzzleState = PuzzleStateVO.toPuzzleState(record.value())
            launchService.process(topic, puzzleState, puzzleConfig)
        }

    }
}
class CustomPuzzleStateDeserializer : Deserializer<PuzzleStateVO?> {
    private val objectMapper = ObjectMapper()
    private val log = KotlinLogging.logger {}

    override fun configure(configs: Map<String?, *>?, isKey: Boolean) {}
    override fun deserialize(topic: String?, data: ByteArray?): PuzzleStateVO? {
        return try {
            if (data == null) {
                log.warn { "Null received at deserializing" }
                return null
            }
            log.debug {"Deserializing..."}
            objectMapper.readValue(String(data), PuzzleStateVO::class.java)
        } catch (e: Exception) {
            throw SerializationException("Error when deserializing byte[] to NewTaskVO")
        }
    }

    override fun close() {}
}


abstract class CustomMessageListener {
    abstract fun createKafkaListenerEndpoint(name: String, topic: String, groupId: String): KafkaListenerEndpoint
    protected fun createDefaultMethodKafkaListenerEndpoint(
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
            CustomMessageListener::class.java.canonicalName + "#" + NUMBER_OF_LISTENERS++
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

