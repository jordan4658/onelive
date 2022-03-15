package com.onelive.pay.model.business;

import com.alibaba.fastjson.JSONObject;
import com.onelive.common.constants.business.PayConstants;
import com.onelive.common.exception.BusinessException;
import com.onelive.common.model.dto.pay.RechargeDTO;
import com.onelive.common.mybatis.entity.*;
import com.onelive.common.utils.http.HttpClient;
import com.onelive.common.utils.http.HttpClient_Fh_Util;
import com.onelive.common.utils.others.DateInnerUtil;
import com.onelive.common.utils.pay.RSAUtil;
import com.onelive.common.utils.pay.SignMd5Utils;
import com.onelive.pay.service.PayOrderRechargeService;
import com.onelive.pay.service.PayWayService;
import com.onelive.pay.service.SysBusParameterService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

/**
 * @author 就不告诉你
 * @version V1.0
 * @ClassName: payCommonBusiness
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @date 创建时间：2021/4/14 16:16
 */
@Component
@Slf4j
@Scope(proxyMode = ScopedProxyMode.TARGET_CLASS)
public class PayCommonBusiness {

    @Resource
    private PayOrderRechargeService payOrderRechargeService;
    @Resource
    private SysBusParameterService sysBusParameterService;
    @Resource
    private OrderPayMentInfoBusiness orderPayMentInfoBusiness;
    @Resource
    private PayWayService payWayService;


    /**
     * 公用订单处理方法
     *
     * @param rechargeDto
     * @param payWay
     */
    public PayOrderRecharge orderInfoHand(RechargeDTO rechargeDto, PayWay payWay) {
        PayOrderRecharge payOrderRecharge = new PayOrderRecharge();
        //根据充值类型添加对应的订单号前缀
        String orderNo = rechargeDto.getOrderNo();
//        for (PayConstants.RechargePrefixEnum prefixEnum : PayConstants.RechargePrefixEnum.values()) {
//            if (prefixEnum.getPayTypeCode().intValue() == payWay.getPayTypeCode().intValue()) {
//                orderNo = prefixEnum.getCode() + orderNo;
//                break;
//            }
//        }
        //支付方式id
        payOrderRecharge.setPayWayId(payWay.getPayWayId());
        //支付方式 线下-(BANK)、线上-(H5、WAP、JSAPI)
        payOrderRecharge.setPayWayTag(payWay.getPayWayTag());
        payOrderRecharge.setSource(rechargeDto.getSource());
        payOrderRecharge.setOrderType(PayConstants.PayProviderTypeEnum.ONLINE.getCode());
        Date date = new Date();
        //判断是线下还是线上 充值
        if (PayConstants.PayTypeEnum.BANK.getCode().intValue() == payWay.getPayTypeCode().intValue()) {
            //支付时间
            payOrderRecharge.setPayDate(date);
            //汇款备注
            payOrderRecharge.setPayNote(rechargeDto.getPayNot());
            //汇款姓名
            payOrderRecharge.setPayUser(rechargeDto.getPayName());
            //设置充值类型  线下、线上
//            payOrderRecharge.setOrderType(PayConstants.PayProviderTypeEnum.OFFLINE.getCode());
        }
        //充值人账号
        payOrderRecharge.setAccount(rechargeDto.getAccount());
        //充值金币数量
        payOrderRecharge.setSumAmt(rechargeDto.getPrice());
        //充值金币数量
        payOrderRecharge.setCurrencyCode(rechargeDto.getCurrencyCode());
        //实际充值支付的金额（对应国家币种）
        payOrderRecharge.setActualPayment(rechargeDto.getActualPayment());
        //平台币对国家币种的汇率
        payOrderRecharge.setExchange(rechargeDto.getCzExchange());
        //订单号
        payOrderRecharge.setOrderNo(orderNo);
        //状态
        payOrderRecharge.setOrderStatus(PayConstants.PayOrderStatusEnum.IN_HAND.getCode());
        //订单备注
        payOrderRecharge.setOrderNote(null);
        //是否删除
        payOrderRecharge.setIsDelete(false);
        //创建人
        payOrderRecharge.setCreateUser(rechargeDto.getAccount());
        //创建时间
        payOrderRecharge.setCreateTime(date);
        //更新人
        payOrderRecharge.setUpdateUser(rechargeDto.getAccount());
        //更新时间
        payOrderRecharge.setUpdateTime(date);
        //预支付id（微信、支付宝  支付下单后返回的预支付id）
        payOrderRecharge.setPrepayId(null);
        return payOrderRecharge;
    }


