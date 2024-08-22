package io.github.qifan777.server.refund.entity;

import com.github.binarywang.wxpay.bean.notify.WxPayRefundNotifyV3Result;
import com.github.binarywang.wxpay.bean.result.WxPayRefundV3Result;
import io.github.qifan777.server.dict.model.DictConstants;
import io.github.qifan777.server.infrastructure.jimmer.BaseEntity;
import io.qifan.infrastructure.generator.core.GenEntity;
import jakarta.validation.constraints.Null;
import org.babyfish.jimmer.sql.Entity;
import org.babyfish.jimmer.sql.Serialized;

import java.math.BigDecimal;

/**
 * 退款记录
 */
@GenEntity
@Entity
public interface RefundRecord extends BaseEntity {
    /**
     * 基础订单id
     */
    String orderId();

    /**
     * 退款理由
     */
    String reason();

    /**
     * 退款金额
     */
    BigDecimal amount();

    /**
     * 状态
     */
    DictConstants.RefundStatus status();

    /**
     * 退款申请详情
     */
    @Serialized
    WxPayRefundV3Result refundApplicationDetails();

    @Serialized
    @Null
    WxPayRefundNotifyV3Result.DecryptNotifyResult refundNotifyDetails();

    /**
     * 微信退款订单id
     */
    @Null
    String refundId();
}

