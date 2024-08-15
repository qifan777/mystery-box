package io.github.qifan777.server.dict.repository;

import io.github.qifan777.server.dict.entity.Dict;
import io.github.qifan777.server.dict.entity.DictFetcher;
import io.github.qifan777.server.dict.entity.DictTable;
import io.github.qifan777.server.dict.entity.dto.DictSpec;
import io.github.qifan777.server.infrastructure.model.QueryRequest;
import io.github.qifan777.server.user.root.entity.UserFetcher;
import org.babyfish.jimmer.spring.repository.JRepository;
import org.babyfish.jimmer.spring.repository.SpringOrders;
import org.babyfish.jimmer.spring.repository.support.SpringPageFactory;
import org.babyfish.jimmer.sql.fetcher.Fetcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface DictRepository extends JRepository<Dict, String> {

  DictTable dictTable = DictTable.$;
  DictFetcher COMPLEX_FETCHER = DictFetcher.$.allScalarFields()
      .creator(UserFetcher.$.phone().nickname())
      .editor(UserFetcher.$.phone().nickname());

  default Page<Dict> findPage(QueryRequest<DictSpec> queryRequest, Fetcher<Dict> fetcher) {
    DictSpec query = queryRequest.getQuery();
    Pageable pageable = queryRequest.toPageable();
    return sql().createQuery(dictTable)
        .where(query)
        .orderBy(SpringOrders.toOrders(dictTable, pageable.getSort()))
        .select(dictTable.fetch(fetcher))
        .fetchPage(queryRequest.getPageNum() - 1, queryRequest.getPageSize(),
            SpringPageFactory.getInstance());
  }
}