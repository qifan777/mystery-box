package io.qifan.server.address.repository;

import io.qifan.server.address.entity.Address;
import io.qifan.server.address.entity.AddressFetcher;
import io.qifan.server.address.entity.AddressTable;
import io.qifan.server.address.entity.dto.AddressSpec;
import io.qifan.server.infrastructure.model.QueryRequest;
import io.qifan.server.user.root.entity.UserFetcher;
import org.babyfish.jimmer.spring.repository.JRepository;
import org.babyfish.jimmer.spring.repository.SpringOrders;
import org.babyfish.jimmer.spring.repository.support.SpringPageFactory;
import org.babyfish.jimmer.sql.fetcher.Fetcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.util.StringUtils;

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
}