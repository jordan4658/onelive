package com.onelive.anchor.modules.live.controller;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.onelive.anchor.modules.live.busiess.LiveBusiness;
import com.onelive.common.annotation.AllowAccess;
import com.onelive.common.client.OneliveApiFeignClient;
import com.onelive.common.model.common.ResultInfo;
import com.onelive.common.model.dto.platform.KickingDto;
import com.onelive.common.model.req.live.BannedReq;
import com.onelive.common.model.req.live.LiveBeginReq;
import com.onelive.common.model.req.live.LiveStudioNumReq;
import com.onelive.common.model.req.live.LiveSwitchChargeReq;
import com.onelive.common.model.req.live.RoomSetAdminReq;
import com.onelive.common.model.vo.live.LiveStudioForBegin;
import com.onelive.common.model.vo.live.LiveUserDetailVO;
import com.onelive.common.model.vo.lottery.LotteryRoomVO;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/live")
@Api(tags = "主播直播相关接口")
public class LiveController {

	@Autowired
	private LiveBusiness liveBusiness;
	
	@Resource
	private OneliveApiFeignClient oneliveApiFeignClient;

	@PostMapping("/app/v1/begin")
	@ApiOperation("主播开播")
	@ResponseBody
	public ResultInfo<LiveStudioForBegin> begin(@RequestBody LiveBeginReq req) throws Exception {
		ResultInfo<LiveStudioForBegin> vo = liveBusiness.begin(req);
		return vo;
	}
	
	@ApiOperation("主播开房间时候，可以选择的彩票列表")
    @PostMapping("/app/v1/getLottery")
    @AllowAccess
    public ResultInfo<List<LotteryRoomVO>> getLottery() {
       return ResultInfo.ok(liveBusiness.getLottery());
    }

	@PostMapping("/app/v1/switchCharge")
	@ApiOperation("主播开启/关闭直播间状态为收费")
	@ResponseBody
	public ResultInfo<Boolean> switchCharge(@RequestBody LiveSwitchChargeReq req) throws Exception {
		return ResultInfo.ok(liveBusiness.switchCharge(req));
	}

	@PostMapping("/app/v1/setAdmin")
	@ApiOperation("主播设置/取消用户管理员")
	@ResponseBody
	public ResultInfo<Boolean> setAdmin(@RequestBody RoomSetAdminReq roomSetAdminReq) {
		ResultInfo<Boolean> result = liveBusiness.setAdmin(roomSetAdminReq);
		return result;
	}
	
	//远程调用
	@PostMapping("/app/v1/kicking")
	@ApiOperation("管理员/主播踢人")
	@ResponseBody
	public ResultInfo<Boolean> kicking(@RequestBody KickingDto kickingDto) {
		return oneliveApiFeignClient.kicking(kickingDto);
	}

	@PostMapping("/app/v1/banned")
	@ApiOperation("管理员/主播禁言观众")
	@ResponseBody
	public ResultInfo<Boolean> banned(@RequestBody BannedReq bannedReq) {
		return oneliveApiFeignClient.banned(bannedReq);
	}
	
	@PostMapping("/app/v1/onlineUsers")
	@ApiOperation("查询直播间在线观众50个")
	@ResponseBody
	public ResultInfo<List<LiveUserDetailVO>> onlineUsers(@RequestBody LiveStudioNumReq liveStudioNumReq) {
		return ResultInfo.ok(oneliveApiFeignClient.onlineUsers(liveStudioNumReq));
	}

}
