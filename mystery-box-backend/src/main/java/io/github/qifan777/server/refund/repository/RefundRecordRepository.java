package io.github.qifan777.server.refund.repository;

import io.github.qifan777.server.infrastructure.model.QueryRequest;
import io.github.qifan777.server.refund.entity.RefundRecord;
import io.github.qifan777.server.refund.entity.RefundRecordFetcher;
import io.github.qifan777.server.refund.entity.RefundRecordTable;
import io.github.qifan777.server.refund.entity.dto.RefundRecordSpec;
import io.github.qifan777.server.user.root.entity.UserFetcher;
import org.babyfish.jimmer.spring.repository.JRepository;
import org.babyfish.jimmer.spring.repository.SpringOrders;
import org.babyfish.jimmer.spring.repository.support.SpringPageFactory;
import org.babyfish.jimmer.sql.fetcher.Fetcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface RefundRecordRepository extends JRepository<RefundRecord, String> {
    RefundRecordTable t = RefundRecordTable.$;
    RefundRecordFetcher COMPLEX_FETCHER_FOR_ADMIN = RefundRecordFetcher.$.allScalarFields()
            .creator(UserFetcher.$.phone().nickname())
            .editor(UserFetcher.$.phone().nickname());
    RefundRecordFetcher COMPLEX_FETCHER_FOR_FRONT = RefundRecordFetcher.$.allScalarFields()
            .creator(true);

    default Page<RefundRecord> findPage(QueryRequest<RefundRecordSpec> queryRequest,
                                        Fetcher<RefundRecord> fetcher) {
        RefundRecordSpec query = queryRequest.getQuery();
        Pageable pageable = queryRequest.toPageable();
        return sql().createQuery(t)
                .where(query)
                .orderBy(SpringOrders.toOrders(t, pageable.getSort()))
                .select(t.fetch(fetcher))
                .fetchPage(queryRequest.getPageNum() - 1, queryRequest.getPageSize(),
                        SpringPageFactory.getInstance());
    }
}