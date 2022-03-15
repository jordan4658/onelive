package com.onelive.manage.service.live;

import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.PageInfo;
import com.onelive.common.model.req.live.LiveBeginForAdminReq;
import com.onelive.common.model.req.live.LiveStudioListReq;
import com.onelive.common.model.vo.live.LiveStudioListManegeVO;
import com.onelive.common.model.vo.live.LiveStudioListVo;
import com.onelive.common.mybatis.entity.LiveStudioList;

public interface LiveStudioListService extends IService<LiveStudioList> {

	/**
	 * 	后台管理查询实时直播间
	 * @param param
	 * @return
	 */
	PageInfo<LiveStudioListManegeVO> getList(LiveStudioListReq param);

	LiveStudioListVo lastSortNum(String merchantCode);

	/**
	 * 	主播下线 
	 * @return
	 */
	Boolean breakShow(Long userId);

	
	/**
	 * 	当前用主播间的在线用户数量
	 * @param studioNum
	 * @return
	 */
	Integer onlineUsersCount(String studioNum);
	
	/**
	 * 	后台指定视频播放，开启直播
	 * 
	 * @param req
	 * @return
	 */
	Boolean beginVideo(LiveBeginForAdminReq req);

	/**
	 * 	根据用户id查询直播间信息
	 * @param id
	 * @return
	 */
	LiveStudioList getByUserId(Long userId);

	/**	
	 * 	根据id查询基本信息
	 * @param studioId
	 * @return
	 */
	LiveStudioList getByStudioId(Integer studioId);

	/**
	 * 	查询当前国家的固定位置的直播间是否存在
	 * @param countryCode
	 * @param sortNum
	 * @return
	 */
	Boolean getThisCountryFixedBySortNum(String countryCode, Integer sortNum);
}
