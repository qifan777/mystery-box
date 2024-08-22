package io.github.qifan777.server.vip.root.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
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
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("admin/vip")
@AllArgsConstructor
@DefaultFetcherOwner(VipRepository.class)
@SaCheckPermission("/vip")
@Transactional
public class VipForAdminController {
    private final VipRepository vipRepository;

    @GetMapping("{id}")
    public @FetchBy(value = "COMPLEX_FETCHER_FOR_ADMIN") Vip findById(@PathVariable String id) {
        return vipRepository.findById(id, VipRepository.COMPLEX_FETCHER_FOR_ADMIN).orElseThrow(() -> new BusinessException("数据不存在"));
    }

    @PostMapping("query")
    public Page<@FetchBy(value = "COMPLEX_FETCHER_FOR_ADMIN") Vip> query(@RequestBody QueryRequest<VipSpec> queryRequest) {
        return vipRepository.findPage(queryRequest, VipRepository.COMPLEX_FETCHER_FOR_ADMIN);
    }

    @PostMapping("save")
    public String save(@RequestBody @Validated VipInput vipInput) {
        return vipRepository.save(vipInput.toEntity()).id();
    }

    @DeleteMapping
    public Boolean delete(@RequestBody List<String> ids) {
        vipRepository.deleteAllById(ids);
        return true;
    }
}