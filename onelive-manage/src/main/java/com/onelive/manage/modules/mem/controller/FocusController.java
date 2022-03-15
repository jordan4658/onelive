package com.onelive.manage.modules.mem.controller;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.github.pagehelper.PageInfo;
import com.onelive.common.model.common.ResultInfo;
import com.onelive.common.model.vo.mem.FocusUserVo;
import com.onelive.manage.modules.base.BaseAdminController;
import com.onelive.manage.modules.mem.business.FocusBusiness;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping(value = "/focus")
@Api(tags = "订阅详情-主播被关注的奖励")
public class FocusController extends BaseAdminController {

	@Resource
	private FocusBusiness focusBusiness;

	@PostMapping(value = "/v1/getList")
	@ApiOperation("订阅详情列表")
	@ResponseBody
	public ResultInfo<PageInfo<FocusUserVo>> getList(@RequestBody FocusUserVo focusUserVo) {
		return ResultInfo.ok(focusBusiness.getList(focusUserVo));
	}

}
