package com.onelive.api.modules.sys.controller;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.onelive.api.modules.sys.business.PlatformBusiness;
import com.onelive.common.annotation.AllowAccess;
import com.onelive.common.model.common.ResultInfo;
import com.onelive.common.model.dto.platformConfig.PlatformConfigForIndexDto;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/platform")
@Api(tags = "用户端查询平台配置")
public class PlatformController {

	@Resource
	private PlatformBusiness platformBusiness;

	@PostMapping(value = {"/app/getConfig", "/pc/getConfig"})
	@ApiOperation("平台配置")
	@AllowAccess
	@ResponseBody
	public ResultInfo<PlatformConfigForIndexDto> getConfig() {
		return ResultInfo.ok(platformBusiness.getConfig());
	}

}
