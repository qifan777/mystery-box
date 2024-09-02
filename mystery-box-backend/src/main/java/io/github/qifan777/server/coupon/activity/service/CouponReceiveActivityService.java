package io.github.qifan777.server.coupon.activity.service;

import io.github.qifan777.server.coupon.activity.repository.CouponReceiveActivityRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@AllArgsConstructor
@Transactional
public class CouponReceiveActivityService {
    private final CouponReceiveActivityRepository couponReceiveActivityRepository;

}