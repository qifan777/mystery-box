package io.github.qifan777.server.box.order.controller;

import cn.dev33.satoken.stp.StpUtil;
import com.github.binarywang.wxpay.bean.notify.SignatureHeader;
import com.github.binarywang.wxpay.bean.result.WxPayUnifiedOrderV3Result;
import io.github.qifan777.server.box.order.entity.MysteryBoxOrder;
import io.github.qifan777.server.box.order.entity.dto.MysteryBoxOrderInput;
import io.github.qifan777.server.box.order.entity.dto.MysteryBoxOrderSpec;
import io.github.qifan777.server.box.order.repository.MysteryBoxOrderRepository;
import io.github.qifan777.server.box.order.service.MysteryBoxOrderService;
import io.github.qifan777.server.box.root.entity.dto.MysteryBoxInput;
import io.github.qifan777.server.infrastructure.aop.NotRepeat;
import io.github.qifan777.server.infrastructure.model.QueryRequest;
import io.github.qifan777.server.payment.entity.dto.PaymentPriceView;
import io.qifan.infrastructure.common.exception.BusinessException;
import lombok.AllArgsConstructor;
import org.babyfish.jimmer.client.ApiIgnore;
import org.babyfish.jimmer.client.FetchBy;
import org.babyfish.jimmer.client.meta.DefaultFetcherOwner;
import org.springframework.data.domain.Page;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("front/mystery-box-order")
@AllArgsConstructor
@DefaultFetcherOwner(MysteryBoxOrderRepository.class)
@Transactional
public class MysteryBoxOrderForFrontController {
    private final MysteryBoxOrderRepository mysteryBoxOrderRepository;
    private final MysteryBoxOrderService mysteryBoxOrderService;

    @GetMapping("{id}")
    public @FetchBy(value = "COMPLEX_FETCHER_FOR_FRONT") MysteryBoxOrder findById(@PathVariable String id) {
        return mysteryBoxOrderRepository.findById(id, MysteryBoxOrderRepository.COMPLEX_FETCHER_FOR_FRONT).orElseThrow(() -> new BusinessException("数据不存在"));
    }

    @PostMapping("query")
    public Page<@FetchBy(value = "COMPLEX_FETCHER_FOR_FRONT") MysteryBoxOrder> query(@RequestBody QueryRequest<MysteryBoxOrderSpec> queryRequest) {
        queryRequest.getQuery().setCreatorId(StpUtil.getLoginIdAsString());
        return mysteryBoxOrderRepository.findPage(queryRequest, MysteryBoxOrderRepository.COMPLEX_FETCHER_FOR_FRONT);
    }

    @PostMapping("create")
    @NotRepeat
    public String create(@RequestBody @Validated MysteryBoxOrderInput mysteryBoxOrderInput) {
        return mysteryBoxOrderService.create(mysteryBoxOrderInput);
    }

    @PostMapping("{id}/prepay/wechat")
    @NotRepeat
    public WxPayUnifiedOrderV3Result.JsapiResult prepayWechat(@PathVariable String id) {
        return mysteryBoxOrderService.prepay(id);
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
        return mysteryBoxOrderService.paymentNotifyWechat(body, signatureHeader);
    }

    @PostMapping("{id}/unpaid/cancel/user")
    @NotRepeat
    public String unpaidCancelForUser(@PathVariable String id) {
        return mysteryBoxOrderService.unpaidCancelForUser(id);
    }


    @PostMapping("notify/refund/wechat")
    @ApiIgnore
    public String refundNotifyWeChat(@RequestBody String body,
                                     @RequestHeader(value = "Wechatpay-Timestamp") String timestamp,
                                     @RequestHeader(value = "Wechatpay-Nonce") String nonce,
                                     @RequestHeader(value = "Wechatpay-Signature") String signature,
                                     @RequestHeader(value = "Wechatpay-Serial") String serial) {
        SignatureHeader signatureHeader = SignatureHeader.builder().signature(signature)
                .serial(serial)
                .nonce(nonce)
                .timeStamp(timestamp).build();
        return mysteryBoxOrderService.refundNotifyWeChat(body, signatureHeader);
    }

    @PostMapping("calculate")
    public PaymentPriceView calculate(@Validated @RequestBody MysteryBoxOrderInput productOrderInput) {
        return mysteryBoxOrderService.calculate(productOrderInput);
    }
}
