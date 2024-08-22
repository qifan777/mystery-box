package io.github.qifan777.server.vip.order.service;

import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.util.IdUtil;
import com.github.binarywang.wxpay.bean.notify.SignatureHeader;
import com.github.binarywang.wxpay.bean.notify.WxPayNotifyV3Result;
import com.github.binarywang.wxpay.bean.result.WxPayUnifiedOrderV3Result;
import com.github.binarywang.wxpay.service.WxPayService;
import io.github.qifan777.server.dict.model.DictConstants;
import io.github.qifan777.server.payment.entity.Payment;
import io.github.qifan777.server.payment.entity.PaymentDraft;
import io.github.qifan777.server.payment.model.WeChatPayModel;
import io.github.qifan777.server.payment.service.WeChatPayService;
import io.github.qifan777.server.vip.level.entity.VipLevel;
import io.github.qifan777.server.vip.level.repository.VipLevelRepository;
import io.github.qifan777.server.vip.order.entity.VipOrder;
import io.github.qifan777.server.vip.order.entity.VipOrderDraft;
import io.github.qifan777.server.vip.order.entity.dto.VipOrderInput;
import io.github.qifan777.server.vip.order.repository.VipOrderRepository;
import io.github.qifan777.server.vip.root.entity.Vip;
import io.github.qifan777.server.vip.root.entity.VipDraft;
import io.github.qifan777.server.vip.root.repository.VipRepository;
import io.qifan.infrastructure.common.constants.ResultCode;
import io.qifan.infrastructure.common.exception.BusinessException;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Service
@Slf4j
@AllArgsConstructor
@Transactional
public class VipOrderService {
    private final VipOrderRepository vipOrderRepository;
    private final VipLevelRepository vipLevelRepository;
    private final WeChatPayService weChatPayService;
    private final VipRepository vipRepository;
    private final WxPayService wxPayService;

    private Payment initPayment(BigDecimal price) {
        return PaymentDraft.$.produce(draft -> {
            draft.setProductAmount(price)
                    .setPayType(DictConstants.PayType.WE_CHAT_PAY)
                    .setCouponAmount(BigDecimal.ZERO)
                    .setVipAmount(BigDecimal.ZERO)
                    .setDeliveryFee(BigDecimal.ZERO)
                    .setPayAmount(price)
                    .setId(IdUtil.fastSimpleUUID());
        });
    }

    public WxPayUnifiedOrderV3Result.JsapiResult save(VipOrderInput vipOrderInput) {
        VipLevel vipLevel = vipLevelRepository.findById(vipOrderInput.getVipLevelId()).orElseThrow(() -> new BusinessException(ResultCode.NotFindError));
        Payment payment = this.initPayment(vipLevel.price());
        String orderId = IdUtil.fastSimpleUUID();
        VipOrder produce = VipOrderDraft.$.produce(vipOrderInput.toEntity(),draft -> {
            draft.setId(orderId)
                    .setUserId(StpUtil.getLoginIdAsString());
            draft.applyBaseOrder(baseOrderDraft -> {
                baseOrderDraft.setId(orderId)
                        .setPayment(payment)
                        .setRemark("userId:" + StpUtil.getLoginIdAsString() + ";vipLevelId:" + vipLevel.id())
                        .setType(DictConstants.OrderType.VIP_ORDER)
                ;
            });
        });
        VipOrder activityOrder = vipOrderRepository.save(produce);
        return weChatPayService.prepay(new WeChatPayModel().setBaseOrder(activityOrder.baseOrder())
                .setExpiredMinutes(5)
                .setNotifyUrl("/front/vip-order/notify/pay/wechat"));
    }

    @SneakyThrows
    public String paymentNotifyWechat(String body, SignatureHeader signatureHeader) {
        WxPayNotifyV3Result.DecryptNotifyResult notifyResult = wxPayService.parseOrderNotifyV3Result(
                        body, signatureHeader)
                .getResult();
        log.info("收到微信支付回调通知，订单号：{}", notifyResult);
        String outTradeNo = notifyResult.getOutTradeNo();
        VipOrder vipOrder = vipOrderRepository.findById(outTradeNo, VipOrderRepository.COMPLEX_FETCHER_FOR_ADMIN).orElseThrow(() -> new BusinessException(ResultCode.NotFindError, "订单不存在"));
        StpUtil.switchTo(vipOrder.creator().id());
        // 插入中间表
        Vip vip = vipRepository.findCurrentUserVip()
                .orElseGet(() ->
                        VipDraft.$.produce(draft -> {
                            draft.setUserId(StpUtil.getLoginIdAsString())
                                    .setEndTime(LocalDateTime.now());
                        }));
        vipRepository.save(VipDraft.$.produce(vip, draft -> {
            LocalDateTime endTime = LocalDateTime.now().isAfter(vip.endTime()) ? LocalDateTime.now() : vip.endTime();
            draft.setEndTime(endTime.plusDays(vipOrder.vipLevel().days()));
        }));
        // 更新支付信息
        return vipOrderRepository.save(VipOrderDraft.$.produce(vipOrder, draft -> draft.baseOrder().payment().setPayTime(LocalDateTime.now()).setTradeNo(outTradeNo))).id();
    }
}