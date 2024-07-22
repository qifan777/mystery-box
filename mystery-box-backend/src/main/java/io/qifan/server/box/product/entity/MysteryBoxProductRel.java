package io.qifan.server.box.product.entity;

import io.qifan.server.box.root.entity.MysteryBox;
import io.qifan.server.infrastructure.jimmer.BaseEntity;
import io.qifan.server.product.root.entity.Product;
import org.babyfish.jimmer.sql.Entity;
import org.babyfish.jimmer.sql.IdView;
import org.babyfish.jimmer.sql.ManyToOne;

/**
 * Entity for table "mystery_box_product_rel"
 */
@Entity
public interface MysteryBoxProductRel extends BaseEntity {

    /**
     * 盲盒
     */
    @ManyToOne
    MysteryBox mysteryBox();

    @IdView
    String mysteryBoxId();

    @ManyToOne
    Product product();

    /**
     * 商品
     */
    @IdView
    String productId();
}

