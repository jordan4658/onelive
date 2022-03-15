package com.onelive.api.common.client.live.contoller;

import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import com.onelive.common.annotation.SupperAccess;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.onelive.api.common.client.live.business.LiveFeignBusiness;
import com.onelive.api.modules.live.business.RoomBusiness;
import com.onelive.api.modules.ranking.business.RankingBusiness;
import com.onelive.common.model.common.ResultInfo;
import com.onelive.common.model.dto.platform.KickingDto;
import com.onelive.common.model.req.live.BannedReq;
import com.onelive.common.model.req.live.LiveStudioNumReq;
import com.onelive.common.model.req.live.RankingAnchorReq;
import com.onelive.common.model.req.live.RankingReq;
import com.onelive.common.model.vo.live.LiveUserDetailVO;
import com.onelive.common.model.vo.ranking.RankingVo;
import com.onelive.common.utils.others.BeanCopyUtil;

@RequestMapping("/onelive-api/live")
@RestController
public class LiveFeignController {

	@Resource
	private RankingBusiness rankingBusiness;

	@Resource
	private LiveFeignBusiness liveFeignBusiness;

	@Autowired
	private RoomBusiness roomBusiness;

	@PostMapping("/liveChargeUser")
	public Set<Long> liveChargeUser() {
		return liveFeignBusiness.liveChargeUser();
	}

	@PostMapping("/room/kicking")
	public ResultInfo<Boolean> kicking(@RequestBody KickingDto kickingDto) {
		return roomBusiness.kicking(kickingDto);
	}

	@PostMapping("/room/banned")
	public ResultInfo<Boolean> banned(@RequestBody BannedReq bannedReq) {
		return roomBusiness.banned(bannedReq);
	}

	@PostMapping("/room/onlineUsers")
	public List<LiveUserDetailVO> onlineUsers(@RequestBody LiveStudioNumReq liveStudioNumReq) {
		return roomBusiness.onlineUsers(liveStudioNumReq);
	}

	@PostMapping("/rankingAnchor")
	@SupperAccess
	public List<RankingVo> rankingAnchor(@RequestBody RankingAnchorReq rankingReq) {
		RankingReq queryDto = new RankingReq();
		BeanCopyUtil.copyProperties(rankingReq, queryDto);
		queryDto.setIsShowfans(true);
		return rankingBusiness.anchor(queryDto);
	}

}
