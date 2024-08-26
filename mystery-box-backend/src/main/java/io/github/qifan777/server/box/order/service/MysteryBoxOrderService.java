package io.github.qifan777.server.box.order.service;

import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.RandomUtil;
import com.github.binarywang.wxpay.bean.notify.SignatureHeader;
import com.github.binarywang.wxpay.bean.notify.WxPayNotifyV3Result;
import com.github.binarywang.wxpay.bean.notify.WxPayRefundNotifyV3Result;
import com.github.binarywang.wxpay.bean.request.WxPayRefundV3Request;
import com.github.binarywang.wxpay.bean.result.WxPayRefundV3Result;
import com.github.binarywang.wxpay.bean.result.WxPayUnifiedOrderV3Result;
import com.github.binarywang.wxpay.service.WxPayService;
import io.github.qifan777.server.Fetchers;
import io.github.qifan777.server.Objects;
import io.github.qifan777.server.Tables;
import io.github.qifan777.server.address.entity.dto.AddressView;
import io.github.qifan777.server.address.repository.AddressRepository;
import io.github.qifan777.server.box.item.entity.MysteryBoxOrderItemTable;
import io.github.qifan777.server.box.order.entity.MysteryBoxOrder;
import io.github.qifan777.server.box.order.entity.MysteryBoxOrderTable;
import io.github.qifan777.server.box.order.entity.dto.MysteryBoxOrderInput;
import io.github.qifan777.server.box.order.repository.MysteryBoxOrderRepository;
import io.github.qifan777.server.box.root.entity.MysteryBox;
import io.github.qifan777.server.box.root.entity.MysteryBoxFetcher;
import io.github.qifan777.server.box.root.entity.dto.MystryBoxView;
import io.github.qifan777.server.box.root.repository.MysteryBoxRepository;
import io.github.qifan777.server.carriage.service.CarriageTemplateService;
import io.github.qifan777.server.coupon.root.service.CouponService;
import io.github.qifan777.server.dict.model.DictConstants;
import io.github.qifan777.server.infrastructure.model.WxPayPropertiesExtension;
import io.github.qifan777.server.order.entity.BaseOrderTable;
import io.github.qifan777.server.payment.entity.Payment;
import io.github.qifan777.server.payment.entity.PaymentDraft;
import io.github.qifan777.server.payment.entity.PaymentTable;
import io.github.qifan777.server.payment.entity.dto.PaymentPriceView;
import io.github.qifan777.server.payment.model.WeChatPayModel;
import io.github.qifan777.server.payment.service.WeChatPayService;
import io.github.qifan777.server.product.root.entity.Product;
import io.github.qifan777.server.product.root.entity.ProductFetcher;
import io.github.qifan777.server.product.root.entity.dto.ProductVIew;
import io.github.qifan777.server.refund.entity.RefundRecord;
import io.github.qifan777.server.refund.entity.RefundRecordDraft;
import io.github.qifan777.server.refund.repository.RefundRecordRepository;
import io.github.qifan777.server.vip.root.service.VipService;
import io.qifan.infrastructure.common.constants.ResultCode;
import io.qifan.infrastructure.common.exception.BusinessException;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.babyfish.jimmer.sql.JSqlClient;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@AllArgsConstructor
@Transactional
public class MysteryBoxOrderService {
    private final MysteryBoxOrderRepository mysteryBoxOrderRepository;
    private final JSqlClient jSqlClient;
    private final AddressRepository addressRepository;
    private final VipService vipService;
    private final CouponService couponService;
    private final WeChatPayService weChatPayService;
    private final WxPayService wxPayService;
    private final WxPayPropertiesExtension wxPayPropertiesExtension;
    private final CarriageTemplateService carriageTemplateService;
    private final MysteryBoxRepository mysteryBoxRepository;
    private final int[] LEGENDARY_RANGE = new int[]{0, 12};
    private final int[] HIDDEN_RANGE = new int[]{13, 758};
    private final int[] GENERAL_RANGE = new int[]{759, 9999};

