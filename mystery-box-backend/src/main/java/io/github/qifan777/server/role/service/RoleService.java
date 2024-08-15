package io.github.qifan777.server.role.service;

import io.qifan.infrastructure.common.constants.ResultCode;
import io.qifan.infrastructure.common.exception.BusinessException;
import io.github.qifan777.server.infrastructure.model.QueryRequest;
import io.github.qifan777.server.role.entity.Role;
import io.github.qifan777.server.role.entity.RoleDraft;
import io.github.qifan777.server.role.entity.dto.RoleInput;
import io.github.qifan777.server.role.entity.dto.RoleSpec;
import io.github.qifan777.server.role.repository.RoleRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
@Slf4j
@AllArgsConstructor
@Transactional
public class RoleService {

  private final RoleRepository roleRepository;

  public Role findById(String id) {
    return roleRepository.findById(id, RoleRepository.ROLE_MENU_FETCHER)
        .orElseThrow(() -> new BusinessException(
            ResultCode.NotFindError, "数据不存在"));
  }

  public String save(RoleInput roleInput) {
    Role role = roleInput.toEntity();
    return roleRepository.save(RoleDraft.$.produce(role, draft -> {
      draft.setMenus(new ArrayList<>());
      Arrays.stream(roleInput.getMenuIds()).forEach(menuId -> {
        draft.addIntoMenus(roleMenuRelDraft -> roleMenuRelDraft.setRole(role)
            .applyMenu(menuDraft -> menuDraft.setId(menuId)));
      });
    })).id();
  }

  public Page<Role> query(QueryRequest<RoleSpec> queryRequest) {
    return roleRepository.findPage(queryRequest, RoleRepository.COMPLEX_FETCHER);
  }

  public boolean delete(List<String> ids) {
    roleRepository.deleteAllById(ids);
    return true;
  }
}