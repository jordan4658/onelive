package com.onelive.common.client;

import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Component;

import com.onelive.common.model.common.ResultInfo;
import com.onelive.common.model.dto.platform.KickingDto;
import com.onelive.common.model.req.live.BannedReq;
import com.onelive.common.model.req.live.LiveStudioNumReq;
import com.onelive.common.model.req.live.RankingAnchorReq;
import com.onelive.common.model.vo.live.LiveUserDetailVO;
import com.onelive.common.model.vo.ranking.RankingVo;

@Component
public class OneliveApiFeignCallBack implements OneliveApiFeignClient {

	@Override
	public Set<Long> liveChargeUser() {
		return null;
	}

	@Override
	public ResultInfo<Boolean> kicking(KickingDto kickingDto) {
		return null;
	}

	@Override
	public ResultInfo<Boolean> banned(BannedReq bannedReq) {
		return null;
	}

	@Override
	public List<LiveUserDetailVO> onlineUsers(LiveStudioNumReq liveStudioNumReq) {
		return null;
	}

	@Override
	public List<RankingVo> rankingAnchor(RankingAnchorReq rankingReq) {
		return null;
	}

}
