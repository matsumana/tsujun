package info.matsumana.tsujun.service

import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.KotlinModule
import com.fasterxml.jackson.module.kotlin.readValue
import info.matsumana.tsujun.config.KsqlServerConfig
import info.matsumana.tsujun.exception.KsqlException
import info.matsumana.tsujun.model.Request
import info.matsumana.tsujun.model.ResponseTable
import info.matsumana.tsujun.model.ksql.KsqlRequest
import info.matsumana.tsujun.model.ksql.KsqlResponse
import info.matsumana.tsujun.model.ksql.KsqlResponseColumns
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.client.WebClientResponseException
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import java.io.IOException
import java.util.*

@Service
class KsqlService(private val ksqlServerConfig: KsqlServerConfig) {

    companion object {
        private val logger = LoggerFactory.getLogger(this::class.java.enclosingClass)
        private val mapper = ObjectMapper().registerModule(KotlinModule())
                .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
        private val emptyResponse = KsqlResponse(KsqlResponseColumns(arrayOf()))
    }

    fun sql(request: Request): Flux<ResponseTable> {

        logger.debug("KSQL server={}", ksqlServerConfig)

        // In the WebClient, sometimes the response gets cut off in the middle of json.
        // For example, as in the following log.
        // So, temporarily save the failed data to a variable, combine and use the next response.
        //
        // 2017-12-28 20:54:27.832 DEBUG 70874 --- [ctor-http-nio-5] i.matsumana.tsujun.service.KsqlService   : {"r
        // 2017-12-28 20:54:27.833 DEBUG 70874 --- [ctor-http-nio-5] i.matsumana.tsujun.service.KsqlService   : ow":{"columns":["Page_27",0]},"errorMessage":null}
        val previousFailed = ArrayDeque<String>()

        return WebClient.create(ksqlServerConfig.server)
                .post()
                .uri("/query")
                .body(Mono.just(KsqlRequest(request.sql)), KsqlRequest::class.java)
                .retrieve()
                .bodyToFlux(String::class.java)
                .doOnError { e ->
                    logger.info("WebClient Error", e)

                    if (e is WebClientResponseException) {
                        throw KsqlException(request.sequence, request.sql, e.statusCode.value(), e.responseBodyAsString)
                    } else {
                        throw e
                    }
                }
                .map { orgString ->
                    logger.debug(orgString)

                    val s = orgString.trim()
                    if (s.isEmpty()) {
                        emptyResponse
                    } else {
                        try {
                            mapper.readValue<KsqlResponse>(s)
                        } catch (ignore: IOException) {
                            if (previousFailed.isEmpty()) {
                                previousFailed.addFirst(s)
                                emptyResponse
                            } else {
                                try {
                                    val completeJson = previousFailed.removeFirst() + s
                                    mapper.readValue<KsqlResponse>(completeJson)
                                } catch (ignore: IOException) {
                                    emptyResponse
                                }
                            }
                        }
                    }
                }
                .filter { res -> !res.row.columns.isEmpty() }
                .map { ksqlResponse ->
                    ResponseTable(sequence = request.sequence,
                            sql = request.sql,
                            mode = 1,
                            data = ksqlResponse.row.columns)
                }
    }
}
