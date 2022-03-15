package com.onelive.common.client;

import java.util.List;
import java.util.Set;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.onelive.common.constants.other.ServerConstants;
import com.onelive.common.model.common.ResultInfo;
import com.onelive.common.model.dto.platform.KickingDto;
import com.onelive.common.model.req.live.BannedReq;
import com.onelive.common.model.req.live.LiveStudioNumReq;
import com.onelive.common.model.req.live.RankingAnchorReq;
import com.onelive.common.model.vo.live.LiveUserDetailVO;
import com.onelive.common.model.vo.ranking.RankingVo;

@FeignClient(value = ServerConstants.ONELIVE_API, fallback = OneliveApiFeignCallBack.class)
public interface OneliveApiFeignClient {

	String ONELIVE_API_PREFIX = "/onelive-api";

	/**
	 *	收费房间扣钱
	 *
	 * @return
	 */
	@PostMapping(value = ONELIVE_API_PREFIX + "/live/liveChargeUser")
	Set<Long> liveChargeUser();

	/**
	 * 	主播房间踢人
	 * 
	 * @param kickingDto
	 * @return
	 */
	@PostMapping(value = ONELIVE_API_PREFIX + "/live/room/kicking")
	ResultInfo<Boolean> kicking(@RequestBody KickingDto kickingDto);

	/**
	 * 	主播禁言用户
	 * 
	 * @param bannedReq
	 * @return
	 */
	@PostMapping(value = ONELIVE_API_PREFIX + "/live/room/banned")
	ResultInfo<Boolean> banned(@RequestBody BannedReq bannedReq);

	/**
	 * 	主播查看自己房间前50名用户
	 * 
	 * @return
	 */
	@PostMapping(value = ONELIVE_API_PREFIX + "/live/room/onlineUsers")
	List<LiveUserDetailVO> onlineUsers(@RequestBody LiveStudioNumReq liveStudioNumReq);
	
	/**
	 * 	主播排行榜
	 * 
	 * @return
	 */
	@PostMapping(value = ONELIVE_API_PREFIX + "/live/rankingAnchor")
	List<RankingVo> rankingAnchor(@RequestBody RankingAnchorReq rankingReq);

}
