package com.onelive.api.modules.live.controller;

import java.util.List;

import com.onelive.common.model.vo.live.BuyProductVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.onelive.api.modules.live.business.RoomBusiness;
import com.onelive.common.annotation.AllowAccess;
import com.onelive.common.enums.StatusCode;
import com.onelive.common.model.common.ResultInfo;
import com.onelive.common.model.dto.live.RoomFastWordsDto;
import com.onelive.common.model.dto.platform.GiftGivingDto;
import com.onelive.common.model.dto.platform.KickingDto;
import com.onelive.common.model.dto.platform.LiveFloatForIndexDto;
import com.onelive.common.model.dto.platform.LiveGiftForIndexDto;
import com.onelive.common.model.dto.platform.ProductForRoomDto;
import com.onelive.common.model.dto.platform.SendBarrageDto;
import com.onelive.common.model.req.live.BannedReq;
import com.onelive.common.model.req.live.GiftGivingComboEndReq;
import com.onelive.common.model.req.live.GiftGivingReq;
import com.onelive.common.model.req.live.LiveStudioNumReq;
import com.onelive.common.model.req.platform.GiftgCheckDto;
import com.onelive.common.model.vo.live.LiveUserDetailVO;
import com.onelive.common.utils.others.StringUtils;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/room")
@Api(tags = "直播间房间内相关接口")
public class RoomController {

	@Autowired
	private RoomBusiness roomBusiness;

	@PostMapping(value = { "/app/v1/giftList", "/pc/v1/giftList" })
	@ApiOperation("直播礼物列表")
	@ResponseBody
	@AllowAccess
	public ResultInfo<List<LiveGiftForIndexDto>> getGiftList() {
		return ResultInfo.ok(roomBusiness.getGiftList());
	}
	
	@PostMapping(value = { "/app/v1/floatList", "/pc/v1/floatList" })
	@ApiOperation("直播间内悬浮窗")
	@ResponseBody
	@AllowAccess
	public ResultInfo<List<LiveFloatForIndexDto>> floatList() {
		return ResultInfo.ok(roomBusiness.getFloatList());
	}
	

	@PostMapping(value = { "/app/v1/giftGiving", "/pc/v1/giftGiving" })
	@ApiOperation("礼物打赏接口")
	@ResponseBody
	public ResultInfo<GiftGivingDto> giftGiving(@RequestBody GiftGivingReq giftGivingReq) throws Exception {
		return roomBusiness.giftGiving(giftGivingReq);
	}

	@PostMapping(value = { "/app/v1/checkGift", "/pc/v1/checkGift" })
	@ApiOperation("礼物校验接口：当前礼物是否可赠送，不可赠送需刷新礼物列表")
	@ResponseBody
	public ResultInfo<Boolean> checkGift(@RequestBody GiftgCheckDto giftgCheckDto) throws Exception {
		return roomBusiness.checkGift(giftgCheckDto);
	}

	@PostMapping(value = { "/app/v1/sendBarrage", "/pc/v1/sendBarrage" })
	@ApiOperation("用户发送弹幕接口")
	@ResponseBody
	public ResultInfo<GiftGivingDto> sendBarrage(@RequestBody SendBarrageDto sendBarrageDto) throws Exception {
		return roomBusiness.sendBarrage(sendBarrageDto);
	}

	@PostMapping(value = { "/app/v1/kicking", "/pc/v1/kicking" })
	@ApiOperation("管理员/主播踢人")
	@ResponseBody
	public ResultInfo<Boolean> kicking(@RequestBody KickingDto kickingDto) {
		ResultInfo<Boolean> result = roomBusiness.kicking(kickingDto);
		return result;
	}

	@PostMapping(value = { "/app/v1/banned", "/pc/v1/banned" })
	@ApiOperation("管理员/主播禁言观众")
	@ResponseBody
	public ResultInfo<Boolean> banned(@RequestBody BannedReq bannedReq) {
		ResultInfo<Boolean> result = roomBusiness.banned(bannedReq);
		return result;
	}

	@PostMapping(value = { "/app/v1/checkBanned", "/pc/v1/checkBanned" })
	@ApiOperation("校验当前用户是否可以发言， true: 可以发言")
	@ResponseBody
	public ResultInfo<Boolean> checkBanned(@RequestBody LiveStudioNumReq liveStudioNumReq) {
		ResultInfo<Boolean> result = roomBusiness.checkBanned(liveStudioNumReq);
		return result;
	}

	@PostMapping(value = { "/app/v1/checKicking", "/pc/v1/checKicking" })
	@ApiOperation("校验当前用户是否可以进入房间， true: 可以进入")
	@ResponseBody
	public ResultInfo<Boolean> checKicking(@RequestBody LiveStudioNumReq liveStudioNumReq) {
		ResultInfo<Boolean> result = roomBusiness.checKicking(liveStudioNumReq);
		return result;
	}

