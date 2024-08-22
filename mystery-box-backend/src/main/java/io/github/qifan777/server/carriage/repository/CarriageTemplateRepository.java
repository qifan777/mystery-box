package io.github.qifan777.server.carriage.repository;

import io.github.qifan777.server.carriage.entity.CarriageTemplate;
import io.github.qifan777.server.carriage.entity.CarriageTemplateFetcher;
import io.github.qifan777.server.carriage.entity.CarriageTemplateTable;
import io.github.qifan777.server.infrastructure.model.QueryRequest;
import io.github.qifan777.server.carriage.entity.CarriageTemplateFetcher;
import io.github.qifan777.server.carriage.entity.CarriageTemplateTable;
import io.github.qifan777.server.carriage.entity.dto.CarriageTemplateSpec;
import io.github.qifan777.server.user.root.entity.UserFetcher;
import io.qifan.infrastructure.common.exception.BusinessException;
import org.babyfish.jimmer.spring.repository.JRepository;
import org.babyfish.jimmer.spring.repository.SpringOrders;
import org.babyfish.jimmer.spring.repository.support.SpringPageFactory;
import org.babyfish.jimmer.sql.fetcher.Fetcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CarriageTemplateRepository extends JRepository<CarriageTemplate, String> {
    CarriageTemplateTable t = CarriageTemplateTable.$;
    CarriageTemplateFetcher COMPLEX_FETCHER_FOR_ADMIN = CarriageTemplateFetcher.$.allScalarFields()
            .creator(UserFetcher.$.phone().nickname())
            .editor(UserFetcher.$.phone().nickname());
    CarriageTemplateFetcher COMPLEX_FETCHER_FOR_FRONT = CarriageTemplateFetcher.$.allScalarFields()
            .creator(true);

    default Page<CarriageTemplate> findPage(QueryRequest<CarriageTemplateSpec> queryRequest,
                                            Fetcher<CarriageTemplate> fetcher) {
        CarriageTemplateSpec query = queryRequest.getQuery();
        Pageable pageable = queryRequest.toPageable();
        return sql().createQuery(t)
                .where(query)
                .orderBy(SpringOrders.toOrders(t, pageable.getSort()))
                .select(t.fetch(fetcher))
                .fetchPage(queryRequest.getPageNum() - 1, queryRequest.getPageSize(),
                        SpringPageFactory.getInstance());
    }

    default void updateAllInvalid() {
        sql().createUpdate(t)
                .set(t.valid(), false)
                .where(t.valid().eq(true))
                .execute();
    }

    default CarriageTemplate findValid() {
        return sql().createQuery(t)
                .where(t.valid().eq(true))
                .select(t)
                .fetchOptional()
                .orElseThrow(() -> new BusinessException("未找到可用的运费模板"));
    }
}