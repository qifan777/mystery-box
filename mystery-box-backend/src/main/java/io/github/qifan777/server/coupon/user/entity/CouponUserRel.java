package io.github.qifan777.server.coupon.user.entity;

import io.github.qifan777.server.coupon.root.entity.Coupon;
import io.github.qifan777.server.dict.model.DictConstants;
import io.github.qifan777.server.infrastructure.jimmer.BaseEntity;
import io.github.qifan777.server.user.root.entity.User;
import io.qifan.infrastructure.generator.core.GenAssociationField;
import io.qifan.infrastructure.generator.core.GenDictField;
import io.qifan.infrastructure.generator.core.GenEntity;
import org.babyfish.jimmer.sql.Entity;
import org.babyfish.jimmer.sql.ManyToOne;

/**
 * 用户拥有的优惠券
 */
@GenEntity
@Entity
public interface CouponUserRel extends BaseEntity {
    /**
     * 获取途径类型
     */
    @GenDictField(label = "获取途径类型", dictEnName = DictConstants.COUPON_RECEIVE_TYPE, order = 1)
    DictConstants.CouponReceiveType receiveType();

    /**
     * 优惠券
     */
    @GenAssociationField(label = "优惠券", order = 2, prop = "couponId")
    @ManyToOne
    Coupon coupon();

    /**
     * 优惠券状态
     */
    @GenDictField(label = "优惠券状态", dictEnName = DictConstants.COUPON_USE_STATUS, order = 3)
    DictConstants.CouponUseStatus status();

    /**
     * 用户信息
     */
    @GenAssociationField(label = "用户信息", order = 4, prop = "userId")
    @ManyToOne
    User user();
}

