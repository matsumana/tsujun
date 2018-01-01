package info.matsumana.tsujun

import info.matsumana.tsujun.service.KsqlService
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig

@SpringJUnitConfig(TsujunApplication::class)
class TsujunApplicationTest {

    @Autowired lateinit var ksqlService: KsqlService

    @Test
    fun contextLoads() {
        Assertions.assertNotNull(ksqlService)
    }
}
