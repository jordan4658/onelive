package com.onelive.common.service.accountChange.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.onelive.common.constants.business.PayConstants;
import com.onelive.common.constants.business.RedisKeys;
import com.onelive.common.enums.StatusCode;
import com.onelive.common.enums.WalletTypeEnum;
import com.onelive.common.exception.BusinessException;
import com.onelive.common.model.dto.pay.BalanceChangeDTO;
import com.onelive.common.model.dto.pay.CommonBalanceChangeDTO;
import com.onelive.common.model.vo.mem.MemAccountChangeVO;
import com.onelive.common.mybatis.entity.MemGoldchange;
import com.onelive.common.mybatis.entity.MemUser;
import com.onelive.common.mybatis.entity.MemWallet;
import com.onelive.common.service.accountChange.AccountBalanceChangeService;
import com.onelive.common.service.accountChange.GoldChangeService;
import com.onelive.common.service.accountChange.MemInfoService;
import com.onelive.common.service.accountChange.WalletService;
import com.onelive.common.utils.Login.LoginInfoUtil;
import com.onelive.common.utils.pay.PayUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.redisson.api.RReadWriteLock;
import org.redisson.api.RedissonClient;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.swing.plaf.basic.BasicIconFactory;
import java.math.BigDecimal;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * @author 就不告诉你
 * @version V1.0
 * @ClassName: AccountBalanceChangeBusiness
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @date 创建时间：2021/4/27 14:37
 */
@Slf4j
@Scope(proxyMode = ScopedProxyMode.TARGET_CLASS)
@Service
public class AccountBalanceChangeImpl implements AccountBalanceChangeService {
    @Resource
    private RedissonClient redissonClient;
    @Resource
    private MemInfoService memInfoService;
    @Resource
    private GoldChangeService goldChangeService;
    @Resource
    private WalletService walletService;

    /**
     * 更新用户余额
     *
     * @param changeVO
     * @return
     */

    @Override
    @Transactional
    public BalanceChangeDTO publicAccountBalanceChange(MemAccountChangeVO changeVO) {
        if (changeVO == null || changeVO.getIsSilverBean() == null) {
            throw new BusinessException("参数错误：！" + JSONObject.toJSONString(changeVO));
        }
        BalanceChangeDTO changeDTO = new BalanceChangeDTO();
        switch (changeVO.getIsSilverBean()) {
            case 1://银豆账变
                changeDTO = silverBeanBalanceChange(changeVO);
                break;
            case 2://金币账变
                changeDTO = goldBeanBalanceChange(changeVO);
                break;
            case 3: //金币兑换银豆
                changeDTO = goldBeanAndSilverBeanBalanceChange(changeVO);
                break;
            case 4: //用户钱包划转
                changeDTO = transferOutOrInWallet(changeVO);
                break;
            case 5: //第三方游戏注单账变
                changeDTO = thirdBettingBalanceChange(changeVO);
                break;
            default:
                throw new BusinessException("账变金额类型错误！");
        }
        return changeDTO;
    }


    public CommonBalanceChangeDTO commonBalanceChangeHandel(MemAccountChangeVO changeVO, MemWallet memWallet) {

        CommonBalanceChangeDTO changeDTO = new CommonBalanceChangeDTO();
        //账变金币金额
        BigDecimal change_amount = changeVO.getPrice();
        changeDTO.setChange_amount(change_amount);
        //账变银豆金额
        BigDecimal change_silver_amount = changeVO.getSilverBeanPrice();
        changeDTO.setChange_silver_amount(change_silver_amount);
        /** ------------------------------------------------------------------------*/
        //账变前金币余额
        BigDecimal pre_amount = memWallet.getAmount();
        changeDTO.setPre_amount(pre_amount);
        //账变前银豆余额
        BigDecimal pre_silver_amount = memWallet.getSilverBean();
        changeDTO.setPre_silver_amount(pre_silver_amount);
        /** ------------------------------------------------------------------------*/
        //账变后银豆余额
        BigDecimal later_silver_amount = pre_silver_amount.add(change_silver_amount);
        changeDTO.setLater_silver_amount(later_silver_amount);
        //账变后金币余额
        BigDecimal later_amount = pre_amount.add(change_amount);
        changeDTO.setLater_amount(later_amount);
        /** ------------------------------------------------------------------------*/
        //账变打码量
        BigDecimal change_dml = changeVO.getDml();
        changeDTO.setChange_dml(change_dml);
        //账变前打码量
        BigDecimal pre_dml = memWallet.getAccountDml();
        changeDTO.setPre_dml(pre_dml);
        //账变后打码量
        BigDecimal later_dml = pre_dml.add(change_dml).compareTo(BigDecimal.ZERO) < 0 ? new BigDecimal("0.0000") : pre_dml.add(change_dml);
        changeDTO.setLater_dml(later_dml);
        /** ------------------------------------------------------------------------*/
        //累计打码量
        BigDecimal sum_dml = change_dml.compareTo(BigDecimal.ZERO) >= 0 ? memWallet.getSumAccountDml() : memWallet.getSumAccountDml().add(change_dml.abs());
        changeDTO.setSum_dml(sum_dml);
        //累计充值金额
        BigDecimal sum_recharge_amount = memWallet.getSumRechargeAmount();
        changeDTO.setSum_recharge_amount(sum_recharge_amount);
        //累计提现金额
        BigDecimal sum_withdraw_amount = memWallet.getSumWithdrawAmount();
        changeDTO.setSum_withdraw_amount(sum_withdraw_amount);
        //当前 最大充值金额
        BigDecimal payMax = memWallet.getPayMax();
        changeDTO.setPayMax(payMax);
        //当前 首次充值金额
        BigDecimal payFirst = memWallet.getPayFirst();
        changeDTO.setPayFirst(payFirst);
        //当前 充值总次数
        Integer pay_num = memWallet.getPayNum();
        changeDTO.setPay_num(pay_num);
        //当前 最大提现金额
        BigDecimal withdrawal_max = memWallet.getWithdrawalMax();
        changeDTO.setWithdrawal_max(withdrawal_max);
        //当前 首次提现金额
        BigDecimal withdrawal_first = memWallet.getWithdrawalFirst();
        changeDTO.setWithdrawal_first(withdrawal_first);
        //当前 提现总次数
        Integer withdrawal_num = memWallet.getWithdrawalNum();
        changeDTO.setWithdrawal_num(withdrawal_num);

        return changeDTO;
    }

