package io.github.qifan777.server.coupon.activity.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import io.github.qifan777.server.coupon.activity.entity.CouponReceiveActivity;
import io.github.qifan777.server.coupon.activity.entity.dto.CouponReceiveActivityInput;
import io.github.qifan777.server.coupon.activity.entity.dto.CouponReceiveActivitySpec;
import io.github.qifan777.server.coupon.activity.repository.CouponReceiveActivityRepository;
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
@RequestMapping("admin/coupon-receive-activity")
@AllArgsConstructor
@DefaultFetcherOwner(CouponReceiveActivityRepository.class)
@SaCheckPermission("/coupon-receive-activity")
@Transactional
public class CouponReceiveActivityForAdminController {
    private final CouponReceiveActivityRepository couponReceiveActivityRepository;

    @GetMapping("{id}")
    public @FetchBy(value = "COMPLEX_FETCHER_FOR_ADMIN") CouponReceiveActivity findById(@PathVariable String id) {
        return couponReceiveActivityRepository.findById(id, CouponReceiveActivityRepository.COMPLEX_FETCHER_FOR_ADMIN).orElseThrow(() -> new BusinessException("数据不存在"));
    }

    @PostMapping("query")
    public Page<@FetchBy(value = "COMPLEX_FETCHER_FOR_ADMIN") CouponReceiveActivity> query(@RequestBody QueryRequest<CouponReceiveActivitySpec> queryRequest) {
        return couponReceiveActivityRepository.findPage(queryRequest, CouponReceiveActivityRepository.COMPLEX_FETCHER_FOR_ADMIN);
    }

    @PostMapping("save")
    public String save(@RequestBody @Validated CouponReceiveActivityInput couponReceiveActivityInput) {
        return couponReceiveActivityRepository.save(couponReceiveActivityInput.toEntity()).id();
    }

    @DeleteMapping
    public Boolean delete(@RequestBody List<String> ids) {
        couponReceiveActivityRepository.deleteAllById(ids);
        return true;
    }
}