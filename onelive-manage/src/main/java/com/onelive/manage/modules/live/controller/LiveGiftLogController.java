package com.onelive.manage.modules.live.controller;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.github.pagehelper.PageInfo;
import com.onelive.common.model.common.ResultInfo;
import com.onelive.common.model.dto.platform.LiveGiftLogDetailDto;
import com.onelive.manage.modules.live.business.LiveGiftLogBusiness;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/gift")
@Api(tags = "礼物详情")
public class LiveGiftLogController {

	@Resource
	private LiveGiftLogBusiness liveGiftLogBusiness;

	@PostMapping("/v1/getList")
	@ApiOperation("礼物赠送详情列表")
	@ResponseBody
	public ResultInfo<PageInfo<LiveGiftLogDetailDto>> getList(@RequestBody LiveGiftLogDetailDto param) {
		return ResultInfo.ok(liveGiftLogBusiness.getList(param));
	}

}
