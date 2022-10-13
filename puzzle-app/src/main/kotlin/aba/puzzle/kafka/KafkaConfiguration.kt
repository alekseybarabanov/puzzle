package aba.puzzle.kafka

import aba.puzzle.domain.PuzzleConfig
import aba.puzzle.domain.dto.NewTaskVO
import aba.puzzle.domain.dto.PuzzleStateVO
import aba.puzzle.service.LaunchService
import io.confluent.kafka.serializers.KafkaJsonSerializer
import mu.KotlinLogging
import org.apache.kafka.clients.admin.AdminClientConfig
import org.apache.kafka.clients.producer.ProducerConfig
import org.apache.kafka.common.serialization.StringSerializer
import org.springframework.beans.factory.BeanFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.kafka.config.KafkaListenerContainerFactory
import org.springframework.kafka.config.KafkaListenerEndpointRegistry
import org.springframework.kafka.core.DefaultKafkaProducerFactory
import org.springframework.kafka.core.KafkaAdmin
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.kafka.core.ProducerFactory
import org.springframework.stereotype.Component


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

    @Bean("producerFactoryNewTopic")
    fun producerFactoryNewTopic(): ProducerFactory<String, NewTaskVO> {
        log.info { "kafka bootstrap $bootstrapAddress" }
        val configProps: MutableMap<String, Any> = HashMap()
        configProps[ProducerConfig.BOOTSTRAP_SERVERS_CONFIG] = bootstrapAddress
        configProps[ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG] = StringSerializer::class.java
        configProps[ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG] = KafkaJsonSerializer::class.java
        return DefaultKafkaProducerFactory(configProps)
    }

    @Bean
    fun kafkaTemplateNewTopic(@Autowired @Qualifier("producerFactoryNewTopic") factory: ProducerFactory<String, NewTaskVO>): KafkaTemplate<String, NewTaskVO> {
        return KafkaTemplate(factory)
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
    private lateinit var launchService: LaunchService

    fun registerCustomKafkaListener(name: String, topic: String, groupId: String, puzzleConfig: PuzzleConfig, startImmediately: Boolean) {
        kotlin.runCatching {
            val listenerClass = PuzzleTopicListener::class.java
            val customMessageListener =
                beanFactory.getBean(Class.forName(listenerClass.canonicalName), launchService, puzzleConfig) as CustomMessageListener
            kafkaListenerEndpointRegistry.registerListenerContainer(
                customMessageListener.createKafkaListenerEndpoint(name, topic, groupId),
                kafkaListenerContainerFactory, startImmediately
            )
        }.onFailure {
            log.error(it) { "Exception in bean creation"}
        }
    }
}