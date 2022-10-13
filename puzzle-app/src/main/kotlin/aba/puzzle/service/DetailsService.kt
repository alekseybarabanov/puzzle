package aba.puzzle.service

import aba.puzzle.domain.Detail
import aba.puzzle.domain.dto.DetailVO
import mu.KotlinLogging
import org.springframework.beans.factory.InitializingBean
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.MediaType
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.client.WebClient

interface DetailsService {
    fun getDetails(): Collection<Detail>
}

@Component
class DetailsServiceImpl(@Value("\${app.repositoryUrl}") val repositoryUrl: String) : DetailsService, InitializingBean {
    private val log = KotlinLogging.logger {}

    lateinit var allDetails: Collection<Detail>

    override fun getDetails(): Collection<Detail> = allDetails

    override fun afterPropertiesSet() {
        log.info{ "--- Loading details from $repositoryUrl --" }
        val detailList = WebClient.create(repositoryUrl).get().uri("/details")
            .accept(MediaType.APPLICATION_JSON).retrieve().bodyToFlux(DetailVO::class.java)
            .map { DetailVO.toDetail(it) }.collectList().block()
        allDetails = detailList!!.toList()
        log.info { "Details are loaded: $allDetails" }
    }

}