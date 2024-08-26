package io.github.qifan777.server.box.category.repository;

import io.github.qifan777.server.box.category.entity.MysteryBoxCategory;
import io.github.qifan777.server.box.category.entity.MysteryBoxCategoryFetcher;
import io.github.qifan777.server.box.category.entity.MysteryBoxCategoryTable;
import io.github.qifan777.server.box.category.entity.dto.MysteryBoxCategorySpec;
import io.github.qifan777.server.infrastructure.model.QueryRequest;
import io.github.qifan777.server.user.root.entity.UserFetcher;
import org.babyfish.jimmer.spring.repository.JRepository;
import org.babyfish.jimmer.spring.repository.SpringOrders;
import org.babyfish.jimmer.spring.repository.support.SpringPageFactory;
import org.babyfish.jimmer.sql.fetcher.Fetcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface MysteryBoxCategoryRepository extends JRepository<MysteryBoxCategory, String> {
    MysteryBoxCategoryTable t = MysteryBoxCategoryTable.$;
    MysteryBoxCategoryFetcher COMPLEX_FETCHER_FOR_ADMIN = MysteryBoxCategoryFetcher.$.allScalarFields()
        .creator(UserFetcher.$.phone().nickname())
        .editor(UserFetcher.$.phone().nickname());
    MysteryBoxCategoryFetcher COMPLEX_FETCHER_FOR_FRONT = MysteryBoxCategoryFetcher.$.allScalarFields()
            .creator(true);
  default Page<MysteryBoxCategory> findPage(QueryRequest<MysteryBoxCategorySpec> queryRequest,
                                            Fetcher<MysteryBoxCategory> fetcher) {
    MysteryBoxCategorySpec query = queryRequest.getQuery();
    Pageable pageable = queryRequest.toPageable();
    return sql().createQuery(t)
        .where(query)
        .orderBy(SpringOrders.toOrders(t, pageable.getSort()))
        .select(t.fetch(fetcher))
        .fetchPage(queryRequest.getPageNum() - 1, queryRequest.getPageSize(),
            SpringPageFactory.getInstance());
  }
}