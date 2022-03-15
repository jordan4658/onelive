package com.onelive.manage.modules.platform.controller;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.onelive.common.model.common.ResultInfo;
import com.onelive.common.model.dto.login.LoginUser;
import com.onelive.common.model.dto.platform.PlatformShareConfigDto;
import com.onelive.manage.common.annotation.Log;
import com.onelive.manage.modules.base.BaseAdminController;
import com.onelive.manage.modules.platform.business.ShareConfigBusiness;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping(value = "/platform/share")
@Api(tags = "分享管理")
public class ShareConfigController extends BaseAdminController {

	@Resource
	private ShareConfigBusiness shareConfigBusiness;

	@PostMapping(value = "/v1/getList")
	@ApiOperation("分享列表")
	public ResultInfo<List<PlatformShareConfigDto>> getList() {
		return ResultInfo.ok(shareConfigBusiness.getList());
	}

	@Log("分享编辑")
	@PostMapping(value = "/v1/update")
	@ApiOperation("分享编辑")
	public ResultInfo<String> update(@RequestBody PlatformShareConfigDto platformShareConfigDto) {
		LoginUser loginUser = getLoginAdmin();
		shareConfigBusiness.update(platformShareConfigDto, loginUser);
		return ResultInfo.ok();
	}

}