    @Transactional
    public BalanceChangeDTO thirdBettingBalanceChange(MemAccountChangeVO changeVO) {
        if (StringUtils.isBlank(changeVO.getAccount()) ||
                changeVO.getPrice() == null ||
                changeVO.getDml() == null ||
                changeVO.getChangeType() == null ||
                changeVO.getFlowType() == null) {
            throw new BusinessException("参数错误：！" + JSONObject.toJSONString(changeVO));
        }
        String account = changeVO.getAccount();
        //每次账变前先对该用户加上账变写锁
        RReadWriteLock lock = redissonClient.getReadWriteLock(RedisKeys.PAY_UPDATE_BALANCE + account);
        try {
            // 写锁（等待时间20s，超时时间10S[自动解锁]，单位：秒）【设定超时时间，超时后自动释放锁，防止死锁】
            boolean bool = lock.writeLock().tryLock(11, 10, TimeUnit.SECONDS);
            //是否拿到用户账变写锁
            if (bool) {
                log.info("账变start: " + JSONObject.toJSONString(changeVO));
                //判断用户是否存在
                MemUser memUser = memInfoService.queryByAccount(changeVO.getAccount());
                if (memUser == null) {
                    throw new BusinessException("此账号" + changeVO.getAccount() + "，对应用户不存在！");
                }
                //查询用户钱包信息
                MemWallet memWallet = walletService.getByAccount(changeVO.getAccount(), WalletTypeEnum.WOODEN_PLATFORM.getCode());
                if (memWallet == null) {
                    throw new BusinessException("用户还未初始化钱包信息！");
                }
                log.info("用户修改打码量拿到锁{}", account);

                Date date = new Date();
                //新增用户账变记录
                MemGoldchange memGoldChange = new MemGoldchange();
                String changeOrderNo = "ZB" + PayUtils.getOrderNo();
                //新增用户账变记录
                //账变前-钱包
                memGoldChange.setGoldNum(memWallet.getAmount());
                memGoldChange.setSilverNum(memWallet.getSilverBean());
                //账变后-钱包
                memGoldChange.setRecgoldNum(memWallet.getAmount());
                memGoldChange.setRecSilverNum(memWallet.getSilverBean());
                //账变金额
                memGoldChange.setAmount(changeVO.getPrice());
                memGoldChange.setSilverAmount(changeVO.getSilverBeanPrice());
                //账变打码量
                memGoldChange.setPreCgdml(memWallet.getAccountDml());
                BigDecimal userDml = memWallet.getAccountDml().subtract(changeVO.getDml()).compareTo(BigDecimal.ZERO) <= 0 ? BigDecimal.ZERO : memWallet.getAccountDml().subtract(changeVO.getDml());
                memGoldChange.setAfterCgdml(userDml);
                //其他
                memGoldChange.setGoldChangeOrderNo(changeOrderNo);
                memGoldChange.setAccount(changeVO.getAccount());
                memGoldChange.setGoldType(PayConstants.GoldTypeEnum.GOLD_TYPE_2.getCode());
                memGoldChange.setOpnote(changeVO.getOpNote());
                memGoldChange.setRefNo(changeVO.getOrderNo());
                memGoldChange.setChangWalletType(memWallet.getWalletType());
                memGoldChange.setChangeType(changeVO.getChangeType());
                memGoldChange.setCreateTime(date);
                memGoldChange.setCreateUser(PayConstants.SYS);
                memGoldChange.setFlowType(changeVO.getFlowType());
                memGoldChange.setThirdType(changeVO.getThirdType());
                Integer changeNum = goldChangeService.insetGoldChange(memGoldChange);
                if (changeNum <= 0) {
                    throw new BusinessException("公共账变-新增用户账变明细信息失败！");
                }
                //修改用户钱包信息
                UpdateWrapper<MemWallet> memWrapper = new UpdateWrapper();
                memWrapper.lambda().set(MemWallet::getUpdateTime, date);
                memWrapper.lambda().set(MemWallet::getAccountDml, userDml);
                memWrapper.lambda().set(MemWallet::getSumAccountDml, memWallet.getSumAccountDml().add(changeVO.getDml()));
                memWrapper.lambda().set(MemWallet::getUpdateUser, changeVO.getAccount());
                memWrapper.lambda().set(MemWallet::getUpdateTime, date);
                memWrapper.lambda().eq(MemWallet::getWalletId, memWallet.getWalletId());
                boolean walletFlag = walletService.update(memWrapper);
                if (!walletFlag) {
                    throw new BusinessException("公共账变-更新用户钱包信息失败！");
                }
                BalanceChangeDTO changeDTO = new BalanceChangeDTO();
                changeDTO.setFlag(true);
                changeDTO.setChangeOrderNo(changeOrderNo);
                return changeDTO;
            } else {
                log.error("{} publicAccountChange 用户修改打码量没拿到锁, 记录修改打码量变动对象 {}", account, changeVO);
                throw new BusinessException("请求过于频繁，请稍后再试！");
            }
        } catch (Exception e) {
            log.error("{} publicAccountChange occur error. change info:{}", account, changeVO, e);
            throw new BusinessException(e.getMessage(), e.getCause());
        } finally {
            // 释放锁
            lock.writeLock().unlock();
            log.info("{} publicAccountChange 用户修改打码量释放锁", account);
        }
    }

