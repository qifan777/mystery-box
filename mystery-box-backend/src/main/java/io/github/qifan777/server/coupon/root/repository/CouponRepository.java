package io.github.qifan777.server.coupon.root.repository;

import io.github.qifan777.server.coupon.root.entity.Coupon;
import io.github.qifan777.server.coupon.root.entity.CouponFetcher;
import io.github.qifan777.server.coupon.root.entity.CouponTable;
import io.github.qifan777.server.coupon.root.entity.dto.CouponSpec;
import io.github.qifan777.server.infrastructure.model.QueryRequest;
import io.github.qifan777.server.user.root.entity.UserFetcher;
import org.babyfish.jimmer.spring.repository.JRepository;
import org.babyfish.jimmer.spring.repository.SpringOrders;
import org.babyfish.jimmer.spring.repository.support.SpringPageFactory;
import org.babyfish.jimmer.sql.fetcher.Fetcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CouponRepository extends JRepository<Coupon, String> {
    CouponTable t = CouponTable.$;
    CouponFetcher COMPLEX_FETCHER_FOR_ADMIN = CouponFetcher.$.allScalarFields()
            .creator(UserFetcher.$.phone().nickname())
            .editor(UserFetcher.$.phone().nickname());
    CouponFetcher COMPLEX_FETCHER_FOR_FRONT = CouponFetcher.$.allScalarFields()
            .creator(true);

    default Page<Coupon> findPage(QueryRequest<CouponSpec> queryRequest,
                                  Fetcher<Coupon> fetcher) {
        CouponSpec query = queryRequest.getQuery();
        Pageable pageable = queryRequest.toPageable();
        return sql().createQuery(t)
                .where(query)
                .orderBy(SpringOrders.toOrders(t, pageable.getSort()))
                .select(t.fetch(fetcher))
                .fetchPage(queryRequest.getPageNum() - 1, queryRequest.getPageSize(),
                        SpringPageFactory.getInstance());
    }
}