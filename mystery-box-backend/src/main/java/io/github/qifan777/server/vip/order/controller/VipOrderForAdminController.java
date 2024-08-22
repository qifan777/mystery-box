package io.github.qifan777.server.vip.order.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import io.github.qifan777.server.infrastructure.model.QueryRequest;
import io.github.qifan777.server.vip.order.entity.VipOrder;
import io.github.qifan777.server.vip.order.entity.dto.VipOrderInput;
import io.github.qifan777.server.vip.order.entity.dto.VipOrderSpec;
import io.github.qifan777.server.vip.order.repository.VipOrderRepository;
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
@RequestMapping("admin/vip-order")
@AllArgsConstructor
@DefaultFetcherOwner(VipOrderRepository.class)
@SaCheckPermission("/vip-order")
@Transactional
public class VipOrderForAdminController {
    private final VipOrderRepository vipOrderRepository;

    @GetMapping("{id}")
    public @FetchBy(value = "COMPLEX_FETCHER_FOR_ADMIN") VipOrder findById(@PathVariable String id) {
        return vipOrderRepository.findById(id, VipOrderRepository.COMPLEX_FETCHER_FOR_ADMIN).orElseThrow(() -> new BusinessException("数据不存在"));
    }

    @PostMapping("query")
    public Page<@FetchBy(value = "COMPLEX_FETCHER_FOR_ADMIN") VipOrder> query(@RequestBody QueryRequest<VipOrderSpec> queryRequest) {
        return vipOrderRepository.findPage(queryRequest, VipOrderRepository.COMPLEX_FETCHER_FOR_ADMIN);
    }

    @PostMapping("save")
    public String save(@RequestBody @Validated VipOrderInput vipOrderInput) {
        return vipOrderRepository.save(vipOrderInput.toEntity()).id();
    }

    @DeleteMapping
    public Boolean delete(@RequestBody List<String> ids) {
        vipOrderRepository.deleteAllById(ids);
        return true;
    }
}