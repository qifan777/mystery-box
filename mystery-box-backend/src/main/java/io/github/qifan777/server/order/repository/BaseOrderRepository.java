package io.github.qifan777.server.order.repository;

import io.github.qifan777.server.Tables;
import io.github.qifan777.server.order.entity.BaseOrder;
import io.github.qifan777.server.order.entity.BaseOrderTable;
import org.babyfish.jimmer.spring.repository.JRepository;

public interface BaseOrderRepository extends JRepository<BaseOrder, String> {
    BaseOrderTable t = Tables.BASE_ORDER_TABLE;

    default void updateTrackingNumber(String id, String trackingNumber) {
        sql().createUpdate(t)
                .where(t.id().eq(id))
                .set(t.trackingNumber(), trackingNumber)
                .execute();
    }
}
