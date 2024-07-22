package io.qifan.server.product.category.repository;

import io.qifan.server.infrastructure.model.QueryRequest;
import io.qifan.server.product.category.entity.ProductCategory;
import io.qifan.server.product.category.entity.ProductCategoryFetcher;
import io.qifan.server.product.category.entity.ProductCategoryTable;
import io.qifan.server.product.category.entity.dto.ProductCategorySpec;
import io.qifan.server.user.root.entity.UserFetcher;
import org.babyfish.jimmer.spring.repository.JRepository;
import org.babyfish.jimmer.spring.repository.SpringOrders;
import org.babyfish.jimmer.spring.repository.support.SpringPageFactory;
import org.babyfish.jimmer.sql.fetcher.Fetcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProductCategoryRepository extends JRepository<ProductCategory, String> {
    ProductCategoryTable t = ProductCategoryTable.$;
    ProductCategoryFetcher COMPLEX_FETCHER_FOR_ADMIN = ProductCategoryFetcher.$.allScalarFields()
            .parentId()
            .parent(ProductCategoryFetcher.$.name())
            .creator(UserFetcher.$.phone().nickname())
            .editor(UserFetcher.$.phone().nickname());
    ProductCategoryFetcher COMPLEX_FETCHER_FOR_FRONT = ProductCategoryFetcher.$.allScalarFields()
            .creator(true);

    default Page<ProductCategory> findPage(QueryRequest<ProductCategorySpec> queryRequest,
                                           Fetcher<ProductCategory> fetcher) {
        ProductCategorySpec query = queryRequest.getQuery();
        Pageable pageable = queryRequest.toPageable();
        return sql().createQuery(t)
                .where(query)
                .orderBy(SpringOrders.toOrders(t, pageable.getSort()))
                .select(t.fetch(fetcher))
                .fetchPage(queryRequest.getPageNum() - 1, queryRequest.getPageSize(),
                        SpringPageFactory.getInstance());
    }
}