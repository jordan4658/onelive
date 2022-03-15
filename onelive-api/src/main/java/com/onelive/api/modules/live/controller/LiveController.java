package com.onelive.api.modules.live.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.onelive.api.modules.live.business.LiveBusiness;
import com.onelive.common.annotation.AllowAccess;
import com.onelive.common.model.common.ResultInfo;
import com.onelive.common.model.dto.platform.ProductChargeDto;
import com.onelive.common.model.dto.platform.ProductTypeDto;
import com.onelive.common.model.req.live.LiveRecommendReq;
import com.onelive.common.model.req.live.LiveRoomActReq;
import com.onelive.common.model.req.live.LiveRoomDetailReq;
import com.onelive.common.model.req.live.LiveSwitchChargeReq;
import com.onelive.common.model.req.mem.MemUserIdReq;
import com.onelive.common.model.vo.live.LiveActVO;
import com.onelive.common.model.vo.live.LiveAnchorInfoVO;
import com.onelive.common.model.vo.live.LiveRoomDetailVo;
import com.onelive.common.model.vo.live.LiveStudioListForIndexVO;
import com.onelive.common.model.vo.live.LiveTabItemVo;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@RestController
@RequestMapping("/live")
@Api(tags = "直播相关接口")
public class LiveController {

	@Autowired
	private LiveBusiness liveBusiness;

	@PostMapping(value = { "/app/v1/roomProducts", "/pc/v1/roomProducts" })
	@ApiOperation("房间的商品类型")
	@ResponseBody
	@AllowAccess
	public ResultInfo<List<ProductChargeDto>> roomProducts(@RequestBody ProductTypeDto productTypeDto) {
		return ResultInfo.ok(liveBusiness.roomProducts(productTypeDto));
	}

	@PostMapping(value = { "/app/v1/switchCharge", "/pc/v1/switchCharge" })
	@ApiOperation("主播开启/关闭直播间状态为收费")
	@ResponseBody
	public ResultInfo<Boolean> switchCharge(@RequestBody LiveSwitchChargeReq req) throws Exception {
		return ResultInfo.ok(liveBusiness.switchCharge(req));
	}

	@PostMapping(value = { "/app/v1/getRoomActList", "/pc/v1/getRoomActList" })
	@ApiOperation("返回直播间活动信息列表")
	@ResponseBody
	@AllowAccess
	public ResultInfo<LiveActVO> getRoomAct(@RequestBody LiveRoomActReq req) throws Exception {
		return ResultInfo.ok(liveBusiness.getRoomAct(req.getType()));
	}

	@ApiOperation("通过房间号获取直播间详情,进入房间之前请求的接口")
	@PostMapping(value = { "/app/v1/getRoomDetail", "/pc/v1/getRoomDetail" })
	@ResponseBody
	@AllowAccess
	public ResultInfo<LiveRoomDetailVo> getRoomDetail(@RequestBody LiveRoomDetailReq req) throws Exception {
		return ResultInfo.ok(liveBusiness.getRoomDetail(req.getStudioNum()));
	}

	@ApiOperation("通过房间号获取推荐的直播房间列表")
	@PostMapping(value = { "/app/v1/getLiveRecommendList", "/pc/v1/getLiveRecommendList" })
	@ResponseBody
	@AllowAccess
	public ResultInfo<List<LiveStudioListForIndexVO>> getLiveRecommendList(@RequestBody LiveRecommendReq req)
			throws Exception {
		return ResultInfo.ok(liveBusiness.getLiveRecommendList(req));
	}
	
//	@ApiOperation("通过房间号获取守护榜列表,一期不做")
//	@PostMapping(value = { "/app/v2/getLiveGuardList", "/pc/v2/getLiveGuardList" })
//	@ResponseBody
//	@AllowAccess
//	public ResultInfo<List<LiveGuardListVO>> getLiveGuardList(@RequestBody LiveGuardReq req) throws Exception {
//		return ResultInfo.ok(liveBusiness.getLiveGuardList(req));
//	}

	@ApiOperation("获取tab栏配置信息")
	@PostMapping(value = { "/app/v1/getLiveTabItemList", "/pc/v1/getLiveTabItemList" })
	@ResponseBody
	@AllowAccess
	public ResultInfo<List<LiveTabItemVo>> getLiveTabItemList() throws Exception {
		return ResultInfo.ok(liveBusiness.getLiveTabItemList());
	}

	@PostMapping(value = { "/app/v1/getAnchorInfo", "/pc/v1/getAnchorInfo" })
	@ApiOperation("主播信息")
	@ResponseBody
	public ResultInfo<LiveAnchorInfoVO> getAnchorInfo(@ApiParam("主播的用户ID") @RequestBody MemUserIdReq memUserIdReq) {
		return ResultInfo.ok(liveBusiness.getAnchorInfo(memUserIdReq.getUserId()));
	}

}