    @Transactional
    public BalanceChangeDTO transferOutOrInWallet(MemAccountChangeVO changeVO) {
        if (StringUtils.isBlank(changeVO.getAccount()) ||
                changeVO.getPrice() == null ||
                changeVO.getTransferInWalletId() == null ||
                changeVO.getTransferOutWalletId() == null ||
                changeVO.getChangeType() == null ||
                changeVO.getFlowType() == null) {
            throw new BusinessException("参数错误：！" + JSONObject.toJSONString(changeVO));
        }
        String account = changeVO.getAccount();
        //每次账变前先对该用户加上账变写锁
        RReadWriteLock lock = redissonClient.getReadWriteLock(RedisKeys.PAY_UPDATE_BALANCE + account);
        try {
            // 写锁（等待时间20s，超时时间10S[自动解锁]，单位：秒）【设定超时时间，超时后自动释放锁，防止死锁】
            boolean bool = lock.writeLock().tryLock(11, 10, TimeUnit.SECONDS);
            //是否拿到用户账变写锁
            if (bool) {
                log.info("账变start: " + JSONObject.toJSONString(changeVO));
                //判断用户是否存在
                MemUser memUser = memInfoService.queryByAccount(changeVO.getAccount());
                if (memUser == null) {
                    throw new BusinessException("此账号" + changeVO.getAccount() + "，对应用户不存在！");
                }
                //用户转入的钱包信息
                MemWallet inWallet = walletService.getByWalletId(changeVO.getTransferInWalletId());
                //用户转出的钱包信息
                MemWallet outWallet = walletService.getByWalletId(changeVO.getTransferOutWalletId());
                if (inWallet == null || outWallet == null) {
                    throw new BusinessException("用户还未初始化钱包信息！");
                }
                if (outWallet.getAmount().add(changeVO.getPrice()).compareTo(BigDecimal.ZERO) < 0) {
                    //钱包余额不足
                    throw new BusinessException(StatusCode.CHANGE_BALANCE_LACK);
                }
                log.info("用户修改余额拿到锁{}", account);

//*********************************扣钱钱包账变****start**********************************************************
                Date date = new Date();
                //新增用户账变记录
                MemGoldchange memGoldChange = new MemGoldchange();
                String changeOrderNo = "ZB" + PayUtils.getOrderNo();
                //新增用户账变记录
                //账变前-钱包
                memGoldChange.setGoldNum(outWallet.getAmount());
                memGoldChange.setSilverNum(outWallet.getSilverBean());
                //账变后-钱包
                BigDecimal userAmoutOut = outWallet.getAmount().subtract(changeVO.getPrice()).compareTo(BigDecimal.ZERO) <= 0 ? BigDecimal.ZERO : outWallet.getAmount().subtract(changeVO.getPrice());
                memGoldChange.setRecgoldNum(userAmoutOut);
                memGoldChange.setRecSilverNum(outWallet.getSilverBean());
                //账变金额
                memGoldChange.setAmount(changeVO.getPrice());
                memGoldChange.setSilverAmount(changeVO.getSilverBeanPrice());
                //账变打码量
                memGoldChange.setPreCgdml(outWallet.getAccountDml());
                memGoldChange.setAfterCgdml(outWallet.getAccountDml());
                //其他
                memGoldChange.setGoldChangeOrderNo(changeOrderNo);
                memGoldChange.setAccount(changeVO.getAccount());
                memGoldChange.setGoldType(PayConstants.GoldTypeEnum.GOLD_TYPE_2.getCode());
                memGoldChange.setOpnote(changeVO.getOpNote());
                memGoldChange.setRefNo(changeVO.getOrderNo());
                memGoldChange.setChangWalletType(outWallet.getWalletType());
                memGoldChange.setChangeType(changeVO.getChangeType());
                memGoldChange.setCreateTime(date);
                memGoldChange.setCreateUser(PayConstants.SYS);
                memGoldChange.setFlowType(changeVO.getFlowType());
                memGoldChange.setThirdType(changeVO.getThirdType());
                Integer changeNum = goldChangeService.insetGoldChange(memGoldChange);
                if (changeNum <= 0) {
                    throw new BusinessException("公共账变-新增用户账变明细信息失败！");
                }
                //修改用户钱包信息
                UpdateWrapper<MemWallet> outWrapper = new UpdateWrapper();
                outWrapper.lambda().set(MemWallet::getAmount, userAmoutOut);
                outWrapper.lambda().set(MemWallet::getUpdateTime, date);
                outWrapper.lambda().set(MemWallet::getUpdateUser, changeVO.getAccount());
                outWrapper.lambda().set(MemWallet::getUpdateTime, date);
                outWrapper.lambda().eq(MemWallet::getWalletId, inWallet.getWalletId());
                boolean walletFlag = walletService.update(outWrapper);
                if (!walletFlag) {
                    throw new BusinessException("公共账变-更新用户钱包信息失败！");
                }
//*********************************扣钱钱包账变****end**********************************************************

//*********************************加钱钱包账变****start**********************************************************
                Date date2 = new Date();
                //新增用户账变记录
                MemGoldchange memGoldChange2 = new MemGoldchange();
                String changeOrderNo2 = "ZB" + PayUtils.getOrderNo();
                //新增用户账变记录
                //账变前-钱包
                memGoldChange2.setGoldNum(inWallet.getAmount());
                memGoldChange2.setSilverNum(inWallet.getSilverBean());
                //账变后-钱包
                BigDecimal userAmoutIn = inWallet.getAmount().add(changeVO.getPrice()).compareTo(BigDecimal.ZERO) <= 0 ? BigDecimal.ZERO : inWallet.getAmount().add(changeVO.getPrice());
                memGoldChange2.setRecgoldNum(userAmoutIn);
                memGoldChange2.setRecSilverNum(inWallet.getSilverBean());
                //账变金额
                memGoldChange2.setAmount(changeVO.getPrice());
                memGoldChange2.setSilverAmount(changeVO.getSilverBeanPrice());
                //账变打码量
                memGoldChange2.setPreCgdml(inWallet.getAccountDml());
                memGoldChange2.setAfterCgdml(inWallet.getAccountDml());
                //其他
                memGoldChange2.setGoldChangeOrderNo(changeOrderNo2);
                memGoldChange2.setAccount(changeVO.getAccount());
                memGoldChange2.setGoldType(PayConstants.GoldTypeEnum.GOLD_TYPE_2.getCode());
                memGoldChange2.setOpnote(changeVO.getOpNote());
                memGoldChange2.setRefNo(changeVO.getOrderNo());
                memGoldChange2.setChangWalletType(inWallet.getWalletType());
                memGoldChange2.setChangeType(changeVO.getChangeType());
                memGoldChange2.setCreateTime(date2);
                memGoldChange2.setCreateUser(PayConstants.SYS);
                memGoldChange2.setFlowType(changeVO.getFlowType());
                memGoldChange2.setThirdType(changeVO.getThirdType());
                Integer changeNum2 = goldChangeService.insetGoldChange(memGoldChange2);
                if (changeNum2 <= 0) {
                    throw new BusinessException("公共账变-新增用户账变明细信息失败！");
                }
                //修改用户钱包信息
                UpdateWrapper<MemWallet> inWrapper = new UpdateWrapper();
                inWrapper.lambda().set(MemWallet::getAmount, userAmoutIn);
                inWrapper.lambda().set(MemWallet::getUpdateTime, date);
                inWrapper.lambda().set(MemWallet::getUpdateUser, changeVO.getAccount());
                inWrapper.lambda().set(MemWallet::getUpdateTime, date);
                inWrapper.lambda().eq(MemWallet::getWalletId, outWallet.getWalletId());
                boolean walletFlag2 = walletService.update(inWrapper);
                if (!walletFlag2) {
                    throw new BusinessException("公共账变-更新用户钱包信息失败！");
                }
//*********************************加钱钱包账变****end**********************************************************
                BalanceChangeDTO changeDTO = new BalanceChangeDTO();
                changeDTO.setFlag(true);
                changeDTO.setChangeOrderNo(changeOrderNo2);
                return changeDTO;
            } else {
                log.error("{} publicAccountChange 用户修改余额没拿到锁, 记录修改余额变动对象 {}", account, changeVO);
                throw new BusinessException("请求过于频繁，请稍后再试！");
            }
        } catch (Exception e) {
            log.error("{} publicAccountChange occur error. change info:{}", account, changeVO, e);
            throw new BusinessException(e.getMessage(), e.getCause());
        } finally {
            // 释放锁
            lock.writeLock().unlock();
            log.info("{} publicAccountChange 用户修改余额释放锁", account);
        }
    }


