package com.onelive.api.modules.ranking.business;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

import com.onelive.api.service.platform.LiveGiftLogService;
import com.onelive.common.model.req.live.RankingReq;
import com.onelive.common.model.vo.ranking.RankingVo;
import com.onelive.common.utils.upload.AWSS3Util;

/**
 * @author mao TODO： 时间地区优化 TODO： 查询优化：放缓存，每次用户送礼后查询该缓存，比较大小，判断是否进行数据重置
 */
@Component
@Scope(proxyMode = ScopedProxyMode.TARGET_CLASS)
public class RankingBusiness {

	@Resource
	private LiveGiftLogService liveGiftLogService;

	/**
	 * 直播间内的用户贡献榜单
	 * 
	 * @param rankingReq
	 * @return
	 */
	public List<RankingVo> room(RankingReq rankingReq) {
		List<RankingVo> rankingList = new ArrayList<>();
		// 当天
		if (rankingReq.getType() == 1) {
			rankingList = liveGiftLogService.roomTodayRanking(rankingReq.getUserId());
		}
		// 本周
		if (rankingReq.getType() == 2) {
			rankingList = liveGiftLogService.roomWeekRanking(rankingReq.getUserId());
		}
		// 本月
		if (rankingReq.getType() == 3) {
			rankingList = liveGiftLogService.roomMonthRanking(rankingReq.getUserId());
		}
		// 总的
		if (rankingReq.getType() == 4) {
			rankingList = liveGiftLogService.roomTotalRanking(rankingReq.getUserId());
		}
		return handleRankingVo(rankingList);
	}

	/**
	 * 通用处理
	 * 
	 * @param rankingList
	 * @return
	 */
	private List<RankingVo> handleRankingVo(List<RankingVo> rankingList) {
		// 排序：火力值由大到小
		rankingList.sort((x, y) -> Integer.compare(y.getFirepower(), x.getFirepower()));
		for (int i = 0; i < rankingList.size(); i++) {
			rankingList.get(i).setAvatar(AWSS3Util.getAbsoluteUrl(rankingList.get(i).getAvatar()));
			rankingList.get(i).setPosition(i + 1);
			int distance = 0;
			// 如果不是第一名
			if (i != 0) {
				distance = rankingList.get(i - 1).getFirepower() - rankingList.get(i).getFirepower();
			}
			rankingList.get(i).setDistance(distance);
		}
		RankingVo rankingVo1 = new RankingVo();
		rankingVo1.setPosition(1);
		rankingVo1.setUserId(1L);
		rankingVo1.setNickName("mao1");
		rankingVo1.setDistance(0);
		rankingVo1.setSex(1);
		rankingVo1.setUserLevel(5);
		rankingVo1.setAvatar("https://onelive.s3.ap-northeast-2.amazonaws.com/oneLive_image/photo_2021-10-18_15-33-13.jpg");
		rankingVo1.setFirepower(9999);
		rankingVo1.setFansCount(5959);
		RankingVo rankingVo2 = new RankingVo();
		rankingVo2.setPosition(2);
		rankingVo2.setUserId(2L);
		rankingVo2.setNickName("mao2");
		rankingVo2.setDistance(10);
		rankingVo2.setSex(2);
		rankingVo2.setUserLevel(3);
		rankingVo2.setAvatar("https://onelive.s3.ap-northeast-2.amazonaws.com/oneLive_image/photo_2021-10-18_15-33-13.jpg");
		rankingVo2.setFirepower(8656);
		rankingVo2.setFansCount(1121);
		RankingVo rankingVo3 = new RankingVo();
		rankingVo3.setPosition(3);
		rankingVo3.setUserId(3L);
		rankingVo3.setNickName("mao3");
		rankingVo3.setDistance(320);
		rankingVo3.setSex(1);
		rankingVo3.setUserLevel(2);
		rankingVo3.setAvatar("https://onelive.s3.ap-northeast-2.amazonaws.com/oneLive_image/photo_2021-10-18_15-33-13.jpg");
		rankingVo3.setFirepower(5651);
		rankingVo3.setFansCount(11);
		RankingVo rankingVo4 = new RankingVo();
		rankingVo4.setPosition(4);
		rankingVo4.setUserId(4L);
		rankingVo4.setNickName("mao4");
		rankingVo4.setDistance(506);
		rankingVo4.setSex(2);
		rankingVo4.setUserLevel(1);
		rankingVo4.setAvatar("https://onelive.s3.ap-northeast-2.amazonaws.com/oneLive_image/photo_2021-10-18_15-33-13.jpg");
		rankingVo4.setFirepower(1250);
		rankingVo4.setFansCount(25);
		
		rankingList.add(rankingVo1);
		rankingList.add(rankingVo2);
		rankingList.add(rankingVo3);
		rankingList.add(rankingVo4);
		
		return rankingList;
	}

	/**
	 * 	主播排行榜
	 * 
	 * @param rankingReq
	 * @return
	 */
	public List<RankingVo> anchor(RankingReq rankingReq) {
		List<RankingVo> rankingList = new ArrayList<>();
		// 当天
		if (rankingReq.getType() == 1) {
			rankingList = liveGiftLogService.anchorTodayRanking(rankingReq.getIsPrevious(), rankingReq.getIsShowfans());
		}
		// 本周
		if (rankingReq.getType() == 2) {
			rankingList = liveGiftLogService.anchorWeekRanking(rankingReq.getIsPrevious(), rankingReq.getIsShowfans());
		}
		// 本月
		if (rankingReq.getType() == 3) {
			rankingList = liveGiftLogService.anchorMonthRanking(rankingReq.getIsPrevious(), rankingReq.getIsShowfans());
		}
		return handleRankingVo(rankingList);
	}

	/**
	 * 土豪榜，总的用户排行榜
	 * 
	 * @param rankingReq
	 * @return
	 */
	public List<RankingVo> user(RankingReq rankingReq) {
		List<RankingVo> rankingList = new ArrayList<>();
		// 当天
		if (rankingReq.getType() == 1) {
			rankingList = liveGiftLogService.userTodayRanking(rankingReq.getIsPrevious());
		}
		// 本周
		if (rankingReq.getType() == 2) {
			rankingList = liveGiftLogService.userWeekRanking(rankingReq.getIsPrevious());
		}
		// 本月
		if (rankingReq.getType() == 3) {
			rankingList = liveGiftLogService.userMonthRanking(rankingReq.getIsPrevious());
		}
		return handleRankingVo(rankingList);
	}

}
