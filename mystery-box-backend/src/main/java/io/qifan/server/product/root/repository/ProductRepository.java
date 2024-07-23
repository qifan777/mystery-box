package io.qifan.server.product.root.repository;

import io.qifan.server.TableExes;
import io.qifan.server.box.product.entity.MysteryBoxProductRelTableEx;
import io.qifan.server.infrastructure.model.QueryRequest;
import io.qifan.server.product.category.entity.ProductCategoryFetcher;
import io.qifan.server.product.root.entity.Product;
import io.qifan.server.product.root.entity.ProductFetcher;
import io.qifan.server.product.root.entity.ProductTable;
import io.qifan.server.product.root.entity.dto.ProductSpec;
import io.qifan.server.user.root.entity.UserFetcher;
import org.babyfish.jimmer.spring.repository.JRepository;
import org.babyfish.jimmer.spring.repository.SpringOrders;
import org.babyfish.jimmer.spring.repository.support.SpringPageFactory;
import org.babyfish.jimmer.sql.fetcher.Fetcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.util.StringUtils;

public interface ProductRepository extends JRepository<Product, String> {
    ProductTable t = ProductTable.$;
    ProductFetcher COMPLEX_FETCHER_FOR_ADMIN = ProductFetcher.$.allScalarFields()
            .categoryId()
            .category(ProductCategoryFetcher.$.allScalarFields())
            .creator(UserFetcher.$.phone().nickname())
            .editor(UserFetcher.$.phone().nickname());
    ProductFetcher COMPLEX_FETCHER_FOR_FRONT = ProductFetcher.$.allScalarFields()
            .creator(true);

    default Page<Product> findPage(QueryRequest<ProductSpec> queryRequest,
                                   Fetcher<Product> fetcher) {
        ProductSpec query = queryRequest.getQuery();
        Pageable pageable = queryRequest.toPageable();
        MysteryBoxProductRelTableEx t2 = TableExes.MYSTERY_BOX_PRODUCT_REL_TABLE_EX;
        return sql().createQuery(t)
                .where(query)
                .whereIf(StringUtils.hasText(query.getBoxId()), t.id()
                        .notIn(sql().createSubQuery(t2)
                                .where(t2.mysteryBoxId().eqIf(StringUtils.hasText(query.getBoxId()), query.getBoxId()))
                                .select(t2.productId())))
                .orderBy(SpringOrders.toOrders(t, pageable.getSort()))
                .select(t.fetch(fetcher))
                .fetchPage(queryRequest.getPageNum() - 1, queryRequest.getPageSize(),
                        SpringPageFactory.getInstance());
    }
}