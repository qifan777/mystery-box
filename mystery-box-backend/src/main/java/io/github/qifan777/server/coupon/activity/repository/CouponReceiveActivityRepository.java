package io.github.qifan777.server.coupon.activity.repository;

import io.github.qifan777.server.coupon.activity.entity.CouponReceiveActivity;
import io.github.qifan777.server.coupon.activity.entity.CouponReceiveActivityFetcher;
import io.github.qifan777.server.coupon.activity.entity.CouponReceiveActivityTable;
import io.github.qifan777.server.coupon.activity.entity.dto.CouponReceiveActivitySpec;
import io.github.qifan777.server.infrastructure.model.QueryRequest;
import io.github.qifan777.server.user.root.entity.UserFetcher;
import org.babyfish.jimmer.spring.repository.JRepository;
import org.babyfish.jimmer.spring.repository.SpringOrders;
import org.babyfish.jimmer.spring.repository.support.SpringPageFactory;
import org.babyfish.jimmer.sql.fetcher.Fetcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CouponReceiveActivityRepository extends JRepository<CouponReceiveActivity, String> {
    CouponReceiveActivityTable t = CouponReceiveActivityTable.$;
    CouponReceiveActivityFetcher COMPLEX_FETCHER_FOR_ADMIN = CouponReceiveActivityFetcher.$.allScalarFields()
            .creator(UserFetcher.$.phone().nickname())
            .editor(UserFetcher.$.phone().nickname());
    CouponReceiveActivityFetcher COMPLEX_FETCHER_FOR_FRONT = CouponReceiveActivityFetcher.$.allScalarFields()
            .creator(true);

    default Page<CouponReceiveActivity> findPage(QueryRequest<CouponReceiveActivitySpec> queryRequest,
                                                 Fetcher<CouponReceiveActivity> fetcher) {
        CouponReceiveActivitySpec query = queryRequest.getQuery();
        Pageable pageable = queryRequest.toPageable();
        return sql().createQuery(t)
                .where(query)
                .orderBy(SpringOrders.toOrders(t, pageable.getSort()))
                .select(t.fetch(fetcher))
                .fetchPage(queryRequest.getPageNum() - 1, queryRequest.getPageSize(),
                        SpringPageFactory.getInstance());
    }
}