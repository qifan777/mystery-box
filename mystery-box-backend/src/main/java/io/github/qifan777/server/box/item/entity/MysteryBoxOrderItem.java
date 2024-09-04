package io.github.qifan777.server.box.item.entity;

import io.github.qifan777.server.box.order.entity.MysteryBoxOrder;
import io.github.qifan777.server.box.root.entity.dto.MystryBoxView;
import io.github.qifan777.server.infrastructure.jimmer.BaseEntity;
import io.github.qifan777.server.product.root.entity.dto.ProductView;
import jakarta.validation.constraints.Null;
import org.babyfish.jimmer.sql.Entity;
import org.babyfish.jimmer.sql.Key;
import org.babyfish.jimmer.sql.ManyToOne;
import org.babyfish.jimmer.sql.Serialized;

import java.util.List;

@Entity
public interface MysteryBoxOrderItem extends BaseEntity {
    @Key
    String mysteryBoxId();

    int mysteryBoxCount();

    @Serialized
    MystryBoxView mysteryBox();

    @ManyToOne
    @Key
    MysteryBoxOrder mysteryBoxOrder();

    @Null
    @Serialized
    List<ProductView> products();
}
