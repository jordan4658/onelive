package com.onelive.api.modules.live.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.github.pagehelper.PageInfo;
import com.onelive.api.modules.live.business.FocusBusiness;
import com.onelive.common.model.common.ResultInfo;
import com.onelive.common.model.dto.platform.MemFocusUserRemindDto;
import com.onelive.common.model.req.mem.MemUserIdReq;
import com.onelive.common.model.vo.live.FansUserVO;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/focus")
@Api("用户关注相关接口")
public class FocusController {

	@Autowired
	private FocusBusiness focusBusiness;

	@PostMapping(value = { "/app/v1/focusList", "/pc/v1/focusList" })
	@ApiOperation("用户的关注主播列表,分页查询,不传用户ID即查询当前用户的关注列表")
	@ResponseBody
	public ResultInfo<PageInfo<FansUserVO>> focusList(@RequestBody MemUserIdReq memUserIdReq) throws Exception {
		return ResultInfo.ok(focusBusiness.focusList(memUserIdReq));
	}

	@PostMapping(value = { "/app/v1/setRemind", "/pc/v1/setRemind" })
	@ApiOperation("用户设置主播开播提醒")
	@ResponseBody
	public ResultInfo<Boolean> setRemind(@RequestBody MemFocusUserRemindDto memFocusUserRemindDto) throws Exception {
		return ResultInfo.ok(focusBusiness.setStartedToRemind(memFocusUserRemindDto));
	}

	@PostMapping(value = { "/app/v1/fansList", "/pc/v1/fansList" })
	@ApiOperation("根据主播id查询关注主播的用户信息列表")
	@ResponseBody
	public ResultInfo<PageInfo<FansUserVO>> fansList(@RequestBody MemUserIdReq memUserIdReq) {
		return ResultInfo.ok(focusBusiness.fansList(memUserIdReq));
	}
	
//	@PostMapping(value = { "/app/v1/fansTotal", "/pc/v1/fansTotal" })
//	@ApiOperation("根据主播id查询关注主播的用户信息总数")
//	@ResponseBody
//	public ResultInfo<Integer> fansTotal(@RequestBody MemUserIdReq memUserIdReq) {
//		return ResultInfo.ok(focusBusiness.fansTotal(memUserIdReq));
//	}

//	@PostMapping(value = "/selectHostFocusUserList")
//	@ApiOperation("根据主播id查询关注主播的用户信息列表")
//	@ResponseBody
//	@ApiImplicitParams({
//			@ApiImplicitParam(paramType = "query", name = "hostId", required = true, value = "主播ID", dataType = "String"),
//			@ApiImplicitParam(paramType = "query", name = "pageNum", required = true, value = "页码，默认为1", dataType = "Integer"),
//			@ApiImplicitParam(paramType = "query", name = "pageSize", required = true, value = "条数，默认10", dataType = "Integer") })
//	public ResultInfo<PageInfo<AudienceUserVo>> selectHostFocusUserList(Integer pageNum, Integer pageSize,
//			String hostId) {
//		try {
//			return focusBusiness.selectHostFocusUserList(hostId, pageNum, pageSize);
//		} catch (Exception e) {
//			return ResultInfo.error(CodeMsg.SERVER_EXCEPTION);
//		}
//	}

//

}
