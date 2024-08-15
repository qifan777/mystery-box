package io.github.qifan777.server.menu.repository;

import io.github.qifan777.server.infrastructure.model.QueryRequest;
import io.github.qifan777.server.menu.entity.Menu;
import io.github.qifan777.server.menu.entity.MenuFetcher;
import io.github.qifan777.server.menu.entity.MenuTable;
import io.github.qifan777.server.menu.entity.dto.MenuSpec;
import io.github.qifan777.server.user.root.entity.UserFetcher;
import org.babyfish.jimmer.spring.repository.JRepository;
import org.babyfish.jimmer.spring.repository.SpringOrders;
import org.babyfish.jimmer.spring.repository.support.SpringPageFactory;
import org.babyfish.jimmer.sql.fetcher.Fetcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface MenuRepository extends JRepository<Menu, String> {

  MenuTable menuTable = MenuTable.$;
  MenuFetcher COMPLEX_FETCHER = MenuFetcher.$.allScalarFields()
      .creator(UserFetcher.$.phone().nickname())
      .editor(UserFetcher.$.phone().nickname());
  MenuFetcher SIMPLE_FETCHER = MenuFetcher.$.allScalarFields();

  default Page<Menu> findPage(QueryRequest<MenuSpec> queryRequest, Fetcher<Menu> fetcher) {
    MenuSpec query = queryRequest.getQuery();
    Pageable pageable = queryRequest.toPageable();
    return sql().createQuery(menuTable)
        .where(query)
        .orderBy(SpringOrders.toOrders(menuTable, pageable.getSort()))
        .select(menuTable.fetch(fetcher))
        .fetchPage(queryRequest.getPageNum() - 1, queryRequest.getPageSize(),
            SpringPageFactory.getInstance());
  }
}