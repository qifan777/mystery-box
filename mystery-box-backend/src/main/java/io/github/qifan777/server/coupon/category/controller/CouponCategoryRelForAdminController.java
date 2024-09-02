package io.github.qifan777.server.coupon.category.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import io.github.qifan777.server.coupon.category.entity.CouponCategoryRel;
import io.github.qifan777.server.coupon.category.entity.dto.CouponCategoryRelInput;
import io.github.qifan777.server.coupon.category.entity.dto.CouponCategoryRelSpec;
import io.github.qifan777.server.coupon.category.repository.CouponCategoryRelRepository;
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
@RequestMapping("admin/coupon-category-rel")
@AllArgsConstructor
@DefaultFetcherOwner(CouponCategoryRelRepository.class)
@SaCheckPermission("/coupon-category-rel")
@Transactional
public class CouponCategoryRelForAdminController {
    private final CouponCategoryRelRepository couponCategoryRelRepository;

    @GetMapping("{id}")
    public @FetchBy(value = "COMPLEX_FETCHER_FOR_ADMIN") CouponCategoryRel findById(@PathVariable String id) {
        return couponCategoryRelRepository.findById(id, CouponCategoryRelRepository.COMPLEX_FETCHER_FOR_ADMIN).orElseThrow(() -> new BusinessException("数据不存在"));
    }

    @PostMapping("query")
    public Page<@FetchBy(value = "COMPLEX_FETCHER_FOR_ADMIN") CouponCategoryRel> query(@RequestBody QueryRequest<CouponCategoryRelSpec> queryRequest) {
        return couponCategoryRelRepository.findPage(queryRequest, CouponCategoryRelRepository.COMPLEX_FETCHER_FOR_ADMIN);
    }

    @PostMapping("save")
    public String save(@RequestBody @Validated CouponCategoryRelInput couponCategoryRelInput) {
        return couponCategoryRelRepository.save(couponCategoryRelInput.toEntity()).id();
    }

    @DeleteMapping
    public Boolean delete(@RequestBody List<String> ids) {
        couponCategoryRelRepository.deleteAllById(ids);
        return true;
    }
}