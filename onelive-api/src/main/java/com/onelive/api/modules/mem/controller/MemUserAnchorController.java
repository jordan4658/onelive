package com.onelive.api.modules.mem.controller;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.onelive.api.modules.mem.business.MemUserAnchorBusiness;
import com.onelive.common.annotation.AllowAccess;
import com.onelive.common.base.BaseController;
import com.onelive.common.model.common.ResultInfo;
import com.onelive.common.model.req.mem.MemUserAnchorInfoReq;
import com.onelive.common.model.vo.live.LiveAnchorDetailVO;
import com.onelive.common.model.vo.live.LiveAnchorVO;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * @Description: 主播信息接口
 */
@RestController
@RequestMapping("/anchor")
@Api(tags = "主播信息接口")
public class MemUserAnchorController extends BaseController {

	@Resource
	private MemUserAnchorBusiness memUserAnchorBusiness;

	@ApiOperation("获取直播间头部主播信息")
	@PostMapping(value = { "/app/v1/getAnchorInfo", "/pc/v1/getAnchorInfo" })
	@AllowAccess
	public ResultInfo<LiveAnchorVO> getAnchorInfo(@RequestBody MemUserAnchorInfoReq req) {
		return ResultInfo.ok(memUserAnchorBusiness.getAnchorInfo(req.getStudioNum()));
	}

	@ApiOperation("获取直播间主播名片")
	@PostMapping(value = { "/app/v1/getAnchorDetailInfo", "/pc/v1/getAnchorDetailInfo" })
	@AllowAccess
	public ResultInfo<LiveAnchorDetailVO> getAnchorDetailInfo(@RequestBody MemUserAnchorInfoReq req) {
		return memUserAnchorBusiness.getAnchorDetailInfo(req.getStudioNum());
	}

}
