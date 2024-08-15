package io.github.qifan777.server.box.root.entity;

import io.qifan.infrastructure.generator.core.*;
import io.github.qifan777.server.box.product.entity.MysteryBoxProductRel;
import io.github.qifan777.server.infrastructure.jimmer.BaseEntity;
import io.github.qifan777.server.product.root.entity.Product;
import org.babyfish.jimmer.sql.Entity;
import org.babyfish.jimmer.sql.ManyToManyView;
import org.babyfish.jimmer.sql.OneToMany;

import java.math.BigDecimal;
import java.util.List;

/**
 * 盲盒
 */
@GenEntity
@Entity
public interface MysteryBox extends BaseEntity {

    /**
     * 盲盒名字
     */
    @GenTextField(label = "盲盒名字", order = 0)
    String name();

    /**
     * 盲盒详情
     */
    @GenTextAreaField(label = "盲盒详情", order = 1)
    String details();

    /**
     * 购买提示
     */
    @GenTextField(label = "购买提示", order = 2)
    String tips();

    /**
     * 价格
     */
    @GenNumberField(label = "价格", order = 3)
    BigDecimal price();

    /**
     * 封面
     */
    @GenImageField(label = "封面", order = 4)
    String cover();

    @OneToMany(mappedBy = "mysteryBox")
    List<MysteryBoxProductRel> boxRelList();

    @ManyToManyView(prop = "boxRelList")
    List<Product> products();
}

