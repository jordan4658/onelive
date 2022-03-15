package com.onelive.pay.model.business;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.onelive.common.constants.business.PayConstants;
import com.onelive.common.exception.BusinessException;
import com.onelive.common.model.req.pay.ConfirmWithdrawHandleReq;
import com.onelive.common.model.req.pay.OfflineRechargeHandleReq;
import com.onelive.common.model.req.pay.callback.FuYingCallBackReq;
import com.onelive.common.model.req.pay.callback.VgPayCallBackReq;
import com.onelive.common.model.req.util.ReqUtil;
import com.onelive.common.model.vo.pay.PaymentVastVO;
import com.onelive.common.model.vo.pay.callback.DileiCallbackVo;
import com.onelive.common.model.vo.pay.callback.KgDepositCallBackVO;
import com.onelive.common.mybatis.entity.PayMentInfo;
import com.onelive.common.mybatis.entity.PayOrderRecharge;
import com.onelive.common.mybatis.entity.PayThreeProvider;
import com.onelive.common.mybatis.entity.PayWay;
import com.onelive.common.utils.pay.PayUtils;
import com.onelive.common.utils.pay.RSAUtil;
import com.onelive.common.utils.pay.Result;
import com.onelive.common.utils.pay.SignMd5Utils;
import com.onelive.common.utils.redis.RedisUtil;
import com.onelive.pay.service.PayMentInfoService;
import com.onelive.pay.service.PayOrderRechargeService;
import com.onelive.pay.service.PayThreeProviderService;
import com.onelive.pay.service.PayWayService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.redisson.api.RReadWriteLock;
import org.redisson.api.RedissonClient;
import org.springframework.cglib.beans.BeanMap;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.TimeUnit;

/**
 * @author 就不告诉你
 * @version V1.0
 * @ClassName: PayCallBackBusiness
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @date 创建时间：2021/4/12 16:32
 */
@Slf4j
@Component
@Scope(proxyMode = ScopedProxyMode.TARGET_CLASS)
public class PayCallBackBusiness {

    @Resource
    private RedissonClient redissonClient;

    @Resource
    private PayOrderRechargeService payOrderRechargeService;
    @Resource
    private PayMentInfoService payMentInfoService;
    @Resource
    private PayThreeProviderService payThreeProviderService;
    @Resource
    private PayChangeBusiness payChangeBusiness;
    @Resource
    private PayWayService payWayService;


    // 支付回调成功
    public static final String[] PAY_CALLBACK_BIG_OK = {"OK", "success"};
    //kg支付回调
    public static final String PAY_CALLBACK_KG_OK = "SUCCESS";
    // 支付回调失败
    public static final String[] PAY_CALLBACK_FAIL = {"fail"};


    /**
     * 公共获取支付流水表信息
     */
    private PayMentInfo queryPayMentinfo(String orderNo) {
        return payMentInfoService.selectByOrderNo(orderNo);
    }

    /**
     * 公共获取商户配置
     */
    private PayThreeProvider queryPayThreeProvider(String agentNo) {
        return payThreeProviderService.getByAgentNo(agentNo);
    }

    /**
     * 公共获取商户配置
     */
    private PayThreeProvider getMerchantCode(String merchantCode) {
        return payThreeProviderService.getMerchantCode(merchantCode);
    }

    /**
     * 公共获取商户配置
     */
    private PayThreeProvider queryPayThreeProviderByPayWayId(Long payWayId) {
        return payThreeProviderService.getByPayWayId(payWayId);
    }

    /**
     * 公共获取订单表信息
     */
    private PayOrderRecharge queryPayOrderInfo(String orderNo) {
        return payOrderRechargeService.getByOrderNo(orderNo);
    }

