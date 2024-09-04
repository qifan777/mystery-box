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
import io.github.qifan777.server.Objects;
import io.github.qifan777.server.address.entity.Address;
import io.github.qifan777.server.address.entity.dto.AddressView;
import io.github.qifan777.server.address.repository.AddressRepository;
import io.github.qifan777.server.box.item.repository.MysteryBoxOrderItemRepository;
import io.github.qifan777.server.box.order.entity.MysteryBoxOrder;
import io.github.qifan777.server.box.order.entity.dto.MysteryBoxOrderInput;
import io.github.qifan777.server.box.order.repository.MysteryBoxOrderRepository;
import io.github.qifan777.server.box.root.entity.MysteryBox;
import io.github.qifan777.server.box.root.entity.dto.MystryBoxView;
import io.github.qifan777.server.box.root.repository.MysteryBoxRepository;
import io.github.qifan777.server.carriage.service.CarriageTemplateService;
import io.github.qifan777.server.coupon.root.service.CouponService;
import io.github.qifan777.server.dict.model.DictConstants;
import io.github.qifan777.server.infrastructure.model.WxPayPropertiesExtension;
import io.github.qifan777.server.order.repository.BaseOrderRepository;
import io.github.qifan777.server.payment.entity.Payment;
import io.github.qifan777.server.payment.entity.PaymentDraft;
import io.github.qifan777.server.payment.entity.dto.PaymentPriceView;
import io.github.qifan777.server.payment.model.WeChatPayModel;
import io.github.qifan777.server.payment.repository.PaymentRepository;
import io.github.qifan777.server.payment.service.WeChatPayService;
import io.github.qifan777.server.product.root.entity.Product;
import io.github.qifan777.server.product.root.entity.dto.ProductView;
import io.github.qifan777.server.refund.entity.RefundRecord;
import io.github.qifan777.server.refund.entity.RefundRecordDraft;
import io.github.qifan777.server.refund.repository.RefundRecordRepository;
import io.github.qifan777.server.vip.root.service.VipService;
import io.qifan.infrastructure.common.constants.ResultCode;
import io.qifan.infrastructure.common.exception.BusinessException;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static io.github.qifan777.server.dict.model.DictConstants.*;

@Service
@Slf4j
@AllArgsConstructor
@Transactional
public class MysteryBoxOrderService {
    private final int[] LEGENDARY_RANGE = new int[]{0, 12};
    private final int[] HIDDEN_RANGE = new int[]{13, 758};
    private final int[] GENERAL_RANGE = new int[]{759, 9999};
    private final WxPayPropertiesExtension wxPayPropertiesExtension;
    private final CouponService couponService;
    private final CarriageTemplateService carriageTemplateService;
    private final VipService vipService;
    private final WeChatPayService weChatPayService;
    private final WxPayService wxPayService;
    private final AddressRepository addressRepository;
    private final BaseOrderRepository baseOrderRepository;
    private final MysteryBoxRepository mysteryBoxRepository;
    private final MysteryBoxOrderRepository mysteryBoxOrderRepository;
    private final MysteryBoxOrderItemRepository mysteryBoxOrderItemRepository;
    private final PaymentRepository paymentRepository;
    private final RefundRecordRepository refundRecordRepository;

