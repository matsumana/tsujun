package info.matsumana.tsujun.controller

import info.matsumana.tsujun.model.Request
import info.matsumana.tsujun.model.ResponseTable
import info.matsumana.tsujun.service.KsqlService
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Flux

@RestController
@RequestMapping("/")
class SqlController(private val ksqlService: KsqlService) {

    @PostMapping("sql")
    fun sql(@RequestBody request: Request): Flux<ResponseTable> {
        return ksqlService.sql(request)
    }
}