    /**
     * 公共支付信息处理
     *
     * @param payOrderRecharge
     * @param providerId
     * @return
     */
    public PayMentInfo paymentInfoHand(PayOrderRecharge payOrderRecharge, Long providerId) {
        PayMentInfo info = new PayMentInfo();
        //会员标识号
        info.setAccno(payOrderRecharge.getAccount());
        //订单号
        info.setOrderNo(payOrderRecharge.getOrderNo());
        //支付备注
        info.setPayNote("充值");
        //支付金额
        info.setPayAmt(payOrderRecharge.getActualPayment());
        //支付标示第三方支付标示号，如支付宝的订单号或微信的prepay_id等
        info.setPayCode(payOrderRecharge.getPrepayId());
        //支付标示二维码(页面)
//        info.setPayCodeUrl();
        //支付类别 weixin微信支付 alipay支付宝支付
        info.setPayWayId(payOrderRecharge.getPayWayId());
        Date date = new Date();
        //支付开始时间
        info.setPayStartTime(date);
        //支付完成时间
//        info.setPayEndTime();
        //支付流水号
//        String serialNo = PayUtils.getOrderNo();
//        info.setSerialNo(serialNo);
        //支付方式标识：H5、WAP、JSAPI
        info.setPayType(payOrderRecharge.getPayWayTag());
        //支付流水状态
        info.setPayStatus(PayConstants.PayOrderStatusEnum.IN_HAND.getCode());
        //支付渠道商id
        info.setProviderId(providerId);
        info.setCreateUser(payOrderRecharge.getAccount());
        info.setCreateTime(date);
        info.setUpdateUser(payOrderRecharge.getAccount());
        info.setUpdateTime(date);
        return info;
    }

    /**
     * 银行卡转账
     *
     * @param rechargeDto
     * @param payWay
     * @param payThreeProvider
     */
    @Transactional
    public JSONObject bankTransfer(RechargeDTO rechargeDto, PayWay payWay, PayThreeProvider payThreeProvider) {
        PayOrderRecharge payOrderRecharge = orderInfoHand(rechargeDto, payWay);
        payOrderRechargeService.insertOrderInfo(payOrderRecharge);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("createTime", payOrderRecharge.getCreateTime().getTime());
        jsonObject.put("amt", payOrderRecharge.getSumAmt());
        jsonObject.put("orderNo", payOrderRecharge.getOrderNo());
        jsonObject.put("payType", payWay.getPayTypeCode());
        return jsonObject;
    }


    /**
     * KG支付
     *
     * @param rechargeDto
     * @param payWay
     * @param payThreeProvider
     * @return
     */
    @Transactional
    public JSONObject kgPay(RechargeDTO rechargeDto, PayWay payWay, PayThreeProvider payThreeProvider) {
        //处理订单信息
        PayOrderRecharge payOrderRecharge = orderInfoHand(rechargeDto, payWay);
        //预支付id（微信、支付宝  支付下单后返回的预支付id）
        payOrderRecharge.setPrepayId(null);
        log.info("KG支付业务处理开始================================================");
        JSONObject json = new JSONObject();
        try {
            //商户自定义订单号18位
            String merchantOrderNo = payOrderRecharge.getOrderNo();
            //支付方式
            String method = payOrderRecharge.getPayWayTag();
            //商户id
            String merchantCode = payThreeProvider.getProviderCode();
            //商户key
            String apikey = payThreeProvider.getSecretCode();
            //第三方支付 下单地址
            String orderUrl = payThreeProvider.getAddOrderUrl();
            //获取平台服务的网关地址信息
            SysBusParameter sysBusParameter = sysBusParameterService.getByCode(PayConstants.PAY_CALLBACK_HTTP);
            //回调地址 = 平台服务的网关地址+业务参数里面配置的
            String notifyUrl = sysBusParameter.getParamValue() + payThreeProvider.getBackUrl();
            Map<String, Object> map = new HashMap<>();
            map.put("merchantCode", merchantCode);
            map.put("method", method);
            map.put("signType", "MD5");
            map.put("timestamp", DateInnerUtil.getTime_yyyMMddHHmmss());
            map.put("merchantOrderNo", merchantOrderNo);
            map.put("orderAmount", payOrderRecharge.getActualPayment().intValue());

            if("W2W_MOMO".equals(payWay.getPayWayTag())){
                map.put("accountName", "MOMO");
            }else if("W2W_ZALO".equals(payWay.getPayWayTag())){
                map.put("accountName", "ZALO");
            }else{
                map.put("accountName", rechargeDto.getPayName());
            }

            map.put("accountBank", rechargeDto.getBackCode());
            map.put("notifyUrl", notifyUrl);
            String signString = SignMd5Utils.commonSignByObject(map, apikey);
            map.put("sign", signString);
            //获取支付信息
            PayMentInfo payMentInfo = paymentInfoHand(payOrderRecharge, payThreeProvider.getProviderId());

            log.info("发起KG支付参数-----:{}", JSONObject.toJSONString(map));
            String result = HttpClient.formPostByObject(orderUrl, map);
            JSONObject jsonObject = JSONObject.parseObject(result);
            log.info("KG支付 发起支付接口返回结果：" + JSONObject.toJSONString(jsonObject));
            if (jsonObject.containsKey("status")) {
                throw new BusinessException("KG支付，报错：" + jsonObject.get("msg"));
            }
            //添加订单信息、添加支付流水表信息
            payMentInfo.setSerialNo(jsonObject.getString("platformOrderNo"));
            Boolean flag = orderPayMentInfoBusiness.insertOrderAndPayMentInfo(payOrderRecharge, payMentInfo);
            if (!flag) {
                throw new BusinessException("KG支付下单插入失败");
            }
            String pay_url = jsonObject.getString("depositUrl");
            json.put("code", 0);
            json.put("url", pay_url);
            json.put("orderNo", merchantOrderNo);
        } catch (Exception e) {
            log.error("KG支付出错", e);
            throw new BusinessException(e.getMessage());
        }
        log.info("KG支付业务处理结束================================================");
        return json;
    }


