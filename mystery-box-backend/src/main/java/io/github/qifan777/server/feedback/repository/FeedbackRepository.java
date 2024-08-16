package io.github.qifan777.server.feedback.repository;

import io.github.qifan777.server.feedback.entity.Feedback;
import io.github.qifan777.server.feedback.entity.FeedbackFetcher;
import io.github.qifan777.server.feedback.entity.FeedbackTable;
import io.github.qifan777.server.feedback.entity.dto.FeedbackSpec;
import io.github.qifan777.server.infrastructure.model.QueryRequest;
import io.github.qifan777.server.user.root.entity.UserFetcher;
import org.babyfish.jimmer.spring.repository.JRepository;
import org.babyfish.jimmer.spring.repository.SpringOrders;
import org.babyfish.jimmer.spring.repository.support.SpringPageFactory;
import org.babyfish.jimmer.sql.fetcher.Fetcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface FeedbackRepository extends JRepository<Feedback, String> {
    FeedbackTable t = FeedbackTable.$;
    FeedbackFetcher COMPLEX_FETCHER_FOR_ADMIN = FeedbackFetcher.$.allScalarFields()
            .creator(UserFetcher.$.phone().nickname())
            .editor(UserFetcher.$.phone().nickname());
    FeedbackFetcher COMPLEX_FETCHER_FOR_FRONT = FeedbackFetcher.$.allScalarFields()
            .creator(true);

    default Page<Feedback> findPage(QueryRequest<FeedbackSpec> queryRequest,
                                    Fetcher<Feedback> fetcher) {
        FeedbackSpec query = queryRequest.getQuery();
        Pageable pageable = queryRequest.toPageable();
        return sql().createQuery(t)
                .where(query)
                .orderBy(SpringOrders.toOrders(t, pageable.getSort()))
                .select(t.fetch(fetcher))
                .fetchPage(queryRequest.getPageNum() - 1, queryRequest.getPageSize(),
                        SpringPageFactory.getInstance());
    }
}