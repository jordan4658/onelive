package com.onelive.anchor.service;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.IService;
import com.onelive.common.model.vo.live.LiveHistoryVo;
import com.onelive.common.mybatis.entity.LiveStudioLog;


public interface LiveStudioLogService extends IService<LiveStudioLog> {

	/**
	 *  	查询直播间最后一场直播
	 * @param studioNum
	 * @return
	 */
	LiveStudioLog selectLastByStudioNum(String studioNum);

	/**
	 *   根据主播的用户id查询最近一条 直播记录
	 * @param hostId
	 * @return
	 */
	LiveStudioLog selectLastByUserId(Long hostId);

	/**
	 * 	主播查看自己的直播记录，最近三十天
	 * @param userId
	 * @return
	 */
	List<LiveHistoryVo> liveHistory(Long userId);
	
}
