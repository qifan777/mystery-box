package io.github.qifan777.server.carriage.entity.model;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class CarriageConfig {
    /**
     * 省份
     */
    List<String> province;
    /**
     * 运费配置
     */
    List<PriceRange> priceRanges;

    @Data
    public static class PriceRange {
        /**
         * 订单最高价格
         */
        BigDecimal maxPrice;
        /**
         * 订单最低价格
         */
        BigDecimal minPrice;
        /**
         * 运费
         */
        BigDecimal carriage;
    }
}

