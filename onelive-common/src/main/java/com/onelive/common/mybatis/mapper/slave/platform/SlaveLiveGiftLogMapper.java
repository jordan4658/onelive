package com.onelive.common.mybatis.mapper.slave.platform;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.onelive.common.model.dto.platform.LiveGiftLogDetailDto;
import com.onelive.common.model.dto.platform.LiveGiftLogDto;
import com.onelive.common.model.dto.report.LiveGifReportDto;
import com.onelive.common.model.vo.live.LiveUserDetailVO;
import com.onelive.common.model.vo.ranking.RankingVo;
import com.onelive.common.mybatis.entity.LiveGiftLog;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author ${author}
 * @since 2021-11-11
 */
public interface SlaveLiveGiftLogMapper extends BaseMapper<LiveGiftLog> {

	/**
	 * 	查询赠送礼物给主播前50人,用户列表用，显示前50个人
	 * 
	 * @param hostId
	 * @return
	 */
	List<LiveUserDetailVO> queryTopgGiveGift(Long hostId);
	
	/**
	 * 		根据用户id + 主播id查询 当前用户给主播送礼总额
	 * 
	 * @param givingId  : null :即只查询主播的所有收礼
	 * @param hostId	: null :即只查询用户的所有送礼
	 * @return
	 */
	BigDecimal selectUserSumByhostId(Long givingId, Long hostId);

	/**
	 * 	当日贡献榜前三十
	 * 
	 * @param userId
	 * @return
	 */
	List<RankingVo> roomTodayRanking(Long userId, Date givingLocalTime);

	/**
	 * 	本周贡献榜前三十
	 * @param userId
	 * @return
	 */
	List<RankingVo> roomWeekRanking(Long userId, Date givingLocalTime);
	
	/**
	 * 	本月贡献榜前三十
	 * @param userId
	 * @return
	 */
	List<RankingVo> roomMonthRanking(Long userId, Date givingLocalTime);

	/**
	 * 	主播总贡献榜
	 * 
	 * @param userId
	 * @return
	 */
	List<RankingVo> roomTotalRanking(Long userId, Date givingLocalTime);

	/**
	 * 	主播《日》收礼榜查询
	 * @param isPrevious 
	 * 
	 * @return
	 */
	List<RankingVo> anchorTodayRanking(Boolean isPrevious, boolean isShowfans, Date givingLocalTime);
	
	/**
	 * 	主播《周》收礼榜查询
	 * @param isPrevious 
	 * @return
	 */
	List<RankingVo> anchorWeekRanking(Boolean isPrevious, boolean isShowfans, Date givingLocalTime);

	/**
	 * 	主播《月》收礼榜查询
	 * @param isPrevious 
	 * @return
	 */
	List<RankingVo> anchorMonthRanking(Boolean isPrevious, boolean isShowfans, Date givingLocalTime);

	/**
	 * 		用户《日》送礼榜查询
	 * @param isPrevious
	 * @return
	 */
	List<RankingVo> userTodayRanking(Boolean isPrevious, Date givingLocalTime);

	/**
	 * 		用户《周》送礼榜查询
	 * @param isPrevious
	 * @return
	 */
	List<RankingVo> userWeekRanking(Boolean isPrevious, Date givingLocalTime);

	/**
	 * 		用户《月》送礼榜查询
	 * @param isPrevious
	 * @return
	 */
	List<RankingVo> userMonthRanking(Boolean isPrevious, Date givingLocalTime);

	/**
	 * 		后台查询礼物报表
	 * @param liveGifReportDto
	 * @return
	 */
	List<LiveGifReportDto> getReportList(LiveGifReportDto liveGifReportDto);


    Integer getGiftComboTotal(Map<String, Object> parameterMap);

	/**
	 * 查询用户当前直播商品的消费
	 * @param userId
	 * @param productId
	 * @param logId
	 * @return
	 */
    LiveGiftLogDto getFishProductLog(Long userId, Integer productId, Integer logId);

    /**
     * 	礼物记录查询,一次赠送一个礼物
     * @param param
     * @return
     */
    List<LiveGiftLogDetailDto> getList(LiveGiftLogDetailDto param);
}
