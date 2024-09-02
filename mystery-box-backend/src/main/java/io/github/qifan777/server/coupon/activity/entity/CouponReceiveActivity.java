package io.github.qifan777.server.coupon.activity.entity;

import io.github.qifan777.server.coupon.root.entity.Coupon;
import io.github.qifan777.server.infrastructure.jimmer.BaseEntity;
import io.qifan.infrastructure.generator.core.GenAssociationField;
import io.qifan.infrastructure.generator.core.GenDateTimeField;
import io.qifan.infrastructure.generator.core.GenEntity;
import org.babyfish.jimmer.sql.Entity;
import org.babyfish.jimmer.sql.ManyToOne;

import java.time.LocalDateTime;

/**
 * 优惠券领取活动
 */
@GenEntity
@Entity
public interface CouponReceiveActivity extends BaseEntity {

    /**
     * 优惠券id
     */
    @GenAssociationField(label = "优惠券", order = 0, prop = "couponId")
    @ManyToOne
    Coupon coupon();

    /**
     * 活动生效时间
     */
    @GenDateTimeField(label = "活动生效时间", order = 1)
    LocalDateTime effectiveDate();

    /**
     * 活动失效时间
     */
    @GenDateTimeField(label = "活动失效时间", order = 2)
    LocalDateTime expirationDate();
}

