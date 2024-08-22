package io.github.qifan777.server.vip.root.repository;

import cn.dev33.satoken.stp.StpUtil;
import io.github.qifan777.server.infrastructure.model.QueryRequest;
import io.github.qifan777.server.user.root.entity.UserFetcher;
import io.github.qifan777.server.vip.root.entity.Vip;
import io.github.qifan777.server.vip.root.entity.VipFetcher;
import io.github.qifan777.server.vip.root.entity.VipTable;
import io.github.qifan777.server.vip.root.entity.dto.VipSpec;
import org.babyfish.jimmer.spring.repository.JRepository;
import org.babyfish.jimmer.spring.repository.SpringOrders;
import org.babyfish.jimmer.spring.repository.support.SpringPageFactory;
import org.babyfish.jimmer.sql.fetcher.Fetcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface VipRepository extends JRepository<Vip, String> {
    VipTable t = VipTable.$;
    VipFetcher COMPLEX_FETCHER_FOR_ADMIN = VipFetcher.$.allScalarFields()
            .user(UserFetcher.$.allScalarFields())
            .creator(UserFetcher.$.phone().nickname())
            .editor(UserFetcher.$.phone().nickname());
    VipFetcher COMPLEX_FETCHER_FOR_FRONT = VipFetcher.$.allScalarFields()
            .creator(true);

    default Page<Vip> findPage(QueryRequest<VipSpec> queryRequest,
                               Fetcher<Vip> fetcher) {
        VipSpec query = queryRequest.getQuery();
        Pageable pageable = queryRequest.toPageable();
        return sql().createQuery(t)
                .where(query)
                .orderBy(SpringOrders.toOrders(t, pageable.getSort()))
                .select(t.fetch(fetcher))
                .fetchPage(queryRequest.getPageNum() - 1, queryRequest.getPageSize(),
                        SpringPageFactory.getInstance());
    }

    default Optional<Vip> findCurrentUserVip() {
        return sql().createQuery(t)
                .where(t.userId().eq(StpUtil.getLoginIdAsString()))
                .select(t.fetch(COMPLEX_FETCHER_FOR_FRONT))
                .fetchOptional();
    }
}