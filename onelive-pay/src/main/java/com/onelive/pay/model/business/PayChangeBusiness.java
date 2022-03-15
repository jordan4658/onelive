package com.onelive.pay.model.business;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.onelive.common.constants.business.PayConstants;
import com.onelive.common.enums.WalletTypeEnum;
import com.onelive.common.exception.BusinessException;
import com.onelive.common.model.dto.pay.BalanceChangeDTO;
import com.onelive.common.model.req.pay.ConfirmWithdrawHandleReq;
import com.onelive.common.model.vo.mem.MemAccountChangeVO;
import com.onelive.common.model.vo.pay.PaymentVastVO;
import com.onelive.common.mybatis.entity.*;
import com.onelive.common.service.accountChange.AccountBalanceChangeService;
import com.onelive.common.service.accountChange.WalletService;
import com.onelive.pay.service.PayMentInfoService;
import com.onelive.pay.service.PayOrderRechargeService;
import com.onelive.pay.service.PayOrderWithdrawService;
import com.onelive.pay.service.PayWayService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author 就不告诉你
 * @version V1.0
 * @ClassName: AccountChangBusiness
 * @Description: 用户账变
 * @date 创建时间：2021/4/12 19:34
 */

@Slf4j
@Component
@Scope(proxyMode = ScopedProxyMode.TARGET_CLASS)
public class PayChangeBusiness {


    @Resource
    private PayOrderRechargeService payOrderRechargeService;
    @Resource
    private PayMentInfoService payMentInfoService;
    @Resource
    private WalletService walletService;
    //    @Resource
//    private MemBaseInfoBusiness memBaseInfoBusiness;
    @Resource
    private AccountBalanceChangeService accountBalanceChangeService;
    @Resource
    private PayWayService payWayService;
    @Resource
    private PayOrderWithdrawService payOrderWithdrawService;


    //系统操作员
    private static final String SYS = "sys";


