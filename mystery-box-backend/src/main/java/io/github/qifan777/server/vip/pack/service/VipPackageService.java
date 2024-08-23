package io.github.qifan777.server.vip.pack.service;

import io.github.qifan777.server.vip.pack.repository.VipPackageRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@AllArgsConstructor
@Transactional
public class VipPackageService {
    private final VipPackageRepository vipPackageRepository;

}