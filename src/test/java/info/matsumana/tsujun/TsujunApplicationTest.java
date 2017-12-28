package info.matsumana.tsujun;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import info.matsumana.tsujun.service.KsqlService;

@SpringJUnitConfig(TsujunApplication.class)
public class TsujunApplicationTest {

    @Autowired
    private KsqlService ksqlService;

    @Test
    void contextLoads() {
        assertNotNull(ksqlService);
    }

}
