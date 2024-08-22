package io.github.qifan777.server.vip.order.controller;

import cn.dev33.satoken.stp.StpUtil;
import com.github.binarywang.wxpay.bean.notify.SignatureHeader;
import com.github.binarywang.wxpay.bean.result.WxPayUnifiedOrderV3Result;
import io.github.qifan777.server.infrastructure.model.QueryRequest;
import io.github.qifan777.server.vip.order.entity.VipOrder;
import io.github.qifan777.server.vip.order.entity.dto.VipOrderInput;
import io.github.qifan777.server.vip.order.entity.dto.VipOrderSpec;
import io.github.qifan777.server.vip.order.repository.VipOrderRepository;
import io.github.qifan777.server.vip.order.service.VipOrderService;
import io.qifan.infrastructure.common.exception.BusinessException;
import lombok.AllArgsConstructor;
import org.babyfish.jimmer.client.ApiIgnore;
import org.babyfish.jimmer.client.FetchBy;
import org.babyfish.jimmer.client.meta.DefaultFetcherOwner;
import org.springframework.data.domain.Page;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("front/vip-order")
@AllArgsConstructor
@DefaultFetcherOwner(VipOrderRepository.class)
@Transactional
public class VipOrderForFrontController {
    private final VipOrderRepository vipOrderRepository;
    private final VipOrderService vipOrderService;

    @GetMapping("{id}")
    public @FetchBy(value = "COMPLEX_FETCHER_FOR_FRONT") VipOrder findById(@PathVariable String id) {
        return vipOrderRepository.findById(id, VipOrderRepository.COMPLEX_FETCHER_FOR_FRONT).orElseThrow(() -> new BusinessException("数据不存在"));
    }

    @PostMapping("query")
    public Page<@FetchBy(value = "COMPLEX_FETCHER_FOR_FRONT") VipOrder> query(@RequestBody QueryRequest<VipOrderSpec> queryRequest) {
        queryRequest.getQuery().setCreatorId(StpUtil.getLoginIdAsString());
        return vipOrderRepository.findPage(queryRequest, VipOrderRepository.COMPLEX_FETCHER_FOR_FRONT);
    }

    @PostMapping("save")
    public WxPayUnifiedOrderV3Result.JsapiResult save(@RequestBody @Validated VipOrderInput vipOrderInput) {
        return vipOrderService.save(vipOrderInput);
    }

    @DeleteMapping
    public Boolean delete(@RequestBody List<String> ids) {
        vipOrderRepository.findByIds(ids, VipOrderRepository.COMPLEX_FETCHER_FOR_FRONT).forEach(vipOrder -> {
            if (!vipOrder.creator().id().equals(StpUtil.getLoginIdAsString())) {
                throw new BusinessException("只能删除自己的数据");
            }
        });
        vipOrderRepository.deleteAllById(ids);
        return true;
    }

    @PostMapping("notify/pay/wechat")
    @ApiIgnore
    public String paymentNotifyWechat(@RequestBody String body,
                                      @RequestHeader(value = "Wechatpay-Timestamp") String timestamp,
                                      @RequestHeader(value = "Wechatpay-Nonce") String nonce,
                                      @RequestHeader(value = "Wechatpay-Signature") String signature,
                                      @RequestHeader(value = "Wechatpay-Serial") String serial) {
        SignatureHeader signatureHeader = SignatureHeader.builder().signature(signature)
                .serial(serial)
                .nonce(nonce)
                .timeStamp(timestamp).build();
        return vipOrderService.paymentNotifyWechat(body, signatureHeader);
    }
}
