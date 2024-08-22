package io.github.qifan777.server.vip.root.controller;

import cn.dev33.satoken.stp.StpUtil;
import io.github.qifan777.server.Objects;
import io.github.qifan777.server.infrastructure.model.QueryRequest;
import io.github.qifan777.server.vip.root.entity.Vip;
import io.github.qifan777.server.vip.root.entity.dto.VipInput;
import io.github.qifan777.server.vip.root.entity.dto.VipSpec;
import io.github.qifan777.server.vip.root.repository.VipRepository;
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
@RequestMapping("front/vip")
@AllArgsConstructor
@DefaultFetcherOwner(VipRepository.class)
@Transactional
public class VipForFrontController {
    private final VipRepository vipRepository;

    @GetMapping
    public @FetchBy(value = "COMPLEX_FETCHER_FOR_FRONT") Vip find() {
        return vipRepository.findCurrentUserVip().orElse(Objects.createVip(draft -> {
        }));
    }

    @PostMapping("query")
    public Page<@FetchBy(value = "COMPLEX_FETCHER_FOR_FRONT") Vip> query(@RequestBody QueryRequest<VipSpec> queryRequest) {
        queryRequest.getQuery().setCreatorId(StpUtil.getLoginIdAsString());
        return vipRepository.findPage(queryRequest, VipRepository.COMPLEX_FETCHER_FOR_FRONT);
    }

    @PostMapping("save")
    public String save(@RequestBody @Validated VipInput vipInput) {
        if (StringUtils.hasText(vipInput.getId())) {
            Vip vip = vipRepository.findById(vipInput.getId(), VipRepository.COMPLEX_FETCHER_FOR_FRONT).orElseThrow(() -> new BusinessException("数据不存在"));
            if (!vip.creator().id().equals(StpUtil.getLoginIdAsString())) {
                throw new BusinessException("只能修改自己的数据");
            }
        }
        return vipRepository.save(vipInput.toEntity()).id();
    }

    @DeleteMapping
    public Boolean delete(@RequestBody List<String> ids) {
        vipRepository.findByIds(ids, VipRepository.COMPLEX_FETCHER_FOR_FRONT).forEach(vip -> {
            if (!vip.creator().id().equals(StpUtil.getLoginIdAsString())) {
                throw new BusinessException("只能删除自己的数据");
            }
        });
        vipRepository.deleteAllById(ids);
        return true;
    }
}
