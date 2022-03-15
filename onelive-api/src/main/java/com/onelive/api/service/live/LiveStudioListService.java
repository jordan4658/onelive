package com.onelive.api.service.live;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.IService;
import com.onelive.common.model.req.live.LiveColumnCodeReq;
import com.onelive.common.model.req.live.LiveListReq;
import com.onelive.common.model.vo.live.LiveStudioListForIndexVO;
import com.onelive.common.mybatis.entity.LiveStudioList;

import java.util.Collection;
import java.util.List;
import java.util.Set;

/**
 * 	直播间
 * 
 * @author mao
 *
 */
public interface LiveStudioListService extends IService<LiveStudioList> {
	
	/**
	 * 获取推荐的直播房间列表
	 * 
	 * @return
	 * @
	 */
	List<LiveStudioListForIndexVO> getLiveRecommendListVO(String studioNum) ;

	/**
	 * 获取关注页签推荐的直播房间列表
	 * 
	 * @return
	 * @
	 */
	List<LiveStudioListForIndexVO> getLiveFocusRecommendList() ;

	/**
	 * 通过房间号获取房间信息
	 * 
	 * @param studioNum
	 * @return
	 * @
	 */
	LiveStudioList getByRoomNum(String studioNum);

	/**
	 * 	列表用 ，显示直播间的在线用户数量
	 * 
	 * 
	 * @param studioNum
	 * @return
	 */
	Integer onlineUsersCount(String studioNum);
	
	/**
	 * 	根据主播的userid查询直播间
	 * 
	 * @param userId
	 * @return
	 */
	LiveStudioList getByUserId(Long userId);

	/**
	 * 	获取当前所有收费房间号
	 * 
	 * @return
	 */
	Set<String> getChargeRoom();

	/**
	 * 根据房间号查询正在直播的房间
	 * 
	 * @param liveStudioNums
	 * @return
	 */
	List<LiveStudioListForIndexVO> getLiveByStudioNums(Collection<String> liveStudioNums);
	
	/**
	 * 
	 * 	推荐主播:根据主播火力值，排序根据后台设置的 ，固定，置底
	 * 
	 * 			:排除光年推荐4个,推荐区的四个
	 * 
	 * @return
	 */
	List<LiveStudioListForIndexVO> getRecommendByHeat(LiveListReq req);
	
	
	// ↓ ↓ ↓ ↓ ↓ ↓ ↓ ↓ ↓ ↓ ↓ 根据栏目code查询直播间列表↓ ↓ ↓ ↓ ↓ ↓ ↓ ↓ ↓ ↓ ↓ ↓ ↓ ↓ ↓ ↓ ↓ ↓ ↓ ↓ ↓ ↓ ↓ ↓ ↓ ↓ ↓ ↓ ↓ ↓ ↓ ↓ ↓ ↓ ↓ ↓ ↓ ↓ 

	
	/**
	 * 获取热门的直播房间列表
	 * 
	 * @return
	 * @
	 */
	List<LiveStudioListForIndexVO> getHotList(LiveColumnCodeReq req) ;
	
	/**
	 * 获取附近的直播房间列表,与用户同一国家的直播间
	 * 
	 * @return
	 * @
	 */
	List<LiveStudioListForIndexVO> getNearbyList(LiveColumnCodeReq req) ;

	/**
	 * 星秀，即：黄播
	 * 
	 * @param req
	 * @return
	 */
	List<LiveStudioListForIndexVO> getStarList(LiveColumnCodeReq req);
	
	/**
	 * 	直播中有游戏的房间
	 * 
	 * @param req
	 */
	List<LiveStudioListForIndexVO> getGameList(LiveColumnCodeReq req);
	
	// ↑ ↑ ↑ ↑ ↑ ↑ ↑ ↑ ↑ ↑ ↑ 根据栏目code查询直播间列表 ↑ ↑ ↑ ↑ ↑ ↑ ↑ ↑ ↑ ↑ ↑ ↑ ↑ ↑ ↑ ↑ ↑ ↑ 
	/**
	 * 	光年推荐(最多四个，人数前四的游戏直播间)
	 * 
	 * @return
	 */
	List<LiveStudioListForIndexVO> getPromotion();
	
	/**
	 * 	通用根据房间号查询直播间信息，根据在线人数排序
	 * 	
	 * @param liveStudioListList 直播间列表，包含countryId,当前国家的直播间优先排序 可为空
	 * @param studioNum 可为空
	 * @param pageSize 查询的条数，为空默认studioNums.size条
	 * @param orderBy  根据排序的字段 1：在线人数  2：火力值
	 * @return
	 */
	List<LiveStudioListForIndexVO> studioListByOrder(List<LiveStudioList> liveStudioListList, String studioNum,
			Integer pageSize, Integer orderBy);

	/**
	 * 	用户关注的正在直播的房间
	 * 
	 * @param req
	 * @return
	 */
	List<LiveStudioListForIndexVO> getLiveFocusListVO(LiveListReq req);

	/**
	 * 	动态查询
	 * 
	 * @param queryWrapper
	 * @return
	 */
	List<LiveStudioList> getByQueryWrapper(QueryWrapper<LiveStudioList> queryWrapper);

	/**
	 * 	 推荐区的固定四个推荐直播
	 * 
	 * @return
	 */
	List<LiveStudioListForIndexVO> getRecommend(String countryCode);
	
	/**
	 * 	 获取推荐的房间号
	 * 
	 * @return
	 */
	List<LiveStudioList> getRecommendStudioNum(String countryCode);


	/**
	 * 推荐列表数据查询(新)
	 * @param req
	 * @return
	 */
	List<LiveStudioListForIndexVO> getRecommendByHeatNew(LiveListReq req);
}
