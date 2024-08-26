package io.github.qifan777.server.box.category.controller;
import cn.dev33.satoken.annotation.SaCheckPermission;
import io.github.qifan777.server.box.category.entity.MysteryBoxCategory;
import io.github.qifan777.server.box.category.entity.dto.MysteryBoxCategoryInput;
import io.github.qifan777.server.box.category.entity.dto.MysteryBoxCategorySpec;
import io.github.qifan777.server.box.category.repository.MysteryBoxCategoryRepository;
import io.github.qifan777.server.infrastructure.model.QueryRequest;
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
@RequestMapping("admin/mystery-box-category")
@AllArgsConstructor
@DefaultFetcherOwner(MysteryBoxCategoryRepository.class)
@SaCheckPermission("/mystery-box-category")
@Transactional
public class  MysteryBoxCategoryForAdminController {
    private final  MysteryBoxCategoryRepository mysteryBoxCategoryRepository;

    @GetMapping("{id}")
    public @FetchBy(value = "COMPLEX_FETCHER_FOR_ADMIN")  MysteryBoxCategory findById(@PathVariable String id) {
        return mysteryBoxCategoryRepository.findById(id,MysteryBoxCategoryRepository.COMPLEX_FETCHER_FOR_ADMIN).orElseThrow(() -> new BusinessException("数据不存在"));
    }

    @PostMapping("query")
    public Page< @FetchBy(value = "COMPLEX_FETCHER_FOR_ADMIN")  MysteryBoxCategory> query(@RequestBody QueryRequest<MysteryBoxCategorySpec> queryRequest) {
        return mysteryBoxCategoryRepository.findPage(queryRequest, MysteryBoxCategoryRepository.COMPLEX_FETCHER_FOR_ADMIN);
    }

    @PostMapping("save")
    public String save(@RequestBody @Validated MysteryBoxCategoryInput mysteryBoxCategoryInput) {
        return mysteryBoxCategoryRepository.save(mysteryBoxCategoryInput.toEntity()).id();
    }

    @DeleteMapping
    public Boolean delete(@RequestBody List<String> ids) {
        mysteryBoxCategoryRepository.deleteAllById(ids);
        return true;
    }
}