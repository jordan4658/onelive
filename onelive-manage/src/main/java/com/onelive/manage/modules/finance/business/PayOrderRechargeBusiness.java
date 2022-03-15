package com.onelive.manage.modules.finance.business;

import cn.hutool.core.date.DateUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.onelive.common.constants.business.PayConstants;
import com.onelive.common.enums.WalletTypeEnum;
import com.onelive.common.exception.BusinessException;
import com.onelive.common.model.dto.login.LoginUser;
import com.onelive.common.model.dto.pay.BalanceChangeDTO;
import com.onelive.common.model.req.pay.OfflineRechargeHandleReq;
import com.onelive.common.model.vo.mem.MemAccountChangeVO;
import com.onelive.common.model.vo.pay.OfflinePayOrderRechargeBackVO;
import com.onelive.common.model.vo.pay.OnlinePayOrderRechargeBackVO;
import com.onelive.common.mybatis.entity.MemWallet;
import com.onelive.common.mybatis.entity.PayOrderRecharge;
import com.onelive.common.mybatis.entity.PayWay;
import com.onelive.common.service.accountChange.AccountBalanceChangeService;
import com.onelive.common.utils.others.DateInnerUtil;
import com.onelive.manage.common.constant.SystemRedisKeys;
import com.onelive.manage.service.finance.PayOrderRechargeService;
import com.onelive.manage.service.finance.PayWayService;
import com.onelive.manage.service.mem.MemWalletService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.redisson.api.RReadWriteLock;
import org.redisson.api.RedissonClient;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author 就不告诉你
 * @version V1.0
 * @ClassName: PayOrderRechargeBusiness
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @date 创建时间：2021/4/20 10:02
 */
@Slf4j
@Component
@Scope(proxyMode = ScopedProxyMode.TARGET_CLASS)
public class PayOrderRechargeBusiness {

    @Resource
    private PayOrderRechargeService payOrderRechargeService;
    @Resource
    private MemWalletService memWalletService;
    @Resource
    private PayWayService payWayService;
    @Resource
    private AccountBalanceChangeService accountBalanceChangeBusinessService;
    @Resource
    private RedissonClient redissonClient;


    public PageInfo<OfflinePayOrderRechargeBackVO> offlineListPage(Integer pageNum, Integer pageSize, Integer orderStatus,

                                                                   String account, String orderNo, String startDateStr, String endDateStr, LoginUser user) {
        Date startDate;
        Date endDate;
        Date nowDate = new Date();
        if (StringUtils.isBlank(startDateStr)) {
            startDate = DateUtil.beginOfDay(nowDate);
        } else {
            startDate = DateUtil.beginOfDay(DateUtil.parse(startDateStr));
        }
        if (StringUtils.isBlank(endDateStr)) {
            endDate = DateUtil.endOfDay(nowDate);
        } else {
            endDate = DateUtil.endOfDay(DateUtil.parse(endDateStr));
        }
        if (endDate.getTime() < startDate.getTime()) {
            throw new BusinessException("开始时间不能大于结束时间");
        }

        if (DateInnerUtil.betweenDay(startDate, endDate) > 30) {
            throw new BusinessException("查询日期范围超过30天！");
        }

        PageHelper.startPage(pageNum, pageSize);
        List<OfflinePayOrderRechargeBackVO> list = payOrderRechargeService.offlineListPage(PayConstants.PayProviderTypeEnum.OFFLINE.getCode(),
                orderStatus, account, orderNo, startDate, endDate);
        return new PageInfo<>(list);
    }

