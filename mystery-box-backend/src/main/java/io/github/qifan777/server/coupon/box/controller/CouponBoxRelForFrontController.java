package io.github.qifan777.server.coupon.box.controller;

import cn.dev33.satoken.stp.StpUtil;
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
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("front/coupon-box-rel")
@AllArgsConstructor
@DefaultFetcherOwner(CouponBoxRelRepository.class)
@Transactional
public class CouponBoxRelForFrontController {
    private final CouponBoxRelRepository couponBoxRelRepository;

    @GetMapping("{id}")
    public @FetchBy(value = "COMPLEX_FETCHER_FOR_FRONT") CouponBoxRel findById(@PathVariable String id) {
        return couponBoxRelRepository.findById(id, CouponBoxRelRepository.COMPLEX_FETCHER_FOR_FRONT).orElseThrow(() -> new BusinessException("数据不存在"));
    }

    @PostMapping("query")
    public Page<@FetchBy(value = "COMPLEX_FETCHER_FOR_FRONT") CouponBoxRel> query(@RequestBody QueryRequest<CouponBoxRelSpec> queryRequest) {
        queryRequest.getQuery().setCreatorId(StpUtil.getLoginIdAsString());
        return couponBoxRelRepository.findPage(queryRequest, CouponBoxRelRepository.COMPLEX_FETCHER_FOR_FRONT);
    }

    @PostMapping("save")
    public String save(@RequestBody @Validated CouponBoxRelInput couponBoxRelInput) {
        if (StringUtils.hasText(couponBoxRelInput.getId())) {
            CouponBoxRel couponBoxRel = couponBoxRelRepository.findById(couponBoxRelInput.getId(), CouponBoxRelRepository.COMPLEX_FETCHER_FOR_FRONT).orElseThrow(() -> new BusinessException("数据不存在"));
            if (!couponBoxRel.creator().id().equals(StpUtil.getLoginIdAsString())) {
                throw new BusinessException("只能修改自己的数据");
            }
        }
        return couponBoxRelRepository.save(couponBoxRelInput.toEntity()).id();
    }

    @DeleteMapping
    public Boolean delete(@RequestBody List<String> ids) {
        couponBoxRelRepository.findByIds(ids, CouponBoxRelRepository.COMPLEX_FETCHER_FOR_FRONT).forEach(couponBoxRel -> {
            if (!couponBoxRel.creator().id().equals(StpUtil.getLoginIdAsString())) {
                throw new BusinessException("只能删除自己的数据");
            }
        });
        couponBoxRelRepository.deleteAllById(ids);
        return true;
    }
}