    public String create(MysteryBoxOrderInput mysteryBoxOrderInput) {
        String orderId = IdUtil.fastSimpleUUID();
        PaymentPriceView calculated = calculate(mysteryBoxOrderInput);
        Payment payment = PaymentDraft.$.produce(
                draft -> draft
                        .setId(orderId)
                        .setPayType(DictConstants.PayType.WE_CHAT_PAY)
                        // 优惠券价格
                        .setCouponAmount(calculated.getCouponAmount())
                        // 商品价格
                        .setProductAmount(calculated.getProductAmount())
                        // 运费
                        .setDeliveryFee(calculated.getDeliveryFee())
                        // VIP价格
                        .setVipAmount(calculated.getVipAmount())
                        // 实际支付价格
                        .setPayAmount(calculated.getPayAmount())
        );
        MysteryBoxOrder entity = Objects.createMysteryBoxOrder(mysteryBoxOrderInput
                        .toEntity(),
                draft -> {
                    // 设置订单项关联的订单id
                    draft.setItems(draft
                            .items()
                            .stream()
                            .map(item -> Objects.createMysteryBoxOrderItem(item, mysteryBoxOrderItemDraft -> {
                                mysteryBoxOrderItemDraft.setMysteryBoxOrderId(orderId)
                                        .setMysteryBox(new MystryBoxView(mysteryBoxRepository.findById(item.mysteryBoxId(), MysteryBoxRepository.COMPLEX_FETCHER_FOR_FRONT)
                                                .orElseThrow(() -> new BusinessException(ResultCode.NotFindError, "盲盒不存在"))));
                            }))
                            .toList()
                    );
                    // 设置订单的id和状态
                    draft.setId(orderId)
                            .setStatus(DictConstants.ProductOrderStatus.TO_BE_PAID);
                    // 设置支付详情
                    draft.baseOrder()
                            .setId(orderId)
                            .setType(DictConstants.OrderType.PRODUCT_ORDER)
                            .setPayment(payment)
                            .setAddress(new AddressView(addressRepository.findUserAddressById(mysteryBoxOrderInput.getBaseOrder().getAddressId())
                                    .orElseThrow(() -> new BusinessException("地址不存在"))));
                });
        MysteryBoxOrder save = mysteryBoxOrderRepository.save(entity);
        return save.id();
    }

    public PaymentPriceView calculate(MysteryBoxOrderInput mysteryBoxOrderInput) {
        var baseOrder = mysteryBoxOrderInput.getBaseOrder();
        Payment produce = PaymentDraft.$.produce(draft -> {
            draft.setProductAmount(BigDecimal.ZERO)
                    .setDeliveryFee(BigDecimal.ZERO)
                    .setVipAmount(BigDecimal.ZERO)
                    .setCouponAmount(BigDecimal.ZERO);
            BigDecimal totalPrice = BigDecimal.ZERO;
            for (var item : mysteryBoxOrderInput.getItems()) {
                MysteryBox product = Optional.ofNullable(jSqlClient.findById(MysteryBox.class, item.getMysteryBoxId()))
                        .orElseThrow(() -> new BusinessException(ResultCode.NotFindError, "盲盒不存在"));
                BigDecimal price = product.price().multiply(BigDecimal.valueOf(item.getMysteryBoxCount()));
                totalPrice = totalPrice.add(price);
            }
            // 计算商品总价
            draft.setProductAmount(totalPrice);
            // 计算优惠券
            draft.setCouponAmount(couponService.calculate(baseOrder.getCouponUserId(), totalPrice));
            // 计算运费
            draft.setDeliveryFee(carriageTemplateService.calculate(baseOrder.getAddressId(), totalPrice));
            // 计算VIP优惠价格
            draft.setVipAmount(vipService.calculate(totalPrice));
            // 计算实际支付价格
            draft.setPayAmount(
                    draft.productAmount()
                            .add(draft.deliveryFee())
                            .subtract(draft.couponAmount())
                            .subtract(draft.vipAmount())
            );
        });
        return new PaymentPriceView(produce);
    }

    public WxPayUnifiedOrderV3Result.JsapiResult prepay(String id) {
        MysteryBoxOrder mysteryBoxOrder = mysteryBoxOrderRepository.findById(id, MysteryBoxOrderRepository.COMPLEX_FETCHER_FOR_FRONT)
                .orElseThrow(() -> new BusinessException(ResultCode.NotFindError));
        checkStatus(mysteryBoxOrder, DictConstants.ProductOrderStatus.TO_BE_PAID);
        checkOwner(mysteryBoxOrder);
        WxPayUnifiedOrderV3Result.JsapiResult prepay = weChatPayService.prepay(new WeChatPayModel().setBaseOrder(mysteryBoxOrder.baseOrder())
                .setExpiredMinutes(5)
                .setNotifyUrl("/front/mystery-box-order/notify/pay/wechat"));
        log.info("预支付订单内容：{}", prepay);
        return prepay;
    }

