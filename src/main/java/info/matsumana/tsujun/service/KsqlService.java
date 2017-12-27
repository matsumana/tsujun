package info.matsumana.tsujun.service;

import java.io.IOException;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import com.google.common.base.Strings;

import info.matsumana.tsujun.config.KsqlServerConfig;
import info.matsumana.tsujun.model.Request;
import info.matsumana.tsujun.model.ResponseTable;
import info.matsumana.tsujun.model.ksql.KsqlRequest;
import info.matsumana.tsujun.model.ksql.KsqlResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class KsqlService {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    private final ObjectReader reader = new ObjectMapper()
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
            .readerFor(new TypeReference<KsqlResponse>() {});

    private final KsqlServerConfig ksqlServerConfig;

    public KsqlService(KsqlServerConfig ksqlServerConfig) {
        this.ksqlServerConfig = ksqlServerConfig;
    }

    public Flux<ResponseTable> sql(Request request) {

        KsqlRequest ksqlQuery = new KsqlRequest(request.getSql());

        logger.debug("KSQL server={}", ksqlServerConfig);

        return WebClient.create(ksqlServerConfig.getServer())
                        .post()
                        .uri("/query")
                        .body(Mono.just(ksqlQuery), KsqlRequest.class)
                        .retrieve()
                        .bodyToFlux(String.class)
                        .map(s -> {
                            logger.debug(s);

                            s = StringUtils.trimToEmpty(s);
                            if (Strings.isNullOrEmpty(s)) {
                                return new KsqlResponse();
                            } else {
                                try {
                                    return reader.readValue(s);
                                } catch (IOException ignore) {
                                    return new KsqlResponse();
                                }
                            }
                        })
                        .filter(res -> res.getRow() != null)
                        .map(ksqlResponse -> {
                            ResponseTable responseTable = new ResponseTable();
                            responseTable.setData(ksqlResponse.getRow());
                            responseTable.setSequence(request.getSequence());
                            responseTable.setMode(1);
                            responseTable.setSql(request.getSql());
                            return responseTable;
                        });
    }
}
