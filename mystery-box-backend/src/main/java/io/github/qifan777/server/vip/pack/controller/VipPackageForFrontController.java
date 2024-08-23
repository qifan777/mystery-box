package io.github.qifan777.server.vip.pack.controller;

import io.github.qifan777.server.infrastructure.model.QueryRequest;
import io.github.qifan777.server.vip.pack.entity.VipPackage;
import io.github.qifan777.server.vip.pack.entity.dto.VipPackageSpec;
import io.github.qifan777.server.vip.pack.repository.VipPackageRepository;
import io.qifan.infrastructure.common.exception.BusinessException;
import lombok.AllArgsConstructor;
import org.babyfish.jimmer.client.FetchBy;
import org.babyfish.jimmer.client.meta.DefaultFetcherOwner;
import org.springframework.data.domain.Page;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("front/vip-package")
@AllArgsConstructor
@DefaultFetcherOwner(VipPackageRepository.class)
@Transactional
public class VipPackageForFrontController {
    private final VipPackageRepository vipPackageRepository;

    @GetMapping("{id}")
    public @FetchBy(value = "COMPLEX_FETCHER_FOR_FRONT") VipPackage findById(@PathVariable String id) {
        return vipPackageRepository.findById(id, VipPackageRepository.COMPLEX_FETCHER_FOR_FRONT).orElseThrow(() -> new BusinessException("数据不存在"));
    }

    @PostMapping("query")
    public Page<@FetchBy(value = "COMPLEX_FETCHER_FOR_FRONT") VipPackage> query(@RequestBody QueryRequest<VipPackageSpec> queryRequest) {
        return vipPackageRepository.findPage(queryRequest, VipPackageRepository.COMPLEX_FETCHER_FOR_FRONT);
    }

}
