package io.qifan.server.box.product.repository;

import io.qifan.server.box.product.entity.MysteryBoxProductRel;
import io.qifan.server.box.product.entity.MysteryBoxProductRelFetcher;
import io.qifan.server.box.product.entity.MysteryBoxProductRelTable;
import io.qifan.server.box.product.entity.dto.MysteryBoxProductRelSpec;
import io.qifan.server.infrastructure.model.QueryRequest;
import io.qifan.server.user.root.entity.UserFetcher;
import org.babyfish.jimmer.spring.repository.JRepository;
import org.babyfish.jimmer.spring.repository.SpringOrders;
import org.babyfish.jimmer.spring.repository.support.SpringPageFactory;
import org.babyfish.jimmer.sql.fetcher.Fetcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface MysteryBoxProductRelRepository extends JRepository<MysteryBoxProductRel, String> {
    MysteryBoxProductRelTable t = MysteryBoxProductRelTable.$;
    MysteryBoxProductRelFetcher COMPLEX_FETCHER_FOR_ADMIN = MysteryBoxProductRelFetcher.$.allScalarFields()
            .product()
            .creator(UserFetcher.$.phone().nickname())
            .editor(UserFetcher.$.phone().nickname());
    MysteryBoxProductRelFetcher COMPLEX_FETCHER_FOR_FRONT = MysteryBoxProductRelFetcher.$.allScalarFields()
            .creator(true);

    default Page<MysteryBoxProductRel> findPage(QueryRequest<MysteryBoxProductRelSpec> queryRequest,
                                                Fetcher<MysteryBoxProductRel> fetcher) {
        MysteryBoxProductRelSpec query = queryRequest.getQuery();
        Pageable pageable = queryRequest.toPageable();
        return sql().createQuery(t)
                .where(query)
                .orderBy(SpringOrders.toOrders(t, pageable.getSort()))
                .select(t.fetch(fetcher))
                .fetchPage(queryRequest.getPageNum() - 1, queryRequest.getPageSize(),
                        SpringPageFactory.getInstance());
    }
}