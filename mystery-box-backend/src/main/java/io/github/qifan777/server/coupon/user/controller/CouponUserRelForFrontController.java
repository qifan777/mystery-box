package io.github.qifan777.server.coupon.user.controller;

import cn.dev33.satoken.stp.StpUtil;
import io.github.qifan777.server.coupon.user.entity.CouponUserRel;
import io.github.qifan777.server.coupon.user.entity.dto.CouponUserRelSpec;
import io.github.qifan777.server.coupon.user.repository.CouponUserRelRepository;
import io.github.qifan777.server.dict.model.DictConstants;
import io.github.qifan777.server.infrastructure.model.QueryRequest;
import io.qifan.infrastructure.common.exception.BusinessException;
import lombok.AllArgsConstructor;
import org.babyfish.jimmer.client.FetchBy;
import org.babyfish.jimmer.client.meta.DefaultFetcherOwner;
import org.springframework.data.domain.Page;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("front/coupon-user-rel")
@AllArgsConstructor
@DefaultFetcherOwner(CouponUserRelRepository.class)
@Transactional
public class CouponUserRelForFrontController {
    private final CouponUserRelRepository couponUserRelRepository;

    @GetMapping("{id}")
    public @FetchBy(value = "COMPLEX_FETCHER_FOR_FRONT") CouponUserRel findById(@PathVariable String id) {
        return couponUserRelRepository.findById(id, CouponUserRelRepository.COMPLEX_FETCHER_FOR_FRONT).orElseThrow(() -> new BusinessException("数据不存在"));
    }

    @PostMapping("query")
    public Page<@FetchBy(value = "COMPLEX_FETCHER_FOR_FRONT") CouponUserRel> query(@RequestBody QueryRequest<CouponUserRelSpec> queryRequest) {
        CouponUserRelSpec query = queryRequest.getQuery();
        query.setUserId(StpUtil.getLoginIdAsString());
        query.setMinExpirationDate(LocalDateTime.now());
        query.setMaxEffectiveDate(LocalDateTime.now());
        query.setStatus(DictConstants.CouponUseStatus.UNUSED);
        query.setCouponStatus(true);
        return couponUserRelRepository.findPage(queryRequest, CouponUserRelRepository.COMPLEX_FETCHER_FOR_FRONT);
    }
}