    @SneakyThrows
    public String paymentNotifyWechat(String body, SignatureHeader signatureHeader) {
        WxPayNotifyV3Result.DecryptNotifyResult notifyResult = wxPayService.parseOrderNotifyV3Result(body, signatureHeader)
                .getResult();
        log.info("支付回调:{}", notifyResult);
        String outTradeNo = notifyResult.getOutTradeNo();
        MysteryBoxOrder mysteryBoxOrder = mysteryBoxOrderRepository.findById(outTradeNo,
                MysteryBoxOrderRepository.COMPLEX_FETCHER_FOR_FRONT).orElseThrow(
                () -> new BusinessException(ResultCode.NotFindError, "订单不存在"));
        // 设置微信支付订单id
        StpUtil.switchTo(mysteryBoxOrder.creator().id());
        mysteryBoxOrder.items().stream().forEach(mysteryBoxOrderItem -> {
            MysteryBox mysteryBox = mysteryBoxRepository.findById(mysteryBoxOrderItem.mysteryBoxId(),
                            MysteryBoxFetcher.$.products(ProductFetcher.$.allTableFields()))
                    .orElseThrow(() -> new BusinessException(ResultCode.NotFindError, "盲盒不存在"));
            List<Product> legendaryList = mysteryBox.products().stream().filter(product -> product.qualityType().equals(DictConstants.QualityType.LEGENDARY)).toList();
            List<Product> hiddenList = mysteryBox.products().stream().filter(product -> product.qualityType().equals(DictConstants.QualityType.HIDDEN)).toList();
            List<Product> generalList = mysteryBox.products().stream().filter(product -> product.qualityType().equals(DictConstants.QualityType.GENERAL)).toList();
            int legendaryCount = 0;
            int hiddenCount = 0;
            int generalCount = 0;
            for (int i = 0; i < mysteryBoxOrderItem.mysteryBoxCount(); i++) {
                int randomInt = RandomUtil.randomInt(0, 10000);
                if (randomInt >= LEGENDARY_RANGE[0] && randomInt <= LEGENDARY_RANGE[1]) {
                    legendaryCount++;
                }
                if (randomInt >= HIDDEN_RANGE[0] && randomInt <= HIDDEN_RANGE[1]) {
                    hiddenCount++;
                }
                if (randomInt >= GENERAL_RANGE[0] && randomInt <= GENERAL_RANGE[1]) {
                    generalCount++;
                }
            }
            List<Product> products = new ArrayList<>();
            products.addAll(RandomUtil.randomEleList(legendaryList, legendaryCount));
            products.addAll(RandomUtil.randomEleList(hiddenList, hiddenCount));
            products.addAll(RandomUtil.randomEleList(generalList, generalCount));
            MysteryBoxOrderItemTable t = MysteryBoxOrderItemTable.$;
            jSqlClient.createUpdate(t)
                    .set(t.products(), products.stream().map(ProductVIew::new).toList())
                    .where(t.id().eq(mysteryBoxOrderItem.id()))
                    .execute();
        });

        PaymentTable t1 = PaymentTable.$;
        jSqlClient.createUpdate(t1)
                .where(t1.baseOrder().id().eq(mysteryBoxOrder.id()))
                .set(t1.tradeNo(), notifyResult.getTransactionId())
                .set(t1.payTime(), LocalDateTime.now())
                .execute();
        changeStatus(mysteryBoxOrder.id(), DictConstants.ProductOrderStatus.TO_BE_DELIVERED);
        return mysteryBoxOrder.id();
    }

    public String deliver(String id, String trackingNumber) {
        checkStatus(mysteryBoxOrderRepository.findById(id).orElseThrow(() -> new BusinessException(ResultCode.NotFindError, "订单不存在"))
                , DictConstants.ProductOrderStatus.TO_BE_DELIVERED, DictConstants.ProductOrderStatus.TO_BE_RECEIVED);
        var t = BaseOrderTable.$;
        jSqlClient.createUpdate(t)
                .set(t.trackingNumber(), trackingNumber)
                .where(t.id().eq(id));
        changeStatus(id, DictConstants.ProductOrderStatus.TO_BE_RECEIVED);
        return id;
    }

    public String unpaidCancelForUser(String id) {
        MysteryBoxOrder mysteryBoxOrder = mysteryBoxOrderRepository.findById(id, MysteryBoxOrderRepository.COMPLEX_FETCHER_FOR_FRONT)
                .orElseThrow(() -> new BusinessException(ResultCode.NotFindError, "订单不存在"));
        checkStatus(mysteryBoxOrder, DictConstants.ProductOrderStatus.TO_BE_PAID);
        checkOwner(mysteryBoxOrder);
        changeStatus(mysteryBoxOrder.id(), DictConstants.ProductOrderStatus.CLOSED);
        return mysteryBoxOrder.id();
    }

