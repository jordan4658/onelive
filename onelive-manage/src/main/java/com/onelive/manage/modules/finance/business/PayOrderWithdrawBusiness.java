package com.onelive.manage.modules.finance.business;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.onelive.common.constants.business.PayConstants;
import com.onelive.common.enums.WalletTypeEnum;
import com.onelive.common.exception.BusinessException;
import com.onelive.common.model.dto.login.LoginUser;
import com.onelive.common.model.dto.pay.BalanceChangeDTO;
import com.onelive.common.model.req.pay.ConfirmWithdrawHandleReq;
import com.onelive.common.model.vo.mem.MemAccountChangeVO;
import com.onelive.common.model.vo.pay.PayOrderWithdrawBackVO;
import com.onelive.common.mybatis.entity.MemWallet;
import com.onelive.common.mybatis.entity.PayOrderWithdraw;
import com.onelive.common.service.accountChange.AccountBalanceChangeService;
import com.onelive.common.utils.others.DateInnerUtil;
import com.onelive.manage.common.constant.SystemRedisKeys;
import com.onelive.manage.service.finance.PayOrderWithdrawService;
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
 * @ClassName: PayOrderWithdrawBusiness
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @date 创建时间：2021/4/21 15:09
 */
@Slf4j
@Component
@Scope(proxyMode = ScopedProxyMode.TARGET_CLASS)
public class PayOrderWithdrawBusiness {

    @Resource
    private PayOrderWithdrawService payOrderWithdrawService;
    @Resource
    private AccountBalanceChangeService accountBalanceChangeService;
    @Resource
    private MemWalletService memWalletService;
    @Resource
    private RedissonClient redissonClient;


    public PageInfo<PayOrderWithdrawBackVO> listPage(Integer pageNum, Integer pageSize, Integer orderStatus, String account,
                                                     String orderNo, Date startDate, Date endDate, LoginUser user) {
        if (startDate == null) {
            startDate = DateInnerUtil.beginOfDay(new Date());
        } else {
            endDate = DateInnerUtil.endOfDay(endDate);
        }
        if (endDate == null) {
            endDate = DateInnerUtil.beginOfDay(new Date());
        } else {
            startDate = DateInnerUtil.beginOfDay(startDate);
        }
        if (endDate.compareTo(startDate) < 0) {
            throw new BusinessException("开始日期大于结束日期！");
        }
        if (DateInnerUtil.betweenDay(startDate, DateInnerUtil.parse(DateInnerUtil.today())) > 30) {
            throw new BusinessException("查询日期范围超过30天！");
        }
        PageHelper.startPage(pageNum, pageSize);
        List<PayOrderWithdrawBackVO> list = payOrderWithdrawService.listPage(account, orderStatus, orderNo, startDate, endDate);
        return new PageInfo<>(list);
    }

