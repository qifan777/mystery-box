package io.github.qifan777.server.product.root.entity;

import io.qifan.infrastructure.generator.core.*;
import io.github.qifan777.server.infrastructure.jimmer.BaseEntity;
import io.github.qifan777.server.product.category.entity.ProductCategory;
import io.github.qifan777.server.product.root.model.KeyValue;
import org.babyfish.jimmer.sql.*;

import java.math.BigDecimal;
import java.util.List;

/**
 * 商品表
 */
@GenEntity
@Entity
public interface Product extends BaseEntity {

    /**
     * 名称
     */
    @GenTextField(label = "名称", order = 0)
    String name();

    /**
     * 价格
     */
    @GenNumberField(label = "价格", order = 1)
    BigDecimal price();

    /**
     * 封面
     */
    @GenImageField(label = "封面", order = 2)
    String cover();

    /**
     * 品牌
     */
    @GenTextField(label = "品牌", order = 3)
    String brand();

    /**
     * 类别id
     */
    @GenAssociationField(label = "类别", order = 4, prop = "categoryId")
    @ManyToOne
    ProductCategory category();

    @IdView
    String categoryId();

    /**
     * 描述
     */
    @GenTextAreaField(label = "描述", order = 5)
    String description();

    /**
     * 标签
     */
    @Serialized
    @GenValueField(label = "标签", order = 6)
    List<String> tags();

    /**
     * 规格
     */
    @Serialized
    @GenKeyValueField(label = "规格", order = 7)
    List<KeyValue> specifications();

    /**
     * 属性
     */
    @Serialized
    @GenKeyValueField(label = "属性", order = 8)
    List<KeyValue> attributes();
}