    @SneakyThrows
    public String paidCancelForAdmin(String orderId) {
        MysteryBoxOrder mysteryBoxOrder = mysteryBoxOrderRepository.findById(orderId, MysteryBoxOrderRepository.COMPLEX_FETCHER_FOR_FRONT)
                .orElseThrow(() -> new BusinessException(ResultCode.NotFindError, "订单不存在"));
        checkStatus(mysteryBoxOrder, DictConstants.ProductOrderStatus.TO_BE_RECEIVED, DictConstants.ProductOrderStatus.TO_BE_DELIVERED);
        String refundOrderId = IdUtil.fastSimpleUUID();
        RefundRecord refundRecord = RefundRecordDraft.$.produce(draft -> {
            draft.setId(refundOrderId);
            WxPayRefundV3Request wxPayRefundV3Request = new WxPayRefundV3Request().setOutTradeNo(
                            orderId)
                    .setOutRefundNo(refundOrderId)
                    .setNotifyUrl(wxPayPropertiesExtension.getNotifyUrl() + "/front/mystery-box-order/notify/refund/wechat")
                    .setReason("退款")
                    .setAmount(new WxPayRefundV3Request
                            .Amount()
                            // 计算价格
                            .setRefund(mysteryBoxOrder.baseOrder().payment().payAmount()
                                    .multiply(BigDecimal.valueOf(100)).intValue())
                            .setTotal(mysteryBoxOrder.baseOrder().payment().payAmount()
                                    .multiply(BigDecimal.valueOf(100)).intValue())
                            .setCurrency("CNY"));
            WxPayRefundV3Result wxPayRefundV3Result = wxPayService.refundV3(wxPayRefundV3Request);
            draft.setRefundId(wxPayRefundV3Result.getRefundId())
                    .setStatus(DictConstants.RefundStatus.REFUNDING)
                    .setOrderId(orderId)
                    .setAmount(mysteryBoxOrder.baseOrder().payment().payAmount())
                    .setRefundApplicationDetails(wxPayRefundV3Result)
                    .setReason("退款");
        });
        return jSqlClient.save(refundRecord).getModifiedEntity().id();
    }

    @SneakyThrows
    public String refundNotifyWeChat(String body, SignatureHeader signatureHeader) {
        WxPayRefundNotifyV3Result.DecryptNotifyResult result = wxPayService.parseRefundNotifyV3Result(body, signatureHeader)
                .getResult();
        log.info("退款回调：{}", result);
        RefundRecord refundRecord = Optional.ofNullable(jSqlClient.findById(RefundRecordRepository.COMPLEX_FETCHER_FOR_FRONT, result.getOutRefundNo()))
                .orElseThrow(() -> new BusinessException(ResultCode.NotFindError, "退款订单不存在"));
        MysteryBoxOrder mysteryBoxOrder = mysteryBoxOrderRepository.findById(refundRecord.orderId(), Fetchers.MYSTERY_BOX_ORDER_FETCHER.creator(true))
                .orElseThrow(() -> new BusinessException(ResultCode.NotFindError, "订单不存在"));
        StpUtil.switchTo(mysteryBoxOrder.creator().id());
        if (result.getRefundStatus().equals("SUCCESS")) {
            // 需要将订单回退到未支付状态再取消
            log.info("将订单状态回退到待支付");
            changeStatus(result.getOutTradeNo(), DictConstants.ProductOrderStatus.TO_BE_PAID);
            log.info("取消订单订单");
            unpaidCancelForUser(result.getOutTradeNo());
            log.info("将订单状态设置为已退款");
            changeStatus(result.getOutTradeNo(), DictConstants.ProductOrderStatus.REFUNDED);
            log.info("生成退款记录");
            jSqlClient.save(RefundRecordDraft.$.produce(refundRecord, draft -> draft.setRefundNotifyDetails(result)
                    .setStatus(DictConstants.RefundStatus.SUCCESS)));
        } else {
            jSqlClient.save(RefundRecordDraft.$.produce(refundRecord, draft -> draft.setRefundNotifyDetails(result)
                    .setStatus(DictConstants.RefundStatus.FAILED)));
        }

        return refundRecord.id();
    }

    public void changeStatus(String id, DictConstants.ProductOrderStatus productOrderStatus) {
        MysteryBoxOrderTable t = Tables.MYSTERY_BOX_ORDER_TABLE;
        mysteryBoxOrderRepository.sql().createUpdate(t)
                .where(t.id().eq(id))
                .set(t.status(), productOrderStatus)
                .execute();
    }

    public void checkStatus(MysteryBoxOrder mysteryBoxOrder, DictConstants.ProductOrderStatus... productOrderStatusList) {
        for (var status : productOrderStatusList) {
            if (mysteryBoxOrder.status().equals(status)) {
                return;
            }
        }
        throw new BusinessException(ResultCode.ParamSetIllegal, "订单状态不正确");
    }

    public void checkOwner(MysteryBoxOrder mysteryBoxOrder) {
        if (!mysteryBoxOrder.creator().id().equals(StpUtil.getLoginIdAsString())) {
            throw new BusinessException(ResultCode.ParamSetIllegal, "非本人操作");
        }
    }

}