package io.github.qifan777.server.product.root.controller;

import cn.dev33.satoken.stp.StpUtil;
import io.qifan.infrastructure.common.exception.BusinessException;
import io.github.qifan777.server.infrastructure.model.QueryRequest;
import io.github.qifan777.server.product.root.entity.Product;
import io.github.qifan777.server.product.root.entity.dto.ProductInput;
import io.github.qifan777.server.product.root.entity.dto.ProductSpec;
import io.github.qifan777.server.product.root.repository.ProductRepository;
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
@RequestMapping("front/product")
@AllArgsConstructor
@DefaultFetcherOwner(ProductRepository.class)
@Transactional
public class ProductForFrontController {
    private final ProductRepository productRepository;

    @GetMapping("{id}")
    public @FetchBy(value = "COMPLEX_FETCHER_FOR_FRONT") Product findById(@PathVariable String id) {
        return productRepository.findById(id, ProductRepository.COMPLEX_FETCHER_FOR_FRONT).orElseThrow(() -> new BusinessException("数据不存在"));
    }

    @PostMapping("query")
    public Page<@FetchBy(value = "COMPLEX_FETCHER_FOR_FRONT") Product> query(@RequestBody QueryRequest<ProductSpec> queryRequest) {
        queryRequest.getQuery().setCreatorId(StpUtil.getLoginIdAsString());
        return productRepository.findPage(queryRequest, ProductRepository.COMPLEX_FETCHER_FOR_FRONT);
    }

    @PostMapping("save")
    public String save(@RequestBody @Validated ProductInput productInput) {
        if (StringUtils.hasText(productInput.getId())) {
            Product product = productRepository.findById(productInput.getId(), ProductRepository.COMPLEX_FETCHER_FOR_FRONT).orElseThrow(() -> new BusinessException("数据不存在"));
            if (!product.creator().id().equals(StpUtil.getLoginIdAsString())) {
                throw new BusinessException("只能修改自己的数据");
            }
        }
        return productRepository.save(productInput.toEntity()).id();
    }

    @DeleteMapping
    public Boolean delete(@RequestBody List<String> ids) {
        productRepository.findByIds(ids, ProductRepository.COMPLEX_FETCHER_FOR_FRONT).forEach(product -> {
            if (!product.creator().id().equals(StpUtil.getLoginIdAsString())) {
                throw new BusinessException("只能删除自己的数据");
            }
        });
        productRepository.deleteAllById(ids);
        return true;
    }
}
