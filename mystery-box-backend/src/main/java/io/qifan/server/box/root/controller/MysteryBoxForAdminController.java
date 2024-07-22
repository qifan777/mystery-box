package io.qifan.server.box.root.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import io.qifan.infrastructure.common.exception.BusinessException;
import io.qifan.server.box.root.entity.MysteryBox;
import io.qifan.server.box.root.entity.dto.MysteryBoxInput;
import io.qifan.server.box.root.entity.dto.MysteryBoxSpec;
import io.qifan.server.box.root.repository.MysteryBoxRepository;
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
@RequestMapping("admin/mystery-box")
@AllArgsConstructor
@DefaultFetcherOwner(MysteryBoxRepository.class)
@SaCheckPermission("/mystery-box")
@Transactional
public class MysteryBoxForAdminController {
    private final MysteryBoxRepository mysteryBoxRepository;

    @GetMapping("{id}")
    public @FetchBy(value = "COMPLEX_FETCHER_FOR_ADMIN") MysteryBox findById(@PathVariable String id) {
        return mysteryBoxRepository.findById(id, MysteryBoxRepository.COMPLEX_FETCHER_FOR_ADMIN).orElseThrow(() -> new BusinessException("数据不存在"));
    }

    @PostMapping("query")
    public Page<@FetchBy(value = "COMPLEX_FETCHER_FOR_ADMIN") MysteryBox> query(@RequestBody QueryRequest<MysteryBoxSpec> queryRequest) {
        return mysteryBoxRepository.findPage(queryRequest, MysteryBoxRepository.COMPLEX_FETCHER_FOR_ADMIN);
    }

    @PostMapping("save")
    public String save(@RequestBody @Validated MysteryBoxInput mysteryBoxInput) {
        return mysteryBoxRepository.save(mysteryBoxInput.toEntity()).id();
    }

    @DeleteMapping
    public Boolean delete(@RequestBody List<String> ids) {
        mysteryBoxRepository.deleteAllById(ids);
        return true;
    }
}