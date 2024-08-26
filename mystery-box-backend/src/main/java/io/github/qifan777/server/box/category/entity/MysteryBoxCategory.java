package io.github.qifan777.server.box.category.entity;

import io.github.qifan777.server.infrastructure.jimmer.BaseEntity;
import io.qifan.infrastructure.generator.core.GenEntity;
import io.qifan.infrastructure.generator.core.GenImageField;
import io.qifan.infrastructure.generator.core.GenNumberField;
import io.qifan.infrastructure.generator.core.GenTextField;
import jakarta.validation.constraints.Null;
import org.babyfish.jimmer.sql.Entity;


/**
 * 盲盒类别
 */
@GenEntity
@Entity
public interface MysteryBoxCategory extends BaseEntity {

    /**
     * 类别名称
     */
    @GenTextField(label = "类别名称", order = 0)
    String name();

    /**
     * 类别图标
     */
    @GenImageField(label = "类别图标", order = 1)
    @Null
    String icon();

    /**
     * 描述
     */
    @GenTextField(label = "描述", order = 2)
    @Null
    String description();

    /**
     * 排序号
     */
    @GenNumberField(label = "排序号", order = 3)
    Integer sortOrder();
}

