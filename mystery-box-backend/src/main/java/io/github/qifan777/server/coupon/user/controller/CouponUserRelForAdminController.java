package io.github.qifan777.server.coupon.user.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import io.github.qifan777.server.coupon.user.entity.CouponUserRel;
import io.github.qifan777.server.coupon.user.entity.dto.CouponUserRelInput;
import io.github.qifan777.server.coupon.user.entity.dto.CouponUserRelSpec;
import io.github.qifan777.server.coupon.user.repository.CouponUserRelRepository;
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
@RequestMapping("admin/coupon-user-rel")
@AllArgsConstructor
@DefaultFetcherOwner(CouponUserRelRepository.class)
@SaCheckPermission("/coupon-user-rel")
@Transactional
public class CouponUserRelForAdminController {
    private final CouponUserRelRepository couponUserRelRepository;

    @GetMapping("{id}")
    public @FetchBy(value = "COMPLEX_FETCHER_FOR_ADMIN") CouponUserRel findById(@PathVariable String id) {
        return couponUserRelRepository.findById(id, CouponUserRelRepository.COMPLEX_FETCHER_FOR_ADMIN).orElseThrow(() -> new BusinessException("数据不存在"));
    }

    @PostMapping("query")
    public Page<@FetchBy(value = "COMPLEX_FETCHER_FOR_ADMIN") CouponUserRel> query(@RequestBody QueryRequest<CouponUserRelSpec> queryRequest) {
        return couponUserRelRepository.findPage(queryRequest, CouponUserRelRepository.COMPLEX_FETCHER_FOR_ADMIN);
    }

    @PostMapping("save")
    public String save(@RequestBody @Validated CouponUserRelInput couponUserRelInput) {
        return couponUserRelRepository.save(couponUserRelInput.toEntity()).id();
    }

    @DeleteMapping
    public Boolean delete(@RequestBody List<String> ids) {
        couponUserRelRepository.deleteAllById(ids);
        return true;
    }
}