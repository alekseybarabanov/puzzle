package aba.puzzle.kafka

import aba.puzzle.domain.dto.NewTaskVO
import aba.puzzle.service.PuzzleStateService
import io.confluent.kafka.serializers.KafkaJsonDeserializer
import mu.KotlinLogging
import org.apache.kafka.clients.consumer.ConsumerConfig
import org.apache.kafka.clients.consumer.ConsumerRecord
import org.springframework.kafka.config.KafkaListenerEndpoint
import org.springframework.kafka.config.MethodKafkaListenerEndpoint
import org.springframework.kafka.listener.MessageListener
import org.springframework.messaging.handler.annotation.support.DefaultMessageHandlerMethodFactory
import org.springframework.stereotype.Component
import java.util.*

@Component
class PuzzleTopicListener(
    val puzzleStateService: PuzzleStateService
) : CustomMessageListener() {


    override fun createKafkaListenerEndpoint(name: String, topic: String, groupId: String): KafkaListenerEndpoint {
        val kafkaListenerEndpoint = createDefaultMethodKafkaListenerEndpoint(name, topic, groupId)
        kafkaListenerEndpoint.bean = MyMessageListener(puzzleStateService)
        kafkaListenerEndpoint.method = MyMessageListener::class.java.getMethod(
            "onMessage", ConsumerRecord::class.java
        )
        return kafkaListenerEndpoint
    }

    private class MyMessageListener(val puzzleStateService: PuzzleStateService) : MessageListener<String, NewTaskVO> {
        private val log = KotlinLogging.logger {}
        override fun onMessage(record: ConsumerRecord<String, NewTaskVO>) {
            log.info { "My message listener got a new record: $record" }
            log.info { "My message listener done processing record: $record" }
        }

    }
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
        props[ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG] = KafkaJsonDeserializer::class.java
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

