package io.qifan.server.box.root.repository;

import io.qifan.server.box.root.entity.MysteryBox;
import io.qifan.server.box.root.entity.MysteryBoxFetcher;
import io.qifan.server.box.root.entity.MysteryBoxTable;
import io.qifan.server.box.root.entity.dto.MysteryBoxSpec;
import io.qifan.server.infrastructure.model.QueryRequest;
import io.qifan.server.user.root.entity.UserFetcher;
import org.babyfish.jimmer.spring.repository.JRepository;
import org.babyfish.jimmer.spring.repository.SpringOrders;
import org.babyfish.jimmer.spring.repository.support.SpringPageFactory;
import org.babyfish.jimmer.sql.fetcher.Fetcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface MysteryBoxRepository extends JRepository<MysteryBox, String> {
    MysteryBoxTable t = MysteryBoxTable.$;
    MysteryBoxFetcher COMPLEX_FETCHER_FOR_ADMIN = MysteryBoxFetcher.$.allScalarFields()
            .creator(UserFetcher.$.phone().nickname())
            .editor(UserFetcher.$.phone().nickname());
    MysteryBoxFetcher COMPLEX_FETCHER_FOR_FRONT = MysteryBoxFetcher.$.allScalarFields()
            .creator(true);

    default Page<MysteryBox> findPage(QueryRequest<MysteryBoxSpec> queryRequest,
                                      Fetcher<MysteryBox> fetcher) {
        MysteryBoxSpec query = queryRequest.getQuery();
        Pageable pageable = queryRequest.toPageable();
        return sql().createQuery(t)
                .where(query)
                .orderBy(SpringOrders.toOrders(t, pageable.getSort()))
                .select(t.fetch(fetcher))
                .fetchPage(queryRequest.getPageNum() - 1, queryRequest.getPageSize(),
                        SpringPageFactory.getInstance());
    }
}