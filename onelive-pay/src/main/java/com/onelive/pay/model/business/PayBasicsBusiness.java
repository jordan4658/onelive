package com.onelive.pay.model.business;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.onelive.common.constants.business.PayConstants;
import com.onelive.common.constants.business.RedisKeys;
import com.onelive.common.exception.BusinessException;
import com.onelive.common.model.dto.login.AppLoginUser;
import com.onelive.common.model.dto.pay.RechargeDTO;
import com.onelive.common.model.req.pay.CancelRechargeReq;
import com.onelive.common.model.req.pay.RechargeAddReq;
import com.onelive.common.model.req.pay.RechargeOptionsQueryReq;
import com.onelive.common.model.vo.pay.*;
import com.onelive.common.mybatis.entity.*;
import com.onelive.common.utils.Login.LoginInfoUtil;
import com.onelive.common.utils.others.BeanCopyUtil;
import com.onelive.common.utils.pay.PayUtils;
import com.onelive.pay.service.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.velocity.util.ArrayListWrapper;
import org.redisson.api.RReadWriteLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.BeanUtils;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author 就不告诉你
 * @version V1.0
 * @ClassName: PayInsertBusiness
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @date 创建时间：2021/4/7 12:26
 */
@Slf4j
@Component
@Scope(proxyMode = ScopedProxyMode.TARGET_CLASS)
public class PayBasicsBusiness {

    @Resource
    private PayTypeService payTypeService;
    @Resource
    private PayExchangeCurrencyService payExchangeCurrencyService;
    @Resource
    private PayWayService payWayService;
    @Resource
    private PayThreeProviderService payThreeProviderService;
    @Resource
    private PayCommonBusiness payCommonBusiness;
    @Resource
    private PayOrderRechargeService payOrderRechargeService;
    @Resource
    private PayChannelService payChannelService;
    @Resource
    private SysCountryService sysCountryService;
    @Resource
    private PayShortcutOptionsService payShortcutOptionsService;
    @Resource
    private MemUserService memUserService;
    @Resource
    private SysBusParameterService sysBusParameterService;
    @Resource
    private PaySilverBeanOptionsService paySilverBeanOptionsService;


    @Resource
    private RedissonClient redissonClient;


    /**
     * 查询支付方式列表
     *
     * @return
     */
    public List<PayWayVO> getPayWayList() {

        String lang = LoginInfoUtil.getLang();
        String countryCode = LoginInfoUtil.getCountryCode();
        MemUser memUser=memUserService.getById(LoginInfoUtil.getUserId());
        SysCountry sysCountry = sysCountryService.getByCountryCode(memUser.getRegisterCountryCode());
        PayExchangeCurrency exchangeCurrency = payExchangeCurrencyService.selectByCurrencyCode(sysCountry.getLocalCurrency());
        if (exchangeCurrency == null) {
            throw new BusinessException("未获取到当前币种充值的汇率！");
        }
        //查找所有启用的支付方式
        List<PayWayVO> listWay = payWayService.getPayWayList(countryCode);
        //查询所有启用的 支付通道
        QueryWrapper<PayChannel> queryWrapper = new QueryWrapper();
        queryWrapper.lambda().eq(PayChannel::getIsDelete, false).eq(PayChannel::getStatus, true);
        List<PayChannel> listChannel = payChannelService.list(queryWrapper);
        //查询平台金币单位
        SysBusParameter goldUnit = sysBusParameterService.getByCode(PayConstants.PLATFORM_GOLD_UNIT);
        for (PayWayVO wayVO : listWay) {
            List<PayChannelVO> channelList = new ArrayList<>();
            for (PayChannel channel : listChannel) {
                if (wayVO.getPayWayId() == channel.getPayWayId()) {
                    PayChannelVO channelVO = new PayChannelVO();
                    channelVO.setChannelCode(channel.getChannelCode());
                    channelVO.setChannelName(channel.getChannelName());
                    channelList.add(channelVO);
                }
            }
            wayVO.setShortcutOptionsUnit(goldUnit.getParamValue());
            wayVO.setCzExChange(exchangeCurrency.getCzExchange());
            wayVO.setCurrencyUnit(exchangeCurrency.getCurrencyUnit());
            wayVO.setPayChannelVOList(channelList);
        }


        return listWay;
    }

