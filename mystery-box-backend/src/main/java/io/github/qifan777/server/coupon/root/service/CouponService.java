package io.github.qifan777.server.coupon.root.service;

import cn.dev33.satoken.stp.StpUtil;
import io.github.qifan777.server.coupon.root.entity.Coupon;
import io.github.qifan777.server.coupon.root.entity.dto.CouponGiftInput;
import io.github.qifan777.server.coupon.root.repository.CouponRepository;
import io.github.qifan777.server.coupon.user.entity.CouponUserRel;
import io.github.qifan777.server.coupon.user.entity.CouponUserRelDraft;
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

    public void gift(CouponGiftInput giftInput) {
        Coupon coupon = couponRepository.findById(giftInput.getId())
                .orElseThrow(() -> new BusinessException(ResultCode.NotFindError, "优惠券不存在"));
        if (coupon.releasedQuantity() - giftInput.getUserIds().length < 0) {
            throw new BusinessException(ResultCode.NotFindError, "优惠券不足");
        }
        if (!coupon.status()) {
            throw new BusinessException(ResultCode.NotFindError, "优惠券已下架");
        }
        List<CouponUserRel> couponUsers = Arrays.stream(giftInput.getUserIds())
                .map(userId -> CouponUserRelDraft.$.produce(draft -> {
                    draft.setCouponId(giftInput.getId())
                            .setUserId(userId)
                            .setReceiveType(DictConstants.CouponReceiveType.GIFT)
                            .setStatus(DictConstants.CouponUseStatus.UNUSED);
                })).collect(Collectors.toList());
        couponUserRelRepository.saveEntities(couponUsers);
    }

    public BigDecimal calculate(String id, BigDecimal amount) {
        if (!StringUtils.hasText(id)) return BigDecimal.ZERO;
        CouponUserRel couponUserRel = couponUserRelRepository.findById(id, CouponUserRelRepository.COMPLEX_FETCHER_FOR_ADMIN)
                .orElseThrow(() -> new BusinessException(ResultCode.NotFindError, "优惠券不存在"));
        checkCouponUser(couponUserRel);
        Coupon coupon = couponUserRel.coupon();
        if (coupon.couponType().equals(DictConstants.CouponType.DISCOUNT)) {
            return BigDecimal.TEN.subtract(coupon.discount()).divide(BigDecimal.TEN, RoundingMode.DOWN)
                    .multiply(amount);
        } else if (coupon.couponType().equals(DictConstants.CouponType.REDUCE)) {
            return coupon.amount();
        }
        throw new BusinessException(ResultCode.ParamSetIllegal, "优惠券类型错误");
    }

    public void checkCouponUser(CouponUserRel couponUserRel) {
        if (!couponUserRel.status().equals(DictConstants.CouponUseStatus.UNUSED)) {
            throw new BusinessException(ResultCode.ParamSetIllegal, "优惠券已使用");
        }
        if (!couponUserRel.user().id().equals(StpUtil.getLoginIdAsString())) {
            throw new BusinessException(ResultCode.ParamSetIllegal, "优惠券非本人");
        }
        if (!couponUserRel.coupon().effectiveDate().isBefore(LocalDateTime.now())) {
            throw new BusinessException(ResultCode.ParamSetIllegal, "优惠券未生效");
        }
        if (!couponUserRel.coupon().expirationDate().isAfter(LocalDateTime.now())) {
            throw new BusinessException(ResultCode.ParamSetIllegal, "优惠券已过期");
        }
        if (!couponUserRel.coupon().status()) {
            throw new BusinessException(ResultCode.ParamSetIllegal, "优惠券已下架");
        }
    }

    public void changeStatus(String id, DictConstants.CouponUseStatus status) {
        couponUserRelRepository.changeStatus(id, status);
    }
}