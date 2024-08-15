package io.github.qifan777.server.role.entity;

import io.github.qifan777.server.infrastructure.jimmer.BaseEntity;
import io.github.qifan777.server.menu.entity.Menu;
import org.babyfish.jimmer.sql.*;

@Entity
public interface RoleMenuRel extends BaseEntity {

  @OnDissociate(DissociateAction.DELETE)
  @ManyToOne
  @Key
  Role role();

  @OnDissociate(DissociateAction.DELETE)
  @ManyToOne
  @Key
  Menu menu();
}
