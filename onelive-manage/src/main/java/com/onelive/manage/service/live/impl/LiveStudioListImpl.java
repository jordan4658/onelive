package com.onelive.manage.service.live.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageInfo;
import com.onelive.common.client.WebSocketFeignClient;
import com.onelive.common.constants.business.LiveConstants;
import com.onelive.common.constants.business.PayConstants;
import com.onelive.common.constants.business.RedisCacheableKeys;
import com.onelive.common.constants.business.RedisKeys;
import com.onelive.common.constants.sys.SysParameterConstants;
import com.onelive.common.constants.webSocket.WebSocketRedisKeys;
import com.onelive.common.enums.StatusCode;
import com.onelive.common.exception.BusinessException;
import com.onelive.common.model.dto.pay.BalanceChangeDTO;
import com.onelive.common.model.dto.platform.StudioLogLiveGiftDto;
import com.onelive.common.model.req.live.LiveBeginForAdminReq;
import com.onelive.common.model.req.live.LiveStudioListReq;
import com.onelive.common.model.vo.live.LiveStudioListManegeVO;
import com.onelive.common.model.vo.live.LiveStudioListVo;
import com.onelive.common.model.vo.live.LiveStudioUserCountVO;
import com.onelive.common.model.vo.mem.MemAccountChangeVO;
import com.onelive.common.model.vo.webSocket.SendMsgVO;
import com.onelive.common.mybatis.entity.*;
import com.onelive.common.mybatis.mapper.master.live.LiveStudioListMapper;
import com.onelive.common.mybatis.mapper.master.platform.LiveGiftLogMapper;
import com.onelive.common.mybatis.mapper.slave.live.SlaveLiveStudioListMapper;
import com.onelive.common.mybatis.mapper.slave.live.SlaveLiveStudioLogMapper;
import com.onelive.common.mybatis.mapper.slave.mem.SlaveMemUserAnchorMapper;
import com.onelive.common.service.accountChange.AccountBalanceChangeService;
import com.onelive.common.utils.Login.LoginInfoUtil;
import com.onelive.common.utils.pay.RandomUtil;
import com.onelive.common.utils.pay.SnowflakeIdWorker;
import com.onelive.manage.service.live.LiveStudioListService;
import com.onelive.manage.service.live.LiveStudioLogService;
import com.onelive.manage.service.mem.MemUserAnchorService;
import com.onelive.manage.service.mem.MemUserService;
import com.onelive.manage.service.platform.SysStreamConfigService;
import com.onelive.manage.service.sys.SysParameterService;
import com.onelive.manage.utils.redis.SysBusinessRedisUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.redisson.api.RReadWriteLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Slf4j
@Service
public class LiveStudioListImpl extends ServiceImpl<LiveStudioListMapper, LiveStudioList>
		implements LiveStudioListService {

	@Resource
	private MemUserAnchorService memUserAnchorService;
	@Resource
	private MemUserService memUserService;

	@Resource
	private LiveStudioLogService liveStudioLogService;

	@Resource
	private SlaveLiveStudioListMapper slaveLiveStudioListMapper;

	@Resource
	private SlaveLiveStudioLogMapper slaveLiveStudioLogMapper;

	@Resource
	private LiveGiftLogMapper liveGiftLogMapper;

	@Resource
	private SlaveMemUserAnchorMapper slaveMemUserAnchorMapper;

	@Resource
	private WebSocketFeignClient webSocketFeignClient;

	@Autowired
	private SysStreamConfigService sysStreamConfigService;
	
	@Autowired
	private SysParameterService sysParameterService;
	
    @Resource
    private AccountBalanceChangeService accountBalanceChangeService;
    
    @Resource
    private RedissonClient redissonClient;
	
	/**
	 * 查询正在直播的直播间
	 */
	@Override
	public PageInfo<LiveStudioListManegeVO> getList(LiveStudioListReq param) {
		String countryCode = LoginInfoUtil.getCountryCode();
		param.setLang(LoginInfoUtil.getLang());
		// 所有符合条件的直播间
		List<LiveStudioListManegeVO> allLiveList = slaveLiveStudioListMapper.getList(param);
		List<LiveStudioListManegeVO> selectList = allLiveList.stream().filter(t -> t.getStudioStatus() == 1).collect(Collectors.toList());
		
		// 先给当前国家的直播间排序
		List<LiveStudioListManegeVO> thisCountryList = selectList.stream()
				.filter(t -> t.getCountryCode().equals(countryCode)).collect(Collectors.toList());
		List<LiveStudioUserCountVO> orderList = getRecommendByOrder(thisCountryList, true);
		// 非当前国家的直播间排序
		List<LiveStudioListManegeVO> otherCountryList = selectList.stream()
				.filter(t -> !t.getCountryCode().equals(countryCode)).collect(Collectors.toList());
		orderList.addAll(getRecommendByOrder(otherCountryList, false));

		// 所有关闭直播的房间 studioStatus = 0
		List<LiveStudioListManegeVO> closeLive = allLiveList.stream().filter(t -> t.getStudioStatus() == 0).collect(Collectors.toList());
		// 根据时间排序，降序
		closeLive.sort(Comparator.comparing(LiveStudioListManegeVO::getStartTime, Comparator.reverseOrder()));
		for (LiveStudioListManegeVO liveStudioListManegeVO : closeLive) {
			orderList.add(new LiveStudioUserCountVO(liveStudioListManegeVO.getStudioNum(), 0));
		}
		int start = (param.getPageNum() - 1) * param.getPageSize();
		int end = param.getPageNum() * param.getPageSize();
		end = end > orderList.size() ? orderList.size() : end;
		
		orderList = orderList.subList(start, end);

		List<LiveStudioListManegeVO> result = new ArrayList<>();
		
		for (LiveStudioUserCountVO liveStudioUserCountVO : orderList) {
			Optional<LiveStudioListManegeVO> findFirst = allLiveList.stream()
					.filter(t -> t.getStudioNum().equals(liveStudioUserCountVO.getStudioNum())).findFirst();
			boolean present = findFirst.isPresent();
			if (!present) {
				continue;
			}
			LiveStudioListManegeVO liveStudioListManegeVO = findFirst.get();
			liveStudioListManegeVO.setOnlineMem(onlineUsersCount(liveStudioListManegeVO.getStudioNum()));
			// 进入房间的人次
			Long sGetSetSize = SysBusinessRedisUtils
					.sGetSetSize(WebSocketRedisKeys.studioNum_enter_times + liveStudioListManegeVO.getStudioNum());
			liveStudioListManegeVO.setWatchMem(sGetSetSize.intValue());
			result.add(liveStudioListManegeVO);
		}

		PageInfo<LiveStudioListManegeVO> userInfoPage = new PageInfo<LiveStudioListManegeVO>(result);
		userInfoPage.setPageNum(param.getPageNum());
		userInfoPage.setPageSize(param.getPageSize());
		userInfoPage.setTotal(selectList.size());
		return userInfoPage;
	}

	/**
	 * 获取配置的排序方式 礼物金额， 在线人数， 随机排序， 开播时间
	 * 
	 * @param selectList
	 * @param isThisCountry
	 * @return
	 */
	private List<LiveStudioUserCountVO> getRecommendByOrder(List<LiveStudioListManegeVO> selectList,
			boolean isThisCountry) {
		List<LiveStudioListManegeVO> notFixedList = selectList;
		if (isThisCountry) {
			// 过滤出非固定
			notFixedList = selectList.stream().filter(t -> !t.getIsFixed())
					.collect(Collectors.toList());
			// 过滤出非置底的房间
			notFixedList = notFixedList.stream().filter(t -> t.getIsFirst() != 2)
					.collect(Collectors.toList());
		}

		// 1 在线人数，2礼物金额 ， 3随机排序， 4开播时间
		Integer sortCode = Integer
				.parseInt(sysParameterService.getByCode(SysParameterConstants.LIVE_STUDIO_SORT).getParamValue());

		// 排序的list
		LinkedList<LiveStudioUserCountVO> orderList = new LinkedList<>();
		// 对出非固定的房间按照人数进行倒序排序
		for (LiveStudioListManegeVO liveStudioList : notFixedList) {
			if(sortCode == 1) {
				// 获取每个直播间得真是在线人数
				int score = SysBusinessRedisUtils.hMGetSize(WebSocketRedisKeys.user_list + liveStudioList.getStudioNum())
						.intValue();
				orderList.add(new LiveStudioUserCountVO(liveStudioList.getStudioNum(), score));
			}
			// 2礼物金额
			if (sortCode == 2) {
				// 查询当前直播间的收礼金额
				 Object giftTotalObject = SysBusinessRedisUtils.hGet(WebSocketRedisKeys.live_StudioLog_gift, liveStudioList.getStudioNum());
				 int scoreGift = giftTotalObject == null ? 0 : new BigDecimal(giftTotalObject.toString()).intValue();
				 orderList.add(new LiveStudioUserCountVO(liveStudioList.getStudioNum(), scoreGift));
			}
			// 3随机排序
			if (sortCode == 3) {
				orderList.add(new LiveStudioUserCountVO(liveStudioList.getStudioNum(), RandomUtil.getRandomOne(0, 999 + notFixedList.size())));
			}
			// 4开播时间
			if (sortCode == 4) {
				// 开播时间
				Long startTime = liveStudioList.getStartTime().getTime() / 1000;
				orderList.add(new LiveStudioUserCountVO(liveStudioList.getStudioNum(), startTime.intValue()));
			}
		}

		// 根据人数排序，降序
		orderList.sort(Comparator.comparing(LiveStudioUserCountVO::getScore, Comparator.reverseOrder()));

		// 当前国家
		if (isThisCountry) {
			// 获取置底的房间号
			List<LiveStudioListManegeVO> bottomList = selectList.stream().filter(t -> t.getIsFirst() == 2)
					.collect(Collectors.toList());
			// 根据序号升序
			bottomList.sort(Comparator.comparing(LiveStudioListManegeVO::getSortNum));
			// 添加到房间列表orderList
			for (LiveStudioListManegeVO liveStudioList : bottomList) {
				orderList.addLast(new LiveStudioUserCountVO(liveStudioList.getStudioNum(), 0));
			}

			// 获取固定的房间号
			List<LiveStudioListManegeVO> fixedList = selectList.stream().filter(t -> t.getIsFixed())
					.collect(Collectors.toList());
			// 根据序号升序,插入到指定的位置
			fixedList.sort(Comparator.comparing(LiveStudioListManegeVO::getSortNum));
			for (LiveStudioListManegeVO liveStudioList : fixedList) {
				int addIndex = liveStudioList.getSortNum() - 1 > orderList.size() ? orderList.size()
						: liveStudioList.getSortNum() - 1;
				orderList.add(addIndex, new LiveStudioUserCountVO(liveStudioList.getStudioNum(), 0));
			}
		}

		return new ArrayList<LiveStudioUserCountVO>(orderList);
	}

	/**
	 * 根据房间号 查询真实的在线用户数
	 * 
	 * 
	 * @param studioNum
	 * @return
	 */
	@Override
	public Integer onlineUsersCount(String studioNum) {
		return SysBusinessRedisUtils.hMGetSize(WebSocketRedisKeys.user_list + studioNum).intValue();
	}

	@Override
	public LiveStudioListVo lastSortNum(String merchantCode) {
		return slaveLiveStudioListMapper.lastSortNum(merchantCode);
	}

	/**
	 * 下播 强制主播下线
	 */
	@Override
	@Transactional
	public Boolean breakShow(Long userId) {

		MemUser memUser = memUserService.getById(userId);

		if(memUser==null){
			throw new BusinessException(StatusCode.PARAM_ERROR);
		}

		QueryWrapper<LiveStudioList> queryWrapper = new QueryWrapper<>();
		queryWrapper.lambda().eq(LiveStudioList::getUserId, userId);
		LiveStudioList liveStudioList = slaveLiveStudioListMapper.selectOne(queryWrapper);
		if (liveStudioList == null) {
			return true;
		}
		String studioNum = liveStudioList.getStudioNum();
		// 最新的一条直播记录
		LiveStudioLog liveStudioLog = slaveLiveStudioLogMapper.selectLastByUserId(userId);
		if (liveStudioLog == null || liveStudioLog.getStudioStatus() != 1) {
			return true;
		}
		
		RReadWriteLock lock = redissonClient.getReadWriteLock(RedisKeys.SYNC_LIVE_STUDIO_CLOSE + studioNum);
		try {
			boolean bool = lock.writeLock().tryLock(10, 20, TimeUnit.SECONDS);
			if (bool) {
				// 当前直播间
				if (liveStudioLog.getStudioStatus() == 0) {
					return true;
				}
				// 通知观众主播下播
				try {
					SendMsgVO wordMsgVO = new SendMsgVO();
					wordMsgVO.setContent("当前主播结束直播~");
					wordMsgVO.setOperatorType(7);
					wordMsgVO.setTargetId(studioNum);
					webSocketFeignClient.sendRoomMsg(wordMsgVO);
					webSocketFeignClient.removeRoom(studioNum);
				} catch (Exception e) {
					e.printStackTrace();
					log.error("推送下播消息失败!");
				}
		
				// 更新直播间状态
				UpdateWrapper<LiveStudioList> updateWrapperLiveStudioList = new UpdateWrapper<LiveStudioList>();
				updateWrapperLiveStudioList.lambda().set(LiveStudioList :: getStudioStatus, 0)
						.set(LiveStudioList::getIsFixed,false)
						.set(LiveStudioList::getSortNum,0) //固定位置复位
						.set(LiveStudioList::getIsFirst,0);//推荐/置底复位
				updateWrapperLiveStudioList.lambda().eq(LiveStudioList :: getStudioNum, studioNum);
				this.update(updateWrapperLiveStudioList);
				
				// 主播信息，礼物分成比例
				MemUserAnchor memUserAnchor = memUserAnchorService.getInfoByUserId(userId);
				// 查询主播本场直播收到的收益统计，
				StudioLogLiveGiftDto studioLogLiveGiftDto = liveGiftLogMapper.selectSumBystudioLogId(liveStudioLog.getLogId());
				
				// 主播的提成比例 例如:30 得到礼物总金额的30%
				BigDecimal barrageTotal = studioLogLiveGiftDto.getBarrageTotal();
				BigDecimal giftTotal = studioLogLiveGiftDto.getGiftTotal();
				BigDecimal revenueTotal = barrageTotal.add(giftTotal);
		
				// -----记录直播间闭播信息--start---
				
				
				// TODO 给主播加钱  ↓  ↓  ↓  ↓  ↓  ↓  ↓  ↓  ↓  ↓  ↓  ↓  ↓  ↓  ↓  ↓  ↓  ↓  ↓  ↓  ↓  ↓  ↓  ↓ 
				String earningLogNo = SnowflakeIdWorker.generateShortId();
			    MemAccountChangeVO memAccountChangeVO = new MemAccountChangeVO();
			    memAccountChangeVO.setOrderNo(earningLogNo);
			    memAccountChangeVO.setAccount(memUser.getUserAccount());
			    memAccountChangeVO.setIsSilverBean(PayConstants.GoldTypeEnum.GOLD_TYPE_2.getCode());
			    // 礼物银豆 / 10 = 金币
				memAccountChangeVO.setPrice(revenueTotal.divide(new BigDecimal("10.0")));
			    memAccountChangeVO.setChangeType(PayConstants.AccountChangTypeEnum.LIVE_EARNING32.getPayTypeCode());
			    memAccountChangeVO.setOpNote(liveStudioLog.getLogId().toString());
			    memAccountChangeVO.setFlowType(0);
			    memAccountChangeVO.setDml(new BigDecimal("0"));
			    BalanceChangeDTO flagVo = accountBalanceChangeService.publicAccountBalanceChange(memAccountChangeVO);
			    if (!flagVo.getFlag()) {
			        log.error("liveClose----主播关播入款失败，房间号 ：{} userid：{} 入款银豆总额：{} ", studioNum, LoginInfoUtil.getUserId(), revenueTotal);
			    }
			    // 给主播加钱  ↑ ↑ ↑ ↑ ↑ ↑ ↑ ↑ ↑ ↑ ↑ ↑ ↑ ↑ ↑ ↑ ↑ ↑ ↑ ↑ ↑ ↑ ↑ ↑ ↑ ↑ ↑ ↑ ↑ ↑ ↑ 
				        
		    	// TODO 有效观众数量
				Integer effectiveAudience = SysBusinessRedisUtils.hMGetSize(WebSocketRedisKeys.user_list + studioNum)
						.intValue();
				liveStudioLog.setStudioStatus(0);// 关闭直播
				Date endTime = new Date();
				// 人次
		 		Long sGetSetSize = SysBusinessRedisUtils.sGetSetSize(WebSocketRedisKeys.studioNum_enter_times + studioNum);
				// 直播时长，单位秒
				Long liveTime = (endTime.getTime() - liveStudioLog.getStartTime().getTime()) / 60;
				liveStudioLog.setEndTime(endTime);
				liveStudioLog.setLiveTime(liveTime);
				liveStudioLog.setMoneyNumber(revenueTotal);// 本场直播收到打赏总金额
				liveStudioLog.setEffectiveAudience(effectiveAudience);// 本场直播有效观众数量
				liveStudioLog.setEndReason(2);
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
		
				// 清空缓存数据
				SysBusinessRedisUtils.del(WebSocketRedisKeys.studioNum_Try_See_User_Id + studioNum);
				SysBusinessRedisUtils.del(WebSocketRedisKeys.user_list + studioNum);
				SysBusinessRedisUtils.del(WebSocketRedisKeys.studioNum_enter_times + studioNum);
				SysBusinessRedisUtils.hDel(WebSocketRedisKeys.anchor_studioNum, studioNum);
				// 推荐直播的缓存
				SysBusinessRedisUtils.del(RedisCacheableKeys.ROOM_RECOMMEND_STUDIONUM + "countryCode_" + liveStudioList.getCountryCode());
				SysBusinessRedisUtils.del(RedisCacheableKeys.ROOM_RECOMMEND + "countryCode_" + liveStudioList.getCountryCode());
				// -------返回主播下播----礼物、直播时长、有效观众人数信息---end------------------------
			} else {
				log.error("直播间号:{} 关播没拿到锁, 本次关播失败 ", studioNum);
			}
		} catch (Exception e) {
			log.error("直播间号:" + studioNum + "关播异常, 主播本场直播统计收益失败" , e);
			throw new BusinessException("关播失败，请联系管理员!");
		} finally {
			// 释放锁
			lock.writeLock().unlock();
			log.info("直播间号:{} 关播结束，释放锁 ", studioNum);
		}
		return true;
	}

	/**
	 * 后台指定主播开播调用接口（播放视频）
	 */
	@Override
	@Transactional
	public Boolean beginVideo(LiveBeginForAdminReq req) {

		Long userId = req.getUserId();
		if (StringUtils.isEmpty(req.getStudioTitle())) {
			throw new BusinessException("直播标题不能为空");
		}

		LiveStudioList liveStudioList = this.getByUserId(userId);
		liveStudioList = liveStudioList == null ? new LiveStudioList() : liveStudioList;

		// 如果是已经开启的状态，不可以后台开启直播
		if (liveStudioList.getStudioStatus() != null && liveStudioList.getStudioStatus() == 1) {
			throw new BusinessException("当前主播正在直播！");
		}
		Integer productId = req.getProductId() == null ? 0 : req.getProductId();
		liveStudioList.setUserId(userId);
		liveStudioList.setStudioTitle(req.getStudioTitle());
		liveStudioList.setStudioBackground(req.getStudioBackground());
		liveStudioList.setStudioStatus(1);
		liveStudioList.setCountryCode(req.getCountryCode());
		liveStudioList.setColour(1);
		liveStudioList.setSharpness("720");
		liveStudioList.setProductId(productId);
		liveStudioList.setGameId(req.getGameId());
		liveStudioList.setMerchantCode(req.getMerchantCode());
		String studioNum = liveStudioList.getStudioNum();
		if (StringUtils.isEmpty(studioNum)) {
			// 获取直播间唯一房间号
			studioNum = String.valueOf(SysBusinessRedisUtils.createStudioNum(RedisKeys.LIVE_STUDIO_NUM,
					LiveConstants.LIVE_STUDIO_START_NUM));
			liveStudioList.setStudioNum(studioNum.toString());
		}

		// 更新/保存 主播直播状态
		this.baseMapper.saveOrUpdate(liveStudioList);

		// 添加直播记录
		LiveStudioLog log = new LiveStudioLog();
		log.setUserId(userId);
		log.setStudioStatus(1);// 开播
		log.setStudioNum(studioNum.toString());
		log.setStartTime(new Date());
		log.setMerchantCode(LoginInfoUtil.getMerchantCode());
		log.setGameId(req.getGameId());
		log.setDevice(LoginInfoUtil.getDeviceType());
		log.setProductId(productId);
		liveStudioLogService.save(log);
		// 查询当前主播的总收礼金额：计算火力值
		QueryWrapper<MemUserAnchor> queryWrapperMemUserAnchor = new QueryWrapper<>();
		queryWrapperMemUserAnchor.lambda().eq(MemUserAnchor :: getUserId, userId);
		MemUserAnchor memUserAnchor = memUserAnchorService.getOne(queryWrapperMemUserAnchor);
		Integer studioHeat = memUserAnchor.getGiftTotal().multiply(new BigDecimal(10))
				.setScale(0, BigDecimal.ROUND_DOWN).intValue();
		SysStreamConfig sysStreamConfig = sysStreamConfigService.getUse();
		// 推流地址
		liveStudioList.setStudioLivePushFlow(
				sysStreamConfig.getPushDomainRtmp() + "?key=" + sysStreamConfig.getPushCheckKey());

		// 消息推送给关注当前主播的用户
		pushBeginLiveToUser();
		SysBusinessRedisUtils.del(WebSocketRedisKeys.user_list + studioNum);
		// 主播列表
		SysBusinessRedisUtils.hset(WebSocketRedisKeys.anchor_studioNum, studioNum, 1);
		// 直播间热度：每次送礼后更新该值
		SysBusinessRedisUtils.hset(WebSocketRedisKeys.studioNum_Heat, studioNum, studioHeat);
		
		// 推荐直播的缓存
		SysBusinessRedisUtils.del(RedisCacheableKeys.ROOM_RECOMMEND_STUDIONUM + "countryCode_" + liveStudioList.getCountryCode());
		SysBusinessRedisUtils.del(RedisCacheableKeys.ROOM_RECOMMEND + "countryCode_" + liveStudioList.getCountryCode());
		return true;
	}

	private void pushBeginLiveToUser() {

	}

	@Override
	public LiveStudioList getByUserId(Long userId) {
		QueryWrapper<LiveStudioList> queryWrapper = new QueryWrapper<>();
		queryWrapper.lambda().eq(LiveStudioList::getUserId, userId);
		return slaveLiveStudioListMapper.selectOne(queryWrapper);
	}

	@Override
	public LiveStudioList getByStudioId(Integer studioId) {
		QueryWrapper<LiveStudioList> queryWrapper = new QueryWrapper<>();
		queryWrapper.lambda().eq(LiveStudioList::getStudioId, studioId);
		return slaveLiveStudioListMapper.selectOne(queryWrapper);
	}

	@Override
	public Boolean getThisCountryFixedBySortNum(String countryCode, Integer sortNum) {
		QueryWrapper<LiveStudioList> queryWrapper = new QueryWrapper<>();
		queryWrapper.lambda().eq(LiveStudioList::getCountryCode, countryCode);
		queryWrapper.lambda().eq(LiveStudioList::getIsFixed, true);
		queryWrapper.lambda().eq(LiveStudioList::getSortNum, sortNum);
		return slaveLiveStudioListMapper.selectCount(queryWrapper) > 0;
	}

}
