package io.github.qifan777.server.vip.level.repository;

import io.github.qifan777.server.infrastructure.model.QueryRequest;
import io.github.qifan777.server.user.root.entity.UserFetcher;
import io.github.qifan777.server.vip.level.entity.VipLevel;
import io.github.qifan777.server.vip.level.entity.VipLevelFetcher;
import io.github.qifan777.server.vip.level.entity.VipLevelTable;
import io.github.qifan777.server.vip.level.entity.dto.VipLevelSpec;
import org.babyfish.jimmer.spring.repository.JRepository;
import org.babyfish.jimmer.spring.repository.SpringOrders;
import org.babyfish.jimmer.spring.repository.support.SpringPageFactory;
import org.babyfish.jimmer.sql.fetcher.Fetcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface VipLevelRepository extends JRepository<VipLevel, String> {
    VipLevelTable t = VipLevelTable.$;
    VipLevelFetcher COMPLEX_FETCHER_FOR_ADMIN = VipLevelFetcher.$.allScalarFields()
            .creator(UserFetcher.$.phone().nickname())
            .editor(UserFetcher.$.phone().nickname());
    VipLevelFetcher COMPLEX_FETCHER_FOR_FRONT = VipLevelFetcher.$.allScalarFields()
            .creator(true);

    default Page<VipLevel> findPage(QueryRequest<VipLevelSpec> queryRequest,
                                    Fetcher<VipLevel> fetcher) {
        VipLevelSpec query = queryRequest.getQuery();
        Pageable pageable = queryRequest.toPageable();
        return sql().createQuery(t)
                .where(query)
                .orderBy(SpringOrders.toOrders(t, pageable.getSort()))
                .select(t.fetch(fetcher))
                .fetchPage(queryRequest.getPageNum() - 1, queryRequest.getPageSize(),
                        SpringPageFactory.getInstance());
    }
}