    @Transactional
    public BalanceChangeDTO goldBeanAndSilverBeanBalanceChange(MemAccountChangeVO changeVO) {
        if (StringUtils.isBlank(changeVO.getAccount()) || changeVO.getPrice() == null) {
            throw new BusinessException("参数错误：！" + JSONObject.toJSONString(changeVO));
        }
        String account = changeVO.getAccount();
        //每次账变前先对该用户加上账变写锁
        RReadWriteLock lock = redissonClient.getReadWriteLock(RedisKeys.PAY_UPDATE_SILVERBEAN_BALANCE + account);
        try {
            // 写锁（等待时间20s，超时时间10S[自动解锁]，单位：秒）【设定超时时间，超时后自动释放锁，防止死锁】
            boolean bool = lock.writeLock().tryLock(11, 10, TimeUnit.SECONDS);
            //是否拿到用户账变写锁
            if (bool) {
                log.info("银豆账变start: " + JSONObject.toJSONString(changeVO));
                //判断用户是否存在
                MemUser memUser = memInfoService.queryByAccount(changeVO.getAccount());
                if (memUser == null) {
                    throw new BusinessException("此账号" + changeVO.getAccount() + "，对应用户不存在！");
                }
                //查询用户钱包信息
                MemWallet memWallet = walletService.getByAccount(changeVO.getAccount(), WalletTypeEnum.WOODEN_PLATFORM.getCode());
                if (memWallet == null) {
                    throw new BusinessException("用户还未初始化平台钱包信息！");
                }
                if (memWallet.getAmount().add(changeVO.getPrice()).compareTo(BigDecimal.ZERO) < 0) {
                    //钱包银豆余额不足
                    throw new BusinessException(StatusCode.CHANGE_BALANCE_LACK);
                }
                log.info("用户修改银豆余额拿到锁{}", account);

                CommonBalanceChangeDTO commonBalanceChange = commonBalanceChangeHandel(changeVO, memWallet);

                Date date = new Date();
                //新增用户账变记录
                MemGoldchange memGoldChange = new MemGoldchange();
                String changeOrderNo = "ZB" + PayUtils.getOrderNo();
                //账变前-钱包
                memGoldChange.setGoldNum(commonBalanceChange.getPre_amount());
                memGoldChange.setSilverNum(commonBalanceChange.getPre_silver_amount());
                //账变后-钱包
                memGoldChange.setRecgoldNum(commonBalanceChange.getLater_amount());
                memGoldChange.setRecSilverNum(commonBalanceChange.getLater_silver_amount());
                //账变金额
                memGoldChange.setAmount(commonBalanceChange.getChange_amount());
                memGoldChange.setSilverAmount(commonBalanceChange.getChange_silver_amount());
                //账变打码量
                memGoldChange.setPreCgdml(commonBalanceChange.getPre_dml());
                memGoldChange.setAfterCgdml(commonBalanceChange.getPre_dml());
                //其他
                memGoldChange.setGoldChangeOrderNo(changeOrderNo);
                memGoldChange.setAccount(changeVO.getAccount());
                memGoldChange.setGoldType(PayConstants.GoldTypeEnum.GOLD_TYPE_3.getCode());
                memGoldChange.setOpnote(changeVO.getOpNote());
                memGoldChange.setRefNo(changeVO.getOrderNo());
                memGoldChange.setChangWalletType(WalletTypeEnum.WOODEN_PLATFORM.getCode());
                memGoldChange.setChangeType(changeVO.getChangeType());
                memGoldChange.setCreateTime(date);
                memGoldChange.setCreateUser(PayConstants.SYS);
                memGoldChange.setFlowType(changeVO.getFlowType());
                memGoldChange.setThirdType(changeVO.getThirdType());

                Integer changeNum1 = goldChangeService.insetGoldChange(memGoldChange);
                if (changeNum1 <= 0) {
                    throw new BusinessException("公共账变-新增用户账变明细信息失败！");
                }
                //修改用户钱包信息
                memWallet.setAmount(commonBalanceChange.getLater_amount());
                memWallet.setSilverBean(commonBalanceChange.getLater_silver_amount());
                memWallet.setAccountDml(commonBalanceChange.getLater_dml());
                memWallet.setSumAccountDml(commonBalanceChange.getSum_dml());
                memWallet.setSumWithdrawAmount(commonBalanceChange.getSum_withdraw_amount());
                memWallet.setSumRechargeAmount(commonBalanceChange.getSum_recharge_amount());
                memWallet.setPayFirst(commonBalanceChange.getPayFirst());
                memWallet.setPayNum(commonBalanceChange.getPay_num());
                memWallet.setPayMax(commonBalanceChange.getPayMax());
                memWallet.setWithdrawalFirst(commonBalanceChange.getWithdrawal_first());
                memWallet.setWithdrawalMax(commonBalanceChange.getWithdrawal_max());
                memWallet.setWithdrawalNum(commonBalanceChange.getWithdrawal_num());
                memWallet.setUpdateTime(date);
                boolean walletFlag = walletService.updateById(memWallet);
                if (!walletFlag) {
                    throw new BusinessException("公共账变-更新用户钱包信息失败！");
                }
                BalanceChangeDTO changeDTO = new BalanceChangeDTO();
                changeDTO.setFlag(true);
                changeDTO.setChangeOrderNo(changeOrderNo);
                return changeDTO;
            } else {
                log.error("{} publicAccountChange 用户修改银豆余额没拿到锁, 记录修改银豆余额变动对象 {}", account, changeVO);
                throw new BusinessException("请求过于频繁，请稍后再试！");
            }
        } catch (Exception e) {
            log.error("{} publicAccountChange occur error. change info:{}", account, changeVO, e);
            throw new BusinessException(e.getMessage(), e.getCause());
        } finally {
            // 释放锁
            lock.writeLock().unlock();
            log.info("{} publicAccountChange 用户修改银豆余额释放锁", account);
        }
    }

