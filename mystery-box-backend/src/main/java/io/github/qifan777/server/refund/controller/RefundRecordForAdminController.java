package io.github.qifan777.server.refund.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import io.github.qifan777.server.infrastructure.model.QueryRequest;
import io.github.qifan777.server.refund.entity.RefundRecord;
import io.github.qifan777.server.refund.entity.dto.RefundRecordInput;
import io.github.qifan777.server.refund.entity.dto.RefundRecordSpec;
import io.github.qifan777.server.refund.repository.RefundRecordRepository;
import io.qifan.infrastructure.common.exception.BusinessException;
import lombok.AllArgsConstructor;
import org.babyfish.jimmer.client.FetchBy;
import org.babyfish.jimmer.client.meta.DefaultFetcherOwner;
import org.springframework.data.domain.Page;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("admin/refund-record")
@AllArgsConstructor
@DefaultFetcherOwner(RefundRecordRepository.class)
@SaCheckPermission("/refund-record")
@Transactional
public class RefundRecordForAdminController {
    private final RefundRecordRepository refundRecordRepository;

    @GetMapping("{id}")
    public @FetchBy(value = "COMPLEX_FETCHER_FOR_ADMIN") RefundRecord findById(@PathVariable String id) {
        return refundRecordRepository.findById(id, RefundRecordRepository.COMPLEX_FETCHER_FOR_ADMIN).orElseThrow(() -> new BusinessException("数据不存在"));
    }

    @PostMapping("query")
    public Page<@FetchBy(value = "COMPLEX_FETCHER_FOR_ADMIN") RefundRecord> query(@RequestBody QueryRequest<RefundRecordSpec> queryRequest) {
        return refundRecordRepository.findPage(queryRequest, RefundRecordRepository.COMPLEX_FETCHER_FOR_ADMIN);
    }

    @PostMapping("save")
    public String save(@RequestBody @Validated RefundRecordInput refundRecordInput) {
        return refundRecordRepository.save(refundRecordInput.toEntity()).id();
    }

    @DeleteMapping
    public Boolean delete(@RequestBody List<String> ids) {
        refundRecordRepository.deleteAllById(ids);
        return true;
    }
}