package io.github.qifan777.server.coupon.category.repository;

import io.github.qifan777.server.coupon.category.entity.CouponCategoryRel;
import io.github.qifan777.server.coupon.category.entity.CouponCategoryRelFetcher;
import io.github.qifan777.server.coupon.category.entity.CouponCategoryRelTable;
import io.github.qifan777.server.coupon.category.entity.dto.CouponCategoryRelSpec;
import io.github.qifan777.server.infrastructure.model.QueryRequest;
import io.github.qifan777.server.user.root.entity.UserFetcher;
import org.babyfish.jimmer.spring.repository.JRepository;
import org.babyfish.jimmer.spring.repository.SpringOrders;
import org.babyfish.jimmer.spring.repository.support.SpringPageFactory;
import org.babyfish.jimmer.sql.fetcher.Fetcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CouponCategoryRelRepository extends JRepository<CouponCategoryRel, String> {
    CouponCategoryRelTable t = CouponCategoryRelTable.$;
    CouponCategoryRelFetcher COMPLEX_FETCHER_FOR_ADMIN = CouponCategoryRelFetcher.$.allScalarFields()
            .creator(UserFetcher.$.phone().nickname())
            .editor(UserFetcher.$.phone().nickname());
    CouponCategoryRelFetcher COMPLEX_FETCHER_FOR_FRONT = CouponCategoryRelFetcher.$.allScalarFields()
            .creator(true);

    default Page<CouponCategoryRel> findPage(QueryRequest<CouponCategoryRelSpec> queryRequest,
                                             Fetcher<CouponCategoryRel> fetcher) {
        CouponCategoryRelSpec query = queryRequest.getQuery();
        Pageable pageable = queryRequest.toPageable();
        return sql().createQuery(t)
                .where(query)
                .orderBy(SpringOrders.toOrders(t, pageable.getSort()))
                .select(t.fetch(fetcher))
                .fetchPage(queryRequest.getPageNum() - 1, queryRequest.getPageSize(),
                        SpringPageFactory.getInstance());
    }
}