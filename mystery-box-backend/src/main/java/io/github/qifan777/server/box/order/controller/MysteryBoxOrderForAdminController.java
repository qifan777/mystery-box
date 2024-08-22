package io.github.qifan777.server.box.order.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import cn.dev33.satoken.stp.StpUtil;
import io.github.qifan777.server.address.entity.dto.AddressView;
import io.github.qifan777.server.box.item.entity.MysteryBoxOrderItemFetcher;
import io.github.qifan777.server.box.order.entity.MysteryBoxOrder;
import io.github.qifan777.server.box.order.entity.dto.MysteryBoxOrderInput;
import io.github.qifan777.server.box.order.entity.dto.MysteryBoxOrderSpec;
import io.github.qifan777.server.box.order.repository.MysteryBoxOrderRepository;
import io.github.qifan777.server.box.order.service.MysteryBoxOrderService;
import io.github.qifan777.server.box.root.entity.dto.MystryBoxView;
import io.github.qifan777.server.infrastructure.aop.NotRepeat;
import io.github.qifan777.server.infrastructure.model.QueryRequest;
import io.qifan.infrastructure.common.exception.BusinessException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.babyfish.jimmer.client.FetchBy;
import org.babyfish.jimmer.client.meta.DefaultFetcherOwner;
import org.springframework.data.domain.Page;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.List;

@RestController
@RequestMapping("admin/mystery-box-order")
@AllArgsConstructor
@DefaultFetcherOwner(MysteryBoxOrderRepository.class)
@SaCheckPermission("/mystery-box-order")
@Transactional
@Slf4j
public class MysteryBoxOrderForAdminController {
    private final MysteryBoxOrderRepository mysteryBoxOrderRepository;
    private final MysteryBoxOrderService mysteryBoxOrderService;

    @GetMapping("{id}")
    public @FetchBy(value = "COMPLEX_FETCHER_FOR_ADMIN") MysteryBoxOrder findById(@PathVariable String id) {
        return mysteryBoxOrderRepository.findById(id, MysteryBoxOrderRepository.COMPLEX_FETCHER_FOR_ADMIN).orElseThrow(() -> new BusinessException("数据不存在"));
    }

    @PostMapping("query")
    public Page<@FetchBy(value = "COMPLEX_FETCHER_FOR_ADMIN") MysteryBoxOrder> query(@RequestBody QueryRequest<MysteryBoxOrderSpec> queryRequest) {
        return mysteryBoxOrderRepository.findPage(queryRequest, MysteryBoxOrderRepository.COMPLEX_FETCHER_FOR_ADMIN);
    }

    @PostMapping("save")
    public String save(@RequestBody @Validated MysteryBoxOrderInput mysteryBoxOrderInput) {
        return mysteryBoxOrderRepository.save(mysteryBoxOrderInput.toEntity()).id();
    }

    @DeleteMapping
    public Boolean delete(@RequestBody List<String> ids) {
        mysteryBoxOrderRepository.deleteAllById(ids);
        return true;
    }

    @PostMapping("{id}/paid/cancel")
    @NotRepeat
    public String paidCancelForAdmin(@PathVariable String id) {
        return mysteryBoxOrderService.paidCancelForAdmin(id);
    }

    @PostMapping("{id}/deliver")
    @NotRepeat
    public String deliver(@PathVariable String id, @RequestParam String trackingNumber) {
        return mysteryBoxOrderService.deliver(id, trackingNumber);
    }

    @Scheduled(cron = "0 0/1 * * * ?")
    public void unpaidCancelAutoCancel() {
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(new MockHttpServletRequest()));
        mysteryBoxOrderRepository.findUnpaidOrder().forEach(mysteryBoxOrder -> {
            try {
                StpUtil.switchTo(mysteryBoxOrder.creator().id());
                mysteryBoxOrderService.unpaidCancelForUser(mysteryBoxOrder.id());
            } catch (Exception e) {
                log.error("自动取消订单失败", e);
            }
        });
    }
    @GetMapping("not-used1")
    public MystryBoxView notUsed1() {
        return null;
    }
    @GetMapping("not-used2")
    public AddressView notUsed2() {
        return null;
    }
}