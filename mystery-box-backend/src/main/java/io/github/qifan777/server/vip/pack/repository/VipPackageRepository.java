package io.github.qifan777.server.vip.pack.repository;

import io.github.qifan777.server.infrastructure.model.QueryRequest;
import io.github.qifan777.server.user.root.entity.UserFetcher;
import io.github.qifan777.server.vip.pack.entity.VipPackage;
import io.github.qifan777.server.vip.pack.entity.VipPackageFetcher;
import io.github.qifan777.server.vip.pack.entity.VipPackageTable;
import io.github.qifan777.server.vip.pack.entity.dto.VipPackageSpec;
import org.babyfish.jimmer.spring.repository.JRepository;
import org.babyfish.jimmer.spring.repository.SpringOrders;
import org.babyfish.jimmer.spring.repository.support.SpringPageFactory;
import org.babyfish.jimmer.sql.fetcher.Fetcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface VipPackageRepository extends JRepository<VipPackage, String> {
    VipPackageTable t = VipPackageTable.$;
    VipPackageFetcher COMPLEX_FETCHER_FOR_ADMIN = VipPackageFetcher.$.allScalarFields()
            .creator(UserFetcher.$.phone().nickname())
            .editor(UserFetcher.$.phone().nickname());
    VipPackageFetcher COMPLEX_FETCHER_FOR_FRONT = VipPackageFetcher.$.allScalarFields()
            .creator(true);

    default Page<VipPackage> findPage(QueryRequest<VipPackageSpec> queryRequest,
                                    Fetcher<VipPackage> fetcher) {
        VipPackageSpec query = queryRequest.getQuery();
        Pageable pageable = queryRequest.toPageable();
        return sql().createQuery(t)
                .where(query)
                .orderBy(SpringOrders.toOrders(t, pageable.getSort()))
                .select(t.fetch(fetcher))
                .fetchPage(queryRequest.getPageNum() - 1, queryRequest.getPageSize(),
                        SpringPageFactory.getInstance());
    }
}