package io.github.qifan777.server.vip.level.entity;

import io.github.qifan777.server.infrastructure.jimmer.BaseEntity;
import io.qifan.infrastructure.generator.core.GenEntity;
import io.qifan.infrastructure.generator.core.GenNumberField;
import io.qifan.infrastructure.generator.core.GenTextField;
import org.babyfish.jimmer.sql.Entity;

import java.math.BigDecimal;

/**
 * Vip等级
 */
@GenEntity
@Entity
public interface VipLevel extends BaseEntity {

    /**
     * 等级名称
     */
    @GenTextField(label = "等级名称", order = 0)
    String name();

    /**
     * 价格
     */
    @GenNumberField(label = "价格", order = 1)
    BigDecimal price();

    @GenNumberField(label = "天数", order = 2)
    int days();
}

