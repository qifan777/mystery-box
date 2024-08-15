package io.github.qifan777.server.role.entity;

import io.qifan.infrastructure.generator.core.GenEntity;
import io.qifan.infrastructure.generator.core.GenField;
import io.github.qifan777.server.infrastructure.jimmer.BaseEntity;
import io.github.qifan777.server.menu.entity.Menu;
import io.github.qifan777.server.user.root.entity.UserRoleRel;
import org.babyfish.jimmer.sql.Entity;
import org.babyfish.jimmer.sql.Key;
import org.babyfish.jimmer.sql.ManyToManyView;
import org.babyfish.jimmer.sql.OneToMany;

import java.util.List;

@GenEntity
@Entity
public interface Role extends BaseEntity {

  @GenField(value = "角色名称")
  @Key
  String name();

  @OneToMany(mappedBy = "role")
  List<UserRoleRel> users();

  @OneToMany(mappedBy = "role")
  List<RoleMenuRel> menus();

  @ManyToManyView(prop = "menus")
  List<Menu> menusView();
}