    @Transactional
    public BalanceChangeDTO silverBeanBalanceChange(MemAccountChangeVO changeVO) {
        if (StringUtils.isBlank(changeVO.getAccount()) || changeVO.getPrice() == null) {
            throw new BusinessException("参数错误：！" + JSONObject.toJSONString(changeVO));
        }
        String account = changeVO.getAccount();
        //每次账变前先对该用户加上账变写锁
        RReadWriteLock lock = redissonClient.getReadWriteLock(RedisKeys.PAY_UPDATE_SILVERBEAN_BALANCE + account);
        try {
            // 写锁（等待时间20s，超时时间10S[自动解锁]，单位：秒）【设定超时时间，超时后自动释放锁，防止死锁】
            boolean bool = lock.writeLock().tryLock(11, 10, TimeUnit.SECONDS);
            //是否拿到用户账变写锁
            if (bool) {
                log.info("银豆账变start: " + JSONObject.toJSONString(changeVO));
                //判断用户是否存在
                MemUser memUser = memInfoService.queryByAccount(changeVO.getAccount());
                if (memUser == null) {
                    throw new BusinessException("此账号" + changeVO.getAccount() + "，对应用户不存在！");
                }
                //查询用户钱包信息
                MemWallet memWallet = walletService.getByAccount(changeVO.getAccount(), WalletTypeEnum.WOODEN_PLATFORM.getCode());
                if (memWallet == null) {
                    throw new BusinessException("用户还未初始化平台钱包信息！");
                }
                if (memWallet.getSilverBean().add(changeVO.getSilverBeanPrice()).compareTo(BigDecimal.ZERO) < 0) {
                    //钱包银豆余额不足
                    throw new BusinessException(StatusCode.CHANGE_BALANCE_LACK);
                }
                log.info("用户修改银豆余额拿到锁{}", account);
                CommonBalanceChangeDTO commonBalanceChange = commonBalanceChangeHandel(changeVO, memWallet);

                Date date = new Date();
                //新增用户账变记录
                MemGoldchange memGoldChange = new MemGoldchange();
                String changeOrderNo = "ZB" + PayUtils.getOrderNo();
                //账变前-钱包
                memGoldChange.setGoldNum(commonBalanceChange.getPre_amount());
                memGoldChange.setSilverNum(commonBalanceChange.getPre_silver_amount());
                //账变后-钱包
                memGoldChange.setRecgoldNum(commonBalanceChange.getLater_amount());
                memGoldChange.setRecSilverNum(commonBalanceChange.getLater_silver_amount());
                //账变金额
                memGoldChange.setAmount(commonBalanceChange.getChange_amount());
                memGoldChange.setSilverAmount(commonBalanceChange.getChange_silver_amount());
                //账变打码量
                memGoldChange.setPreCgdml(commonBalanceChange.getPre_dml());
                memGoldChange.setAfterCgdml(commonBalanceChange.getPre_dml());
                //其他
                memGoldChange.setGoldChangeOrderNo(changeOrderNo);
                memGoldChange.setAccount(changeVO.getAccount());
                memGoldChange.setGoldType(PayConstants.GoldTypeEnum.GOLD_TYPE_1.getCode());
                memGoldChange.setOpnote(changeVO.getOpNote());
                memGoldChange.setRefNo(changeVO.getOrderNo());
                memGoldChange.setChangWalletType(WalletTypeEnum.WOODEN_PLATFORM.getCode());
                memGoldChange.setChangeType(changeVO.getChangeType());
                memGoldChange.setCreateTime(date);
                memGoldChange.setCreateUser(PayConstants.SYS);
                memGoldChange.setFlowType(changeVO.getFlowType());
                memGoldChange.setThirdType(changeVO.getThirdType());

                Integer changeNum1 = goldChangeService.insetGoldChange(memGoldChange);
                if (changeNum1 <= 0) {
                    throw new BusinessException("公共账变-新增用户账变明细信息失败！");
                }
                //修改用户钱包信息
                memWallet.setAmount(commonBalanceChange.getLater_amount());
                memWallet.setSilverBean(commonBalanceChange.getLater_silver_amount());
                memWallet.setAccountDml(commonBalanceChange.getLater_dml());
                memWallet.setSumAccountDml(commonBalanceChange.getSum_dml());
                memWallet.setSumWithdrawAmount(commonBalanceChange.getSum_withdraw_amount());
                memWallet.setSumRechargeAmount(commonBalanceChange.getSum_recharge_amount());
                memWallet.setPayFirst(commonBalanceChange.getPayFirst());
                memWallet.setPayNum(commonBalanceChange.getPay_num());
                memWallet.setPayMax(commonBalanceChange.getPayMax());
                memWallet.setWithdrawalFirst(commonBalanceChange.getWithdrawal_first());
                memWallet.setWithdrawalMax(commonBalanceChange.getWithdrawal_max());
                memWallet.setWithdrawalNum(commonBalanceChange.getWithdrawal_num());
                memWallet.setUpdateTime(date);
                boolean walletFlag = walletService.updateById(memWallet);
                if (!walletFlag) {
                    throw new BusinessException("公共账变-更新用户钱包信息失败！");
                }
                BalanceChangeDTO changeDTO = new BalanceChangeDTO();
                changeDTO.setFlag(true);
                changeDTO.setChangeOrderNo(changeOrderNo);
                return changeDTO;
            } else {
                log.error("{} publicAccountChange 用户修改银豆余额没拿到锁, 记录修改银豆余额变动对象 {}", account, changeVO);
                throw new BusinessException("请求过于频繁，请稍后再试！");
            }
        } catch (Exception e) {
            log.error("{} publicAccountChange occur error. change info:{}", account, changeVO, e);
            throw new BusinessException(e.getMessage(), e.getCause());
        } finally {
            // 释放锁
            lock.writeLock().unlock();
            log.info("{} publicAccountChange 用户修改银豆余额释放锁", account);
        }
    }

