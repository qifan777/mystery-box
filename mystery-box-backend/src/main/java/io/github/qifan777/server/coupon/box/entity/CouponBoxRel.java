package io.github.qifan777.server.coupon.box.entity;

import io.github.qifan777.server.box.root.entity.MysteryBox;
import io.github.qifan777.server.coupon.root.entity.Coupon;
import io.github.qifan777.server.infrastructure.jimmer.BaseEntity;
import io.qifan.infrastructure.generator.core.GenAssociationField;
import io.qifan.infrastructure.generator.core.GenEntity;
import org.babyfish.jimmer.sql.Entity;
import org.babyfish.jimmer.sql.ManyToOne;

/**
 * 优惠券可使用的商品
 */
@GenEntity
@Entity
public interface CouponBoxRel extends BaseEntity {

    /**
     * 优惠券
     */
    @ManyToOne
    @GenAssociationField(label = "优惠券", prop = "couponId")
    Coupon coupon();

    /**
     * 盲盒id
     */
    @ManyToOne
    @GenAssociationField(label = "盲盒", prop = "boxId")
    MysteryBox box();
}

