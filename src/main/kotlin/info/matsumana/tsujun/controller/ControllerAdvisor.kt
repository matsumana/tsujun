package info.matsumana.tsujun.controller

import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.KotlinModule
import com.fasterxml.jackson.module.kotlin.readValue
import info.matsumana.tsujun.exception.KsqlException
import info.matsumana.tsujun.model.ResponseError
import info.matsumana.tsujun.model.ksql.KsqlResponseErrorMessage
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler

@ControllerAdvice
class ControllerAdvisor {

    companion object {
        private val mapper = ObjectMapper().registerModule(KotlinModule())
                .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
    }

    @ExceptionHandler(KsqlException::class)
    fun handleKsqlException(e: KsqlException): ResponseEntity<ResponseError> {
        val errorMessage = mapper.readValue<KsqlResponseErrorMessage>(e.message)
        return ResponseEntity
                .status(e.statusCode)
                .body(ResponseError(e.sequence, e.sql, errorMessage.message))
    }
}
