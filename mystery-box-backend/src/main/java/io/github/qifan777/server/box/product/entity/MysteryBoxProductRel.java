package io.github.qifan777.server.box.product.entity;

import io.qifan.infrastructure.generator.core.GenEntity;
import io.github.qifan777.server.box.root.entity.MysteryBox;
import io.github.qifan777.server.infrastructure.jimmer.BaseEntity;
import io.github.qifan777.server.product.root.entity.Product;
import org.babyfish.jimmer.sql.*;

/**
 * Entity for table "mystery_box_product_rel"
 */
@GenEntity
@Entity
public interface MysteryBoxProductRel extends BaseEntity {

    /**
     * 盲盒
     */
    @OnDissociate(DissociateAction.DELETE)
    @ManyToOne
    @Key
    MysteryBox mysteryBox();

    @IdView
    String mysteryBoxId();

    @OnDissociate(DissociateAction.DELETE)
    @ManyToOne
    @Key
    Product product();

    /**
     * 商品
     */
    @IdView
    String productId();
}

