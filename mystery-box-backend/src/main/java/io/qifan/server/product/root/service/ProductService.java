package io.qifan.server.product.root.service;

import io.qifan.server.product.root.repository.ProductRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@AllArgsConstructor
@Transactional
public class ProductService {
    private final ProductRepository productRepository;

}