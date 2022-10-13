package aba.puzzle.kafka

import aba.puzzle.domain.dto.PuzzleStateVO
import io.confluent.kafka.serializers.KafkaJsonSerializer
import mu.KotlinLogging
import org.apache.kafka.clients.admin.AdminClientConfig
import org.apache.kafka.clients.consumer.ConsumerConfig
import org.apache.kafka.clients.consumer.ConsumerRecord
import org.apache.kafka.clients.producer.ProducerConfig
import org.apache.kafka.common.serialization.StringSerializer
import org.springframework.beans.factory.BeanFactory
import org.springframework.beans.factory.InitializingBean
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.kafka.config.KafkaListenerContainerFactory
import org.springframework.kafka.config.KafkaListenerEndpoint
import org.springframework.kafka.config.KafkaListenerEndpointRegistry
import org.springframework.kafka.config.MethodKafkaListenerEndpoint
import org.springframework.kafka.core.DefaultKafkaProducerFactory
import org.springframework.kafka.core.KafkaAdmin
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.kafka.core.ProducerFactory
import org.springframework.kafka.listener.MessageListener
import org.springframework.messaging.handler.annotation.support.DefaultMessageHandlerMethodFactory
import org.springframework.stereotype.Component
import java.util.*
import java.util.concurrent.CompletableFuture


@Configuration
class KafkaConfiguration {
    private val log = KotlinLogging.logger {}

    @Value("\${app.kafka.bootstrap}")
    lateinit var bootstrapAddress: String

    @Bean
    fun kafkaAdmin() = KafkaAdmin(mapOf(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG to bootstrapAddress))

    @Bean("producerFactory")
    fun producerFactory(): ProducerFactory<String, PuzzleStateVO> {
        log.info { "kafka bootstrap $bootstrapAddress" }
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
        log.info { "kafka bootstrap $bootstrapAddress" }
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

abstract class CustomMessageListener {
    abstract fun createKafkaListenerEndpoint(name: String, topic: String, groupId: String): KafkaListenerEndpoint
    protected fun createDefaultMethodKafkaListenerEndpoint(
        name: String,
        topic: String,
        consumerGroupId: String
    ): MethodKafkaListenerEndpoint<String, String> {
        val props = Properties()
        props[ConsumerConfig.AUTO_OFFSET_RESET_CONFIG] = "earliest"
        return with (MethodKafkaListenerEndpoint<String, String>()) {
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

@Component
class MyCustomMessageListener : CustomMessageListener() {
    override fun createKafkaListenerEndpoint(name: String, topic: String, groupId: String): KafkaListenerEndpoint {
        val kafkaListenerEndpoint = createDefaultMethodKafkaListenerEndpoint(name, topic, groupId)
        kafkaListenerEndpoint.bean = MyMessageListener()
        kafkaListenerEndpoint.method = MyMessageListener::class.java.getMethod(
            "onMessage", ConsumerRecord::class.java
        )
        return kafkaListenerEndpoint
    }

    private class MyMessageListener : MessageListener<String, String> {
        private val log = KotlinLogging.logger {}
        override fun onMessage(record: ConsumerRecord<String, String>) {
            log.info { "My message listener got a new record: $record" }
            CompletableFuture.runAsync { sleep() }.join()
            log.info { "My message listener done processing record: $record" }
        }

        private fun sleep() {
            Thread.sleep(100)
        }
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

    fun registerCustomKafkaListener(name: String, topic: String, groupId: String, startImmediately: Boolean) {
        kotlin.runCatching {
            val listenerClass = MyCustomMessageListener::class.java
            val customMessageListener =
                beanFactory.getBean(Class.forName(listenerClass.canonicalName)) as CustomMessageListener
            kafkaListenerEndpointRegistry.registerListenerContainer(
                customMessageListener.createKafkaListenerEndpoint(name, topic, groupId),
                kafkaListenerContainerFactory, startImmediately
            )
        }.onFailure {
            log.error(it) { "Exception in bean creation"}
        }
    }
}