    @Transactional
    public BalanceChangeDTO goldBeanBalanceChange(MemAccountChangeVO changeVO) {
        if (StringUtils.isBlank(changeVO.getAccount()) ||
                changeVO.getPrice() == null ||
                changeVO.getChangeType() == null ||
                changeVO.getDml() == null ||
                changeVO.getFlowType() == null) {
            throw new BusinessException("参数错误：！" + JSONObject.toJSONString(changeVO));
        }
        String account = changeVO.getAccount();
        //每次账变前先对该用户加上账变写锁
        RReadWriteLock lock = redissonClient.getReadWriteLock(RedisKeys.PAY_UPDATE_BALANCE + account);
        try {
            // 写锁（等待时间20s，超时时间10S[自动解锁]，单位：秒）【设定超时时间，超时后自动释放锁，防止死锁】
            boolean bool = lock.writeLock().tryLock(11, 10, TimeUnit.SECONDS);
            //是否拿到用户账变写锁
            if (bool) {
                log.info("账变start: " + JSONObject.toJSONString(changeVO));
                //判断用户是否存在
                MemUser memUser = memInfoService.queryByAccount(changeVO.getAccount());
                if (memUser == null) {
                    throw new BusinessException("此账号" + changeVO.getAccount() + "，对应用户不存在！");
                }
                //查询用户钱包信息
                MemWallet memWallet = walletService.getByAccount(changeVO.getAccount(), WalletTypeEnum.WOODEN_PLATFORM.getCode());
                if (memWallet == null) {
                    throw new BusinessException("用户还未初始化平台钱包信息！");
                }
                if (memWallet.getAmount().add(changeVO.getPrice()).compareTo(BigDecimal.ZERO) < 0) {
                    //钱包余额不足
                    throw new BusinessException(StatusCode.CHANGE_BALANCE_LACK);
                }
                log.info("用户修改余额拿到锁{}", account);
                //账变金币金额
                BigDecimal change_amount = changeVO.getPrice();
                //账变打码量
                BigDecimal change_dml = changeVO.getDml();
                //账变前金币余额
                BigDecimal pre_amount = memWallet.getAmount();
                //账变前银豆余额
                BigDecimal pre_silver_amount = memWallet.getSilverBean();
                //账变银豆金额
                BigDecimal change_silver_amount = changeVO.getSilverBeanPrice();
                //账变后银豆余额
                BigDecimal later_silver_amount = pre_silver_amount.add(change_silver_amount);
                //账变前打码量
                BigDecimal pre_dml = memWallet.getAccountDml();
                //账变后金币余额
                BigDecimal later_amount = pre_amount.add(change_amount);
                //账变后打码量
                BigDecimal later_dml = pre_dml.add(change_dml).compareTo(BigDecimal.ZERO) < 0 ? new BigDecimal("0.0000") : pre_dml.add(change_dml);
                //累计打码量
                BigDecimal sum_dml = change_dml.compareTo(BigDecimal.ZERO) >= 0 ? memWallet.getSumAccountDml() : memWallet.getSumAccountDml().add(change_dml.abs());
                //累计充值金额
                BigDecimal sum_recharge_amount = memWallet.getSumRechargeAmount();
                //累计提现金额
                BigDecimal sum_withdraw_amount = memWallet.getSumWithdrawAmount();
                //当前 最大充值金额
                BigDecimal payMax = memWallet.getPayMax();
                //当前 首次充值金额
                BigDecimal payFirst = memWallet.getPayFirst();
                //当前 充值总次数
                Integer pay_num = memWallet.getPayNum();
                //当前 最大提现金额
                BigDecimal withdrawal_max = memWallet.getWithdrawalMax();
                //当前 首次提现金额
                BigDecimal withdrawal_first = memWallet.getWithdrawalFirst();
                //当前 提现总次数
                Integer withdrawal_num = memWallet.getWithdrawalNum();
                /**
                 * 加余额、加打码量： 1-充值、2-签到奖励、9-活动奖励、
                 * 只做减打码量的：  12-手动减码
                 * 只做加打码量的：  13-手动加码
                 * 加余额、可能加打码量： 11-手动充值
                 * 加余额、减打码量： 22-彩票开奖
                 * 只做减余额的：    3-提现申请、10-手动提现、7-提现成功、20-彩票下注
                 * 只做加余额的：    4-提现取消、 23-用户彩票撤单、24-管理员彩票撤单
                 */
                if (PayConstants.AccountChangTypeEnum.CHANG_TYPE1.getPayTypeCode() == changeVO.getChangeType() ||
                        PayConstants.AccountChangTypeEnum.CHANG_TYPE11.getPayTypeCode() == changeVO.getChangeType()) {
                    /** 充值处理 */
                    if (change_amount.compareTo(payMax) == 1) {
                        payMax = change_amount;
                    }
                    if (memWallet.getSumRechargeAmount().compareTo(BigDecimal.ZERO) <= 0) {
                        payFirst = change_amount;
                    }
                    pay_num++;
                    sum_recharge_amount = memWallet.getSumRechargeAmount().add(change_amount);
                } else if (PayConstants.AccountChangTypeEnum.CHANG_TYPE7.getPayTypeCode().intValue() == changeVO.getChangeType().intValue()) {
                    //提现处理
                    if (memWallet.getSumWithdrawAmount().compareTo(BigDecimal.ZERO) <= 0) {
                        withdrawal_first = change_amount.abs();
                    }
                    if (change_amount.abs().compareTo(withdrawal_max) == 1) {
                        withdrawal_max = change_amount.abs();
                    }
                    later_amount = pre_amount;
                    withdrawal_num++;
                    sum_withdraw_amount = sum_withdraw_amount.add(change_amount.abs());
                } else if (PayConstants.AccountChangTypeEnum.CHANG_TYPE10.getPayTypeCode() == changeVO.getChangeType()) {
                    if (memWallet.getSumWithdrawAmount().compareTo(BigDecimal.ZERO) <= 0) {
                        withdrawal_first = change_amount.abs();
                    }
                    if (change_amount.abs().compareTo(withdrawal_max) == 1) {
                        withdrawal_max = change_amount.abs();
                    }
                    withdrawal_num++;
                    sum_withdraw_amount = sum_withdraw_amount.add(change_amount.abs());
                }


                Date date = new Date();
                //新增用户账变记录
                MemGoldchange memGoldChange = new MemGoldchange();
                String changeOrderNo = "ZB" + PayUtils.getOrderNo();
                //新增用户账变记录
                //账变前-钱包
                memGoldChange.setGoldNum(pre_amount);
                memGoldChange.setSilverNum(pre_silver_amount);
                //账变后-钱包
                memGoldChange.setRecgoldNum(later_amount);
                memGoldChange.setRecSilverNum(later_silver_amount);
                //账变金额
                memGoldChange.setAmount(change_amount);
                memGoldChange.setSilverAmount(change_silver_amount);
                //账变打码量
                memGoldChange.setPreCgdml(pre_dml);
                memGoldChange.setAfterCgdml(later_dml);
                //其他
                memGoldChange.setGoldChangeOrderNo(changeOrderNo);
                memGoldChange.setAccount(changeVO.getAccount());
                memGoldChange.setGoldType(PayConstants.GoldTypeEnum.GOLD_TYPE_2.getCode());
                memGoldChange.setOpnote(changeVO.getOpNote());
                memGoldChange.setRefNo(changeVO.getOrderNo());
                memGoldChange.setChangWalletType(WalletTypeEnum.WOODEN_PLATFORM.getCode());
                memGoldChange.setChangeType(changeVO.getChangeType());
                memGoldChange.setCreateTime(date);
                memGoldChange.setCreateUser(PayConstants.SYS);
                memGoldChange.setFlowType(changeVO.getFlowType());
                memGoldChange.setThirdType(changeVO.getThirdType());
                Integer changeNum = goldChangeService.insetGoldChange(memGoldChange);
                if (changeNum <= 0) {
                    throw new BusinessException("公共账变-新增用户账变明细信息失败！");
                }
                /**
                 *      * 变动类型 1-充值成功、2-签到奖励、3-提现申请、4-提现取消、5-提现失败、 6-提现处理中 、7-提现成功、8-充值赠送、
                 *      * 9-活动奖励、10-手动提现、11-手动充值、12-手动打码、13-手动加码、15-充值失败、16-充值取消
                 *      * 19-KY上分 、20-KY下分、21-彩票下注、22-彩票中奖、23-用户彩票撤单、24-管理员彩票撤单
                 *      * 25-礼物打赏、26-弹幕、27-关注主播、28-金币兑换银豆、29-直播间门票购买、30-代理下级返点、31-彩票未中奖，32-主播关播统计收入、
                 *      * 33-家族长提现名下主播余额，34-主播被家族长提现余额、35-充值处理中，36-充值等待中,37-充值订单超时
                 */
                //判断是否成功
                if (PayConstants.AccountChangTypeEnum.CHANG_TYPE15.getPayTypeCode().intValue() != changeVO.getChangeType().intValue() &&
                        PayConstants.AccountChangTypeEnum.CHANG_TYPE16.getPayTypeCode().intValue() != changeVO.getChangeType().intValue() &&
                        PayConstants.AccountChangTypeEnum.CHANG_TYPE35.getPayTypeCode().intValue() != changeVO.getChangeType().intValue() &&
                        PayConstants.AccountChangTypeEnum.CHANG_TYPE36.getPayTypeCode().intValue() != changeVO.getChangeType().intValue() &&
                        PayConstants.AccountChangTypeEnum.CHANG_TYPE37.getPayTypeCode().intValue() != changeVO.getChangeType().intValue()
                ) {
                    //修改用户钱包信息
                    memWallet.setAmount(later_amount);
                    memWallet.setSilverBean(later_silver_amount);
                    memWallet.setAccountDml(later_dml);
                    memWallet.setSumAccountDml(sum_dml);
                    memWallet.setSumWithdrawAmount(sum_withdraw_amount);
                    memWallet.setSumRechargeAmount(sum_recharge_amount);
                    memWallet.setPayFirst(payFirst);
                    memWallet.setPayNum(pay_num);
                    memWallet.setPayMax(payMax);
                    memWallet.setWithdrawalFirst(withdrawal_first);
                    memWallet.setWithdrawalMax(withdrawal_max);
                    memWallet.setWithdrawalNum(withdrawal_num);
                    memWallet.setUpdateTime(date);
                    boolean walletFlag = walletService.updateById(memWallet);
                    if (!walletFlag) {
                        throw new BusinessException("公共账变-更新用户钱包信息失败！");
                    }
                }
                BalanceChangeDTO changeDTO = new BalanceChangeDTO();
                changeDTO.setFlag(true);
                changeDTO.setChangeOrderNo(changeOrderNo);
                return changeDTO;
            } else {
                log.error("{} publicAccountChange 用户修改余额没拿到锁, 记录修改余额变动对象 {}", account, changeVO);
                throw new BusinessException("请求过于频繁，请稍后再试！");
            }
        } catch (Exception e) {
            log.error("{} publicAccountChange occur error. change info:{}", account, changeVO, e);
            throw new BusinessException(e.getMessage(), e.getCause());
        } finally {
            // 释放锁
            lock.writeLock().unlock();
            log.info("{} publicAccountChange 用户修改余额释放锁", account);
        }

    }


}
