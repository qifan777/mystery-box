package io.github.qifan777.server.vip.level.controller;

import io.github.qifan777.server.infrastructure.model.QueryRequest;
import io.github.qifan777.server.vip.level.entity.VipLevel;
import io.github.qifan777.server.vip.level.entity.dto.VipLevelSpec;
import io.github.qifan777.server.vip.level.repository.VipLevelRepository;
import io.qifan.infrastructure.common.exception.BusinessException;
import lombok.AllArgsConstructor;
import org.babyfish.jimmer.client.FetchBy;
import org.babyfish.jimmer.client.meta.DefaultFetcherOwner;
import org.springframework.data.domain.Page;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("front/vip-level")
@AllArgsConstructor
@DefaultFetcherOwner(VipLevelRepository.class)
@Transactional
public class VipLevelForFrontController {
    private final VipLevelRepository vipLevelRepository;

    @GetMapping("{id}")
    public @FetchBy(value = "COMPLEX_FETCHER_FOR_FRONT") VipLevel findById(@PathVariable String id) {
        return vipLevelRepository.findById(id, VipLevelRepository.COMPLEX_FETCHER_FOR_FRONT).orElseThrow(() -> new BusinessException("数据不存在"));
    }

    @PostMapping("query")
    public Page<@FetchBy(value = "COMPLEX_FETCHER_FOR_FRONT") VipLevel> query(@RequestBody QueryRequest<VipLevelSpec> queryRequest) {
        return vipLevelRepository.findPage(queryRequest, VipLevelRepository.COMPLEX_FETCHER_FOR_FRONT);
    }

}
