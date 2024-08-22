package io.github.qifan777.server.coupon.root.entity;

import io.github.qifan777.server.dict.model.DictConstants;
import io.github.qifan777.server.infrastructure.jimmer.BaseEntity;
import io.qifan.infrastructure.generator.core.*;
import org.babyfish.jimmer.sql.Entity;
import org.babyfish.jimmer.sql.Key;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 优惠券
 */
@GenEntity
@Entity
public interface Coupon extends BaseEntity {
    /**
     * 优惠券名称
     */
    @Key
    @GenTextField(label = "优惠券名称", order = 0)
    String name();

    /**
     * 门槛金额
     */
    @GenNumberField(label = "门槛金额", order = 1)
    BigDecimal thresholdAmount();

    /**
     * 发放数量
     */
    @GenNumberField(label = "发放数量", order = 2)
    Integer releasedQuantity();

    /**
     * 生效时间
     */
    @GenDateTimeField(label = "生效时间", order = 3)
    LocalDateTime effectiveDate();

    /**
     * 过期时间
     */
    @GenDateTimeField(label = "过期时间", order = 4)
    LocalDateTime expirationDate();

    /**
     * 优惠类型
     */
    @GenDictField(label = "优惠类型", dictEnName = DictConstants.COUPON_TYPE, order = 5)
    DictConstants.CouponType couponType();

    /**
     * 使用范围类型
     */
    @GenDictField(label = "使用范围类型", dictEnName = DictConstants.COUPON_SCOPE_TYPE, order = 6)
    DictConstants.CouponScopeType scopeType();

    /**
     * 优惠金额
     */
    @GenNumberField(label = "优惠金额", order = 7)
    BigDecimal amount();

    /**
     * 折扣
     */
    @GenNumberField(label = "折扣", order = 8)
    BigDecimal discount();
}

