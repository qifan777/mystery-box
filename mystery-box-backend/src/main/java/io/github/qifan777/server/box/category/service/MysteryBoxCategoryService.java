package io.github.qifan777.server.box.category.service;

import io.github.qifan777.server.box.category.repository.MysteryBoxCategoryRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@AllArgsConstructor
@Transactional
public class MysteryBoxCategoryService {
    private final MysteryBoxCategoryRepository mysteryBoxCategoryRepository;

}