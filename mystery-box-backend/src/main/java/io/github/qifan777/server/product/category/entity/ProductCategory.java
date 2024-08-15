package io.github.qifan777.server.product.category.entity;

import io.qifan.infrastructure.generator.core.*;
import io.github.qifan777.server.infrastructure.jimmer.BaseEntity;
import jakarta.validation.constraints.Null;
import org.babyfish.jimmer.sql.Entity;
import org.babyfish.jimmer.sql.IdView;
import org.babyfish.jimmer.sql.ManyToOne;


/**
 * 商品类别
 */
@GenEntity
@Entity
public interface ProductCategory extends BaseEntity {

    /**
     * 类别名称
     */
    @GenTextField(label = "类别名称", order = 0)
    String name();

    /**
     * 父亲类别id
     */
    @Null
    @GenAssociationField(label = "父亲类别", order = 1, prop = "parentId")
    @ManyToOne
    ProductCategory parent();

    @Null
    @IdView
    String parentId();

    /**
     * 类别图标
     */
    @Null
    @GenImageField(label = "类别图标", order = 2)
    String icon();

    /**
     * 描述
     */
    @GenTextAreaField(label = "描述", order = 3)
    String description();

    /**
     * 排序号
     */
    @GenNumberField(label = "排序号", order = 4)
    int sortOrder();
}