    /**
     * 充值处理
     *
     * @param rechargeAddReq
     * @param user
     * @param source
     * @param requestIp
     * @return
     */
    @Transactional
    public JSONObject rechargeOrder(RechargeAddReq rechargeAddReq, AppLoginUser user, String source, String requestIp) {
        if (rechargeAddReq.getPayWayId() == null) {
            throw new BusinessException("支付渠道id为空！");
        }
        if (rechargeAddReq.getPrice() == null || rechargeAddReq.getPrice().compareTo(BigDecimal.ZERO) != 1) {
            throw new BusinessException("充值数量为空或小于0！");
        }
        PayWay payWay = payWayService.selectOne(rechargeAddReq.getPayWayId());
        if (payWay == null) {
            throw new BusinessException("未查询到该充值方式！");
        }
        if (PayConstants.PayTypeEnum.BANK.getCode().intValue() == payWay.getPayTypeCode().intValue()) {
            if (StringUtils.isBlank(rechargeAddReq.getPayName())) {
                throw new BusinessException("入款姓名为空！");
            }
        }
        //查询当前币种汇率
        //查询国家语言相关信息
        MemUser memUser=memUserService.getById(user.getId());
        log.info("==========LoginInfoUtil.getCountryCode()===========:" + JSONObject.toJSONString(memUser));
        SysCountry sysCountry = sysCountryService.getByCountryCode(memUser.getRegisterCountryCode());
        PayExchangeCurrency exchangeCurrency = payExchangeCurrencyService.selectByCurrencyCode(sysCountry.getLocalCurrency());
        if (exchangeCurrency == null) {
            throw new BusinessException("未获取到当前币种充值的汇率！");
        }
        //实际付款金额 =平台币*（当前国家币种兑换平台的汇率）充值币种汇率
        BigDecimal actual_payment = rechargeAddReq.getPrice().multiply(new BigDecimal(exchangeCurrency.getCzExchange())).setScale( 0, BigDecimal.ROUND_UP );
        if (actual_payment.compareTo(payWay.getMinAmt()) < 0 || actual_payment.compareTo(payWay.getMaxAmt()) > 0) {
            throw new BusinessException("充值金额范围:" + payWay.getMinAmt() + "~" + payWay.getMaxAmt() + "！");
        }
        JSONObject jsonObject = new JSONObject();
        //每次账变前先对该用户加上账变写锁
        RReadWriteLock lock = redissonClient.getReadWriteLock(RedisKeys.PAY_RECHARGE_BALANCE + user.getUserAccount());
        try {
            // 写锁（等待时间20s，超时时间10S[自动解锁]，单位：秒）【设定超时时间，超时后自动释放锁，防止死锁】
            boolean bool = lock.writeLock().tryLock(20, 10, TimeUnit.SECONDS);
            //是否拿到用户账变写锁
            if (bool) {
                RechargeDTO rechargeDto = new RechargeDTO();
                BeanUtils.copyProperties(rechargeAddReq, rechargeDto);
                rechargeDto.setSource(source);
                rechargeDto.setActualPayment(actual_payment);
                rechargeDto.setCurrencyCode(exchangeCurrency.getCurrencyCode());
                rechargeDto.setCzExchange(exchangeCurrency.getCzExchange());
                rechargeDto.setRequestIp(requestIp);
                rechargeDto.setAccount(user.getUserAccount());
                //获取支付渠道商码
                PayThreeProvider payThreeProvider = payThreeProviderService.selectOneById(payWay.getProviderId());
                if (payThreeProvider == null) {
                    throw new BusinessException("支付渠道商为空！");
                }
                //生成充值订单编号  添加充值订单号前缀：
                // 充值类型请去充值前缀枚举里面新增前缀类型
                String orderNo = "CZ" + PayUtils.getOrderNo();
                rechargeDto.setOrderNo(orderNo);
                switch (payThreeProvider.getAgentNo()) {
                    case PayConstants.PAY_KG:
                        jsonObject = payCommonBusiness.kgPay(rechargeDto, payWay, payThreeProvider);
                        break;
                    default:
                        throw new BusinessException("支付方式错误！");
                }
            } else {
                throw new BusinessException("请求过于频繁，请稍后再试！");
            }
        } catch (Exception e) {
            log.error("{} rechargeOrder occur error. change info:{}", user.getUserAccount(), rechargeAddReq, e);
            throw new BusinessException("用户账变错误！", e.getCause());
        } finally {
            // 释放锁
            lock.writeLock().unlock();
            log.info("{} rechargeOrder 用户充值释放锁", user.getUserAccount());
        }

        return jsonObject;
    }


