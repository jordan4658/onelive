package com.onelive.api.modules.index.controller;

import javax.annotation.Resource;

import com.onelive.common.annotation.SupperAccess;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.onelive.api.modules.index.business.OnlineServiceBusiness;
import com.onelive.common.model.common.ResultInfo;
import com.onelive.common.model.dto.platformConfig.CustomerSericeLangDto;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping(value = "/onlineservice")
@Api(tags = "在线客服接口")
public class OnlineServiceController {

	@Resource
	private OnlineServiceBusiness onlineServiceBusiness;

	@PostMapping(value = { "/app/v1/getOnlineService", "/pc/v1/getOnlineService" })
	@ApiOperation("获取当前语言的客服")
	@ResponseBody
	@SupperAccess
	public ResultInfo<CustomerSericeLangDto> getOnlineService() {
		return ResultInfo.ok(onlineServiceBusiness.getOnlineService());
	}

}
