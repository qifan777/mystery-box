package io.github.qifan777.server.infrastructure.model;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(
        prefix = "tenant.map"
)
@Data
public class TenantMapProperty {
    private String key;
}