    /**
     * 调用{@link #calculate(MysteryBoxOrderInput)}计算得到支付详情<br/>
     * 创建盲盒订单对象，盲盒订单项对象，基础订单对象
     * @param mysteryBoxOrderInput 订单输入
     * @return 订单id
     */
    public String create(MysteryBoxOrderInput mysteryBoxOrderInput) {
        String orderId = IdUtil.fastSimpleUUID();
        PaymentPriceView calculated = calculate(mysteryBoxOrderInput);
        // 支付详情
        Payment payment = PaymentDraft.$.produce(
                calculated.toEntity(),
                paymentDraft -> paymentDraft
                        .setId(orderId)
                        .setPayType(PayType.WE_CHAT_PAY));
        Address address = addressRepository
                .findUserAddressById(mysteryBoxOrderInput.getBaseOrder().getAddressId())
                .orElseThrow(() -> new BusinessException("地址不存在"));
        MysteryBoxOrder entity = Objects.createMysteryBoxOrder(mysteryBoxOrderInput
                        .toEntity(),
                draft -> {
                    // 设置订单项关联的订单id，并且设置盲盒快照
                    draft.setItems(draft
                            .items()
                            .stream()
                            .map(item -> Objects.createMysteryBoxOrderItem(item, mysteryBoxOrderItemDraft -> {
                                MysteryBox box = mysteryBoxRepository
                                        .findById(item.mysteryBoxId(), MysteryBoxRepository.COMPLEX_FETCHER_FOR_FRONT)
                                        .orElseThrow(() -> new BusinessException(ResultCode.NotFindError, "盲盒不存在"));
                                mysteryBoxOrderItemDraft.setMysteryBoxOrderId(orderId)
                                        // 盲盒快照，购买时的盲盒详情存入
                                        .setMysteryBox(new MystryBoxView(box));
                            }))
                            .toList()
                    );
                    // 设置订单的id和状态
                    draft.setId(orderId)
                            .setStatus(ProductOrderStatus.TO_BE_PAID);
                    // 设置基础订单
                    draft.baseOrder()
                            .setId(orderId)
                            .setType(OrderType.PRODUCT_ORDER)
                            .setPayment(payment)
                            // 地址快照
                            .setAddress(new AddressView(address));
                });
        // 同时创建mysteryBoxOrder, mysteryBoxOrderItem, baseOrder, payment
        MysteryBoxOrder save = mysteryBoxOrderRepository.save(entity);
        // 优惠券设置为已使用
        couponService.changeStatus(mysteryBoxOrderInput.getBaseOrder().getCouponUserId(), CouponUseStatus.USED);
        return save.id();
    }

