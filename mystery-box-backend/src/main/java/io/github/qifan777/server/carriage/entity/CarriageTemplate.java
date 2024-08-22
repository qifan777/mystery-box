package io.github.qifan777.server.carriage.entity;

import io.github.qifan777.server.infrastructure.jimmer.BaseEntity;
import io.github.qifan777.server.carriage.entity.model.CarriageConfig;
import io.qifan.infrastructure.generator.core.GenBooleanField;
import io.qifan.infrastructure.generator.core.GenEntity;
import io.qifan.infrastructure.generator.core.GenTextAreaField;
import io.qifan.infrastructure.generator.core.GenTextField;
import org.babyfish.jimmer.sql.Entity;
import org.babyfish.jimmer.sql.Serialized;

import java.util.List;


/**
 * 运费模板
 */
@GenEntity
@Entity
public interface CarriageTemplate extends BaseEntity {
    /**
     * 模板名称
     */
    @GenTextField(label = "模板名称", order = 1)
    String name();

    @GenTextAreaField(label = "模板描述", order = 2)
    String description();

    /**
     * 是否生效（全局只能存在一个生效）
     */
    @GenBooleanField
    boolean valid();

    /**
     * 运费配置
     */
    @Serialized
    List<CarriageConfig> configs();
}

