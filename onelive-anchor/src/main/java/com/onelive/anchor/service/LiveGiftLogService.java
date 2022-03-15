package com.onelive.anchor.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.extension.service.IService;
import com.onelive.common.model.dto.platform.LiveGiftLogDto;
import com.onelive.common.model.dto.platform.StudioLogLiveGiftDto;
import com.onelive.common.model.vo.live.LiveUserDetailVO;
import com.onelive.common.model.vo.ranking.RankingVo;
import com.onelive.common.mybatis.entity.LiveGiftLog;

/**
 * <p>
 *  		送礼记录服务类
 * </p>
 *
 * @author ${author}
 * @since 2021-11-11
 */
public interface LiveGiftLogService extends IService<LiveGiftLog> {

	StudioLogLiveGiftDto selectSumBystudioLogId(Integer logId);
	
	/**
	 * 		根据用户id + 主播id查询 当前用户给主播送礼总额
	 * 
	 * @param givingId  : null :即只查询主播的所有收礼
	 * @param hostId	: null :即只查询用户的所有送礼
	 * @return
	 */
	BigDecimal selectUserSumByhostId(Long givingId, Long hostId);

	/**
	 * 	查询赠送礼物给主播前50人,用户列表用，显示前50个人
	 * 
	 * @param hostId
	 * @return
	 */
	List<LiveUserDetailVO> queryTopgGiveGift(Long hostId);

	/**
	 * 		主播当天贡献榜
	 * 
	 * @param userId
	 * @return
	 */
	List<RankingVo> roomTodayRanking(Long userId);

	/**
	 * 		主播本周贡献榜
	 * 
	 * @param userId
	 * @return
	 */
	List<RankingVo> roomWeekRanking(Long userId);

	/**
	 * 		主播本月贡献榜
	 * 
	 * @param userId
	 * @return
	 */
	List<RankingVo> roomMonthRanking(Long userId);

	/**
	 * 		主播总贡献榜
	 * 
	 * @param userId
	 * @return
	 */
	List<RankingVo> roomTotalRanking(Long userId);

	/**
	 * 		用户《日》送礼榜查询
	 * @param previous
	 * @return
	 */
	List<RankingVo> userTodayRanking(boolean isPrevious);

	/**
	 * 		用户《周》送礼榜查询
	 * @param previous
	 * @return
	 */
	List<RankingVo> userWeekRanking(boolean previous);

	/**
	 * 		用户《月》送礼榜查询
	 * @param previous
	 * @return
	 */
	List<RankingVo> userMonthRanking(boolean previous);

	/**
	 * 	 统计连击后礼物数量
	 * @param parameterMap
	 * @return
	 */
	Integer getGiftComboTotal(Map<String, Object> parameterMap);

	/**
	 * 查询用户当前直播商品的消费
	 * @param userId
	 * @param productId
	 * @param logId
	 * @return
	 */
	LiveGiftLogDto getFishProductLog(Long userId, Integer productId, Integer logId);


}
