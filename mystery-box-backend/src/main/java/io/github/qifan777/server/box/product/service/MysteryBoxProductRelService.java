package io.github.qifan777.server.box.product.service;

import io.github.qifan777.server.box.product.repository.MysteryBoxProductRelRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@AllArgsConstructor
@Transactional
public class MysteryBoxProductRelService {
    private final MysteryBoxProductRelRepository mysteryBoxProductRelRepository;

}