package io.github.qifan777.server.coupon.box.service;

import io.github.qifan777.server.coupon.box.repository.CouponBoxRelRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@AllArgsConstructor
@Transactional
public class CouponBoxRelService {
    private final CouponBoxRelRepository couponBoxRelRepository;

}