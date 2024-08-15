package io.github.qifan777.server.user.root.entity;

import io.github.qifan777.server.infrastructure.jimmer.BaseEntity;
import io.github.qifan777.server.role.entity.Role;
import org.babyfish.jimmer.sql.*;

@Entity
public interface UserRoleRel extends BaseEntity {

    @ManyToOne
    @Key
    @OnDissociate(DissociateAction.DELETE)
    User user();

    @OnDissociate(DissociateAction.DELETE)
    @ManyToOne
    @Key
    Role role();
}
