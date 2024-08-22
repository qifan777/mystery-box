package io.github.qifan777.server.vip.config;

import lombok.Data;
import lombok.experimental.Accessors;

import java.math.BigDecimal;

@Data
@Accessors(chain = true)
public class VipConfig {
    BigDecimal discount;
    String description;
}
