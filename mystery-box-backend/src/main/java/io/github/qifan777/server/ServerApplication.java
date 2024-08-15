package io.github.qifan777.server;

import io.github.qifan777.server.infrastructure.model.TenantMapProperty;
import io.github.qifan777.server.infrastructure.model.WxPayPropertiesExtension;
import org.babyfish.jimmer.client.EnableImplicitApi;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@EnableAsync
@SpringBootApplication
@EnableImplicitApi
@EnableConfigurationProperties(value = {WxPayPropertiesExtension.class, TenantMapProperty.class})
public class ServerApplication {
    public static void main(String[] args) {
        SpringApplication.run(ServerApplication.class, args);
    }
}