    public PageInfo<OnlinePayOrderRechargeBackVO> onlineListPage(Integer pageNum, Integer pageSize, Integer orderStatus, String account, String orderNo,
                                                                 String providerId, String startDateStr, String endDateStr, LoginUser user) {
        Date startDate;
        Date endDate;
        Date nowDate = new Date();
        if (StringUtils.isBlank(startDateStr)) {
            startDate = DateUtil.beginOfDay(nowDate);
        } else {
            startDate = DateUtil.beginOfDay(DateUtil.parse(startDateStr));
        }
        if (StringUtils.isBlank(endDateStr)) {
            endDate = DateUtil.endOfDay(nowDate);
        } else {
            endDate = DateUtil.endOfDay(DateUtil.parse(endDateStr));
        }
        if (endDate.getTime() < startDate.getTime()) {
            throw new BusinessException("开始时间不能大于结束时间");
        }

        if (DateInnerUtil.betweenDay(startDate, endDate) > 30) {
            throw new BusinessException("查询日期范围超过30天！");
        }
        PageHelper.startPage(pageNum, pageSize);
        List<OnlinePayOrderRechargeBackVO> list = payOrderRechargeService.onlineListPage(PayConstants.PayProviderTypeEnum.ONLINE.getCode(),
                orderStatus, account, orderNo, providerId, startDate, endDate);
        return new PageInfo<>(list);
    }

