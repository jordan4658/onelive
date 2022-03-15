package com.onelive.api.modules.mem.business;

import java.math.BigDecimal;
import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;

import org.redisson.api.RReadWriteLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.onelive.api.service.live.LiveStudioListService;
import com.onelive.api.service.mem.MemFocusUserService;
import com.onelive.api.service.mem.MemLevelVipService;
import com.onelive.api.service.mem.MemUserAnchorService;
import com.onelive.api.service.mem.MemUserService;
import com.onelive.api.service.mem.MemWalletService;
import com.onelive.api.service.mem.PayExchangeCurrencyService;
import com.onelive.api.service.sys.SysBusParameterService;
import com.onelive.api.service.sys.SysCountryService;
import com.onelive.common.base.LocaleMessageSourceService;
import com.onelive.common.client.WebSocketFeignClient;
import com.onelive.common.constants.business.PayConstants;
import com.onelive.common.constants.business.RedisKeys;
import com.onelive.common.enums.StatusCode;
import com.onelive.common.exception.BusinessException;
import com.onelive.common.model.common.ResultInfo;
import com.onelive.common.model.dto.pay.BalanceChangeDTO;
import com.onelive.common.model.req.mem.MemUserFocusUserReq;
import com.onelive.common.model.vo.live.LiveUserDetailVO;
import com.onelive.common.model.vo.mem.MemAccountChangeVO;
import com.onelive.common.model.vo.mem.MemWalletVO;
import com.onelive.common.model.vo.webSocket.SendMsgVO;
import com.onelive.common.mybatis.entity.LiveStudioList;
import com.onelive.common.mybatis.entity.MemFocusUser;
import com.onelive.common.mybatis.entity.MemUser;
import com.onelive.common.mybatis.entity.MemUserAnchor;
import com.onelive.common.mybatis.entity.MemWallet;
import com.onelive.common.mybatis.entity.PayExchangeCurrency;
import com.onelive.common.mybatis.entity.SysBusParameter;
import com.onelive.common.mybatis.entity.SysCountry;
import com.onelive.common.service.accountChange.AccountBalanceChangeService;
import com.onelive.common.utils.Login.LoginInfoUtil;
import com.onelive.common.utils.others.StringUtils;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class MemUserBusiness {

	@Resource
	private MemFocusUserService memFocusUserService;
	@Resource
	private MemUserService memUserService;
	@Resource
	private LocaleMessageSourceService localeMessageSourceService;
	@Resource
	private MemLevelVipService memLevelVipService;
	@Resource
	WebSocketFeignClient wbeSocketFeignClient;
	@Autowired
	private LiveStudioListService liveStudioListService;
	@Resource
	private MemUserAnchorService memUserAnchorService;
	@Resource
	private MemWalletService memWalletService;
	@Resource
	private AccountBalanceChangeService accountBalanceChangeService;
	@Resource
	private SysCountryService sysCountryService;
	@Resource
	private PayExchangeCurrencyService payExchangeCurrencyService;
	@Resource
	private SysBusParameterService sysBusParameterService;
	@Resource
	private RedissonClient redissonClient;

	/**
	 * 用户关注主播
	 *
	 * @return
	 */
	@Transactional
	public ResultInfo<String> focusUser(MemUserFocusUserReq req) {
		Long focusUserId = req.getFocusUserId();
		if (LoginInfoUtil.getUserId().equals(focusUserId)) {
			return ResultInfo.getInstance(StatusCode.FOCUS_CANT_SELF);
		}
		String account = LoginInfoUtil.getUserAccount();
		RReadWriteLock lock = redissonClient.getReadWriteLock(RedisKeys.SYNC_MEM_FOCUS_ANCHOR + account);
		try {
			boolean bool = lock.writeLock().tryLock(11, 10, TimeUnit.SECONDS);
			if (bool) {
				QueryWrapper<MemFocusUser> queryWrapperFocus = new QueryWrapper<>();
				queryWrapperFocus.lambda().eq(MemFocusUser::getUserId, LoginInfoUtil.getUserId());
				queryWrapperFocus.lambda().eq(MemFocusUser::getFocusId, focusUserId);
				// 原有的关注数据
				MemFocusUser memFocusUserOld = memFocusUserService.getOne(queryWrapperFocus);

				// 查询当前主播的奖励金额
				MemUserAnchor focusAward = memUserAnchorService.getFocusAward(focusUserId);
				BigDecimal awardMoney = null;
				Boolean isSccessful = false;
				// 如果原有来没有关注记录
				if (memFocusUserOld == null) {
					MemFocusUser memFocusUser = new MemFocusUser();
					memFocusUser.setFocusId(focusUserId);
					memFocusUser.setUserId(LoginInfoUtil.getUserId());
					memFocusUser.setAward(awardMoney);
					// 新增数据
					isSccessful = memFocusUserService.save(memFocusUser);

					// 第一次关注，如果奖励开关打开，并且奖励金额 大于 0，给主播奖励,
					if (focusAward != null && focusAward.getIsFocusAward()
							&& focusAward.getFocusAward().compareTo(BigDecimal.ZERO) == 1) {
						awardMoney = focusAward.getFocusAward();
						// 给主播加关注奖励
						focusAwardAnchor(focusUserId, memFocusUser.getId(), awardMoney);
					}
				}
				// 原本的数据是非关注，更新关注状态
				else if (!memFocusUserOld.getIsFocus()) {
					memFocusUserOld.setIsFocus(true);
					// 更新状态为 关注
					isSccessful = memFocusUserService.updateById(memFocusUserOld);

					// 如果原有数据的奖励是空，并且当前主播的被关注奖励不为空，且奖励开关打开，且奖励金额 大于 0，对主播进行奖励
					if (focusAward != null && memFocusUserOld.getAward() == null && focusAward.getIsFocusAward()
							&& focusAward.getFocusAward().compareTo(BigDecimal.ZERO) == 1) {
						// 给主播加关注奖励
						awardMoney = focusAward.getFocusAward();
						// 给主播加关注奖励
						focusAwardAnchor(focusUserId, memFocusUserOld.getId(), awardMoney);
					}
				}
				// 通知主播
				if (isSccessful) {
					this.focusNotice(req, true);
				}

			} else {
				log.error("focusUser 用户账号 ： {} 关注主播: 没拿到锁, 关注失败 ", account);
				throw new BusinessException("正在处理中");
			}
		} catch (Exception e) {
			log.error("focusUser 用户账号：" + account + "关注主播异常, 关注主播失败 ", e);
			throw new BusinessException("关注主播异常，请联系管理员");
		} finally {
			// 释放锁
			lock.writeLock().unlock();
			log.info("giftGiving 用户账号:{} 关注主播，释放锁 ", account);
		}
		return ResultInfo.ok();
	}

	/**
	 * 给主播加关注奖励
	 * 
	 * @param focusUserId
	 * @param memFocusId
	 * @param awardMoney
	 */
	private void focusAwardAnchor(Long focusUserId, Long memFocusId, BigDecimal awardMoney) {
		MemUser anchorUser = memUserService.queryById(focusUserId);
		MemAccountChangeVO memAccountChangeVO = new MemAccountChangeVO();
		memAccountChangeVO.setOrderNo(memFocusId.toString());
		memAccountChangeVO.setAccount(anchorUser.getUserAccount());
		memAccountChangeVO.setIsSilverBean(PayConstants.GoldTypeEnum.GOLD_TYPE_2.getCode());
		memAccountChangeVO.setPrice(awardMoney.divide(new BigDecimal("10.0")));
		memAccountChangeVO.setChangeType(PayConstants.AccountChangTypeEnum.CHANG_TYPE27.getPayTypeCode());
		memAccountChangeVO.setOpNote(PayConstants.AccountChangTypeEnum.CHANG_TYPE27.getMsg());
		memAccountChangeVO.setFlowType(0);
		memAccountChangeVO.setDml(new BigDecimal("0"));

		BalanceChangeDTO flagVo = accountBalanceChangeService.publicAccountBalanceChange(memAccountChangeVO);
		if (!flagVo.getFlag()) {
			log.error("用户账号：{} 关注主播 主播账号：{} 金币账变失败！", LoginInfoUtil.getUserAccount(), anchorUser.getUserAccount());
			throw new BusinessException("用户账号：" + LoginInfoUtil.getUserAccount() + " 关注主播，主播账号："
					+ anchorUser.getUserAccount() + " 金币账变失败！");
		}
		// 更新主播的收益金额
		memUserAnchorService.updateAnchorFocuseTotal(focusUserId, awardMoney);
	}

	/**
	 * 关注主播进行通知
	 */
	private void focusNotice(MemUserFocusUserReq req, Boolean isFocus) {
		try {
			// 在房间,即:进行房间广播
			if (req.isInRoom()) {
				Long focusUserId = req.getFocusUserId();
				// 主播的直播间状态
				QueryWrapper<LiveStudioList> queryWrapperStu = new QueryWrapper<>();
				queryWrapperStu.lambda().eq(LiveStudioList::getUserId, focusUserId);
				LiveStudioList liveStudioList = liveStudioListService.getOne(queryWrapperStu);
				// 开播进行通知
				if (liveStudioList.getStudioStatus() == 1) {
					QueryWrapper<MemUser> queryWrapperUser = new QueryWrapper<>();
					queryWrapperUser.lambda().eq(MemUser::getId, LoginInfoUtil.getUserId());
					MemUser user = memUserService.getOne(queryWrapperUser);
					if (isFocus) {
						// 直播间广播:
						SendMsgVO roomMsgVO = new SendMsgVO();
						roomMsgVO.setContent(user.getNickName() + " 关注了主播！");
						roomMsgVO.setOperatorType(4);
						roomMsgVO.setTargetId(liveStudioList.getStudioNum());
						wbeSocketFeignClient.sendRoomMsg(roomMsgVO);

					} else {
						// 通知主播,被取关
						SendMsgVO roomMsgVO = new SendMsgVO();
						roomMsgVO.setContent(user.getNickName() + " 取消了对您的关注！");
						roomMsgVO.setOperatorType(5);
						roomMsgVO.setTargetId(focusUserId.toString());
						wbeSocketFeignClient.sendRoomMsg(roomMsgVO);
					}
				}
			}
			// TODO 对主播进行三方消息推送

		} catch (Exception e) {
			e.printStackTrace();
			log.error("推关注消息失败!");
		}
	}

	/**
	 * 取消关注用户
	 */
	public ResultInfo<String> cancleUser(MemUserFocusUserReq req) {
		Long focusUserId = req.getFocusUserId();

		QueryWrapper<MemFocusUser> queryWrapperFocus = new QueryWrapper<>();
		queryWrapperFocus.lambda().eq(MemFocusUser::getUserId, LoginInfoUtil.getUserId());
		queryWrapperFocus.lambda().eq(MemFocusUser::getFocusId, focusUserId);
		MemFocusUser one = memFocusUserService.getOne(queryWrapperFocus);

		// 存在，且为关注状态
		if (one != null && one.getIsFocus()) {
			one.setIsFocus(false);
			memFocusUserService.updateById(one);
			this.focusNotice(req, false);
		}

		return ResultInfo.ok();
	}

	/**
	 * 直播间用户详细
	 *
	 * @param userId
	 * @return
	 */
	public LiveUserDetailVO getUserDetailInfo(Long userId) {
		return memUserService.getUserDetailInfo(userId);
	}

	public MemWalletVO getUserWalletByUserAccount(String userAccount, Integer walletType) {
		if (StringUtils.isEmpty(userAccount)) {
			throw new BusinessException("用户账号为空！");
		}
		if (walletType == null) {
			throw new BusinessException("钱包类型为空！");
		}
		// 查询当前币种汇率
		// 查询国家语言相关信息
		QueryWrapper<MemUser> queryWrapper = new QueryWrapper<>();
		queryWrapper.lambda().eq(MemUser::getUserAccount, userAccount);
		MemUser memUser = memUserService.getOne(queryWrapper);
		SysCountry sysCountry = sysCountryService.getByCountryCode(memUser.getRegisterCountryCode());
		PayExchangeCurrency exchangeCurrency = payExchangeCurrencyService
				.selectByCurrencyCode(sysCountry.getLocalCurrency());
		if (exchangeCurrency == null) {
			throw new BusinessException("未获取到当前币种充值的汇率！");
		}
		// 查询平台金币单位
		SysBusParameter goldUnit = sysBusParameterService.getByCode(PayConstants.PLATFORM_GOLD_UNIT);
		MemWallet wallet = memWalletService.getWalletByMemId(memUser.getId(), walletType);
		MemWalletVO vo = new MemWalletVO();
		BeanUtils.copyProperties(wallet, vo);
		vo.setTxExChange(exchangeCurrency.getTxExchange());
		vo.setCzExChange(exchangeCurrency.getCzExchange());
		vo.setCurrencyUint(exchangeCurrency.getCurrencyUnit());
		vo.setShortcutOptionsUnit(goldUnit.getParamValue());
		return vo;
	}

	public MemWalletVO getUserWalletByUserId(Long userId, Integer walletType) {
		if (userId == null) {
			throw new BusinessException("userId为空！");
		}
		if (walletType == null) {
			throw new BusinessException("钱包类型为空！");
		}
		// 查询当前币种汇率
		SysCountry sysCountry = sysCountryService.getByCountryCode(LoginInfoUtil.getCountryCode());
		PayExchangeCurrency exchangeCurrency = payExchangeCurrencyService.selectByCurrencyCode(sysCountry.getLocalCurrency());
		if (exchangeCurrency == null) {
			throw new BusinessException("未获取到当前币种充值的汇率！");
		}
		// 查询平台金币单位
		SysBusParameter goldUnit = sysBusParameterService.getByCode(PayConstants.PLATFORM_GOLD_UNIT);
		MemWallet wallet = memWalletService.getWalletByMemId(userId, walletType);
		MemWalletVO vo = new MemWalletVO();
		BeanUtils.copyProperties(wallet, vo);
		vo.setTxExChange(exchangeCurrency.getTxExchange());
		vo.setCzExChange(exchangeCurrency.getCzExchange());
		vo.setCurrencyUint(exchangeCurrency.getCurrencyUnit());
		vo.setShortcutOptionsUnit(goldUnit.getParamValue());
		return vo;
	}
}
