package com.onelive.pay.model.business;

import com.onelive.common.constants.business.PayConstants;
import com.onelive.common.constants.business.RedisKeys;
import com.onelive.common.enums.StatusCode;
import com.onelive.common.enums.WalletTypeEnum;
import com.onelive.common.exception.BusinessException;
import com.onelive.common.model.dto.login.AppLoginUser;
import com.onelive.common.model.dto.pay.BalanceChangeDTO;
import com.onelive.common.model.req.pay.CancelWithdrawReq;
import com.onelive.common.model.req.pay.WithdrawDetailsByOrderNoReq;
import com.onelive.common.model.req.pay.WithdrawReq;
import com.onelive.common.model.vo.mem.MemAccountChangeVO;
import com.onelive.common.model.vo.pay.PayWithdrawResultVO;
import com.onelive.common.model.vo.pay.PayWithdrawResultsVO;
import com.onelive.common.mybatis.entity.*;
import com.onelive.common.service.accountChange.AccountBalanceChangeService;
import com.onelive.common.service.accountChange.WalletService;
import com.onelive.common.service.sms.SmsSendCommonUtils;
import com.onelive.common.utils.others.DateInnerUtil;
import com.onelive.common.utils.pay.PayUtils;
import com.onelive.pay.service.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
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
import java.util.concurrent.TimeUnit;

/**
 * @author 就不告诉你
 * @version V1.0
 * @ClassName: PayWithdrawBusiness
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @date 创建时间：2021/4/14 10:49
 */
@Component
@Slf4j
@Scope(proxyMode = ScopedProxyMode.TARGET_CLASS)
public class PayWithdrawBusiness {


    @Resource
    private MemBankAccountBusiness memBankAccountBusiness;
    @Resource
    private WalletService walletService;
    @Resource
    private PayOrderWithdrawService payOrderWithdrawService;
    @Resource
    private AccountBalanceChangeService accountBalanceChangeService;
    @Resource
    private RedissonClient redissonClient;
    @Resource
    private SysBusParameterService sysBusParameterService;
    @Resource
    private MemUserService memUserService;
    @Resource
    private SysCountryService sysCountryService;
    @Resource
    private PayExchangeCurrencyService payExchangeCurrencyService;

