package com.onelive.anchor.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.onelive.common.model.common.ResultInfo;
import com.onelive.common.model.dto.index.LiveCloseDto;
import com.onelive.common.model.vo.live.LiveStudioForBegin;
import com.onelive.common.mybatis.entity.LiveStudioList;

/**
 * 直播间
 * 
 * @author mao
 *
 */
public interface LiveStudioListService extends IService<LiveStudioList> {

	/**
	 * 主播开播调用接口
	 */
	ResultInfo<LiveStudioForBegin> getBeginToLive(String studioTitle, String studioBackground, String studioThumbImage,
			String countryCode, Integer colour, String sharpness, Integer prodtcuId, Integer trySeeTime, Long gameId);

	/**
	 * 主播下播调用接口
	 */
	ResultInfo<LiveCloseDto> liveClose(String studioNum, Long userId, String deviceType, Integer endReason);

	
	LiveStudioList getByUserId(Long userId);
}
