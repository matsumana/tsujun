package info.matsumana.tsujun

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class TsujunApplication

fun main(args: Array<String>) {
    runApplication<TsujunApplication>(*args)
}
