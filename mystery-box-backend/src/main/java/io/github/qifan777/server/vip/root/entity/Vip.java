package io.github.qifan777.server.vip.root.entity;

import io.github.qifan777.server.infrastructure.jimmer.BaseEntity;
import io.github.qifan777.server.user.root.entity.User;
import io.qifan.infrastructure.generator.core.GenAssociationField;
import io.qifan.infrastructure.generator.core.GenDateTimeField;
import io.qifan.infrastructure.generator.core.GenEntity;
import org.babyfish.jimmer.sql.Entity;
import org.babyfish.jimmer.sql.Key;
import org.babyfish.jimmer.sql.ManyToOne;

import java.time.LocalDateTime;

/**
 * Vip信息
 */
@Entity
@GenEntity
public interface Vip extends BaseEntity {
    /**
     * 用户id
     */
    @Key
    @ManyToOne
    @GenAssociationField(label = "用户信息")
    User user();

    /**
     * 过期时间
     */
    @GenDateTimeField(label = "过期时间")
    LocalDateTime endTime();
}

