package io.github.qifan777.server.coupon.box.repository;

import io.github.qifan777.server.coupon.box.entity.CouponBoxRel;
import io.github.qifan777.server.coupon.box.entity.CouponBoxRelFetcher;
import io.github.qifan777.server.coupon.box.entity.CouponBoxRelTable;
import io.github.qifan777.server.coupon.box.entity.dto.CouponBoxRelSpec;
import io.github.qifan777.server.infrastructure.model.QueryRequest;
import io.github.qifan777.server.user.root.entity.UserFetcher;
import org.babyfish.jimmer.spring.repository.JRepository;
import org.babyfish.jimmer.spring.repository.SpringOrders;
import org.babyfish.jimmer.spring.repository.support.SpringPageFactory;
import org.babyfish.jimmer.sql.fetcher.Fetcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CouponBoxRelRepository extends JRepository<CouponBoxRel, String> {
    CouponBoxRelTable t = CouponBoxRelTable.$;
    CouponBoxRelFetcher COMPLEX_FETCHER_FOR_ADMIN = CouponBoxRelFetcher.$.allScalarFields()
            .creator(UserFetcher.$.phone().nickname())
            .editor(UserFetcher.$.phone().nickname());
    CouponBoxRelFetcher COMPLEX_FETCHER_FOR_FRONT = CouponBoxRelFetcher.$.allScalarFields()
            .creator(true);

    default Page<CouponBoxRel> findPage(QueryRequest<CouponBoxRelSpec> queryRequest,
                                        Fetcher<CouponBoxRel> fetcher) {
        CouponBoxRelSpec query = queryRequest.getQuery();
        Pageable pageable = queryRequest.toPageable();
        return sql().createQuery(t)
                .where(query)
                .orderBy(SpringOrders.toOrders(t, pageable.getSort()))
                .select(t.fetch(fetcher))
                .fetchPage(queryRequest.getPageNum() - 1, queryRequest.getPageSize(),
                        SpringPageFactory.getInstance());
    }
}