    @Transactional
    public PayWithdrawResultVO withdraw(WithdrawReq withdrawReq, AppLoginUser user, String source, String requestIp) {
        if (withdrawReq == null) {
            throw new BusinessException("参数错误！");
        }
        if (withdrawReq.getPrice() == null || withdrawReq.getPrice().compareTo(BigDecimal.ZERO) < 1) {
            throw new BusinessException("提现金额错误！");
        }
        if (withdrawReq.getBankAccid() == null) {
            throw new BusinessException("银行卡为空！");
        }
//        if (StringUtils.isBlank(withdrawReq.getPayPassword())) {
//            throw new BusinessException("支付密码为空！");
//        }
        //验证短信验证码是否正确
//        Boolean smsFlag = SmsSendCommonUtils.getVerificationCode(user.getAreaCode() + user.getMobilePhone(), 5,withdrawReq.getSmsCode());
//        if (!smsFlag) {
//            throw new BusinessException(StatusCode.SMS_CODE_ERROR);
//        }

        MemUser memUser= memUserService.getById(user.getId());
//        if(memUser.getPayPassword().equals(DigestUtils.md5Hex(withdrawReq.getPayPassword()).toLowerCase())){
//            throw new BusinessException("支付密码错误！");
//        }
        //验证用户是否具备提现资格
        MemWallet wallet = walletService.getByAccount(memUser.getUserAccount(), WalletTypeEnum.WOODEN_PLATFORM.getCode());
        if (wallet == null) {
            throw new BusinessException("还未初始化平台钱包！");
        }
        if (wallet.getAccountDml().compareTo(BigDecimal.ZERO) > 0) {
            throw new BusinessException("您还未达到提现要求，如有疑问请联系客服！");
        }
        if (wallet.getAmount().compareTo(withdrawReq.getPrice()) < 0) {
            throw new BusinessException("钱包余额不足！");
        }
        //验证此银行卡是否属于该用户下的
        MemBankAccount bankInfo = memBankAccountBusiness.getByAccountAndBankAccid(memUser.getUserAccount(), withdrawReq.getBankAccid());
//        MemBankAccount bankInfo = new MemBankAccount();
        if (bankInfo == null) {
            log.error("此银行卡" + withdrawReq.getBankAccid() + "不属于该用户=" + memUser.getUserAccount() + ",可能是恶意提现！");
            throw new BusinessException("银行卡错误！");
        }
        //查询当前币种汇率
//        //查询国家语言相关信息
//        MemUser memUser = memUserService.getById(user.getId());
        SysCountry sysCountry = sysCountryService.getByCountryCode(memUser.getRegisterCountryCode());
        PayExchangeCurrency exchangeCurrency = payExchangeCurrencyService.selectByCurrencyCode(sysCountry.getLocalCurrency());
        if (exchangeCurrency == null) {
            throw new BusinessException("未获取到当前币种充值的汇率！");
        }
        PayOrderWithdraw orderWithdraw = new PayOrderWithdraw();
        //生成提现单号
        String orderNo = "TX" + PayUtils.getOrderNo();
        orderWithdraw.setWithdrawNo(orderNo);
        Date date = new Date();
        orderWithdraw.setAccount(memUser.getUserAccount());
        orderWithdraw.setWithdrawAmt(withdrawReq.getPrice());
        orderWithdraw.setWithdrawDate(date);
        orderWithdraw.setBankAccid(withdrawReq.getBankAccid());
        orderWithdraw.setWithdrawStatus(PayConstants.PayOrderStatusEnum.PENDING.getCode());
        orderWithdraw.setCreateUser(memUser.getUserAccount());
        orderWithdraw.setUpdateUser(memUser.getUserAccount());
        orderWithdraw.setCreateTime(date);
        orderWithdraw.setUpdateTime(date);
        orderWithdraw.setCurrencyCode(exchangeCurrency.getCurrencyCode());
        orderWithdraw.setTxExchange(exchangeCurrency.getTxExchange());
        //计算实际提现到账的币种金额
        BigDecimal actual_payment = withdrawReq.getPrice().multiply(new BigDecimal(exchangeCurrency.getTxExchange())).setScale(0, BigDecimal.ROUND_DOWN);
        orderWithdraw.setActualPayment(actual_payment);
        Boolean flag = payOrderWithdrawService.save(orderWithdraw);
        if (!flag) {
            throw new BusinessException("提现下单失败！");
        }
        //设置此卡位默认的银行卡
        memBankAccountBusiness.setAccountIsDefaultBank(memUser.getUserAccount(), withdrawReq.getBankAccid());
        MemAccountChangeVO changeVO = new MemAccountChangeVO();
        changeVO.setChangeType(PayConstants.AccountChangTypeEnum.CHANG_TYPE3.getPayTypeCode());
        changeVO.setIsSilverBean(PayConstants.GoldTypeEnum.GOLD_TYPE_2.getCode());
        changeVO.setAccount(memUser.getUserAccount());
        changeVO.setOpNote("提现申请");
        changeVO.setOrderNo(orderNo);
        changeVO.setPrice(withdrawReq.getPrice().multiply(new BigDecimal(-1)));
        changeVO.setDml(BigDecimal.ZERO);
        changeVO.setFlowType(PayConstants.FlowTypeEnum.DISBURSE.getCode());
        BalanceChangeDTO givinFlag = accountBalanceChangeService.publicAccountBalanceChange(changeVO);
        if (!givinFlag.getFlag()) {
            throw new BusinessException("公共账变失败！");
        }
        PayWithdrawResultVO payWithdrawResultVO = new PayWithdrawResultVO();
        payWithdrawResultVO.setWithdrawOrderNo(orderNo);
        return payWithdrawResultVO;
    }

