package io.github.qifan777.server.coupon.root.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import io.github.qifan777.server.coupon.root.entity.Coupon;
import io.github.qifan777.server.coupon.root.entity.dto.CouponGiftInput;
import io.github.qifan777.server.coupon.root.entity.dto.CouponInput;
import io.github.qifan777.server.coupon.root.entity.dto.CouponSpec;
import io.github.qifan777.server.coupon.root.repository.CouponRepository;
import io.github.qifan777.server.coupon.root.service.CouponService;
import io.github.qifan777.server.infrastructure.model.QueryRequest;
import io.qifan.infrastructure.common.exception.BusinessException;
import lombok.AllArgsConstructor;
import org.babyfish.jimmer.client.FetchBy;
import org.babyfish.jimmer.client.meta.DefaultFetcherOwner;
import org.springframework.data.domain.Page;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("admin/coupon")
@AllArgsConstructor
@DefaultFetcherOwner(CouponRepository.class)
@SaCheckPermission("/coupon")
@Transactional
public class CouponForAdminController {
    private final CouponRepository couponRepository;
    private final CouponService couponService;

    @GetMapping("{id}")
    public @FetchBy(value = "COMPLEX_FETCHER_FOR_ADMIN") Coupon findById(@PathVariable String id) {
        return couponRepository.findById(id, CouponRepository.COMPLEX_FETCHER_FOR_ADMIN).orElseThrow(() -> new BusinessException("数据不存在"));
    }

    @PostMapping("query")
    public Page<@FetchBy(value = "COMPLEX_FETCHER_FOR_ADMIN") Coupon> query(@RequestBody QueryRequest<CouponSpec> queryRequest) {
        return couponRepository.findPage(queryRequest, CouponRepository.COMPLEX_FETCHER_FOR_ADMIN);
    }

    @PostMapping("save")
    public String save(@RequestBody @Validated CouponInput couponInput) {
        return couponRepository.save(couponInput.toEntity()).id();
    }

    @DeleteMapping
    public Boolean delete(@RequestBody List<String> ids) {
        couponRepository.deleteAllById(ids);
        return true;
    }

    @PostMapping("gift")
    public void gift(@RequestBody CouponGiftInput giftInput) {
        couponService.gift(giftInput);
    }
}