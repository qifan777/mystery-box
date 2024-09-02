package io.github.qifan777.server.coupon.category.service;

import io.github.qifan777.server.coupon.category.repository.CouponCategoryRelRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@AllArgsConstructor
@Transactional
public class CouponCategoryRelService {
    private final CouponCategoryRelRepository couponCategoryRelRepository;

}