package com.onelive.common.mybatis.mapper.slave.live;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.onelive.common.model.dto.index.LiveStudioListForIndexDTO;
import com.onelive.common.model.req.live.LiveStudioListReq;
import com.onelive.common.model.vo.live.LiveStudioListForIndexVO;
import com.onelive.common.model.vo.live.LiveStudioListManegeVO;
import com.onelive.common.model.vo.live.LiveStudioListVo;
import com.onelive.common.mybatis.entity.LiveStudioList;
import org.apache.ibatis.annotations.Param;

import java.util.Collection;
import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author ${author}
 * @since 2021-10-19
 */
public interface SlaveLiveStudioListMapper extends BaseMapper<LiveStudioList> {
	
    List<LiveStudioListManegeVO> getList(LiveStudioListReq param);

	/**
	 * 	推荐主播:根据主播火力值
	 * @return
	 */
	List<LiveStudioListForIndexVO> getRecommendByHeat(String lang);

	/**
	 * 	获取关注页签推荐的主播
	 * @param merchantCode
	 * @param lang 
	 * @param userId 
	 */
	List<LiveStudioListForIndexVO> getLiveFocusRecommendList(String merchantCode, String lang, Long userId);


	LiveStudioListVo lastSortNum(String merchantCode);

	/**
	 * 	根据房间号查询正在直播的房间
	 * @param liveStudioNums
	 * @return
	 */
	List<LiveStudioListForIndexVO> getLiveByStudioNums(Collection<String> liveStudioNums, String lang);

	/**
	 * 	获取当前用户关注的，并且在直播的直播间num
	 * 
	 * @param userId
	 * @return
	 */
	List<LiveStudioList> focusStudioNums(Long userId);

	/**
	 * 	所有在直播的房间
	 * 
	 * @return
	 */
	List<LiveStudioList> getAllOnline();

	/**
	 * 	为你推荐的直播间，不包括当前的直播间
	 * 
	 * @param merchantCode
	 * @param lang
	 * @param userId
	 * @param studioNum
	 * @return
	 */
	List<LiveStudioListForIndexVO> getLiveRecommendList(String merchantCode, String lang, String studioNum);

    List<LiveStudioListForIndexDTO> getRecommendByHeatNew(@Param("lang") String lang);
}