    @Transactional
    public void cancelWithdraw(CancelWithdrawReq cancelWithdrawReq, AppLoginUser user) {
        if (cancelWithdrawReq == null) {
            throw new BusinessException("参数错误！");
        }
        if (StringUtils.isBlank(cancelWithdrawReq.getWithdrawOrderNo())) {
            throw new BusinessException("提现订单号为空！");
        }
        MemUser memUser= memUserService.getById(user.getId());
        //验证用户是否具备提现资格
        MemWallet wallet = walletService.getByAccount(memUser.getUserAccount(), WalletTypeEnum.WOODEN_PLATFORM.getCode());
        if (wallet == null) {
            throw new BusinessException("还未初始化平台钱包！");
        }

        //每次账变前先对该用户加上账变写锁
        RReadWriteLock lock = redissonClient.getReadWriteLock(RedisKeys.PAY_CANCEL_WITHDRAW_BALANCE + memUser.getUserAccount());
        try {
            // 写锁（等待时间20s，超时时间10S[自动解锁]，单位：秒）【设定超时时间，超时后自动释放锁，防止死锁】
            boolean bool = lock.writeLock().tryLock(20, 10, TimeUnit.SECONDS);
            //是否拿到用户账变写锁
            if (bool) {
                //判断订单为非 申请提现状态，则不能取消订单，
                PayOrderWithdraw payOrderWithdraw = payOrderWithdrawService.getByAccountAndOrderNo(memUser.getUserAccount(), cancelWithdrawReq.getWithdrawOrderNo());
                if (payOrderWithdraw == null) {
                    throw new BusinessException("取消提现订单号：" + cancelWithdrawReq.getWithdrawOrderNo() + "未查询到对应的订单！");
                }
                if(payOrderWithdraw.getWithdrawStatus() != PayConstants.PayOrderStatusEnum.PENDING.getCode()){
                    throw new BusinessException(StatusCode.WITHDRAW_CANCEL_ERROR.getCode(),"提现订单号:"+cancelWithdrawReq.getWithdrawOrderNo()+",正在处理中、或已处理完成，如需继续取消联系客服");
                }
                //更新提现订单状态
                payOrderWithdraw.setWithdrawStatus(PayConstants.PayOrderStatusEnum.CANCEL.getCode());
                payOrderWithdraw.setUpdateTime(new Date());
                payOrderWithdraw.setUpdateUser(memUser.getUserAccount());
                payOrderWithdraw.setOperationExplain(cancelWithdrawReq.getCancelExplain());
                payOrderWithdrawService.updateById(payOrderWithdraw);
                //账变
                MemAccountChangeVO changeVO = new MemAccountChangeVO();
                changeVO.setChangeType(PayConstants.AccountChangTypeEnum.CHANG_TYPE4.getPayTypeCode());
                changeVO.setAccount(memUser.getUserAccount());
                changeVO.setOpNote("取消提现");
                changeVO.setOrderNo(payOrderWithdraw.getWithdrawNo());
                changeVO.setPrice(payOrderWithdraw.getWithdrawAmt());
                changeVO.setDml(BigDecimal.ZERO);
                changeVO.setFlowType(PayConstants.FlowTypeEnum.DISBURSE.getCode());
                BalanceChangeDTO withdrawFlag = accountBalanceChangeService.publicAccountBalanceChange(changeVO);
                if (!withdrawFlag.getFlag()) {
                    throw new BusinessException("公共账变失败！");
                }
            } else {
                throw new BusinessException("请求过于频繁，请稍后再试！");
            }
        } catch (Exception e) {
            log.error("{} cancelWithdraw occur error. change info:{}", memUser.getUserAccount(), cancelWithdrawReq, e);
            throw new BusinessException("用户账变错误！", e.getCause());
        } finally {
            // 释放锁
            lock.writeLock().unlock();
            log.info("{} cancelWithdraw 用户取消提现释放锁", user.getUserAccount());
        }

    }

    public PayWithdrawResultsVO getWithdrawDetailsByOrderNo(AppLoginUser user, WithdrawDetailsByOrderNoReq req) {
        if (StringUtils.isBlank(req.getOrderNo())) {
            throw new BusinessException("提现订单号为空！");
        }
        PayWithdrawResultsVO payWithdrawResultsVO = payOrderWithdrawService.getWithdrawDetailsByOrderNo(req.getOrderNo(), user.getUserAccount());
        if (payWithdrawResultsVO == null) {
            return null;
        }
        if (StringUtils.isNotBlank(payWithdrawResultsVO.getBankTail())) {
            String bankNo = payWithdrawResultsVO.getBankTail();
            String bankTail = bankNo.substring(bankNo.length() - 4);
            payWithdrawResultsVO.setBankTail(bankTail);
        }
        SysBusParameter parameter = sysBusParameterService.getByCode(PayConstants.TX_ACCOUNTING_TIME);
        if (parameter == null) {
            log.error("预计提现到账间隔时间---业务系统参数：param_code={}未初始化", PayConstants.TX_ACCOUNTING_TIME);
            throw new BusinessException("系统错误，请联系管理员！");
        }
        payWithdrawResultsVO.setEndTime(DateInnerUtil.offsetMinute(payWithdrawResultsVO.getEndTime(), Integer.valueOf(parameter.getParamValue())));
        return payWithdrawResultsVO;
    }

    public PayOrderWithdraw getOrderStatus(String account, Integer code) {
        if (StringUtils.isBlank(account)) {
            throw new BusinessException("用户账号为空！");
        }
        if (code == null) {
            throw new BusinessException("提现订单状态为空！");
        }

        return payOrderWithdrawService.getOrderStatus(account, code);
    }
}