    /**
     * 充值公共的账变接口进行账变：更新订单信息表、支付流水表 、金额变动记录表，用户钱包余额表
     *
     * @param paymentVastVO    支付信息
     * @param payOrderRecharge 充值订单信息
     * @return
     */
    @Transactional
    public Boolean payRechargeChange(PaymentVastVO paymentVastVO, PayOrderRecharge payOrderRecharge) {
        log.info("进入回调数据成功处理=====开始===================================={}==", paymentVastVO.getOrderNo());
        //查询用户钱包信息
        MemWallet memWallet = walletService.getByAccount(payOrderRecharge.getAccount(), WalletTypeEnum.WOODEN_PLATFORM.getCode());
        if (memWallet == null) {
            throw new BusinessException("用户还未初始化平台钱包信息！");
        }
        //更新订单信息表
        Date date = new Date();
        payOrderRecharge.setPayDate(paymentVastVO.getDatetime());
        payOrderRecharge.setOrderStatus(paymentVastVO.getStatus());
        payOrderRecharge.setUpdateTime(date);
        //判断是否首充
        if (memWallet.getSumRechargeAmount().compareTo(BigDecimal.ZERO) > 0) {
            payOrderRecharge.setIsFirst(true);
        }
        if (payOrderRecharge.getOrderStatus().intValue() != PayConstants.PayOrderStatusEnum.SUCCESS.getCode()) {
            payOrderRecharge.setCancelReason("线上充值失败！");
        } else {
            payOrderRecharge.setOrderNote("线上充值成功！");
        }
        Integer num = payOrderRechargeService.updateOrderInfo(payOrderRecharge);
        if (num < PayConstants.KEY_1) {
            throw new BusinessException("更新充值订单信息失败！");
        }
        //更新支付流水表
        UpdateWrapper<PayMentInfo> updateWrapper = new UpdateWrapper();
        updateWrapper.lambda().set(PayMentInfo::getPayNote, "线上地雷支付成功").set(PayMentInfo::getPayStatus, paymentVastVO.getStatus()).set(PayMentInfo::getSerialNo, paymentVastVO.getFlowNo()).set(PayMentInfo::getUpdateTime, date).eq(PayMentInfo::getOrderNo, paymentVastVO.getOrderNo());
        boolean menInfoNum = payMentInfoService.update(updateWrapper);
        if (!menInfoNum) {
            throw new BusinessException("更新支付流水订单信息失败！");
        }
        MemAccountChangeVO memAccountChangeVO = new MemAccountChangeVO();
        memAccountChangeVO.setAccount(payOrderRecharge.getAccount());
        memAccountChangeVO.setChangeType(PayConstants.AccountChangTypeEnum.CHANG_TYPE1.getPayTypeCode());
        memAccountChangeVO.setFlowNo(paymentVastVO.getFlowNo());
        memAccountChangeVO.setOrderNo(paymentVastVO.getOrderNo());
        memAccountChangeVO.setPrice(paymentVastVO.getPrice());
        memAccountChangeVO.setOpNote("线上地雷充值成功");
        memAccountChangeVO.setDml(paymentVastVO.getPrice());
        memAccountChangeVO.setFlowType(PayConstants.FlowTypeEnum.INCOME.getCode());
        BalanceChangeDTO flag = accountBalanceChangeService.publicAccountBalanceChange(memAccountChangeVO);
        if (!flag.getFlag()) {
            throw new BusinessException("地雷支付订单处理失败！");
        }
        //充值附加赠送处理
        //查询支付方式信息（包含充值赠送设定信息）
        PayWay payWay = payWayService.selectOne(payOrderRecharge.getPayWayId());
        //判断是否充值赠送
        if (PayConstants.givingTypeEnum.NOT_GIVING.getCode() == payWay.getGivingType().intValue()) {
            log.info("进入回调数据成功处理===不赠送=====结束================================={}==", paymentVastVO.getOrderNo());
            return true;
        }
        //计算赠送金额
        BigDecimal price = payOrderRecharge.getSumAmt().multiply(payWay.getPayWayGivingRatio().divide(new BigDecimal(100)));
        memAccountChangeVO.setChangeType(PayConstants.AccountChangTypeEnum.CHANG_TYPE8.getPayTypeCode());
        if (PayConstants.givingTypeEnum.FIRST_GIVING.getCode().intValue() == payWay.getGivingType().intValue() && memWallet.getSumRechargeAmount().compareTo(BigDecimal.ZERO) > 0) {
            //首冲赠送
            memAccountChangeVO.setPrice(price);
            memAccountChangeVO.setOpNote("首充赠送");
            memAccountChangeVO.setDml(price);
            BalanceChangeDTO givinFlag = accountBalanceChangeService.publicAccountBalanceChange(memAccountChangeVO);
            if (!givinFlag.getFlag()) {
                throw new BusinessException("首充订单处理失败！");
            }
            log.info("进入回调数据成功处理=====首充赠送===结束================================={}==", paymentVastVO.getOrderNo());
            return true;
        }
        //每次赠送
        memAccountChangeVO.setPrice(price);
        memAccountChangeVO.setOpNote("每次充值赠送");
        memAccountChangeVO.setDml(price);
        BalanceChangeDTO givinFlag = accountBalanceChangeService.publicAccountBalanceChange(memAccountChangeVO);
        if (!givinFlag.getFlag()) {
            throw new BusinessException("每次充值赠送订单处理失败！");
        }
        log.info("进入回调数据成功处理====每次赠送====结束================================={}==", paymentVastVO.getOrderNo());
        return true;
    }


