package io.qifan.server.product.category.service;

import io.qifan.server.product.category.repository.ProductCategoryRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@AllArgsConstructor
@Transactional
public class ProductCategoryService {
    private final ProductCategoryRepository productCategoryRepository;

}