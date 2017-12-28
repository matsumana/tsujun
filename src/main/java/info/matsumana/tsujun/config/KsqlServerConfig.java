package info.matsumana.tsujun.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * see also:
 * https://github.com/spring-projects/spring-boot/wiki/Relaxed-Binding-2.0#environment-variables
 */
@Configuration
@ConfigurationProperties(prefix = "ksql.api")
public class KsqlServerConfig {

    // environment variable KSQL_API_SERVER
    private String server = "http://localhost:8080";

    public String getServer() {
        return server;
    }

    public void setServer(String server) {
        this.server = server;
    }

    @Override
    public String toString() {
        return "KsqlServerConfig{" +
               "server='" + server + '\'' +
               '}';
    }
}