    /**
     * 地雷支付
     *
     * @param rechargeDto
     * @param payWay
     * @param payThreeProvider
     * @return
     */
    @Transactional
    public JSONObject diLeiPay(RechargeDTO rechargeDto, PayWay payWay, PayThreeProvider payThreeProvider) {
        //处理订单信息
        PayOrderRecharge payOrderRecharge = orderInfoHand(rechargeDto, payWay);
        //预支付id（微信、支付宝  支付下单后返回的预支付id）
        payOrderRecharge.setPrepayId(null);
        log.info("地雷支付业务处理开始================================================");
        JSONObject json = new JSONObject();
        try {
            //商户自定义订单号18位
            String order_sn = payOrderRecharge.getOrderNo();
            //支付方式
            String channel = payOrderRecharge.getPayWayTag();
            //商户id
            String merchat_id = payThreeProvider.getAgentNo();
            //商户key
            String apikey = "key=" + payThreeProvider.getSecretCode();
            //第三方支付 下单地址
            String orderUrl = payThreeProvider.getAddOrderUrl();
            //获取平台服务的网关地址信息
            SysBusParameter sysBusParameter = sysBusParameterService.getByCode(PayConstants.PAY_CALLBACK_HTTP);
            //回调地址 = 平台服务的网关地址+业务参数里面配置的
            String notify_url = sysBusParameter.getParamValue() + payThreeProvider.getBackUrl();
            Map<String, String> map = new HashMap<>();
            map.put("pay_memberid", merchat_id);
            map.put("pay_orderid", order_sn);
            map.put("pay_amount", payOrderRecharge.getSumAmt() + "");
            map.put("pay_applydate", DateInnerUtil.getTimeSS());
            map.put("pay_bankcode", channel);
            map.put("pay_notifyurl", notify_url);
            map.put("pay_callbackurl", " ");
            map.put("pay_attach", rechargeDto.getSource());
            map.put("pay_productname", "shop");
            String signString = SignMd5Utils.commonSign(map, apikey);
            map.put("pay_md5sign", signString);
            //获取支付信息
            PayMentInfo payMentInfo = paymentInfoHand(payOrderRecharge, payThreeProvider.getProviderId());
            //添加订单信息、添加支付流水表信息
            Boolean flag = orderPayMentInfoBusiness.insertOrderAndPayMentInfo(payOrderRecharge, payMentInfo);
            if (!flag) {
                throw new BusinessException("地雷支付下单插入失败");
            }
            log.info("发起地雷支付参数-----:{}", JSONObject.toJSONString(map));
            String result = HttpClient.formPost(orderUrl, map);
            JSONObject jsonObject = JSONObject.parseObject(result);
            log.info("地雷支付 发起支付接口返回结果：" + JSONObject.toJSONString(jsonObject));
            if (jsonObject.containsKey("status")) {
                throw new RuntimeException("地雷支付，报错：" + jsonObject.get("msg"));
            }
            String pay_url = jsonObject.getString("value");
            json.put("code", 0);
            json.put("url", pay_url);
            json.put("orderNo", order_sn);
        } catch (Exception e) {
            log.error("地雷支付出错", e);
            throw new BusinessException(e.getMessage());
        }
        log.info("地雷业务处理结束================================================");
        return json;
    }


}