    @Transactional
    public void withdrawHandle(ConfirmWithdrawHandleReq withdrawHandleReq, LoginUser user) {

        if (withdrawHandleReq == null) {
            throw new BusinessException("处理提现请求信息为空！");
        }
        if (StringUtils.isBlank(withdrawHandleReq.getAccount())) {
            throw new BusinessException("会员账号为空！");
        }
        if (withdrawHandleReq.getOrderId() == null) {
            throw new BusinessException("订单id为空！");
        }
        if (withdrawHandleReq.getOrderStatus() == null) {
            throw new BusinessException("订单状态为空！");
        }
        if (StringUtils.isBlank(withdrawHandleReq.getInstructions())) {
            throw new BusinessException("操作说明为空！");
        }
        //redis写锁
        //每次账变前先对该用户的 订单 加上账变写锁
        RReadWriteLock lock = redissonClient.getReadWriteLock(SystemRedisKeys.PAY_MANAGE_WITHDRAW_AMT + withdrawHandleReq.getAccount());
        try {
            // 写锁（等待时间20s，超时时间10S[自动解锁]，单位：秒）【设定超时时间，超时后自动释放锁，防止死锁】
            boolean bool = lock.writeLock().tryLock(20, 10, TimeUnit.SECONDS);
            //是否拿到用户账变写锁
            if (bool) {
                PayOrderWithdraw payOrderWithdraw = payOrderWithdrawService.getById(withdrawHandleReq.getOrderId());
//                if (payOrderWithdraw.getWithdrawStatus() != PayConstants.PayOrderStatusEnum.IN_HAND.getCode()) {
//                    throw new BusinessException("提现订单已处理过，请勿重复处理！");
//                }
                //查询用户钱包信息
                MemWallet memWallet = memWalletService.getByAccount(withdrawHandleReq.getAccount(), WalletTypeEnum.WOODEN_PLATFORM.getCode());
                if (memWallet == null) {
                    throw new BusinessException("用户还未初始化平台钱包信息！");
                }
                //更新订单信息表
                Date date = new Date();
                payOrderWithdraw.setPayDate(new Date());
                payOrderWithdraw.setWithdrawStatus(withdrawHandleReq.getOrderStatus());
                payOrderWithdraw.setUpdateTime(date);
                payOrderWithdraw.setPayDate(date);
                payOrderWithdraw.setPayAmt(payOrderWithdraw.getWithdrawAmt());
                payOrderWithdraw.setPayMemName(user.getAccLogin());
                payOrderWithdraw.setOperationExplain(withdrawHandleReq.getInstructions());
                Boolean withdrawFlag = payOrderWithdrawService.updateById(payOrderWithdraw);
                if (!withdrawFlag) {
                    throw new BusinessException("更新提现订单信息失败！");
                }
                MemAccountChangeVO memAccountChangeVO = new MemAccountChangeVO();
                if (payOrderWithdraw.getWithdrawStatus() == PayConstants.PayOrderStatusEnum.SUCCESS.getCode()) {
                    memAccountChangeVO.setChangeType(PayConstants.AccountChangTypeEnum.CHANG_TYPE7.getPayTypeCode());
                    memAccountChangeVO.setPrice(payOrderWithdraw.getWithdrawAmt().multiply(new BigDecimal(-1)));
                    memAccountChangeVO.setFlowType(PayConstants.FlowTypeEnum.OTHER.getCode());
                } else if (payOrderWithdraw.getWithdrawStatus() == PayConstants.PayOrderStatusEnum.CANCEL.getCode()) {
                    memAccountChangeVO.setChangeType(PayConstants.AccountChangTypeEnum.CHANG_TYPE4.getPayTypeCode());
                    memAccountChangeVO.setPrice(payOrderWithdraw.getWithdrawAmt());
                    memAccountChangeVO.setFlowType(PayConstants.FlowTypeEnum.INCOME.getCode());
                }else if(payOrderWithdraw.getWithdrawStatus() == PayConstants.PayOrderStatusEnum.UN_SUCCESS.getCode()){
                    memAccountChangeVO.setChangeType(PayConstants.AccountChangTypeEnum.CHANG_TYPE5.getPayTypeCode());
                    memAccountChangeVO.setPrice(payOrderWithdraw.getWithdrawAmt());
                    memAccountChangeVO.setFlowType(PayConstants.FlowTypeEnum.INCOME.getCode());
                }else if(payOrderWithdraw.getWithdrawStatus() == PayConstants.PayOrderStatusEnum.IN_HAND.getCode()){
                    // 受理提现订单，只做订单状态修改
                    return;
//                    memAccountChangeVO.setChangeType(PayConstants.AccountChangTypeEnum.CHANG_TYPE6.getPayTypeCode());
//                    memAccountChangeVO.setPrice(payOrderWithdraw.getWithdrawAmt());
//                    memAccountChangeVO.setFlowType(PayConstants.FlowTypeEnum.OTHER.getCode());
                }
                memAccountChangeVO.setAccount(withdrawHandleReq.getAccount());
                memAccountChangeVO.setFlowNo(null);
                memAccountChangeVO.setOrderNo(payOrderWithdraw.getWithdrawNo());
                memAccountChangeVO.setDml(BigDecimal.ZERO);
                memAccountChangeVO.setOpNote(withdrawHandleReq.getInstructions());
                memAccountChangeVO.setIsSilverBean(PayConstants.GoldTypeEnum.GOLD_TYPE_2.getCode());
                BalanceChangeDTO flag = accountBalanceChangeService.publicAccountBalanceChange(memAccountChangeVO);
                if (!flag.getFlag()) {
                    throw new BusinessException("提现订单:" + memAccountChangeVO.getOrderNo() + "处理失败！");
                }
            } else {
                throw new BusinessException("请求过于频繁，请稍后再试！");
            }
        } catch (Exception e) {
            log.error("{} rechargeHandle occur error. change info:{}", withdrawHandleReq.getAccount(), withdrawHandleReq, e);
            throw new BusinessException(e.getMessage(), e.getCause());
        } finally {
            lock.writeLock().unlock();
            log.info("{} rechargeHandle 提现订单处理 释放锁", withdrawHandleReq.getAccount());
        }

    }
}
