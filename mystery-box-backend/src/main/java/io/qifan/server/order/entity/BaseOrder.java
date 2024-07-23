package io.qifan.server.order.entity;

import io.qifan.server.address.entity.dto.AddressView;
import io.qifan.server.infrastructure.jimmer.BaseEntity;
import jakarta.validation.constraints.Null;
import org.babyfish.jimmer.sql.Entity;
import org.babyfish.jimmer.sql.Serialized;

/**
 * Entity for table "base_order"
 */
@Entity
public interface BaseOrder extends BaseEntity {

    /**
     * 支付订单id
     */
    String paymentId();

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
    String couponUserId();

    /**
     * 订单类型
     */
    String type();
}

