package io.github.qifan777.server.vip.root.service;

import io.github.qifan777.server.vip.config.VipConfigRepository;
import io.github.qifan777.server.vip.root.repository.VipRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.concurrent.atomic.AtomicReference;

@Service
@Slf4j
@AllArgsConstructor
@Transactional
public class VipService {
    private final VipRepository vipRepository;
    private final VipConfigRepository vipConfigRepository;

    public BigDecimal calculate(BigDecimal price) {
        AtomicReference<BigDecimal> vipPrice = new AtomicReference<>(BigDecimal.ZERO);
        vipRepository.findCurrentUserVip().ifPresent(vip -> {
            if (vip.endTime().isAfter(LocalDateTime.now())) {
                BigDecimal value = BigDecimal.TEN.subtract(vipConfigRepository.get().getDiscount()).divide(BigDecimal.TEN, 2, RoundingMode.HALF_UP);
                vipPrice.set(price.multiply(value));
            }
        });
        return vipPrice.get();
    }
}