package io.github.qifan777.server.vip.level.service;

import io.github.qifan777.server.vip.level.repository.VipLevelRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@AllArgsConstructor
@Transactional
public class VipLevelService {
    private final VipLevelRepository vipLevelRepository;

}