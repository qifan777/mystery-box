package io.github.qifan777.server.vip.pack.entity;

import io.github.qifan777.server.infrastructure.jimmer.BaseEntity;
import io.qifan.infrastructure.generator.core.GenEntity;
import io.qifan.infrastructure.generator.core.GenNumberField;
import io.qifan.infrastructure.generator.core.GenTextField;
import org.babyfish.jimmer.sql.Entity;

import java.math.BigDecimal;

/**
 * Vip套餐
 */
@GenEntity
@Entity
public interface VipPackage extends BaseEntity {

    /**
     * 套餐名称
     */
    @GenTextField(label = "套餐名称", order = 0)
    String name();

    /**
     * 价格
     */
    @GenNumberField(label = "价格", order = 1)
    BigDecimal price();

    @GenNumberField(label = "天数", order = 2)
    int days();
}

