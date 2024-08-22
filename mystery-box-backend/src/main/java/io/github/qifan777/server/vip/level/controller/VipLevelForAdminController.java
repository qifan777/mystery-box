package io.github.qifan777.server.vip.level.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import io.github.qifan777.server.infrastructure.model.QueryRequest;
import io.github.qifan777.server.vip.level.entity.VipLevel;
import io.github.qifan777.server.vip.level.entity.dto.VipLevelInput;
import io.github.qifan777.server.vip.level.entity.dto.VipLevelSpec;
import io.github.qifan777.server.vip.level.repository.VipLevelRepository;
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
@RequestMapping("admin/vip-level")
@AllArgsConstructor
@DefaultFetcherOwner(VipLevelRepository.class)
@SaCheckPermission("/vip-level")
@Transactional
public class VipLevelForAdminController {
    private final VipLevelRepository vipLevelRepository;

    @GetMapping("{id}")
    public @FetchBy(value = "COMPLEX_FETCHER_FOR_ADMIN") VipLevel findById(@PathVariable String id) {
        return vipLevelRepository.findById(id, VipLevelRepository.COMPLEX_FETCHER_FOR_ADMIN).orElseThrow(() -> new BusinessException("数据不存在"));
    }

    @PostMapping("query")
    public Page<@FetchBy(value = "COMPLEX_FETCHER_FOR_ADMIN") VipLevel> query(@RequestBody QueryRequest<VipLevelSpec> queryRequest) {
        return vipLevelRepository.findPage(queryRequest, VipLevelRepository.COMPLEX_FETCHER_FOR_ADMIN);
    }

    @PostMapping("save")
    public String save(@RequestBody @Validated VipLevelInput vipLevelInput) {
        return vipLevelRepository.save(vipLevelInput.toEntity()).id();
    }

    @DeleteMapping
    public Boolean delete(@RequestBody List<String> ids) {
        vipLevelRepository.deleteAllById(ids);
        return true;
    }
}