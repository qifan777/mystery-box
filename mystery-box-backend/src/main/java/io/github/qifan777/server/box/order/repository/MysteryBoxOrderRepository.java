package io.github.qifan777.server.box.order.repository;

import io.github.qifan777.server.Fetchers;
import io.github.qifan777.server.box.item.entity.MysteryBoxOrderItemFetcher;
import io.github.qifan777.server.box.order.entity.MysteryBoxOrder;
import io.github.qifan777.server.box.order.entity.MysteryBoxOrderFetcher;
import io.github.qifan777.server.box.order.entity.MysteryBoxOrderTable;
import io.github.qifan777.server.box.order.entity.dto.MysteryBoxOrderSpec;
import io.github.qifan777.server.coupon.root.entity.CouponFetcher;
import io.github.qifan777.server.coupon.user.entity.CouponUserRelFetcher;
import io.github.qifan777.server.dict.model.DictConstants;
import io.github.qifan777.server.infrastructure.model.QueryRequest;
import io.github.qifan777.server.order.entity.BaseOrderFetcher;
import io.github.qifan777.server.payment.entity.PaymentFetcher;
import io.github.qifan777.server.user.root.entity.UserFetcher;
import io.qifan.infrastructure.common.constants.ResultCode;
import io.qifan.infrastructure.common.exception.BusinessException;
import org.babyfish.jimmer.spring.repository.JRepository;
import org.babyfish.jimmer.spring.repository.SpringOrders;
import org.babyfish.jimmer.spring.repository.support.SpringPageFactory;
import org.babyfish.jimmer.sql.ast.Expression;
import org.babyfish.jimmer.sql.fetcher.Fetcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.List;

public interface MysteryBoxOrderRepository extends JRepository<MysteryBoxOrder, String> {
    MysteryBoxOrderTable t = MysteryBoxOrderTable.$;
    MysteryBoxOrderFetcher COMPLEX_FETCHER_FOR_ADMIN = MysteryBoxOrderFetcher.$.allScalarFields()
            .creator(UserFetcher.$.phone().nickname())
            .editor(UserFetcher.$.phone().nickname())
            .baseOrder(BaseOrderFetcher.$.allScalarFields()
                    .payment(PaymentFetcher.$.allScalarFields())
                    .couponUser(CouponUserRelFetcher.$.coupon(CouponFetcher.$.allScalarFields())))
            .items(MysteryBoxOrderItemFetcher.$.allScalarFields());
    MysteryBoxOrderFetcher COMPLEX_FETCHER_FOR_FRONT = MysteryBoxOrderFetcher.$.allScalarFields()
            .baseOrder(BaseOrderFetcher.$.allScalarFields()
                    .payment(PaymentFetcher.$.allScalarFields())
                    .couponUser(CouponUserRelFetcher.$.coupon(CouponFetcher.$.allScalarFields())))
            .items(MysteryBoxOrderItemFetcher.$.allScalarFields())
            .creator(true);

    default Page<MysteryBoxOrder> findPage(QueryRequest<MysteryBoxOrderSpec> queryRequest,
                                           Fetcher<MysteryBoxOrder> fetcher) {
        MysteryBoxOrderSpec query = queryRequest.getQuery();
        Pageable pageable = queryRequest.toPageable();
        return sql().createQuery(t)
                .where(query)
                .whereIf(StringUtils.hasText(query.getKeyword()), t.items(ex -> Expression.string()
                        .sql("%e", it -> it.expression(ex.mysteryBox()))
                        .ilikeIf(StringUtils.hasText(query.getKeyword()), query.getKeyword())))
                .orderBy(SpringOrders.toOrders(t, pageable.getSort()))
                .select(t.fetch(fetcher))
                .fetchPage(queryRequest.getPageNum() - 1, queryRequest.getPageSize(),
                        SpringPageFactory.getInstance());
    }

    default MysteryBoxOrder findByIdForFront(String id) {
        return findById(id, COMPLEX_FETCHER_FOR_FRONT).orElseThrow(() -> new BusinessException(ResultCode.NotFindError, "订单不存在"));
    }

    default List<MysteryBoxOrder> findUnpaidOrder() {
        return sql().createQuery(t)
                .where(t.status().eq(DictConstants.ProductOrderStatus.TO_BE_PAID))
                .where(t.createdTime().le(LocalDateTime.now().minusMinutes(5)))
                .select(t.fetch(Fetchers.MYSTERY_BOX_ORDER_FETCHER.allScalarFields().creator(true)))
                .execute();
    }

    default void changeStatus(String id, DictConstants.ProductOrderStatus status) {
        sql().createUpdate(t)
                .where(t.id().eq(id))
                .set(t.status(), status)
                .execute();
    }

}