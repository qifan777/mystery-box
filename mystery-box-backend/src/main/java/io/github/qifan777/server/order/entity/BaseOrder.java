package io.github.qifan777.server.order.entity;

import io.github.qifan777.server.address.entity.dto.AddressView;
import io.github.qifan777.server.coupon.user.entity.CouponUserRel;
import io.github.qifan777.server.dict.model.DictConstants;
import io.github.qifan777.server.infrastructure.jimmer.BaseEntity;
import io.github.qifan777.server.payment.entity.Payment;
import jakarta.validation.constraints.Null;
import org.babyfish.jimmer.sql.Entity;
import org.babyfish.jimmer.sql.JoinColumn;
import org.babyfish.jimmer.sql.OneToOne;
import org.babyfish.jimmer.sql.Serialized;

/**
 * 基础订单
 */
@Entity
public interface BaseOrder extends BaseEntity {
    /**
     * 地址详情
     */
    @Serialized
    @Null
    AddressView address();

    /**
     * 备注
     */
    @Null
    String remark();

    /**
     * 物流单号
     */
    @Null
    String trackingNumber();

    /**
     * 用户优惠券id
     */
    @Null
    @JoinColumn(name = "coupon_user_id")
    @OneToOne
    CouponUserRel couponUser();

    /**
     * 订单类型
     */
    DictConstants.OrderType type();

    /**
     * 支付订单
     */
    @OneToOne
    Payment payment();
}

