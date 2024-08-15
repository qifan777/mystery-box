package io.github.qifan777.server.role.repository;

import io.github.qifan777.server.infrastructure.model.QueryRequest;
import io.github.qifan777.server.role.entity.Role;
import io.github.qifan777.server.role.entity.RoleFetcher;
import io.github.qifan777.server.role.entity.RoleTable;
import io.github.qifan777.server.role.entity.dto.RoleSpec;
import io.github.qifan777.server.user.root.entity.UserFetcher;
import org.babyfish.jimmer.spring.repository.JRepository;
import org.babyfish.jimmer.spring.repository.SpringOrders;
import org.babyfish.jimmer.spring.repository.support.SpringPageFactory;
import org.babyfish.jimmer.sql.fetcher.Fetcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface RoleRepository extends JRepository<Role, String> {

    RoleTable roleTable = RoleTable.$;
    RoleFetcher COMPLEX_FETCHER = RoleFetcher.$.allScalarFields()
            .creator(UserFetcher.$.phone().nickname())
            .editor(UserFetcher.$.phone().nickname());
    RoleFetcher ROLE_MENU_FETCHER = RoleFetcher.$.allScalarFields().menusView(true);

    default Optional<Role> findRoleByName(String role) {
        return sql().createQuery(roleTable)
                .where(roleTable.name().eq("普通用户"))
                .select(roleTable)
                .fetchOptional();

    }

    default Page<Role> findPage(QueryRequest<RoleSpec> queryRequest, Fetcher<Role> fetcher) {
        RoleSpec query = queryRequest.getQuery();
        Pageable pageable = queryRequest.toPageable();
        return sql().createQuery(roleTable)
                .where(query)
                .orderBy(SpringOrders.toOrders(roleTable, pageable.getSort()))
                .select(roleTable.fetch(fetcher))
                .fetchPage(queryRequest.getPageNum() - 1, queryRequest.getPageSize(),
                        SpringPageFactory.getInstance());
    }
}