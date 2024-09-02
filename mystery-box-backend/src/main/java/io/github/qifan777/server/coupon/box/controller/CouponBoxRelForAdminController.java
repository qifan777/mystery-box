package io.github.qifan777.server.coupon.box.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import io.github.qifan777.server.coupon.box.entity.CouponBoxRel;
import io.github.qifan777.server.coupon.box.entity.dto.CouponBoxRelInput;
import io.github.qifan777.server.coupon.box.entity.dto.CouponBoxRelSpec;
import io.github.qifan777.server.coupon.box.repository.CouponBoxRelRepository;
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
@RequestMapping("admin/coupon-box-rel")
@AllArgsConstructor
@DefaultFetcherOwner(CouponBoxRelRepository.class)
@SaCheckPermission("/coupon-box-rel")
@Transactional
public class CouponBoxRelForAdminController {
    private final CouponBoxRelRepository couponBoxRelRepository;

    @GetMapping("{id}")
    public @FetchBy(value = "COMPLEX_FETCHER_FOR_ADMIN") CouponBoxRel findById(@PathVariable String id) {
        return couponBoxRelRepository.findById(id, CouponBoxRelRepository.COMPLEX_FETCHER_FOR_ADMIN).orElseThrow(() -> new BusinessException("数据不存在"));
    }

    @PostMapping("query")
    public Page<@FetchBy(value = "COMPLEX_FETCHER_FOR_ADMIN") CouponBoxRel> query(@RequestBody QueryRequest<CouponBoxRelSpec> queryRequest) {
        return couponBoxRelRepository.findPage(queryRequest, CouponBoxRelRepository.COMPLEX_FETCHER_FOR_ADMIN);
    }

    @PostMapping("save")
    public String save(@RequestBody @Validated CouponBoxRelInput couponBoxRelInput) {
        return couponBoxRelRepository.save(couponBoxRelInput.toEntity()).id();
    }

    @DeleteMapping
    public Boolean delete(@RequestBody List<String> ids) {
        couponBoxRelRepository.deleteAllById(ids);
        return true;
    }
}