	@PostMapping(value = { "/app/v1/fastWords", "/pc/v1/fastWords" })
	@ApiOperation("根据请求头lang 返回聊天快捷语")
	@ResponseBody
	@AllowAccess
	public ResultInfo<List<RoomFastWordsDto>> getList() {
		return ResultInfo.ok(roomBusiness.fastWords());
	}

	@PostMapping(value = { "/app/v1/onlineUsers", "/pc/v1/onlineUsers" })
	@ApiOperation("查询直播间在线观众50个")
	@ResponseBody
	@AllowAccess
	public ResultInfo<List<LiveUserDetailVO>> onlineUsers(@RequestBody LiveStudioNumReq liveStudioNumReq) {
		return ResultInfo.ok(roomBusiness.onlineUsers(liveStudioNumReq));
	}
	
	@PostMapping(value = { "/app/v1/onlineUsersCount", "/pc/v1/onlineUsersCount" })
	@ApiOperation("查询直播间在线观众数量")
	@ResponseBody
	@AllowAccess
	public ResultInfo<Integer> onlineUsersCount(@RequestBody LiveStudioNumReq liveStudioNumReq) {
		Integer result = roomBusiness.onlineUsersCount(liveStudioNumReq.getStudioNum());
		return ResultInfo.ok(result);
	}

//
//	@RequestMapping(value = "/getHostHomeCourseContribution", method = { RequestMethod.POST })
//	@ApiOperation("主播直播间本场打赏榜")
//	@ResponseBody
//	@AllowAccess
//	@ApiImplicitParams({
//			@ApiImplicitParam(paramType = "query", name = "hostId", required = true, value = "主播ID", dataType = "String"),
//			@ApiImplicitParam(paramType = "query", name = "userId", required = false, value = "用户ID", dataType = "String"),
//			@ApiImplicitParam(paramType = "query", name = "pageNum", required = true, value = "页码，默认为1", dataType = "Integer"),
//			@ApiImplicitParam(paramType = "query", name = "pageSize", required = true, value = "条数，默认10", dataType = "Integer") })
//	public ResultInfo<Object> getHostHomeCourseContribution(String hostId, String userId, Integer pageNum,
//			Integer pageSize) {
//		ResultInfo<Object> result = giftGivingService.getHostHomeCourseContribution(hostId, userId, pageNum, pageSize);
//		return result;
//	}
//


	@PostMapping(value = { "/app/v1/giftGivingComboEnd", "/pc/v1/giftGivingComboEnd" })
	@ApiOperation("礼物连击结束调用接口")
	@ResponseBody
	public ResultInfo<Boolean> giftGivingComboEnd(@RequestBody GiftGivingComboEndReq giftGivingComboEndReq) throws Exception {
		Boolean flag=roomBusiness.giftGivingComboEnd(giftGivingComboEndReq);
		return ResultInfo.ok(flag);
	}

	@PostMapping(value = { "/app/v1/productCheck", "/pc/v1/productCheck" })
	@ApiOperation("收费房间门票是否过期,未过期true和返回购买时间,过期返回false")
	@ResponseBody
	public ResultInfo<ProductForRoomDto> productCheck(@RequestBody LiveStudioNumReq liveStudioNumReq) throws Exception {
		if(StringUtils.isEmpty(liveStudioNumReq.getStudioNum())) {
			return ResultInfo.getInstance(StatusCode.LIVE_STUDIONUM_ISNULL);
		}
		return ResultInfo.ok(roomBusiness.productCheck(liveStudioNumReq));
	}
	
	@PostMapping(value = { "/app/v1/buyProduct", "/pc/v1/buyProduct" })
	@ApiOperation("收费房间购买商品")
	@ResponseBody
	public ResultInfo<BuyProductVO> buyProduct(@RequestBody LiveStudioNumReq liveStudioNumReq) throws Exception {
		if(StringUtils.isEmpty(liveStudioNumReq.getStudioNum())) {
			return ResultInfo.getInstance(StatusCode.LIVE_STUDIONUM_ISNULL);
		}
		return ResultInfo.ok(roomBusiness.buyProduct(liveStudioNumReq));
	}
	
	@PostMapping(value = { "/app/v1/trySeeExpire", "/pc/v1/trySeeExpire" })
	@ApiOperation("用户试看超时记录")
	@ResponseBody
	public ResultInfo<Boolean> trySeeExpire(@RequestBody LiveStudioNumReq liveStudioNumReq) throws Exception {
		if(StringUtils.isEmpty(liveStudioNumReq.getStudioNum())) {
			return ResultInfo.getInstance(StatusCode.LIVE_STUDIONUM_ISNULL);
		}
		roomBusiness.trySeeExpire(liveStudioNumReq);
		return ResultInfo.ok();
	}
	
}
