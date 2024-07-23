package io.qifan.server.box.root.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import io.qifan.infrastructure.common.exception.BusinessException;
import io.qifan.server.Objects;
import io.qifan.server.TableExes;
import io.qifan.server.box.product.entity.MysteryBoxProductRelTableEx;
import io.qifan.server.box.product.model.MysteryBoxProductSpec;
import io.qifan.server.box.root.entity.MysteryBox;
import io.qifan.server.box.root.entity.dto.MysteryBoxInput;
import io.qifan.server.box.root.entity.dto.MysteryBoxSpec;
import io.qifan.server.box.root.repository.MysteryBoxRepository;
import io.qifan.server.infrastructure.model.QueryRequest;
import io.qifan.server.product.root.entity.Product;
import io.qifan.server.product.root.repository.ProductRepository;
import lombok.AllArgsConstructor;
import org.babyfish.jimmer.client.FetchBy;
import org.babyfish.jimmer.client.meta.DefaultFetcherOwner;
import org.babyfish.jimmer.sql.JSqlClient;
import org.babyfish.jimmer.sql.ast.Predicate;
import org.babyfish.jimmer.sql.ast.query.ConfigurableSubQuery;
import org.springframework.data.domain.Page;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
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
    private final JSqlClient jSqlClient;
    private final ProductRepository productRepository;

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