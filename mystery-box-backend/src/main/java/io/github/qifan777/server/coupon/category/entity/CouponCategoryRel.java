package io.github.qifan777.server.coupon.category.entity;

import io.github.qifan777.server.box.category.entity.MysteryBoxCategory;
import io.github.qifan777.server.coupon.root.entity.Coupon;
import io.github.qifan777.server.infrastructure.jimmer.BaseEntity;
import io.qifan.infrastructure.generator.core.GenAssociationField;
import io.qifan.infrastructure.generator.core.GenEntity;
import org.babyfish.jimmer.sql.Entity;
import org.babyfish.jimmer.sql.ManyToOne;

/**
 * 优惠券可使用的商品类别
 */
@GenEntity
@Entity
public interface CouponCategoryRel extends BaseEntity {
    /**
     * 优惠券id
     */
    @GenAssociationField(label = "优惠券", prop = "couponId")
    @ManyToOne
    Coupon coupon();

    /**
     * 分类id
     */
    @GenAssociationField(label = "商品类别", prop = "categoryId")
    @ManyToOne
    MysteryBoxCategory category();
}

