package io.github.qifan777.server.slideshow.repository;

import io.github.qifan777.server.infrastructure.model.QueryRequest;
import io.github.qifan777.server.slideshow.entity.Slideshow;
import io.github.qifan777.server.slideshow.entity.SlideshowFetcher;
import io.github.qifan777.server.slideshow.entity.SlideshowTable;
import io.github.qifan777.server.slideshow.entity.dto.SlideshowSpec;
import io.github.qifan777.server.user.root.entity.UserFetcher;
import org.babyfish.jimmer.spring.repository.JRepository;
import org.babyfish.jimmer.spring.repository.SpringOrders;
import org.babyfish.jimmer.spring.repository.support.SpringPageFactory;
import org.babyfish.jimmer.sql.fetcher.Fetcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface SlideshowRepository extends JRepository<Slideshow, String> {
    SlideshowTable t = SlideshowTable.$;
    SlideshowFetcher COMPLEX_FETCHER_FOR_ADMIN = SlideshowFetcher.$.allScalarFields()
            .creator(UserFetcher.$.phone().nickname())
            .editor(UserFetcher.$.phone().nickname());
    SlideshowFetcher COMPLEX_FETCHER_FOR_FRONT = SlideshowFetcher.$.allScalarFields()
            .creator(true);

    default Page<Slideshow> findPage(QueryRequest<SlideshowSpec> queryRequest,
                                     Fetcher<Slideshow> fetcher) {
        SlideshowSpec query = queryRequest.getQuery();
        Pageable pageable = queryRequest.toPageable();
        return sql().createQuery(t)
                .where(query)
                .orderBy(SpringOrders.toOrders(t, pageable.getSort()))
                .select(t.fetch(fetcher))
                .fetchPage(queryRequest.getPageNum() - 1, queryRequest.getPageSize(),
                        SpringPageFactory.getInstance());
    }
}