    @Transactional
    public void rechargeHandle(OfflineRechargeHandleReq rechargeHandleReq) {
        if (rechargeHandleReq.getOrderId() == null) {
            throw new BusinessException("订单id为空！");
        }
        if (rechargeHandleReq.getOrderStatus() == null) {
            throw new BusinessException("订单状态为空！");
        }
        if (StringUtils.isBlank(rechargeHandleReq.getAccount())) {
            throw new BusinessException("用户账号为空！");
        }
        if (StringUtils.isBlank(rechargeHandleReq.getInstructions())) {
            throw new BusinessException("操作说明为空！");
        }
        PayOrderRecharge payOrderRecharge = payOrderRechargeService.getById(rechargeHandleReq.getOrderId());
        if (payOrderRecharge == null) {
            throw new BusinessException("订单不存在！");
        }
        //redis写锁
        //每次账变前先对该用户的 订单 加上账变写锁
        RReadWriteLock lock = redissonClient.getReadWriteLock(SystemRedisKeys.PAY_MANAGE_OFFLINE_CONFIRM + payOrderRecharge.getAccount());
        try {
            // 写锁（等待时间20s，超时时间10S[自动解锁]，单位：秒）【设定超时时间，超时后自动释放锁，防止死锁】
            boolean bool = lock.writeLock().tryLock(20, 10, TimeUnit.SECONDS);
            //是否拿到用户账变写锁
            if (bool) {
                if (payOrderRecharge.getOrderStatus() != PayConstants.PayOrderStatusEnum.IN_HAND.getCode()) {
                    throw new BusinessException("订单已处理过，请勿重复处理！");
                }
                //查询用户钱包信息
                MemWallet memWallet = memWalletService.getByAccount(rechargeHandleReq.getAccount(), WalletTypeEnum.WOODEN_PLATFORM.getCode());
                if (memWallet == null) {
                    throw new BusinessException("用户还未初始化平台钱包信息！");
                }
                //更新订单信息表
                Date date = new Date();
                payOrderRecharge.setPayDate(new Date());
                payOrderRecharge.setOrderStatus(rechargeHandleReq.getOrderStatus());
                payOrderRecharge.setUpdateTime(date);
                payOrderRecharge.setIsFirst(false);
                //判断是否首充
                if (memWallet.getSumRechargeAmount().compareTo(BigDecimal.ZERO) == 0) {
                    payOrderRecharge.setIsFirst(true);
                }
                if (payOrderRecharge.getOrderStatus() != PayConstants.PayOrderStatusEnum.SUCCESS.getCode()) {
                    payOrderRecharge.setCancelReason(rechargeHandleReq.getInstructions());
                } else {
                    payOrderRecharge.setOrderNote("线下充值成功");
                }
                Integer num = payOrderRechargeService.updateOrderInfo(payOrderRecharge);
                if (num < PayConstants.KEY_1) {
                    throw new BusinessException("更新充值订单信息失败！");
                }
                if (payOrderRecharge.getOrderStatus() != PayConstants.PayOrderStatusEnum.SUCCESS.getCode()) {
                    /** 充值失败、充值取消 -----不进行账变，只是修改订单状态 */
                    return;
                }
                MemAccountChangeVO memAccountChangeVO = new MemAccountChangeVO();
                memAccountChangeVO.setAccount(payOrderRecharge.getAccount());
                memAccountChangeVO.setChangeType(PayConstants.AccountChangTypeEnum.CHANG_TYPE1.getPayTypeCode());
                memAccountChangeVO.setOrderNo(payOrderRecharge.getOrderNo());
                memAccountChangeVO.setPrice(payOrderRecharge.getSumAmt());
                memAccountChangeVO.setDml(payOrderRecharge.getSumAmt());
                memAccountChangeVO.setFlowType(PayConstants.FlowTypeEnum.INCOME.getCode());
                memAccountChangeVO.setOpNote("充值成功");
                BalanceChangeDTO balanceChangeDTO = accountBalanceChangeBusinessService.publicAccountBalanceChange(memAccountChangeVO);
                if (!balanceChangeDTO.getFlag()) {
                    throw new BusinessException("充值订单处理失败！");
                }
                //充值附加赠送处理
                //查询支付方式信息（包含充值赠送设定信息）
                PayWay payWay = payWayService.selectOne(payOrderRecharge.getPayWayId());
                //判断是否充值赠送
                if (PayConstants.givingTypeEnum.NOT_GIVING.getCode() == payWay.getGivingType()) {
                    log.info("进入回调数据成功处理===不赠送=====结束================================={}==", payOrderRecharge.getOrderNo());
                    return;
                }
                //计算赠送金额
                BigDecimal price = payOrderRecharge.getSumAmt().multiply(payWay.getPayWayGivingRatio().divide(new BigDecimal(100)));
                memAccountChangeVO.setChangeType(PayConstants.AccountChangTypeEnum.CHANG_TYPE8.getPayTypeCode());
                if (PayConstants.givingTypeEnum.FIRST_GIVING.getCode() == payWay.getGivingType() && memWallet.getSumRechargeAmount().compareTo(BigDecimal.ZERO) > 0) {
                    //首冲赠送
                    memAccountChangeVO.setPrice(price);
                    memAccountChangeVO.setOpNote("首充赠送");
                    BalanceChangeDTO flagGive = accountBalanceChangeBusinessService.publicAccountBalanceChange(memAccountChangeVO);
                    if (!flagGive.getFlag()) {
                        throw new BusinessException("首充订单处理失败！");
                    }
                    log.info("进入回调数据成功处理=====首充赠送===结束================================={}==", payOrderRecharge.getOrderNo());
                    return;
                }
                //每次赠送
                memAccountChangeVO.setPrice(price);
                memAccountChangeVO.setOpNote("每次充值赠送");
                BalanceChangeDTO flagGive = accountBalanceChangeBusinessService.publicAccountBalanceChange(memAccountChangeVO);
                if (!flagGive.getFlag()) {
                    throw new BusinessException("每次充值赠送订单处理失败！");
                }
                log.info("进入回调数据成功处理====每次赠送====结束================================={}==", payOrderRecharge.getOrderNo());
            }else{
                throw new BusinessException("线下充值处理-订单号：" + rechargeHandleReq.getOrderId() + " 正在处理中，请勿重复处理！");
            }
        } catch (Exception e) {
            log.error("{} rechargeHandle occur error. change info:{}", rechargeHandleReq.getAccount(), rechargeHandleReq, e);
            throw new BusinessException("线下充值处理 错误！", e.getCause());
        } finally {
            lock.writeLock().unlock();
            log.info("{} rechargeHandle 线下充值处理 释放锁", rechargeHandleReq.getAccount());
        }
    }
}
