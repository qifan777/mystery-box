package io.github.qifan777.server.user.wechat.entity;

import io.qifan.infrastructure.generator.core.GenEntity;
import io.qifan.infrastructure.generator.core.GenField;
import io.github.qifan777.server.infrastructure.jimmer.BaseDateTime;
import io.github.qifan777.server.infrastructure.jimmer.UUIDIdGenerator;
import io.github.qifan777.server.user.root.entity.User;
import org.babyfish.jimmer.sql.*;


/**
 * Entity for table "user_wechat"
 */
@Entity
@GenEntity
public interface UserWeChat extends BaseDateTime {

    @Id
    @GeneratedValue(generatorType = UUIDIdGenerator.class)
    String id();

    @Key
    @GenField(value = "openId", order = 0)
    String openId();

    @OneToOne
    User user();
}

