package info.matsumana.tsujun.controller

import io.micrometer.prometheus.PrometheusMeterRegistry
import io.prometheus.client.exporter.common.TextFormat
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/prometheus")
class PrometheusController(val registry: PrometheusMeterRegistry) {

    @GetMapping(produces = arrayOf(TextFormat.CONTENT_TYPE_004))
    fun prometheus(): String {
        return registry.scrape()
    }
}
