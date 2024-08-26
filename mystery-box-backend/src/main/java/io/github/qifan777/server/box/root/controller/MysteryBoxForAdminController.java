package io.github.qifan777.server.box.root.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import io.github.qifan777.server.Objects;
import io.github.qifan777.server.box.root.entity.MysteryBox;
import io.github.qifan777.server.box.root.entity.dto.MysteryBoxInput;
import io.github.qifan777.server.box.root.entity.dto.MysteryBoxSpec;
import io.github.qifan777.server.box.root.repository.MysteryBoxRepository;
import io.github.qifan777.server.infrastructure.model.QueryRequest;
import io.qifan.infrastructure.common.exception.BusinessException;
import lombok.AllArgsConstructor;
import org.babyfish.jimmer.client.FetchBy;
import org.babyfish.jimmer.client.meta.DefaultFetcherOwner;
import org.springframework.data.domain.Page;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
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
        MysteryBox entity = mysteryBoxInput.toEntity();
        MysteryBox mysteryBox = Objects.createMysteryBox(entity, draft -> {
            draft.setBoxRelList(new ArrayList<>());
            for (String productId : mysteryBoxInput.getProductIds()) {
                draft.addIntoBoxRelList(relDraft -> {
                    relDraft.setMysteryBox(entity)
                            .setProductId(productId);
                });
            }
        });
        return mysteryBoxRepository.save(mysteryBox).id();
    }

    @DeleteMapping
    public Boolean delete(@RequestBody List<String> ids) {
        mysteryBoxRepository.deleteAllById(ids);
        return true;
    }

}