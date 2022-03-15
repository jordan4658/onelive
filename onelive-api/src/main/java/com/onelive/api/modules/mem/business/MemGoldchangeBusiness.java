package com.onelive.api.modules.mem.business;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.onelive.api.service.mem.MemWalletService;
import com.onelive.api.service.sys.SysBusParameterService;
import com.onelive.common.constants.business.PayConstants;
import com.onelive.common.constants.business.RedisKeys;
import com.onelive.common.enums.StatusCode;
import com.onelive.common.enums.WalletTypeEnum;
import com.onelive.common.exception.BusinessException;
import com.onelive.common.model.common.ResultInfo;
import com.onelive.common.model.vo.mem.ChangeSilverBeanVo;
import com.onelive.common.model.vo.mem.MemAccountChangeVO;
import com.onelive.common.model.vo.mem.MemWalletBalanceVO;
import com.onelive.common.mybatis.entity.MemWallet;
import com.onelive.common.mybatis.entity.SysBusParameter;
import com.onelive.common.service.accountChange.AccountBalanceChangeService;
import com.onelive.common.utils.Login.LoginInfoUtil;
import lombok.extern.slf4j.Slf4j;

import org.redisson.api.RReadWriteLock;
import org.redisson.api.RedissonClient;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.concurrent.TimeUnit;

@Component
@Slf4j
@Scope(proxyMode = ScopedProxyMode.TARGET_CLASS)
public class MemGoldchangeBusiness {

	@Resource
	private MemWalletService memWalletService;

	@Resource
	private AccountBalanceChangeService accountBalanceChangeService;
	
	@Resource
	private SysBusParameterService sysBusParameterService;
	
	@Resource
    private RedissonClient redissonClient;

	@Transactional
	public MemWalletBalanceVO changeSilverBean(ChangeSilverBeanVo changeSilverBeanVo) {
		MemWalletBalanceVO memWalletBalanceVO = new MemWalletBalanceVO();
		
		String account = LoginInfoUtil.getUserAccount();
        RReadWriteLock lock = redissonClient.getReadWriteLock(RedisKeys.SYNC_MEM_GOLD_CHANGE + account);
        try {
            boolean bool = lock.writeLock().tryLock(11, 10, TimeUnit.SECONDS);
            if (bool) {
            	Long userId = LoginInfoUtil.getUserId();
				BigDecimal goldNum = changeSilverBeanVo.getGoldNum();
				QueryWrapper<MemWallet> queryWrapperWallet = new QueryWrapper<>();
				queryWrapperWallet.lambda().eq(MemWallet::getUserId, userId);
				queryWrapperWallet.lambda().eq(MemWallet::getWalletType, WalletTypeEnum.WOODEN_PLATFORM.getCode());
				MemWallet memWallet = memWalletService.getOne(queryWrapperWallet);
				//查询金币兑换银豆汇率
				SysBusParameter silverBeanExChange = sysBusParameterService.getByCode(PayConstants.GOLD_SILVER_EXCHANGE);
				BigDecimal balance = memWallet.getAmount();
				// 如果当前余额小于要兑换的金币,
				if (balance.compareTo(goldNum) == -1) {
					throw new BusinessException("账户金币不足!");
				}
				// 给账户 扣金币 加银豆
				BigDecimal silverBean = goldNum.multiply(new BigDecimal(silverBeanExChange.getParamValue()));
				MemAccountChangeVO memAccountChangeVO = new MemAccountChangeVO();
				memAccountChangeVO.setAccount(LoginInfoUtil.getUserAccount());
				memAccountChangeVO.setIsSilverBean(PayConstants.GoldTypeEnum.GOLD_TYPE_3.getCode());
				memAccountChangeVO.setPrice(goldNum.multiply(new BigDecimal("-1")));
				memAccountChangeVO.setSilverBeanPrice(silverBean);
				memAccountChangeVO.setChangeType(PayConstants.AccountChangTypeEnum.CHANG_TYPE28.getPayTypeCode());
				memAccountChangeVO.setOpNote(PayConstants.AccountChangTypeEnum.CHANG_TYPE28.getMsg());
				accountBalanceChangeService.publicAccountBalanceChange(memAccountChangeVO);
				// 获取最新的余额
				memWalletBalanceVO.setAmount(balance.subtract(goldNum));
				memWalletBalanceVO.setSilverBean(memWallet.getSilverBean().add(silverBean));
				log.info("用户id:{},进行金豆兑换银币，金豆额：{},兑换银币额:{}成功", userId, goldNum, silverBean);
				return memWalletBalanceVO;
				
            } else {
                log.error("changeSilverBean 用户账号:{}金币换银豆没拿到锁, 兑换失败 ", account);
                throw new BusinessException(StatusCode.PROCESSING);
            }
        } catch (Exception e) {
            log.error("changeSilverBean 用户账号:" + account + "用户金币换银豆异常, 兑换失败 ", e);
            throw new BusinessException(StatusCode.FAIL_MEM_GOLD_CHANGE);
        } finally {
            // 释放锁
            lock.writeLock().unlock();
            log.info("changeSilverBean 用户账号:{} 金币换银豆结束，释放锁 ", account);
        }
	}

}
