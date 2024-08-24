package io.github.qifan777.server.vip.pack.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import io.github.qifan777.server.infrastructure.model.QueryRequest;
import io.github.qifan777.server.vip.pack.entity.VipPackage;
import io.github.qifan777.server.vip.pack.entity.dto.VipPackageInput;
import io.github.qifan777.server.vip.pack.entity.dto.VipPackageSpec;
import io.github.qifan777.server.vip.pack.repository.VipPackageRepository;
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
@RequestMapping("admin/vip-package")
@AllArgsConstructor
@DefaultFetcherOwner(VipPackageRepository.class)
@SaCheckPermission("/vip-package")
@Transactional
public class VipPackageForAdminController {
    private final VipPackageRepository vipPackageRepository;

    @GetMapping("{id}")
    public @FetchBy(value = "COMPLEX_FETCHER_FOR_ADMIN") VipPackage findById(@PathVariable String id) {
        return vipPackageRepository.findById(id, VipPackageRepository.COMPLEX_FETCHER_FOR_ADMIN).orElseThrow(() -> new BusinessException("数据不存在"));
    }

    @PostMapping("query")
    public Page<@FetchBy(value = "COMPLEX_FETCHER_FOR_ADMIN") VipPackage> query(@RequestBody QueryRequest<VipPackageSpec> queryRequest) {
        return vipPackageRepository.findPage(queryRequest, VipPackageRepository.COMPLEX_FETCHER_FOR_ADMIN);
    }

    @PostMapping("save")
    public String save(@RequestBody @Validated VipPackageInput vipPackageInput) {
        return vipPackageRepository.save(vipPackageInput.toEntity()).id();
    }

    @DeleteMapping
    public Boolean delete(@RequestBody List<String> ids) {
        vipPackageRepository.deleteAllById(ids);
        return true;
    }
}