    /**
     * 前端输入优惠券id，地址id，盲盒id，购买数量通过计算得到商品总价、优惠券价格、邮费、vip优惠、实付金额
     * @param mysteryBoxOrderInput 订单表单
     * @return 计算价格
     */
    public PaymentPriceView calculate(MysteryBoxOrderInput mysteryBoxOrderInput) {
        var baseOrder = mysteryBoxOrderInput.getBaseOrder();
        Payment produce = PaymentDraft.$.produce(draft -> {
            draft.setProductAmount(BigDecimal.ZERO)
                    .setDeliveryFee(BigDecimal.ZERO)
                    .setVipAmount(BigDecimal.ZERO)
                    .setCouponAmount(BigDecimal.ZERO);
            BigDecimal totalPrice = BigDecimal.ZERO;
            for (var item : mysteryBoxOrderInput.getItems()) {
                MysteryBox mysteryBox = mysteryBoxRepository.findById(item.getMysteryBoxId())
                        .orElseThrow(() -> new BusinessException(ResultCode.NotFindError, "盲盒不存在"));
                BigDecimal price = mysteryBox.price().multiply(BigDecimal.valueOf(item.getMysteryBoxCount()));
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

    /**
     * 生成预支付参数
     * @param id 盲盒订单id
     * @return 微信预支付参数
     */
    public WxPayUnifiedOrderV3Result.JsapiResult prepay(String id) {
        MysteryBoxOrder mysteryBoxOrder = mysteryBoxOrderRepository.findByIdForFront(id);
        checkStatus(mysteryBoxOrder, ProductOrderStatus.TO_BE_PAID);
        checkOwner(mysteryBoxOrder);
        WxPayUnifiedOrderV3Result.JsapiResult prepay = weChatPayService.prepay(new WeChatPayModel()
                .setBaseOrder(mysteryBoxOrder.baseOrder())
                .setExpiredMinutes(5)
                .setNotifyUrl("/front/mystery-box-order/notify/pay/wechat"));
        log.info("预支付订单内容：{}", prepay);
        return prepay;
    }

    /**
     * 支付成功回调
     * @param body 微信回调请求的body，带解密
     * @param signatureHeader 回调的请求头参数
     * @return 返回内容且http状态是200就代表成功，出现异常http状态会变成400，微信会认为回调失败，微信会轮询重试
     */
    @SneakyThrows
    public String paymentNotifyWechat(String body, SignatureHeader signatureHeader) {
        // 解密回调数据
        WxPayNotifyV3Result.DecryptNotifyResult notifyResult = wxPayService.parseOrderNotifyV3Result(body, signatureHeader)
                .getResult();
        log.info("支付回调:{}", notifyResult);
        String outTradeNo = notifyResult.getOutTradeNo();
        // 回调结果中的outTradeNo就代表预支付时填写的盲盒订单id
        MysteryBoxOrder mysteryBoxOrder = mysteryBoxOrderRepository.findByIdForFront(outTradeNo);
        // 切换当前登录上下文为订单创建人的id
        StpUtil.switchTo(mysteryBoxOrder.creator().id());
        mysteryBoxOrder.items().forEach(mysteryBoxOrderItem -> {
            // 从盲盒快照中获取商品信息
            List<Product> products = mysteryBoxOrderItem.mysteryBox()
                    .getProducts()
                    .stream()
                    .map(MystryBoxView.TargetOf_products::toEntity)
                    .toList();
            // 随机生成商品
            List<ProductView> generateProducts = generateProducts(products, mysteryBoxOrderItem.mysteryBoxCount());
            // 更新订单项
            mysteryBoxOrderItemRepository.updateProducts(mysteryBoxOrderItem.id(), generateProducts);
        });
        // 更新支付时间和微信的交易订单id
        paymentRepository.updatePayTimeAndTradeNo(mysteryBoxOrder.id(), notifyResult.getTransactionId(), LocalDateTime.now());
        // 状态变为待发货
        mysteryBoxOrderRepository.changeStatus(mysteryBoxOrder.id(), ProductOrderStatus.TO_BE_DELIVERED);
        return mysteryBoxOrder.id();
    }

    /**
     * 从盲盒关联的商品中随机生成指定数量的商品，生成的概率是由商品品质决定
     * @param products 盲盒中的商品
     * @param count 购买的盲盒数量
     * @return 生成的商品列表
     */
    public List<ProductView> generateProducts(List<Product> products, int count) {
        List<Product> legendaryList = products
                .stream()
                .filter(product -> product.qualityType().equals(DictConstants.QualityType.LEGENDARY))
                .toList();
        List<Product> hiddenList = products
                .stream()
                .filter(product -> product.qualityType().equals(DictConstants.QualityType.HIDDEN))
                .toList();
        List<Product> generalList = products
                .stream()
                .filter(product -> product.qualityType().equals(DictConstants.QualityType.GENERAL))
                .toList();
        int legendaryCount = 0;
        int hiddenCount = 0;
        int generalCount = 0;
        // 根据概率生成
        for (int i = 0; i < count; i++) {
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
        List<Product> generatedProducts = new ArrayList<>();
        generatedProducts.addAll(RandomUtil.randomEleList(legendaryList, legendaryCount));
        generatedProducts.addAll(RandomUtil.randomEleList(hiddenList, hiddenCount));
        generatedProducts.addAll(RandomUtil.randomEleList(generalList, generalCount));
        return generatedProducts.stream().map(ProductView::new).toList();
    }

    /**
     * 管理员在后台发货
     * @param id 订单id
     * @param trackingNumber 物流单号
     * @return 订单id
     */
    public String deliver(String id, String trackingNumber) {
        MysteryBoxOrder mysteryBoxOrder = mysteryBoxOrderRepository.findByIdForFront(id);
        checkStatus(mysteryBoxOrder, ProductOrderStatus.TO_BE_DELIVERED, ProductOrderStatus.TO_BE_RECEIVED);
        baseOrderRepository.updateTrackingNumber(id, trackingNumber);
        mysteryBoxOrderRepository.changeStatus(id, ProductOrderStatus.TO_BE_RECEIVED);
        return id;
    }

    /**
     * 用户在小程序取消未支付的订单
     * @param id 订单id
     * @return 订单id
     */
    public String unpaidCancelForUser(String id) {
        MysteryBoxOrder mysteryBoxOrder = mysteryBoxOrderRepository.findByIdForFront(id);
        checkStatus(mysteryBoxOrder, ProductOrderStatus.TO_BE_PAID);
        checkOwner(mysteryBoxOrder);
        mysteryBoxOrderRepository.changeStatus(mysteryBoxOrder.id(), ProductOrderStatus.CLOSED);
        // 优惠券设置为未使用
        couponService.changeStatus(mysteryBoxOrder.baseOrder().couponUser().id(), CouponUseStatus.UNUSED);
        return mysteryBoxOrder.id();
    }

    /**
     * 管理员在后台取消已支付的订单，并发起退款操作
     * @param id 订单id
     * @return 订单id
     */
    @SneakyThrows
    public String paidCancelForAdmin(String id) {
        MysteryBoxOrder mysteryBoxOrder = mysteryBoxOrderRepository.findByIdForFront(id);
        checkStatus(mysteryBoxOrder, ProductOrderStatus.TO_BE_RECEIVED, ProductOrderStatus.TO_BE_DELIVERED);
        String refundOrderId = IdUtil.fastSimpleUUID();
        RefundRecord refundRecord = RefundRecordDraft.$.produce(draft -> {
            draft.setId(refundOrderId);
            WxPayRefundV3Request wxPayRefundV3Request = new WxPayRefundV3Request().setOutTradeNo(
                            id)
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
                    .setOrderId(id)
                    .setAmount(mysteryBoxOrder.baseOrder().payment().payAmount())
                    .setRefundApplicationDetails(wxPayRefundV3Result)
                    .setReason("退款");
        });
        return refundRecordRepository.save(refundRecord).id();
    }

    /**
     * 微信退款回调
     * @param body 回调的加密请全体
     * @param signatureHeader 回调请求头
     * @return 回调是否成功
     */
    @SneakyThrows
    public String refundNotifyWeChat(String body, SignatureHeader signatureHeader) {
        WxPayRefundNotifyV3Result.DecryptNotifyResult result = wxPayService.parseRefundNotifyV3Result(body, signatureHeader)
                .getResult();
        log.info("退款回调：{}", result);
        RefundRecord refundRecord = refundRecordRepository.findById(result.getOutRefundNo(), RefundRecordRepository.COMPLEX_FETCHER_FOR_FRONT)
                .orElseThrow(() -> new BusinessException(ResultCode.NotFindError, "退款订单不存在"));
        MysteryBoxOrder mysteryBoxOrder = mysteryBoxOrderRepository.findByIdForFront(refundRecord.orderId());
        StpUtil.switchTo(mysteryBoxOrder.creator().id());
        if (result.getRefundStatus().equals("SUCCESS")) {
            // 需要将订单回退到未支付状态再取消
            mysteryBoxOrderRepository.changeStatus(result.getOutTradeNo(), ProductOrderStatus.TO_BE_PAID);
            // 模拟用户取消订单，包含优惠券回退操作，库存回退（暂时没有）
            unpaidCancelForUser(result.getOutTradeNo());
            // 更新退款状态
            mysteryBoxOrderRepository.changeStatus(result.getOutTradeNo(), ProductOrderStatus.REFUNDED);
            // 生成退款记录
            RefundRecord produce = RefundRecordDraft.$.produce(refundRecord,
                    draft -> draft.setRefundNotifyDetails(result)
                            .setStatus(DictConstants.RefundStatus.SUCCESS));
            refundRecordRepository.save(produce);
        } else {
            RefundRecord produce = RefundRecordDraft.$.produce(refundRecord, draft -> draft
                    .setRefundNotifyDetails(result)
                    .setStatus(DictConstants.RefundStatus.FAILED));
            refundRecordRepository.save(produce);
        }
        return refundRecord.id();
    }


    public void checkStatus(MysteryBoxOrder mysteryBoxOrder, ProductOrderStatus... productOrderStatusList) {
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