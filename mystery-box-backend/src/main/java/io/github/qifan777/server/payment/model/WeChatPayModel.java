package io.github.qifan777.server.payment.model;

import io.github.qifan777.server.order.entity.BaseOrder;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class WeChatPayModel {
    private BaseOrder baseOrder;
    private String notifyUrl;
    private int expiredMinutes;
}
