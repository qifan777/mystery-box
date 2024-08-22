package io.github.qifan777.server.coupon.root.controller;

import cn.dev33.satoken.stp.StpUtil;
import io.github.qifan777.server.coupon.root.entity.Coupon;
import io.github.qifan777.server.coupon.root.entity.dto.CouponInput;
import io.github.qifan777.server.coupon.root.entity.dto.CouponSpec;
import io.github.qifan777.server.coupon.root.repository.CouponRepository;
import io.github.qifan777.server.coupon.root.service.CouponService;
import io.github.qifan777.server.coupon.user.entity.CouponUserRel;
import io.github.qifan777.server.coupon.user.repository.CouponUserRelRepository;
import io.github.qifan777.server.infrastructure.model.QueryRequest;
import io.qifan.infrastructure.common.exception.BusinessException;
import lombok.AllArgsConstructor;
import org.babyfish.jimmer.client.FetchBy;
import org.babyfish.jimmer.client.meta.DefaultFetcherOwner;
import org.springframework.data.domain.Page;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("front/coupon")
@AllArgsConstructor
@DefaultFetcherOwner(CouponRepository.class)
@Transactional
public class CouponForFrontController {
    private final CouponRepository couponRepository;
    private final CouponService couponService;

    @GetMapping("{id}")
    public @FetchBy(value = "COMPLEX_FETCHER_FOR_FRONT") Coupon findById(@PathVariable String id) {
        return couponRepository.findById(id, CouponRepository.COMPLEX_FETCHER_FOR_FRONT).orElseThrow(() -> new BusinessException("数据不存在"));
    }

    @PostMapping("query")
    public Page<@FetchBy(value = "COMPLEX_FETCHER_FOR_FRONT") Coupon> query(@RequestBody QueryRequest<CouponSpec> queryRequest) {
        queryRequest.getQuery().setCreatorId(StpUtil.getLoginIdAsString());
        return couponRepository.findPage(queryRequest, CouponRepository.COMPLEX_FETCHER_FOR_FRONT);
    }

    @PostMapping("save")
    public String save(@RequestBody @Validated CouponInput couponInput) {
        if (StringUtils.hasText(couponInput.getId())) {
            Coupon coupon = couponRepository.findById(couponInput.getId(), CouponRepository.COMPLEX_FETCHER_FOR_FRONT).orElseThrow(() -> new BusinessException("数据不存在"));
            if (!coupon.creator().id().equals(StpUtil.getLoginIdAsString())) {
                throw new BusinessException("只能修改自己的数据");
            }
        }
        return couponRepository.save(couponInput.toEntity()).id();
    }

    @DeleteMapping
    public Boolean delete(@RequestBody List<String> ids) {
        couponRepository.findByIds(ids, CouponRepository.COMPLEX_FETCHER_FOR_FRONT).forEach(coupon -> {
            if (!coupon.creator().id().equals(StpUtil.getLoginIdAsString())) {
                throw new BusinessException("只能删除自己的数据");
            }
        });
        couponRepository.deleteAllById(ids);
        return true;
    }

    @GetMapping("available")
    public List<@FetchBy(value = "COMPLEX_FETCHER_FOR_FRONT", ownerType = CouponUserRelRepository.class) CouponUserRel> availableCoupons(@RequestParam BigDecimal amount) {
        return couponService.availableCoupons(amount);
    }
}
