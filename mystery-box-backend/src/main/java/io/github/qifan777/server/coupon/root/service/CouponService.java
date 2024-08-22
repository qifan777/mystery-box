package io.github.qifan777.server.coupon.root.service;

import cn.dev33.satoken.stp.StpUtil;
import io.github.qifan777.server.coupon.root.entity.Coupon;
import io.github.qifan777.server.coupon.root.entity.dto.CouponGiftInput;
import io.github.qifan777.server.coupon.root.repository.CouponRepository;
import io.github.qifan777.server.coupon.user.entity.CouponUserRel;
import io.github.qifan777.server.coupon.user.entity.CouponUserRelDraft;
import io.github.qifan777.server.coupon.user.entity.CouponUserRelTable;
import io.github.qifan777.server.coupon.user.repository.CouponUserRelRepository;
import io.github.qifan777.server.dict.model.DictConstants;
import io.qifan.infrastructure.common.constants.ResultCode;
import io.qifan.infrastructure.common.exception.BusinessException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@AllArgsConstructor
@Transactional
public class CouponService {
    private final CouponRepository couponRepository;
    private final CouponUserRelRepository couponUserRelRepository;

    public void countCheck(String id) {
        Coupon coupon = couponRepository.findById(id)
                .orElseThrow(() -> new BusinessException(ResultCode.NotFindError, "优惠券不存在"));
        if (coupon.releasedQuantity() == 0) {
            throw new BusinessException(ResultCode.NotFindError, "优惠券已领完");
        }
    }

    public void gift(CouponGiftInput giftInput) {
        countCheck(giftInput.getId());
        List<CouponUserRel> couponUsers = Arrays.stream(giftInput.getUserIds())
                .map(userId -> CouponUserRelDraft.$.produce(draft -> {
                    draft.setCouponId(giftInput.getId())
                            .setUserId(userId)
                            .setReceiveType(DictConstants.CouponReceiveType.GIFT)
                            .setStatus(DictConstants.CouponUseStatus.UNUSED);
                })).collect(Collectors.toList());
        couponUserRelRepository.saveEntities(couponUsers);
    }

    public List<CouponUserRel> availableCoupons(BigDecimal amount) {
        CouponUserRelTable t = CouponUserRelTable.$;
        return couponUserRelRepository.sql().createQuery(t)
                .where(t.userId().eq(StpUtil.getLoginIdAsString()))
                .where(t.status().eq(DictConstants.CouponUseStatus.UNUSED))
                .where(t.coupon().thresholdAmount().le(amount))
                .where(t.coupon().effectiveDate().le(LocalDateTime.now()))
                .where(t.coupon().expirationDate().ge(LocalDateTime.now()))
                .select(t.fetch(CouponUserRelRepository.COMPLEX_FETCHER_FOR_FRONT))
                .execute();
    }

    public BigDecimal calculate(String id, BigDecimal amount) {
        if (!StringUtils.hasText(id)) return BigDecimal.ZERO;
        var t = CouponUserRelTable.$;
        CouponUserRel couponUser = couponUserRelRepository.sql().createQuery(t)
                .where(t.userId().eq(StpUtil.getLoginIdAsString()))
                .where(t.status().eq(DictConstants.CouponUseStatus.UNUSED))
                .where(t.coupon().thresholdAmount().le(amount))
                .where(t.coupon().effectiveDate().le(LocalDateTime.now()))
                .where(t.coupon().expirationDate().ge(LocalDateTime.now()))
                .where(t.id().eq(id))
                .select(t.fetch(CouponUserRelRepository.COMPLEX_FETCHER_FOR_ADMIN))
                .fetchOptional()
                .orElseThrow(() -> new BusinessException(ResultCode.NotFindError, "优惠券失效"));
        Coupon coupon = couponUser.coupon();
        if (coupon.couponType().equals(DictConstants.CouponType.DISCOUNT)) {
            return BigDecimal.TEN.subtract(coupon.discount()).divide(BigDecimal.TEN, RoundingMode.DOWN)
                    .multiply(amount);
        } else if (coupon.couponType().equals(DictConstants.CouponType.REDUCE)) {
            return coupon.amount();
        }
        throw new BusinessException(ResultCode.ParamSetIllegal, "优惠券类型错误");
    }

}