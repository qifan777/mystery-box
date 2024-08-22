package io.github.qifan777.server.coupon.user.controller;

import cn.dev33.satoken.stp.StpUtil;
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
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
        queryRequest.getQuery().setCreatorId(StpUtil.getLoginIdAsString());
        return couponUserRelRepository.findPage(queryRequest, CouponUserRelRepository.COMPLEX_FETCHER_FOR_FRONT);
    }

    @PostMapping("save")
    public String save(@RequestBody @Validated CouponUserRelInput couponUserRelInput) {
        if (StringUtils.hasText(couponUserRelInput.getId())) {
            CouponUserRel couponUserRel = couponUserRelRepository.findById(couponUserRelInput.getId(), CouponUserRelRepository.COMPLEX_FETCHER_FOR_FRONT).orElseThrow(() -> new BusinessException("数据不存在"));
            if (!couponUserRel.creator().id().equals(StpUtil.getLoginIdAsString())) {
                throw new BusinessException("只能修改自己的数据");
            }
        }
        return couponUserRelRepository.save(couponUserRelInput.toEntity()).id();
    }

    @DeleteMapping
    public Boolean delete(@RequestBody List<String> ids) {
        couponUserRelRepository.findByIds(ids, CouponUserRelRepository.COMPLEX_FETCHER_FOR_FRONT).forEach(couponUserRel -> {
            if (!couponUserRel.creator().id().equals(StpUtil.getLoginIdAsString())) {
                throw new BusinessException("只能删除自己的数据");
            }
        });
        couponUserRelRepository.deleteAllById(ids);
        return true;
    }
}
