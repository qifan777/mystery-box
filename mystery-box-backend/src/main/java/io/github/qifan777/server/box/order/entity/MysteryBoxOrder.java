package io.github.qifan777.server.box.order.entity;

import io.github.qifan777.server.box.item.entity.MysteryBoxOrderItem;
import io.github.qifan777.server.dict.model.DictConstants;
import io.github.qifan777.server.infrastructure.jimmer.BaseEntity;
import io.github.qifan777.server.order.entity.BaseOrder;
import io.qifan.infrastructure.generator.core.GenDictField;
import io.qifan.infrastructure.generator.core.GenEntity;
import org.babyfish.jimmer.sql.Entity;
import org.babyfish.jimmer.sql.OneToMany;
import org.babyfish.jimmer.sql.OneToOne;

import java.util.List;

@GenEntity
@Entity
public interface MysteryBoxOrder extends BaseEntity {
    @OneToOne
    BaseOrder baseOrder();

    /**
     * 订单状态
     */
    @GenDictField(label = "订单状态", dictEnName = DictConstants.PRODUCT_ORDER_STATUS)
    DictConstants.ProductOrderStatus status();

    @OneToMany(mappedBy = "mysteryBoxOrder")
    List<MysteryBoxOrderItem> items();
}
