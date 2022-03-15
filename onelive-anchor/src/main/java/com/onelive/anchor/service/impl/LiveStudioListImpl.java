package com.onelive.anchor.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.onelive.anchor.service.*;
import com.onelive.anchor.util.AnchorBusinessRedisUtils;
import com.onelive.common.client.WebSocketFeignClient;
import com.onelive.common.constants.business.LiveConstants;
import com.onelive.common.constants.business.PayConstants;
import com.onelive.common.constants.business.RedisCacheableKeys;
import com.onelive.common.constants.business.RedisKeys;
import com.onelive.common.constants.webSocket.WebSocketRedisKeys;
import com.onelive.common.enums.StatusCode;
import com.onelive.common.exception.BusinessException;
import com.onelive.common.model.common.ResultInfo;
import com.onelive.common.model.dto.index.LiveCloseDto;
import com.onelive.common.model.dto.pay.BalanceChangeDTO;
import com.onelive.common.model.dto.platform.StudioLogLiveGiftDto;
import com.onelive.common.model.vo.live.LiveStudioForBegin;
import com.onelive.common.model.vo.live.LiveUserDetailVO;
import com.onelive.common.model.vo.mem.MemAccountChangeVO;
import com.onelive.common.model.vo.webSocket.SendMsgVO;
import com.onelive.common.mybatis.entity.LiveStudioList;
import com.onelive.common.mybatis.entity.LiveStudioLog;
import com.onelive.common.mybatis.entity.MemUserAnchor;
import com.onelive.common.mybatis.entity.SysStreamConfig;
import com.onelive.common.mybatis.mapper.master.live.LiveStudioListMapper;
import com.onelive.common.mybatis.mapper.slave.live.SlaveLiveStudioListMapper;
import com.onelive.common.service.accountChange.AccountBalanceChangeService;
import com.onelive.common.utils.Login.LoginInfoUtil;
import com.onelive.common.utils.others.DateUtils;
import com.onelive.common.utils.pay.SnowflakeIdWorker;
import com.onelive.common.utils.upload.AWSS3Util;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.redisson.api.RReadWriteLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service
public class LiveStudioListImpl extends ServiceImpl<LiveStudioListMapper, LiveStudioList>
		implements LiveStudioListService {

	@Autowired
	private LiveStudioLogService liveStudioLogService;

	@Resource
	private SlaveLiveStudioListMapper slaveLiveStudioListMapper;

	@Resource
	WebSocketFeignClient wbeSocketFeignClient;

	@Autowired
	private MemUserAnchorService memUserAnchorService;

	@Autowired
	private SysStreamConfigService sysStreamConfigService;

	@Resource
	private LiveGiftLogService liveGiftLogService;
	
    @Resource
    private ThreadPoolTaskExecutor taskExecutor;
    
    @Resource
    private AccountBalanceChangeService accountBalanceChangeService;

    @Resource
    private RedissonClient redissonClient;

	/**
	 * 主播开播调用接口
	 */
	@Override
	@Transactional
	public ResultInfo<LiveStudioForBegin> getBeginToLive(String studioTitle, String studioBackground,
			String studioThumbImage, String countryCode, Integer colour, String sharpness, Integer productId,
			Integer trySeeTime, Long gameId) {
		Long userId = LoginInfoUtil.getUserId();
		if (StringUtils.isEmpty(studioTitle)) {
			return ResultInfo.getInstance(StatusCode.LIVE_TITLE_ISNULL);
		}

		// 当前主播的主播直播间
		LiveStudioList liveStudioList = this.getByUserId(userId);

		liveStudioList = liveStudioList == null ? new LiveStudioList() : liveStudioList;
		String studioNum = liveStudioList.getStudioNum();
		if (StringUtils.isEmpty(studioNum)) {
			// 获取直播间唯一房间号
			studioNum = String.valueOf(AnchorBusinessRedisUtils.createStudioNum(RedisKeys.LIVE_STUDIO_NUM,
					LiveConstants.LIVE_STUDIO_START_NUM));
			liveStudioList.setStudioNum(studioNum.toString());
		}

		// 判断切换收费记录
		Object switch_charge = AnchorBusinessRedisUtils.get(RedisKeys.STUDIO_SWITCH_CHARGE + studioNum);
		if (switch_charge != null) {
			Boolean isCharge = (Boolean) switch_charge;
			if ((isCharge && productId == null) || (!isCharge && productId != null)) {
				long switch_charge_time = AnchorBusinessRedisUtils
						.getExpire(RedisKeys.STUDIO_SWITCH_CHARGE + liveStudioList.getStudioNum());
				String charge = productId == null ? "免费" : "收费";
				throw new BusinessException(DateUtils.changeTimeFormat(switch_charge_time) + "后可以切换为" + charge + "模式");
			}
		}

		// 如果是已经开启的状态,先关闭直播间
		if (liveStudioList.getStudioStatus() != null && liveStudioList.getStudioStatus() == 1) {
			liveClose(liveStudioList.getStudioNum(), userId, "all", 1);
		}
		liveStudioList.setUserId(userId);
		liveStudioList.setStudioTitle(studioTitle);
		liveStudioList.setStudioBackground(AWSS3Util.getRelativeUrl(studioBackground));
		liveStudioList.setStudioThumbImage(AWSS3Util.getRelativeUrl(studioThumbImage));
		liveStudioList.setStudioStatus(1);
		liveStudioList.setCountryCode(countryCode);
		liveStudioList.setColour(colour);
		liveStudioList.setSharpness(sharpness);
		productId = productId == null ? 0 : productId;
		liveStudioList.setProductId(productId);
		liveStudioList.setTrySeeTime(trySeeTime);
		liveStudioList.setGameId(gameId);

		// 更新/保存 主播直播状态
		this.baseMapper.saveOrUpdate(liveStudioList);

//		RedisUtil.hSet("room_stream_false_count", studioId.toString(), 0);// 将直播间推流失败次数重置
		// 添加直播记录
		LiveStudioLog log = new LiveStudioLog();
		log.setUserId(userId);
		log.setStudioStatus(1);// 开播
		log.setStudioNum(studioNum.toString());
		log.setStartTime(new Date());
		log.setMerchantCode(LoginInfoUtil.getMerchantCode());
		log.setGameId(gameId);
		log.setDevice(LoginInfoUtil.getDeviceType());
		log.setProductId(productId);
		liveStudioLogService.save(log);
		// 查询当前主播的总收礼金额：计算火力值
		MemUserAnchor memUserAnchor = memUserAnchorService.getInfoByUserId(userId);

		LiveStudioForBegin liveStudioForBegin = new LiveStudioForBegin();
		BeanUtil.copyProperties(liveStudioList, liveStudioForBegin);
		// 查询用户贡献列表
		liveStudioForBegin.setContributeList(queryTopgGiveGift(userId));
		Integer studioHeat = memUserAnchor.getGiftTotal().multiply(new BigDecimal(10))
				.setScale(0, BigDecimal.ROUND_DOWN).intValue();
		// 火力值
		liveStudioForBegin.setStudioHeat(studioHeat);
		SysStreamConfig sysStreamConfig = sysStreamConfigService.getUse();
		// 推流地址
		liveStudioList.setStudioLivePushFlow(
				sysStreamConfig.getPushDomainRtmp() + "?key=" + sysStreamConfig.getPushCheckKey());

		// 消息推送给关注当前主播的用户
		pushBeginLiveToUser();
		// 推荐直播的缓存
		AnchorBusinessRedisUtils.del(RedisCacheableKeys.ROOM_RECOMMEND_STUDIONUM + "countryCode_" + countryCode);
		AnchorBusinessRedisUtils.del(RedisCacheableKeys.ROOM_RECOMMEND + "countryCode_" + countryCode);
		
		AnchorBusinessRedisUtils.del(WebSocketRedisKeys.user_list + studioNum);
		AnchorBusinessRedisUtils.del(WebSocketRedisKeys.studioNum_Try_See_User_Id + studioNum);
//		memUserAnchor.getGiftMoneyTotal();
		// 主播列表
		AnchorBusinessRedisUtils.hset(WebSocketRedisKeys.anchor_studioNum, studioNum, 1);
		// 直播间热度：每次送礼后更新该值
		AnchorBusinessRedisUtils.hset(WebSocketRedisKeys.studioNum_Heat, studioNum, studioHeat);
		// 记录收费模式,收费 true ，免费false
		AnchorBusinessRedisUtils.set(RedisKeys.STUDIO_SWITCH_CHARGE + liveStudioList.getStudioNum(), productId != null,
				60 * 30L);
		return ResultInfo.ok(liveStudioForBegin);
	}

	@Override
	public LiveStudioList getByUserId(Long userId) {
		QueryWrapper<LiveStudioList> queryWrapper = new QueryWrapper<>();
		queryWrapper.lambda().eq(LiveStudioList::getUserId, userId);
		queryWrapper.lambda().eq(LiveStudioList::getStudioStatus, 1);
		LiveStudioList liveStudioList = slaveLiveStudioListMapper.selectOne(queryWrapper);
		return liveStudioList;
	}

	/**
	 * 消息推送给关注当前主播的用户 获取当前粉丝的关注用户列表
	 */
	private void pushBeginLiveToUser() {
	}
	
	/**
	 * 根据主播id 查询送礼最多的50名用户
	 *
	 * @param userId
	 * @return
	 */
	private List<LiveUserDetailVO> queryTopgGiveGift(Long userId) {
		List<LiveUserDetailVO> LiveUserDetails = liveGiftLogService.queryTopgGiveGift(userId);
		return LiveUserDetails;
	}

	/**
	 * 主播下播
	 */
	@Override
	@Transactional
	public ResultInfo<LiveCloseDto> liveClose(String studioNum, Long userId, String deviceType, Integer endReason) {
		if (StringUtils.isEmpty(studioNum)) {
			return ResultInfo.getInstance(StatusCode.LIVE_STUDIONUM_ISNULL);
		}
		if (userId == null) {
			return ResultInfo.getInstance(StatusCode.ANCHOR_ID_ISNULL);
		}
		if (StringUtils.isEmpty(deviceType)) {
			return ResultInfo.getInstance(StatusCode.NOT_NULL_DEVICETYPE);
		}
		RReadWriteLock lock = redissonClient.getReadWriteLock(RedisKeys.SYNC_LIVE_STUDIO_CLOSE + studioNum);

		try {
			boolean bool = lock.writeLock().tryLock(11, 10, TimeUnit.SECONDS);
			if (bool) {
				LiveStudioLog liveStudioLog = liveStudioLogService.selectLastByStudioNum(studioNum);
				// 当前直播间
				if (liveStudioLog.getStudioStatus() == 0) {
					return ResultInfo.getInstance(StatusCode.STUDIO_ALREADY_CLOSE);
				}
				// 通知观众主播下播
				try {
					SendMsgVO wordMsgVO = new SendMsgVO();
					wordMsgVO.setContent("当前主播结束直播~");
					wordMsgVO.setOperatorType(7);
					wordMsgVO.setTargetId(studioNum);
					wbeSocketFeignClient.sendRoomMsg(wordMsgVO);
					wbeSocketFeignClient.removeRoom(studioNum);
				} catch (Exception e) {
					e.printStackTrace();
					log.error("推送下播消息失败!");
				}

				// 更新直播间状态
				UpdateWrapper<LiveStudioList> updateWrapperLiveStudioList = new UpdateWrapper<LiveStudioList>();
				updateWrapperLiveStudioList.lambda().set(LiveStudioList::getStudioStatus, 0)
						.set(LiveStudioList::getIsFixed,false)
						.set(LiveStudioList::getSortNum,0) //固定位置复位
						.set(LiveStudioList::getIsFirst,0);//推荐/置底复位
				updateWrapperLiveStudioList.lambda().eq(LiveStudioList::getStudioNum, studioNum);
				this.update(updateWrapperLiveStudioList);
				// 主播信息，礼物分成比例
				MemUserAnchor memUserAnchor = memUserAnchorService.getInfoByUserId(userId);
				// 查询主播本场直播收到的收益统计，
				StudioLogLiveGiftDto studioLogLiveGiftDto = liveGiftLogService.selectSumBystudioLogId(liveStudioLog.getLogId());
				
				BigDecimal barrageTotal = studioLogLiveGiftDto.getBarrageTotal();
				BigDecimal giftTotal = studioLogLiveGiftDto.getGiftTotal();
				BigDecimal revenueTotal = barrageTotal.add(giftTotal);
				
				// 给主播加钱 ↓ ↓ ↓ ↓ ↓ ↓ ↓ ↓ ↓ ↓ ↓ ↓ ↓ ↓ ↓ ↓ ↓ ↓ ↓ ↓ ↓ ↓ ↓ ↓
				
				String earningLogNo = SnowflakeIdWorker.generateShortId();
				MemAccountChangeVO memAccountChangeVO = new MemAccountChangeVO();
				memAccountChangeVO.setOrderNo(earningLogNo);
				memAccountChangeVO.setAccount(LoginInfoUtil.getUserAccount());
				memAccountChangeVO.setIsSilverBean(PayConstants.GoldTypeEnum.GOLD_TYPE_2.getCode());
				// 给主播账户加金币：礼物银豆/ 10 = 金币
				memAccountChangeVO.setPrice(revenueTotal.divide(new BigDecimal("10.0")));
				memAccountChangeVO.setChangeType(PayConstants.AccountChangTypeEnum.LIVE_EARNING32.getPayTypeCode());
				memAccountChangeVO.setOpNote(liveStudioLog.getLogId().toString());
				memAccountChangeVO.setFlowType(0);
			    memAccountChangeVO.setDml(new BigDecimal("0"));
				BalanceChangeDTO flagVo = accountBalanceChangeService.publicAccountBalanceChange(memAccountChangeVO);
				if (!flagVo.getFlag()) {
					log.error("liveClose----主播关播入款失败，房间号 ：{} userid：{} 入款银豆总额：{} ", studioNum, userId, revenueTotal);
				}
				// 给主播加钱 ↑ ↑ ↑ ↑ ↑ ↑ ↑ ↑ ↑ ↑ ↑ ↑ ↑ ↑ ↑ ↑ ↑ ↑ ↑ ↑ ↑ ↑ ↑ ↑ ↑ ↑ ↑ ↑ ↑ ↑ ↑

				// -----记录直播间闭播信息--start---
				// TODO 有效观众数量
				Integer effectiveAudience = AnchorBusinessRedisUtils.hMGetSize(WebSocketRedisKeys.user_list + studioNum)
						.intValue();
				liveStudioLog.setStudioStatus(0);// 关闭直播
				// 人次
				Long sGetSetSize = AnchorBusinessRedisUtils
						.sGetSetSize(WebSocketRedisKeys.studioNum_enter_times + studioNum);
				Date endTime = new Date();
				// 直播时长，单位秒
				Long liveTime = (endTime.getTime() - liveStudioLog.getStartTime().getTime()) / 60;
				liveStudioLog.setEndTime(endTime);
				liveStudioLog.setLiveTime(liveTime);
				liveStudioLog.setMoneyNumber(revenueTotal);// 本场直播收到打赏总金额
				liveStudioLog.setEffectiveAudience(effectiveAudience);// 本场直播有效观众数量
				liveStudioLog.setEndReason(endReason);
				liveStudioLog.setEarningLogNo(earningLogNo); // 本次直播的收益订单号
				liveStudioLog.setGoldChangeNo(flagVo.getChangeOrderNo()); // 资金账变订单号
				liveStudioLog.setEffectiveAudience(sGetSetSize.intValue());
				liveStudioLogService.updateById(liveStudioLog);

				// 更新主播表
				Long time = (System.currentTimeMillis() - liveStudioLog.getStartTime().getTime()) / 1000;// 计算本场直播时长（单位：秒）
				Integer liveCount = memUserAnchor.getLiveCount() + 1;
				memUserAnchor.setLiveTime((int) (memUserAnchor.getLiveTime() + time));
				memUserAnchor.setLiveCount(liveCount);
				memUserAnchor.setGiftTotal(memUserAnchor.getGiftTotal().add(giftTotal));
				memUserAnchor.setBarrageTotal(barrageTotal);
				memUserAnchorService.updateById(memUserAnchor);

				// -------返回主播下播----礼物、直播时长、有效观众人数信息、打赏前3名用户信息---start------------------------

				LiveCloseDto liveCloseDto = new LiveCloseDto();
				// 进入房间的人次
				liveCloseDto.setUserCoutn(sGetSetSize.intValue());
				liveCloseDto.setLiveTime((System.currentTimeMillis() - liveStudioLog.getStartTime().getTime()) / 1000);
				liveCloseDto.setMoneyNumber(revenueTotal);
				liveCloseDto.setLiveCount(liveCount);

				// 清空缓存数据
				AnchorBusinessRedisUtils.del(WebSocketRedisKeys.user_list + studioNum);
				AnchorBusinessRedisUtils.del(WebSocketRedisKeys.studioNum_enter_times + studioNum);
				AnchorBusinessRedisUtils.hdel(WebSocketRedisKeys.anchor_studioNum, studioNum);
				AnchorBusinessRedisUtils.del(WebSocketRedisKeys.studioNum_Try_See_User_Id + studioNum);

				// 下播删除推荐直播间的缓存
				cacheEvictRoomRecommend(LoginInfoUtil.getCountryCode());
				// -------返回主播下播----礼物、直播时长、有效观众人数信息---end------------------------
				return ResultInfo.ok(liveCloseDto);
			} else {
				log.error("直播间号:{} 关播没拿到锁, 本次关播失败 ", studioNum);
				return ResultInfo.getInstance(StatusCode.PROCESSING);
			}
		} catch (Exception e) {
			log.error("直播间号:" + studioNum + "关播异常, 主播本场直播统计收益失败", e);
			return ResultInfo.getInstance(StatusCode.LIVE_STUDIO_CLOSE_FAIL);
		} finally {
			// 释放锁
			lock.writeLock().unlock();
			log.info("直播间号:{} 关播结束，释放锁 ", studioNum);
		}
	}
	
	private void cacheEvictRoomRecommend(String countryCode) {
		// 推荐直播的缓存
		AnchorBusinessRedisUtils.del(RedisCacheableKeys.ROOM_RECOMMEND_STUDIONUM + "countryCode_" + countryCode);
		AnchorBusinessRedisUtils.del(RedisCacheableKeys.ROOM_RECOMMEND + "countryCode_" + countryCode);
	}
	
	

}
