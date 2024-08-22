package io.github.qifan777.server.refund.service;

import io.github.qifan777.server.refund.repository.RefundRecordRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@AllArgsConstructor
@Transactional
public class RefundRecordService {
    private final RefundRecordRepository refundRecordRepository;

}