    /**
     * KG_充值账变接口进行账变：更新订单信息表、支付流水表 、金额变动记录表，用户钱包余额表
     *
     * @param paymentVastVO    支付信息
     * @param payOrderRecharge 充值订单信息
     * @return
     */
    @Transactional
    public Boolean payKgRechargeChange(PaymentVastVO paymentVastVO, PayOrderRecharge payOrderRecharge) {
        log.info("进入KG支付回调数据成功处理=====开始===================================={}==", paymentVastVO.getOrderNo());
        //查询用户钱包信息
        MemWallet memWallet = walletService.getByAccount(payOrderRecharge.getAccount(), WalletTypeEnum.WOODEN_PLATFORM.getCode());
        if (memWallet == null) {
            throw new BusinessException("用户还未初始化平台钱包信息！");
        }
        //更新订单信息表
        Date date = new Date();
        payOrderRecharge.setPayDate(paymentVastVO.getDatetime());
        payOrderRecharge.setOrderStatus(paymentVastVO.getStatus());
        payOrderRecharge.setUpdateTime(date);
        //判断是否首充
        if (memWallet.getSumRechargeAmount().compareTo(BigDecimal.ZERO) > 0) {
            payOrderRecharge.setIsFirst(true);
        }
        String note="";
        if (payOrderRecharge.getOrderStatus().intValue() != PayConstants.PayOrderStatusEnum.SUCCESS.getCode()) {
            note="线上充值失败！";
            payOrderRecharge.setCancelReason("线上充值失败！");
        } else {
            note="线上充值成功！";
            payOrderRecharge.setOrderNote("线上充值成功！");
        }
        Integer num = payOrderRechargeService.updateOrderInfo(payOrderRecharge);
        if (num < PayConstants.KEY_1) {
            throw new BusinessException("更新充值订单信息失败！");
        }
        //更新支付流水表
        UpdateWrapper<PayMentInfo> updateWrapper = new UpdateWrapper();
        updateWrapper.lambda()
                .set(PayMentInfo::getPayNote, note)
                .set(PayMentInfo::getPayStatus, paymentVastVO.getStatus())
                .set(PayMentInfo::getSerialNo, paymentVastVO.getFlowNo())
                .set(PayMentInfo::getUpdateTime, date)
                .eq(PayMentInfo::getOrderNo, paymentVastVO.getOrderNo());
        boolean menInfoNum = payMentInfoService.update(updateWrapper);
        if (!menInfoNum) {
            throw new BusinessException("更新支付流水订单信息失败！");
        }
        MemAccountChangeVO memAccountChangeVO = new MemAccountChangeVO();
        memAccountChangeVO.setAccount(payOrderRecharge.getAccount());
        if (payOrderRecharge.getOrderStatus() == PayConstants.PayOrderStatusEnum.SUCCESS.getCode()) {
            memAccountChangeVO.setChangeType(PayConstants.AccountChangTypeEnum.CHANG_TYPE1.getPayTypeCode());
            memAccountChangeVO.setOpNote(PayConstants.AccountChangTypeEnum.CHANG_TYPE1.getMsg());
        }else if(payOrderRecharge.getOrderStatus() == PayConstants.PayOrderStatusEnum.CANCEL.getCode()){
            memAccountChangeVO.setChangeType(PayConstants.AccountChangTypeEnum.CHANG_TYPE16.getPayTypeCode());
            memAccountChangeVO.setOpNote(PayConstants.AccountChangTypeEnum.CHANG_TYPE16.getMsg());
        }else if(payOrderRecharge.getOrderStatus() == PayConstants.PayOrderStatusEnum.UN_SUCCESS.getCode()
                || payOrderRecharge.getOrderStatus() == PayConstants.PayOrderStatusEnum.TIMEOUT.getCode()){
            memAccountChangeVO.setChangeType(PayConstants.AccountChangTypeEnum.CHANG_TYPE15.getPayTypeCode());
            memAccountChangeVO.setOpNote(PayConstants.AccountChangTypeEnum.CHANG_TYPE15.getMsg());
        }else  if (payOrderRecharge.getOrderStatus() == PayConstants.PayOrderStatusEnum.IN_HAND.getCode()) {
            memAccountChangeVO.setChangeType(PayConstants.AccountChangTypeEnum.CHANG_TYPE35.getPayTypeCode());
            memAccountChangeVO.setOpNote(PayConstants.AccountChangTypeEnum.CHANG_TYPE35.getMsg());
        }else  if (payOrderRecharge.getOrderStatus() == PayConstants.PayOrderStatusEnum.PENDING.getCode()) {
            memAccountChangeVO.setChangeType(PayConstants.AccountChangTypeEnum.CHANG_TYPE36.getPayTypeCode());
            memAccountChangeVO.setOpNote(PayConstants.AccountChangTypeEnum.CHANG_TYPE36.getMsg());
        }else  if (payOrderRecharge.getOrderStatus() == PayConstants.PayOrderStatusEnum.TIMEOUT.getCode()) {
            memAccountChangeVO.setChangeType(PayConstants.AccountChangTypeEnum.CHANG_TYPE37.getPayTypeCode());
            memAccountChangeVO.setOpNote(PayConstants.AccountChangTypeEnum.CHANG_TYPE37.getMsg());
        }
        memAccountChangeVO.setFlowNo(paymentVastVO.getFlowNo());
        memAccountChangeVO.setOrderNo(paymentVastVO.getOrderNo());
        memAccountChangeVO.setPrice(paymentVastVO.getPrice());
        memAccountChangeVO.setDml(paymentVastVO.getPrice());
        memAccountChangeVO.setFlowType(PayConstants.FlowTypeEnum.INCOME.getCode());
        memAccountChangeVO.setIsSilverBean(PayConstants.GoldTypeEnum.GOLD_TYPE_2.getCode());
        BalanceChangeDTO flag = accountBalanceChangeService.publicAccountBalanceChange(memAccountChangeVO);
        if (!flag.getFlag()) {
            throw new BusinessException("支付订单处理失败！");
        }
        //充值附加赠送处理
        //查询支付方式信息（包含充值赠送设定信息）
        PayWay payWay = payWayService.selectOne(payOrderRecharge.getPayWayId());
        //判断是否充值赠送
        if (PayConstants.givingTypeEnum.NOT_GIVING.getCode() == payWay.getGivingType().intValue()) {
            log.info("进入回调数据成功处理===不赠送=====结束================================={}==", paymentVastVO.getOrderNo());
            return true;
        }
        //计算赠送金币数量
        BigDecimal price = payOrderRecharge.getSumAmt().multiply(payWay.getPayWayGivingRatio().divide(new BigDecimal(100)));
        memAccountChangeVO.setChangeType(PayConstants.AccountChangTypeEnum.CHANG_TYPE8.getPayTypeCode());
        if (PayConstants.givingTypeEnum.FIRST_GIVING.getCode().intValue() == payWay.getGivingType().intValue() && memWallet.getSumRechargeAmount().compareTo(BigDecimal.ZERO) > 0) {
            //首冲赠送
            memAccountChangeVO.setPrice(price);
            memAccountChangeVO.setOpNote("首充赠送");
            memAccountChangeVO.setDml(price);
            BalanceChangeDTO givinFlag = accountBalanceChangeService.publicAccountBalanceChange(memAccountChangeVO);
            if (!givinFlag.getFlag()) {
                throw new BusinessException("首充赠送订单处理失败！");
            }
            log.info("进入回调数据成功处理=====首充赠送===结束================================={}==", paymentVastVO.getOrderNo());
            return true;
        }
        //每次赠送
        memAccountChangeVO.setPrice(price);
        memAccountChangeVO.setOpNote("每次充值赠送");
        memAccountChangeVO.setDml(price);
        BalanceChangeDTO givinFlag = accountBalanceChangeService.publicAccountBalanceChange(memAccountChangeVO);
        if (!givinFlag.getFlag()) {
            throw new BusinessException("每次充值赠送订单处理失败！");
        }
        log.info("KG支付回调数据成功处理========结束================================={}==", paymentVastVO.getOrderNo());
        return true;
    }


