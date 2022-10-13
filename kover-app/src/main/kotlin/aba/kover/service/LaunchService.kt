package aba.kover.service

import aba.kover.domain.Detail
import aba.kover.domain.KoverPosition
import aba.kover.domain.KoverState
import aba.kover.domain.dto.DetailVO
import aba.kover.domain.dto.KoverStateVO
import org.springframework.beans.factory.InitializingBean
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.MediaType
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.client.WebClient

interface LaunchService {
    fun launch(): Boolean
}

@Component
class LaunchServiceImpl(
    @Value("\${app.repositoryUrl}") val repositoryUrl: String,
    @Value("\${app.kafka.topic.koverstate}") val koverStateTopic: String,
    @Autowired val koverStateService: KoverStateService,
    @Autowired val kafkaProducer: KafkaTemplate<String, KoverStateVO>
) : LaunchService, InitializingBean {
    lateinit var allDetails: Collection<Detail>

    override fun launch(): Boolean {
        //TODO: check that the task is not already running. Make call to repository for the answer
        var isAlreayRunning = false

        //TODO: Create topic if missing

        //TODO: Rewind topic offsets for group.id to the end

        //Take each detail and put it on the left upper corner of the puzzle with all possible rotations (4).
        allDetails.forEach {
            koverStateService.addDetail(KoverState(), it, KoverPosition.left_upper).forEach {
                val f = kafkaProducer.send(koverStateTopic, KoverStateVO.fromKoverState(it))
                f.addCallback({rs -> {
                    println("message sent")
                }},{
                    println("Exception in sending to kafka $it")
                }

                )
            }
        }
        return true
    }

    override fun afterPropertiesSet() {
        println("repository url: $repositoryUrl")
        val detailList = WebClient.create(repositoryUrl).get().uri("/details")
            .accept(MediaType.APPLICATION_JSON).retrieve().bodyToFlux(DetailVO::class.java)
            .map { DetailVO.toDetail(it) }.collectList().block()
        allDetails = detailList!!.toList()
        println("rs: $allDetails")
    }
}
