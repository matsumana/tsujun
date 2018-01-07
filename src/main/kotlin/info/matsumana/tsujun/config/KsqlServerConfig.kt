package info.matsumana.tsujun.config

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Configuration

/**
 * see also:
 * https://github.com/spring-projects/spring-boot/wiki/Relaxed-Binding-2.0#environment-variables
 */
@Configuration
@ConfigurationProperties(prefix = "ksql")
data class KsqlServerConfig(var server: String = "http://localhost:8080")
