package io.github.qifan777.server.payment.service;

import cn.dev33.satoken.stp.StpUtil;
import com.binarywang.spring.starter.wxjava.pay.properties.WxPayProperties;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.binarywang.wxpay.bean.request.WxPayUnifiedOrderV3Request;
import com.github.binarywang.wxpay.bean.result.WxPayUnifiedOrderV3Result;
import com.github.binarywang.wxpay.bean.result.enums.TradeTypeEnum;
import com.github.binarywang.wxpay.service.WxPayService;
import io.github.qifan777.server.infrastructure.model.WxPayPropertiesExtension;
import io.github.qifan777.server.order.entity.BaseOrder;
import io.github.qifan777.server.payment.model.WeChatPayModel;
import io.github.qifan777.server.user.wechat.entity.UserWeChatTable;
import io.github.qifan777.server.user.wechat.repository.UserWeChatRepository;
import io.qifan.infrastructure.common.constants.ResultCode;
import io.qifan.infrastructure.common.exception.BusinessException;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.TimeUnit;

@Service
@AllArgsConstructor
@Slf4j
public class WeChatPayService {

    private final UserWeChatRepository userWeChatRepository;
    private final WxPayPropertiesExtension wxPayPropertiesExtension;
    private final WxPayProperties wxPayProperties;
    private final RedisTemplate<String, Object> redisTemplate;
    private final ObjectMapper objectMapper;
    private final WxPayService wxPayService;
    private final static DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ssXXX");

    @SneakyThrows
    public WxPayUnifiedOrderV3Result.JsapiResult prepay(WeChatPayModel weChatPayModel) {
        BaseOrder baseOrder = weChatPayModel.getBaseOrder();
        if (!StpUtil.getLoginIdAsString().equals(baseOrder.creator().id())) {
            throw new BusinessException(ResultCode.NotGrant, "无法支付他人订单");
        }
        Object prepayRes = redisTemplate.opsForValue()
                .get("prepay:" + baseOrder.id());
        if (prepayRes != null) {
            log.info("已存在预支付订单：{}", prepayRes);
            // 如果存在预支付订单则直接发起支付
            return objectMapper.readValue(
                    objectMapper.writeValueAsString(prepayRes), WxPayUnifiedOrderV3Result.JsapiResult.class);
        }
        WxPayUnifiedOrderV3Result.JsapiResult wxPayAppOrderResult = wxPayService.createOrderV3(TradeTypeEnum.JSAPI,
                create(weChatPayModel));
        log.info("保存预支付订单");
        // 预支付订单过期
        redisTemplate.opsForValue()
                .set("prepay:" + baseOrder.id(),
                        wxPayAppOrderResult,
                        weChatPayModel.getExpiredMinutes(),
                        TimeUnit.MINUTES);
        log.info("预支付订单，下单成功：{}", wxPayAppOrderResult);
        return wxPayAppOrderResult;
    }


    public WxPayUnifiedOrderV3Request create(WeChatPayModel weChatPayModel) {
        BaseOrder baseOrder = weChatPayModel.getBaseOrder();
        WxPayUnifiedOrderV3Request wxPayUnifiedOrderV3Request = new WxPayUnifiedOrderV3Request();
        // 支付价格
        WxPayUnifiedOrderV3Request.Amount amount = new WxPayUnifiedOrderV3Request.Amount();
        amount.setTotal(baseOrder.payment().payAmount()
                .multiply(BigDecimal.valueOf(
                        100)).intValue());
        UserWeChatTable t = UserWeChatTable.$;
        // 获取支付人信息
        WxPayUnifiedOrderV3Request.Payer payer = new WxPayUnifiedOrderV3Request.Payer();
        payer.setOpenid(userWeChatRepository.sql().createQuery(t)
                .where(t.userId().eq(StpUtil.getLoginIdAsString()))
                .select(t)
                .fetchOptional()
                .orElseThrow(() -> new BusinessException(ResultCode.NotFindError, "非小程序用户, 请注册")).openId());
        wxPayUnifiedOrderV3Request.setPayer(payer)
                .setAmount(amount)
                // 回调地址
                .setNotifyUrl(wxPayPropertiesExtension.getNotifyUrl() + weChatPayModel.getNotifyUrl())
                // 小程序appid
                .setAppid(wxPayProperties.getAppId())
                // 商家号
                .setMchid(wxPayProperties.getMchId())
                // 支付描述
                .setDescription(StringUtils.hasText(baseOrder.remark()) ? baseOrder.remark() : amount.toString())
                // 订单系统的订单号
                .setOutTradeNo(baseOrder.id())
                // 过期时间
                .setTimeExpire(dateTimeFormatter.format(ZonedDateTime.now().plusMinutes(weChatPayModel.getExpiredMinutes())));
        return wxPayUnifiedOrderV3Request;
    }
}
