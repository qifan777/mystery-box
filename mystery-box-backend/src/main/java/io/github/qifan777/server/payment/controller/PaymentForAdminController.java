package io.github.qifan777.server.payment.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import io.github.qifan777.server.infrastructure.model.QueryRequest;
import io.github.qifan777.server.payment.entity.Payment;
import io.github.qifan777.server.payment.entity.dto.PaymentInput;
import io.github.qifan777.server.payment.entity.dto.PaymentSpec;
import io.github.qifan777.server.payment.repository.PaymentRepository;
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
@RequestMapping("admin/payment")
@AllArgsConstructor
@DefaultFetcherOwner(PaymentRepository.class)
@SaCheckPermission("/payment")
@Transactional
public class PaymentForAdminController {
    private final PaymentRepository paymentRepository;

    @GetMapping("{id}")
    public @FetchBy(value = "COMPLEX_FETCHER_FOR_ADMIN") Payment findById(@PathVariable String id) {
        return paymentRepository.findById(id, PaymentRepository.COMPLEX_FETCHER_FOR_ADMIN).orElseThrow(() -> new BusinessException("数据不存在"));
    }

    @PostMapping("query")
    public Page<@FetchBy(value = "COMPLEX_FETCHER_FOR_ADMIN") Payment> query(@RequestBody QueryRequest<PaymentSpec> queryRequest) {
        return paymentRepository.findPage(queryRequest, PaymentRepository.COMPLEX_FETCHER_FOR_ADMIN);
    }

    @PostMapping("save")
    public String save(@RequestBody @Validated PaymentInput paymentInput) {
        return paymentRepository.save(paymentInput.toEntity()).id();
    }

    @DeleteMapping
    public Boolean delete(@RequestBody List<String> ids) {
        paymentRepository.deleteAllById(ids);
        return true;
    }
}