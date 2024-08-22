package io.github.qifan777.server.vip.order.entity;

import io.github.qifan777.server.infrastructure.jimmer.BaseEntity;
import io.github.qifan777.server.order.entity.BaseOrder;
import io.github.qifan777.server.user.root.entity.User;
import io.github.qifan777.server.vip.level.entity.VipLevel;
import io.qifan.infrastructure.generator.core.GenAssociationField;
import io.qifan.infrastructure.generator.core.GenEntity;
import org.babyfish.jimmer.sql.Entity;
import org.babyfish.jimmer.sql.ManyToOne;

/**
 * Vip订单
 */
@Entity
@GenEntity
public interface VipOrder extends BaseEntity {

    /**
     * 用户id
     */
    @GenAssociationField(label = "用户")
    @ManyToOne
    User user();

    /**
     * vip等级id
     */
    @GenAssociationField(label = "vip等级")
    @ManyToOne
    VipLevel vipLevel();

    /**
     * 订单id
     */
    @ManyToOne
    BaseOrder baseOrder();
}

