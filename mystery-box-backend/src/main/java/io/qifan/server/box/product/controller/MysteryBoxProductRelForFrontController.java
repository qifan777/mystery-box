package io.qifan.server.box.product.controller;

import cn.dev33.satoken.stp.StpUtil;
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
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("front/mystery-box-product-rel")
@AllArgsConstructor
@DefaultFetcherOwner(MysteryBoxProductRelRepository.class)
@Transactional
public class MysteryBoxProductRelForFrontController {
    private final MysteryBoxProductRelRepository mysteryBoxProductRelRepository;

    @GetMapping("{id}")
    public @FetchBy(value = "COMPLEX_FETCHER_FOR_FRONT") MysteryBoxProductRel findById(@PathVariable String id) {
        return mysteryBoxProductRelRepository.findById(id, MysteryBoxProductRelRepository.COMPLEX_FETCHER_FOR_FRONT).orElseThrow(() -> new BusinessException("数据不存在"));
    }

    @PostMapping("query")
    public Page<@FetchBy(value = "COMPLEX_FETCHER_FOR_FRONT") MysteryBoxProductRel> query(@RequestBody QueryRequest<MysteryBoxProductRelSpec> queryRequest) {
        queryRequest.getQuery().setCreatorId(StpUtil.getLoginIdAsString());
        return mysteryBoxProductRelRepository.findPage(queryRequest, MysteryBoxProductRelRepository.COMPLEX_FETCHER_FOR_FRONT);
    }

    @PostMapping("save")
    public String save(@RequestBody @Validated MysteryBoxProductRelInput mysteryBoxProductRelInput) {
        if (StringUtils.hasText(mysteryBoxProductRelInput.getId())) {
            MysteryBoxProductRel mysteryBoxProductRel = mysteryBoxProductRelRepository.findById(mysteryBoxProductRelInput.getId(), MysteryBoxProductRelRepository.COMPLEX_FETCHER_FOR_FRONT).orElseThrow(() -> new BusinessException("数据不存在"));
            if (!mysteryBoxProductRel.creator().id().equals(StpUtil.getLoginIdAsString())) {
                throw new BusinessException("只能修改自己的数据");
            }
        }
        return mysteryBoxProductRelRepository.save(mysteryBoxProductRelInput.toEntity()).id();
    }

    @DeleteMapping
    public Boolean delete(@RequestBody List<String> ids) {
        mysteryBoxProductRelRepository.findByIds(ids, MysteryBoxProductRelRepository.COMPLEX_FETCHER_FOR_FRONT).forEach(mysteryBoxProductRel -> {
            if (!mysteryBoxProductRel.creator().id().equals(StpUtil.getLoginIdAsString())) {
                throw new BusinessException("只能删除自己的数据");
            }
        });
        mysteryBoxProductRelRepository.deleteAllById(ids);
        return true;
    }
}
