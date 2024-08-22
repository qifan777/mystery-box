package io.github.qifan777.server.payment.entity;

import io.github.qifan777.server.dict.model.DictConstants;
import io.github.qifan777.server.infrastructure.jimmer.BaseEntity;
import io.github.qifan777.server.order.entity.BaseOrder;
import io.qifan.infrastructure.generator.core.GenEntity;
import jakarta.validation.constraints.Null;
import org.babyfish.jimmer.sql.Entity;
import org.babyfish.jimmer.sql.OneToOne;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 订单支付详情
 */
@GenEntity
@Entity
public interface Payment extends BaseEntity {

    /**
     * 支付类型
     */
    DictConstants.PayType payType();

    /**
     * 基础订单
     */
    @Null
    @OneToOne(mappedBy = "payment")
    BaseOrder baseOrder();

    /**
     * 支付时间
     */
    @Null
    LocalDateTime payTime();

    /**
     * 实际支付金额
     */
    BigDecimal payAmount();

    /**
     * vip优化金额
     */
    BigDecimal vipAmount();

    /**
     * 优惠券减免金额
     */
    BigDecimal couponAmount();

    /**
     * 商品金额
     */
    BigDecimal productAmount();

    /**
     * 配送费
     */
    BigDecimal deliveryFee();

    /**
     * 外部订单号
     */
    @Null
    String tradeNo();
}

