package io.qifan.server.box.product.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import io.qifan.infrastructure.common.exception.BusinessException;
import io.qifan.server.box.product.entity.MysteryBoxProductRel;
import io.qifan.server.box.product.entity.dto.MysteryBoxProductRelInput;
import io.qifan.server.box.product.entity.dto.MysteryBoxProductRelSpec;
import io.qifan.server.box.product.repository.MysteryBoxProductRelRepository;
import io.qifan.server.infrastructure.model.QueryRequest;
import lombok.AllArgsConstructor;
import org.babyfish.jimmer.client.FetchBy;
import org.babyfish.jimmer.client.meta.DefaultFetcherOwner;
import org.springframework.data.domain.Page;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("admin/mystery-box-product-rel")
@AllArgsConstructor
@DefaultFetcherOwner(MysteryBoxProductRelRepository.class)
@SaCheckPermission("/mystery-box-product-rel")
@Transactional
public class MysteryBoxProductRelForAdminController {
    private final MysteryBoxProductRelRepository mysteryBoxProductRelRepository;

    @GetMapping("{id}")
    public @FetchBy(value = "COMPLEX_FETCHER_FOR_ADMIN") MysteryBoxProductRel findById(@PathVariable String id) {
        return mysteryBoxProductRelRepository.findById(id, MysteryBoxProductRelRepository.COMPLEX_FETCHER_FOR_ADMIN).orElseThrow(() -> new BusinessException("数据不存在"));
    }

    @PostMapping("query")
    public Page<@FetchBy(value = "COMPLEX_FETCHER_FOR_ADMIN") MysteryBoxProductRel> query(@RequestBody QueryRequest<MysteryBoxProductRelSpec> queryRequest) {
        return mysteryBoxProductRelRepository.findPage(queryRequest, MysteryBoxProductRelRepository.COMPLEX_FETCHER_FOR_ADMIN);
    }

    @PostMapping("save")
    public String save(@RequestBody @Validated MysteryBoxProductRelInput mysteryBoxProductRelInput) {
        return mysteryBoxProductRelRepository.save(mysteryBoxProductRelInput.toEntity()).id();
    }

    @DeleteMapping
    public Boolean delete(@RequestBody List<String> ids) {
        mysteryBoxProductRelRepository.deleteAllById(ids);
        return true;
    }
}