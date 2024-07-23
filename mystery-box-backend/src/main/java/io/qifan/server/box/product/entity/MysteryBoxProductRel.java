package io.qifan.server.box.product.entity;

import io.qifan.infrastructure.generator.core.GenEntity;
import io.qifan.server.box.root.entity.MysteryBox;
import io.qifan.server.infrastructure.jimmer.BaseEntity;
import io.qifan.server.product.root.entity.Product;
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

