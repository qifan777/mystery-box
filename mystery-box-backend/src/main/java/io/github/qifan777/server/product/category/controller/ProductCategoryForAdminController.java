package io.github.qifan777.server.product.category.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import io.qifan.infrastructure.common.exception.BusinessException;
import io.github.qifan777.server.infrastructure.model.QueryRequest;
import io.github.qifan777.server.product.category.entity.ProductCategory;
import io.github.qifan777.server.product.category.entity.dto.ProductCategoryInput;
import io.github.qifan777.server.product.category.entity.dto.ProductCategorySpec;
import io.github.qifan777.server.product.category.repository.ProductCategoryRepository;
import lombok.AllArgsConstructor;
import org.babyfish.jimmer.client.FetchBy;
import org.babyfish.jimmer.client.meta.DefaultFetcherOwner;
import org.springframework.data.domain.Page;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("admin/product-category")
@AllArgsConstructor
@DefaultFetcherOwner(ProductCategoryRepository.class)
@SaCheckPermission("/product-category")
@Transactional
public class ProductCategoryForAdminController {
    private final ProductCategoryRepository productCategoryRepository;

    @GetMapping("{id}")
    public @FetchBy(value = "COMPLEX_FETCHER_FOR_ADMIN") ProductCategory findById(@PathVariable String id) {
        return productCategoryRepository.findById(id, ProductCategoryRepository.COMPLEX_FETCHER_FOR_ADMIN).orElseThrow(() -> new BusinessException("数据不存在"));
    }

    @PostMapping("query")
    public Page<@FetchBy(value = "COMPLEX_FETCHER_FOR_ADMIN") ProductCategory> query(@RequestBody QueryRequest<ProductCategorySpec> queryRequest) {
        return productCategoryRepository.findPage(queryRequest, ProductCategoryRepository.COMPLEX_FETCHER_FOR_ADMIN);
    }

    @PostMapping("save")
    public String save(@RequestBody @Validated ProductCategoryInput productCategoryInput) {
        return productCategoryRepository.save(productCategoryInput.toEntity()).id();
    }

    @DeleteMapping
    public Boolean delete(@RequestBody List<String> ids) {
        productCategoryRepository.deleteAllById(ids);
        return true;
    }
}