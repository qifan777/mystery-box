
package io.github.qifan777.server.box.category.controller;

import io.github.qifan777.server.box.category.entity.MysteryBoxCategory;
import io.github.qifan777.server.box.category.entity.dto.MysteryBoxCategorySpec;
import io.github.qifan777.server.box.category.repository.MysteryBoxCategoryRepository;
import io.github.qifan777.server.infrastructure.model.QueryRequest;
import io.qifan.infrastructure.common.exception.BusinessException;
import lombok.AllArgsConstructor;
import org.babyfish.jimmer.client.FetchBy;
import org.babyfish.jimmer.client.meta.DefaultFetcherOwner;
import org.springframework.data.domain.Page;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("front/mystery-box-category")
@AllArgsConstructor
@DefaultFetcherOwner(MysteryBoxCategoryRepository.class)
@Transactional
public class MysteryBoxCategoryForFrontController {
    private final MysteryBoxCategoryRepository mysteryBoxCategoryRepository;

    @GetMapping("{id}")
    public @FetchBy(value = "COMPLEX_FETCHER_FOR_FRONT") MysteryBoxCategory findById(@PathVariable String id) {
        return mysteryBoxCategoryRepository.findById(id, MysteryBoxCategoryRepository.COMPLEX_FETCHER_FOR_FRONT).orElseThrow(() -> new BusinessException("数据不存在"));
    }

    @PostMapping("query")
    public Page<@FetchBy(value = "COMPLEX_FETCHER_FOR_FRONT") MysteryBoxCategory> query(@RequestBody QueryRequest<MysteryBoxCategorySpec> queryRequest) {
        return mysteryBoxCategoryRepository.findPage(queryRequest, MysteryBoxCategoryRepository.COMPLEX_FETCHER_FOR_FRONT);
    }
}
