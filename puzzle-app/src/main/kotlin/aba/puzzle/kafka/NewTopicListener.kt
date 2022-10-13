package aba.puzzle.kafka

import aba.puzzle.domain.dto.NewTaskVO
import aba.puzzle.domain.dto.PuzzleConfigVO
import mu.KotlinLogging
import org.apache.kafka.clients.consumer.ConsumerRecord
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.PropertySource
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.stereotype.Component


@PropertySource("classpath:application.properties")
@Component
class NewTopicListener(
    @Autowired val listenerRegistrar: CustomKafkaListenerRegistrar,
) {
    private val log = KotlinLogging.logger {}

    @KafkaListener(id = "#{ systemProperties['POD_NAME'] }", topics = ["\${app.taskTopicsTopic}"], clientIdPrefix = "topicsClient")
    fun listen(data: ConsumerRecord<String, NewTaskVO>) {
        log.info { "Read from new topic: $data" }
        val (topic, puzzleConfigVO) = data.value()
        val puzzleConfig = PuzzleConfigVO.toPuzzleConfig(puzzleConfigVO)
        listenerRegistrar.registerCustomKafkaListener(topic, topic, "defaultGroup", puzzleConfig, true)
    }
}
