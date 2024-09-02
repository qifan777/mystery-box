package io.github.qifan777.server.coupon.category.controller;

import cn.dev33.satoken.stp.StpUtil;
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
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("front/coupon-category-rel")
@AllArgsConstructor
@DefaultFetcherOwner(CouponCategoryRelRepository.class)
@Transactional
public class CouponCategoryRelForFrontController {
    private final CouponCategoryRelRepository couponCategoryRelRepository;

    @GetMapping("{id}")
    public @FetchBy(value = "COMPLEX_FETCHER_FOR_FRONT") CouponCategoryRel findById(@PathVariable String id) {
        return couponCategoryRelRepository.findById(id, CouponCategoryRelRepository.COMPLEX_FETCHER_FOR_FRONT).orElseThrow(() -> new BusinessException("数据不存在"));
    }

    @PostMapping("query")
    public Page<@FetchBy(value = "COMPLEX_FETCHER_FOR_FRONT") CouponCategoryRel> query(@RequestBody QueryRequest<CouponCategoryRelSpec> queryRequest) {
        queryRequest.getQuery().setCreatorId(StpUtil.getLoginIdAsString());
        return couponCategoryRelRepository.findPage(queryRequest, CouponCategoryRelRepository.COMPLEX_FETCHER_FOR_FRONT);
    }

    @PostMapping("save")
    public String save(@RequestBody @Validated CouponCategoryRelInput couponCategoryRelInput) {
        if (StringUtils.hasText(couponCategoryRelInput.getId())) {
            CouponCategoryRel couponCategoryRel = couponCategoryRelRepository.findById(couponCategoryRelInput.getId(), CouponCategoryRelRepository.COMPLEX_FETCHER_FOR_FRONT).orElseThrow(() -> new BusinessException("数据不存在"));
            if (!couponCategoryRel.creator().id().equals(StpUtil.getLoginIdAsString())) {
                throw new BusinessException("只能修改自己的数据");
            }
        }
        return couponCategoryRelRepository.save(couponCategoryRelInput.toEntity()).id();
    }

    @DeleteMapping
    public Boolean delete(@RequestBody List<String> ids) {
        couponCategoryRelRepository.findByIds(ids, CouponCategoryRelRepository.COMPLEX_FETCHER_FOR_FRONT).forEach(couponCategoryRel -> {
            if (!couponCategoryRel.creator().id().equals(StpUtil.getLoginIdAsString())) {
                throw new BusinessException("只能删除自己的数据");
            }
        });
        couponCategoryRelRepository.deleteAllById(ids);
        return true;
    }
}