    public String kgCallback(KgDepositCallBackVO backVO) {
        if (StringUtils.isBlank(backVO.getSignType())) {
            log.error("参数 SignType 为空！");
            throw new BusinessException("Kg回调参数错误！");
        }
        if (StringUtils.isBlank(backVO.getSign())) {
            log.error("参数 sign 为空！");
            throw new BusinessException("Kg回调参数错误！");
        }
        if (StringUtils.isBlank(backVO.getCode())) {
            log.error("参数 code 为空！");
            throw new BusinessException("Kg回调参数错误！");
        }
        if (StringUtils.isBlank(backVO.getMerchantOrderNo())) {
            log.error("参数 merchantOrderNo 为空！");
            throw new BusinessException("Kg回调参数错误！");
        }
        if (StringUtils.isBlank(backVO.getPlatformOrderNo())) {
            log.error("参数 platformOrderNo 为空！");
            throw new BusinessException("Kg回调参数错误！");
        }
        if (StringUtils.isBlank(backVO.getOrderStatus())) {
            log.error("参数 orderStatus 为空！");
            throw new BusinessException("Kg回调参数错误！");
        }
        if (backVO.getOrderAmount()==null) {
            log.error("参数 orderAmount 为空！");
            throw new BusinessException("Kg回调参数错误！");
        }
        if (backVO.getActualAmount()==null) {
            log.error("参数 actualAmount 为空！");
            throw new BusinessException("Kg回调参数错误！");
        }
        if (backVO.getActualFee()==null) {
            log.error("参数 actualFee 为空！");
            throw new BusinessException("Kg回调参数错误！");
        }

        String key = "KG_" + backVO.getMerchantOrderNo();
        long total = RedisUtil.incr(key, 1);
        if (total > 1) {
            log.error("kgCallback[{}] ", backVO.getMerchantOrderNo());
            return null;
        } else {
            RedisUtil.expire(key, 20);
        }
        RReadWriteLock lock = redissonClient.getReadWriteLock(backVO.getMerchantOrderNo());
        try {
            // 写锁（等待时间100s，超时时间10S[自动解锁]，单位：秒）【设定超时时间，超时后自动释放锁，防止死锁】
            boolean bool = lock.writeLock().tryLock(100, 10, TimeUnit.SECONDS);
            // 判断是否获取到锁
            if (bool) {
                log.info("KG订单号回调处理开始==" + backVO.getMerchantOrderNo());
                Map<String, Object> metaSignMap = new TreeMap<>();
                metaSignMap.put("merchantCode", backVO.getMerchantCode());
                metaSignMap.put("signType", backVO.getSignType());
                metaSignMap.put("sign", backVO.getSign());
                metaSignMap.put("code", backVO.getCode());
                metaSignMap.put("message", backVO.getMessage());
                metaSignMap.put("merchantOrderNo", backVO.getMerchantOrderNo());
                metaSignMap.put("platformOrderNo", backVO.getPlatformOrderNo());
                metaSignMap.put("orderAmount", backVO.getOrderAmount());
                metaSignMap.put("actualAmount", backVO.getActualAmount());
                metaSignMap.put("actualFee", backVO.getActualFee());
                metaSignMap.put("orderStatus", backVO.getOrderStatus());
                PayOrderRecharge payOrderRecharge = queryPayOrderInfo(backVO.getMerchantOrderNo());
                if (payOrderRecharge == null) {
                    log.error("KG支付参数验证异常,查无此订单,req={}", JSON.toJSONString(backVO));
                    return null;
                }
                PayThreeProvider payThreeProvider = getMerchantCode(backVO.getMerchantCode());
                if (ObjectUtils.isEmpty(payThreeProvider)) {
                    log.error("KG支付参数验证异常,商户无,req={}", JSON.toJSONString(backVO));
                    return null;
                }
                // 商户key
                String apikey =payThreeProvider.getSecretCode();
                String signString = SignMd5Utils.commonSignByObject(metaSignMap, apikey);
                if (!signString.equals(backVO.getSign())) {
                    log.error("KG支付签名不正确=={}", signString);
                    return "KG支付签名不在确";
                }
                PayMentInfo payMentInfo = queryPayMentinfo(backVO.getMerchantOrderNo());
                if (ObjectUtils.isEmpty(payMentInfo)) {
                    log.error("KG支付参数验证异常,查无此订单,req={}", JSON.toJSONString(backVO));
                    return null;
                }
                if (PayConstants.PayOrderStatusEnum.IN_HAND.getCode().intValue() != payMentInfo.getPayStatus().intValue()) {
                    log.error("KG支付此单号已经处理=={}", backVO.getMerchantOrderNo());
                    return PAY_CALLBACK_BIG_OK[0];
                }
                if (payMentInfo.getPayAmt().compareTo(new BigDecimal(backVO.getOrderAmount())) != 0) {
                    log.error("KG支付回调支付金额异常=={}", backVO.getMerchantOrderNo());
                    return null;
                }
                //组装需要更新的支付流水信息
                PaymentVastVO paymentVastVO = new PaymentVastVO();
                paymentVastVO.setPayId(payMentInfo.getPayId());
                paymentVastVO.setPrice(payOrderRecharge.getSumAmt());
                paymentVastVO.setOrderNo(backVO.getMerchantOrderNo());
                paymentVastVO.setFlowNo(backVO.getPlatformOrderNo());
                paymentVastVO.setStatus(PayConstants.PayOrderStatusEnum.SUCCESS.getCode());
                paymentVastVO.setDatetime(new Date());
                //判断KG支付回调状态
                for(PayConstants.PayOrderStatusEnum pay_kg_orderStatusEnum:PayConstants.PayOrderStatusEnum.values()){
                    if(pay_kg_orderStatusEnum.getValue().equals(backVO.getOrderStatus())){
                        paymentVastVO.setStatus(pay_kg_orderStatusEnum.getCode());
                    }
                }
                //进行账变、更新订单信息表、支付流水表 、金额变动记录表，钱包余额表
                Boolean flag = payChangeBusiness.payKgRechargeChange(paymentVastVO, payOrderRecharge);
                if (!flag) {
                    log.error("KG支付回调处理失败！");
                    return PAY_CALLBACK_FAIL[0];
                }
            } else {
                throw new BusinessException("KG支付回调处理中-订单号：" + backVO.getMerchantOrderNo() + " 正在处理中，请勿重复处理！");
            }
        } catch (Exception e) {
            log.error("dileiCallback occur error.", e);
            return PAY_CALLBACK_FAIL[0];
        } finally {
            // 释放锁
            lock.writeLock().unlock();
        }
        log.info("KG支付单号处理成功==" + backVO.getMerchantOrderNo());
        return PAY_CALLBACK_KG_OK;
    }

    public void test_confirm_withdraw(ConfirmWithdrawHandleReq req) {
        payChangeBusiness.test_confirm_withdraw(req);
    }


}
