package aba.puzzle.assembler.kafka

import aba.puzzle.assembler.PuzzleProcessor
import aba.puzzle.domain.rest.mapstruct.dto.PuzzleStateDto
import aba.puzzle.domain.rest.mapstruct.mapper.MapStructMapper
import com.fasterxml.jackson.databind.ObjectMapper
import org.apache.commons.logging.Log
import org.apache.commons.logging.LogFactory
import org.apache.kafka.clients.consumer.ConsumerRecord
import org.apache.kafka.common.errors.SerializationException
import org.apache.kafka.common.serialization.Deserializer
import org.springframework.context.annotation.PropertySource
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.stereotype.Component

@PropertySource("classpath:application.yaml")
@Component
class PuzzleStateKafkaListener(
    val puzzleProcessor: PuzzleProcessor,
    private val mapper: MapStructMapper
) {
    private val log: Log = LogFactory.getLog(PuzzleStateKafkaListener::class.java)

    @KafkaListener(
        topics = ["\${app.kafka.puzzleStateTopic}"]
    )
    fun listen(record: ConsumerRecord<String, PuzzleStateDto>) {
        log.debug { "read ${record.value()} record" }
        val puzzleState = mapper.puzzleStateDtoToPuzzleState(record.value())
        puzzleProcessor.process(puzzleState)
    }
}


class CustomPuzzleStateDeserializer : Deserializer<PuzzleStateDto?> {
    private val objectMapper = ObjectMapper()
    private val log: Log = LogFactory.getLog(CustomPuzzleStateDeserializer::class.java)

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