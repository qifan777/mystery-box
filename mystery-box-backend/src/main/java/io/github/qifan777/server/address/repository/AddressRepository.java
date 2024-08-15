package io.github.qifan777.server.address.repository;

import cn.dev33.satoken.stp.StpUtil;
import io.github.qifan777.server.address.entity.Address;
import io.github.qifan777.server.address.entity.AddressFetcher;
import io.github.qifan777.server.address.entity.AddressTable;
import io.github.qifan777.server.address.entity.dto.AddressSpec;
import io.github.qifan777.server.infrastructure.model.QueryRequest;
import io.github.qifan777.server.user.root.entity.UserFetcher;
import org.babyfish.jimmer.spring.repository.JRepository;
import org.babyfish.jimmer.spring.repository.SpringOrders;
import org.babyfish.jimmer.spring.repository.support.SpringPageFactory;
import org.babyfish.jimmer.sql.fetcher.Fetcher;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface AddressRepository extends JRepository<Address, String> {
    AddressTable t = AddressTable.$;
    AddressFetcher COMPLEX_FETCHER_FOR_ADMIN = AddressFetcher.$.allScalarFields()
            .creator(UserFetcher.$.phone().nickname())
            .editor(UserFetcher.$.phone().nickname());
    AddressFetcher COMPLEX_FETCHER_FOR_FRONT = AddressFetcher.$.allScalarFields()
            .creator(true);

    default Page<Address> findPage(QueryRequest<AddressSpec> queryRequest,
                                   Fetcher<Address> fetcher) {
        AddressSpec query = queryRequest.getQuery();
        Pageable pageable = queryRequest.toPageable();
        return sql().createQuery(t)
                .where(query)
                .orderBy(SpringOrders.toOrders(t, pageable.getSort()))
                .select(t.fetch(fetcher))
                .fetchPage(queryRequest.getPageNum() - 1, queryRequest.getPageSize(),
                        SpringPageFactory.getInstance());
    }

    default Optional<Address> findUserAddressById(@NotNull String id) {
        return sql().createQuery(t)
                .where(t.creatorId().eq(StpUtil.getLoginIdAsString()))
                .where(t.id().eq(id))
                .select(t)
                .fetchOptional();
    }

    default List<Address> findUserAll(String userId) {
        return sql().createQuery(t)
                .where(t.creatorId().eq(userId))
                .select(t)
                .execute();
    }
}