    public void test_confirm_withdraw(ConfirmWithdrawHandleReq req) {
        PayOrderWithdraw payOrderWithdraw = payOrderWithdrawService.getById(req.getOrderId());
        if (payOrderWithdraw.getWithdrawStatus().intValue() != PayConstants.PayOrderStatusEnum.IN_HAND.getCode()) {
            throw new BusinessException("提现订单已处理过，请勿重复处理！");
        }
        //查询用户钱包信息
        MemWallet memWallet = walletService.getByAccount(req.getAccount(), WalletTypeEnum.WOODEN_PLATFORM.getCode());
        if (memWallet == null) {
            throw new BusinessException("用户还未初始化平台钱包信息！");
        }
        //更新订单信息表
        Date date = new Date();
        payOrderWithdraw.setPayDate(new Date());
        payOrderWithdraw.setWithdrawStatus(req.getOrderStatus());
        payOrderWithdraw.setUpdateTime(date);
        payOrderWithdraw.setOperationExplain(req.getInstructions());
        Boolean withdrawFlag = payOrderWithdrawService.updateById(payOrderWithdraw);
        if (!withdrawFlag) {
            throw new BusinessException("更新提现订单信息失败！");
        }
        MemAccountChangeVO memAccountChangeVO = new MemAccountChangeVO();
        if (payOrderWithdraw.getWithdrawStatus().intValue() == PayConstants.PayOrderStatusEnum.SUCCESS.getCode()) {
            memAccountChangeVO.setChangeType(PayConstants.AccountChangTypeEnum.CHANG_TYPE7.getPayTypeCode());
            memAccountChangeVO.setPrice(payOrderWithdraw.getWithdrawAmt().multiply(new BigDecimal(-1)));
            memAccountChangeVO.setFlowType(PayConstants.FlowTypeEnum.OTHER.getCode());
        } else if (payOrderWithdraw.getWithdrawStatus().intValue() == PayConstants.PayOrderStatusEnum.CANCEL.getCode() || payOrderWithdraw.getWithdrawStatus().intValue() == PayConstants.PayOrderStatusEnum.UN_SUCCESS.getCode()) {
            memAccountChangeVO.setChangeType(PayConstants.AccountChangTypeEnum.CHANG_TYPE4.getPayTypeCode());
            memAccountChangeVO.setPrice(payOrderWithdraw.getWithdrawAmt());
            memAccountChangeVO.setFlowType(PayConstants.FlowTypeEnum.INCOME.getCode());
        }
        memAccountChangeVO.setOpNote(req.getInstructions());
        memAccountChangeVO.setAccount(req.getAccount());
        memAccountChangeVO.setOrderNo(payOrderWithdraw.getWithdrawNo());
        memAccountChangeVO.setDml(BigDecimal.ZERO);
        BalanceChangeDTO givinFlag = accountBalanceChangeService.publicAccountBalanceChange(memAccountChangeVO);
        if (!givinFlag.getFlag()) {
            throw new BusinessException("订单处理失败！");
        }
    }
}
