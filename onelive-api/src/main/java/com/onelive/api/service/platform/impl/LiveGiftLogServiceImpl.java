package com.onelive.api.service.platform.impl;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.onelive.api.service.platform.LiveGiftLogService;
import com.onelive.common.model.dto.platform.LiveGiftLogDto;
import com.onelive.common.model.vo.live.LiveUserDetailVO;
import com.onelive.common.model.vo.ranking.RankingVo;
import com.onelive.common.mybatis.entity.LiveGiftLog;
import com.onelive.common.mybatis.mapper.master.platform.LiveGiftLogMapper;
import com.onelive.common.mybatis.mapper.slave.platform.SlaveLiveGiftLogMapper;
import com.onelive.common.utils.sys.SystemUtil;

/**
 * <p>
 *   	礼物赠送服务实现类
 * </p>
 *
 * @author ${author}
 * @since 2021-11-11
 */
@Service
public class LiveGiftLogServiceImpl extends ServiceImpl<LiveGiftLogMapper, LiveGiftLog> implements LiveGiftLogService {

	@Resource
	private SlaveLiveGiftLogMapper slaveLiveGiftLogMapper;
	
	/**
	 * 查询当前用户对主播的总送礼金额
	 */
	@Override
	public BigDecimal selectUserSumByhostId(Long givingId, Long hostId) {
		return slaveLiveGiftLogMapper.selectUserSumByhostId(givingId, hostId);
	}

	@Override
	public List<LiveUserDetailVO> queryTopgGiveGift(Long hostId) {
		return slaveLiveGiftLogMapper.queryTopgGiveGift(hostId);
	}

	@Override
	public List<RankingVo> roomTodayRanking(Long userId) {
		return slaveLiveGiftLogMapper.roomTodayRanking(userId, SystemUtil.getLangTime());
	}

	@Override
	public List<RankingVo> roomWeekRanking(Long userId) {
		return slaveLiveGiftLogMapper.roomWeekRanking(userId, SystemUtil.getLangTime());
	}

	@Override
	public List<RankingVo> roomMonthRanking(Long userId) {
		return slaveLiveGiftLogMapper.roomMonthRanking(userId, SystemUtil.getLangTime());
	}

	@Override
	public List<RankingVo> roomTotalRanking(Long userId) {
		return slaveLiveGiftLogMapper.roomTotalRanking(userId, SystemUtil.getLangTime());
	}

	@Override
	public List<RankingVo> anchorTodayRanking(boolean isPrevious, boolean isShowfans) {
		return slaveLiveGiftLogMapper.anchorTodayRanking(isPrevious, isShowfans, SystemUtil.getLangTime());
	}

	@Override
	public List<RankingVo> anchorWeekRanking(boolean isPrevious, boolean isShowfans) {
		return slaveLiveGiftLogMapper.anchorWeekRanking(isPrevious, isShowfans, SystemUtil.getLangTime());
	}

	@Override
	public List<RankingVo> anchorMonthRanking(boolean isPrevious, boolean isShowfans) {
		return slaveLiveGiftLogMapper.anchorMonthRanking(isPrevious, isShowfans, SystemUtil.getLangTime());
	}

	@Override
	public List<RankingVo> userTodayRanking(boolean isPrevious) {
		return slaveLiveGiftLogMapper.userTodayRanking(isPrevious, SystemUtil.getLangTime());
	}

	@Override
	public List<RankingVo> userWeekRanking(boolean isPrevious) {
		return slaveLiveGiftLogMapper.userWeekRanking(isPrevious, SystemUtil.getLangTime());
	}

	@Override
	public List<RankingVo> userMonthRanking(boolean isPrevious) {
		return slaveLiveGiftLogMapper.userMonthRanking(isPrevious, SystemUtil.getLangTime());
	}

	@Override
	public Integer getGiftComboTotal(Map<String, Object> parameterMap) {
		return slaveLiveGiftLogMapper.getGiftComboTotal(parameterMap);
	}

	@Override
	public LiveGiftLogDto getFishProductLog(Long userId, Integer productId, Integer logId) {
		return slaveLiveGiftLogMapper.getFishProductLog(userId, productId, logId);
	}

}
