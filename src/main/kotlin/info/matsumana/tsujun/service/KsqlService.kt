package info.matsumana.tsujun.service

import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.KotlinModule
import com.fasterxml.jackson.module.kotlin.readValue
import info.matsumana.tsujun.config.KsqlServerConfig
import info.matsumana.tsujun.exception.KsqlException
import info.matsumana.tsujun.model.Request
import info.matsumana.tsujun.model.ResponseTable
import info.matsumana.tsujun.model.ksql.*
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
        private val REGEX_SELECT = Regex("""^SELECT\s+?.*""", RegexOption.IGNORE_CASE)
        private val REGEX_QUERIES = Regex("""^(LIST|SHOW)\s+?QUERIES(\s|;)+?""", RegexOption.IGNORE_CASE)
        private val REGEX_STREAMS = Regex("""^(LIST|SHOW)\s+?STREAMS(\s|;)+?""", RegexOption.IGNORE_CASE)
        private val REGEX_TABLES = Regex("""^(LIST|SHOW)\s+?TABLES(\s|;)+?""", RegexOption.IGNORE_CASE)
        private val emptyResponseSelect = KsqlResponseSelect(KsqlResponseSelectColumns(arrayOf()))
        private val emptyResponseQueries =
                arrayOf(KsqlResponseQueries(KsqlResponseQueriesInner("", arrayOf(KsqlResponseQueriesInnerQueries(0, "", "")))))
        private val emptyResponseStreams =
                arrayOf(KsqlResponseStreams(KsqlResponseStreamsInner("", arrayOf(KsqlResponseStreamsInnerStreams("", "", "")))))
        private val emptyResponseTables =
                arrayOf(KsqlResponseTables(KsqlResponseTablesInner("", arrayOf(KsqlResponseTablesInnerTables("", "", "", false)))))
    }

    fun sql(request: Request): Flux<ResponseTable> {
        logger.debug("KSQL server={}", ksqlServerConfig)

        val sql = request.sql.trimStart().trimEnd()
        if (REGEX_SELECT.matches(sql)) {
            return select(request)
        } else if (REGEX_QUERIES.matches(sql)) {
            return queries(request)
        } else if (REGEX_STREAMS.matches(sql)) {
            return streams(request)
        } else if (REGEX_TABLES.matches(sql)) {
            return tables(request)
        } else {
            // TODO create application exception and handle it in ControllerAdvisor
            throw IllegalArgumentException()
        }
    }

    private fun select(request: Request): Flux<ResponseTable> {
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
                    handleException(e, request)
                }
                .map { orgString ->
                    logger.debug(orgString)

                    val s = orgString.trim()
                    if (s.isEmpty()) {
                        emptyResponseSelect
                    } else {
                        try {
                            mapper.readValue<KsqlResponseSelect>(s)
                        } catch (ignore: IOException) {
                            if (previousFailed.isEmpty()) {
                                previousFailed.addFirst(s)
                                emptyResponseSelect
                            } else {
                                try {
                                    val completeJson = previousFailed.removeFirst() + s
                                    mapper.readValue<KsqlResponseSelect>(completeJson)
                                } catch (ignore: IOException) {
                                    emptyResponseSelect
                                }
                            }
                        }
                    }
                }
                .filter { res -> !res.row.columns.isEmpty() }
                .map { res ->
                    ResponseTable(sequence = request.sequence,
                            sql = request.sql,
                            mode = 1,
                            data = res.row.columns)
                }
    }

    private fun queries(request: Request): Flux<ResponseTable> {
        return generateWebClientKsql(request)
                .map { orgString ->
                    logger.debug(orgString)

                    val s = orgString.trim()
                    if (s.isEmpty()) {
                        emptyResponseQueries
                    } else {
                        try {
                            mapper.readValue<Array<KsqlResponseQueries>>(s)
                        } catch (ignore: IOException) {
                            emptyResponseQueries
                        }
                    }
                }
                .map { res -> res.get(0) }
                .filter { res -> !res.queries.queries.isEmpty() }
                .flatMap { res ->
                    val header = ResponseTable(sequence = request.sequence,
                            sql = request.sql,
                            mode = 1,
                            data = arrayOf("ID", "Kafka Topic", "Query String"))

                    val data = res.queries.queries.map { m ->
                        ResponseTable(sequence = request.sequence,
                                sql = request.sql,
                                mode = 1,
                                data = arrayOf(m.id, m.kafkaTopic, m.queryString))
                    }

                    val list = mutableListOf(header)
                    list.addAll(data)
                    Flux.fromIterable(list)
                }
    }

    private fun streams(request: Request): Flux<ResponseTable> {
        return generateWebClientKsql(request)
                .map { orgString ->
                    logger.debug(orgString)

                    val s = orgString.trim()
                    if (s.isEmpty()) {
                        emptyResponseStreams
                    } else {
                        try {
                            mapper.readValue<Array<KsqlResponseStreams>>(s)
                        } catch (ignore: IOException) {
                            emptyResponseStreams
                        }
                    }
                }
                .map { res -> res.get(0) }
                .filter { res -> !res.streams.streams.isEmpty() }
                .flatMap { res ->
                    val header = ResponseTable(sequence = request.sequence,
                            sql = request.sql,
                            mode = 1,
                            data = arrayOf("Stream Name", "Ksql Topic", "Format"))

                    val data = res.streams.streams.map { m ->
                        ResponseTable(sequence = request.sequence,
                                sql = request.sql,
                                mode = 1,
                                data = arrayOf(m.name, m.topic, m.format))
                    }

                    val list = mutableListOf(header)
                    list.addAll(data)
                    Flux.fromIterable(list)
                }
    }

    private fun tables(request: Request): Flux<ResponseTable> {
        return generateWebClientKsql(request)
                .map { orgString ->
                    logger.debug(orgString)

                    val s = orgString.trim()
                    if (s.isEmpty()) {
                        emptyResponseTables
                    } else {
                        try {
                            mapper.readValue<Array<KsqlResponseTables>>(s)
                        } catch (ignore: IOException) {
                            emptyResponseTables
                        }
                    }
                }
                .map { res -> res.get(0) }
                .filter { res -> !res.tables.tables.isEmpty() }
                .flatMap { res ->
                    val header = ResponseTable(sequence = request.sequence,
                            sql = request.sql,
                            mode = 1,
                            data = arrayOf("Stream Name", "Ksql Topic", "Format", "Windowed"))

                    val data = res.tables.tables.map { m ->
                        ResponseTable(sequence = request.sequence,
                                sql = request.sql,
                                mode = 1,
                                data = arrayOf(m.name, m.topic, m.format, m.isWindowed))
                    }

                    val list = mutableListOf(header)
                    list.addAll(data)
                    Flux.fromIterable(list)
                }
    }

    private fun generateWebClientKsql(request: Request): Flux<String> {
        return WebClient.create(ksqlServerConfig.server)
                .post()
                .uri("/ksql")
                .body(Mono.just(KsqlRequest(request.sql)), KsqlRequest::class.java)
                .retrieve()
                .bodyToFlux(String::class.java)
                .doOnError { e ->
                    handleException(e, request)
                }
    }

    private fun handleException(e: Throwable, request: Request) {
        logger.info("WebClient Error", e)

        if (e is WebClientResponseException) {
            throw KsqlException(request.sequence, request.sql, e.statusCode.value(), e.responseBodyAsString)
        } else {
            throw e
        }
    }
}
