package info.matsumana.tsujun.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import info.matsumana.tsujun.model.Request;
import info.matsumana.tsujun.model.ResponseTable;
import info.matsumana.tsujun.service.KsqlService;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping("/")
public class SqlController {

    private final KsqlService ksqlService;

    SqlController(KsqlService ksqlService) {
        this.ksqlService = ksqlService;
    }

    @PostMapping("sql")
    Flux<ResponseTable> sql(@RequestBody Request request) {
        return ksqlService.sql(request);
    }
}