    /**
     * 取消充值处理
     *
     * @param req
     * @param user
     */
    public void cancelRecharge(CancelRechargeReq req, AppLoginUser user) {
        if (req == null) {
            throw new BusinessException("取消订单信息为空！");
        }
        if (StringUtils.isBlank(req.getOrderNo())) {
            throw new BusinessException("订单号为空！");
        }
        payOrderRechargeService.updateByOrderNo(req.getOrderNo(), PayConstants.PayOrderStatusEnum.CANCEL.getCode(), user);
    }

    public List<RechargeShortcutOptionsVO> getRechargeOptions(RechargeOptionsQueryReq req) {
        if (req.getPayWayId() == null) {
            throw new BusinessException("请先选择支付方式！");
        }
        String countryCode = "vi_VN";
        //查询国家语言相关信息
        SysCountry sysCountry = sysCountryService.getByCountryCode(countryCode);
        //查询充值选项
        PayShortcutOptions payShortcutOptions = payShortcutOptionsService.getByPayWayId(req.getPayWayId());
        //查询平台金币单位
        SysBusParameter goldUnit = sysBusParameterService.getByCode(PayConstants.PLATFORM_GOLD_UNIT);
        //查询 币种标识，还有汇率
        PayExchangeCurrency payExchangeCurrency = payExchangeCurrencyService.selectByCurrencyCode(sysCountry.getLocalCurrency());
        String[] strings = payShortcutOptions.getShortcutOptionsContent().split(",");
        List<RechargeShortcutOptionsVO> voList = new ArrayList<>();
        for (String str : strings) {
            RechargeShortcutOptionsVO vo = new RechargeShortcutOptionsVO();
            BigDecimal czExchange = new BigDecimal(payExchangeCurrency.getCzExchange());
            vo.setOption(str);
            vo.setCurrencyUint(payExchangeCurrency.getCurrencyUnit());
            vo.setExChange(payExchangeCurrency.getCzExchange());
            vo.setPayWayId(payShortcutOptions.getPayWayId());
            //计算实际支付币种金额
            vo.setPrice(new BigDecimal(str).multiply(czExchange));
            vo.setShortcutOptionsUnit(goldUnit.getParamValue());
            vo.setShortcutOptionsId(payShortcutOptions.getShortcutOptionsId());
            voList.add(vo);
        }
        return voList;

    }

    public List<SilverBeanOptionsVO> getSilverBeanOptions() {
        //查询银豆兑换快捷选项
        QueryWrapper<PaySilverBeanOptions> queryWrapper=new QueryWrapper();
        queryWrapper.lambda().eq(PaySilverBeanOptions::getIsDelete,false);
        queryWrapper.lambda().eq(PaySilverBeanOptions::getIsEnable,true);
        PaySilverBeanOptions paySilverBeanOptions = paySilverBeanOptionsService.getOne(queryWrapper);
        //查询金币兑换银豆汇率
        SysBusParameter silverBeanExChange = sysBusParameterService.getByCode(PayConstants.GOLD_SILVER_EXCHANGE);
        //查询平台金币单位
        SysBusParameter goldUnit = sysBusParameterService.getByCode(PayConstants.PLATFORM_GOLD_UNIT);
        //查询平台银豆单位
        SysBusParameter silverUnit = sysBusParameterService.getByCode(PayConstants.PLATFORM_SILVER_UNIT);
        String[] strings = paySilverBeanOptions.getOptionsContent().split(",");
        BigDecimal czExchange = new BigDecimal(silverBeanExChange.getParamValue());
        List<SilverBeanOptionsVO> voList = new ArrayList<>();
        for (String str : strings) {
            SilverBeanOptionsVO vo = new SilverBeanOptionsVO();
            vo.setOption(str);
            vo.setPlatformGoldUint(goldUnit.getParamValue());
            vo.setExChange(silverBeanExChange.getParamValue());
            vo.setPlatformGoldNum(new BigDecimal(str).divide(czExchange));
            vo.setOptionsUnit(silverUnit.getParamValue());
            vo.setSilverBeanOptionsId(paySilverBeanOptions.getSilverBeanOptionsId());
            voList.add(vo);
        }
        return voList;

    }
}
