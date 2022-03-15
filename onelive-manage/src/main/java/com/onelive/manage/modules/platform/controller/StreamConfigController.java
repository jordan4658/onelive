package com.onelive.manage.modules.platform.controller;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.pagehelper.PageInfo;
import com.onelive.common.model.common.PageReq;
import com.onelive.common.model.common.ResultInfo;
import com.onelive.common.model.dto.login.LoginUser;
import com.onelive.common.model.dto.platform.CDNDto;
import com.onelive.common.model.dto.platform.SysStreamConfigDto;
import com.onelive.manage.common.annotation.Log;
import com.onelive.manage.modules.base.BaseAdminController;
import com.onelive.manage.modules.platform.business.StreamConfigBusiness;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping(value = "/platform/stream")
@Api(tags = "直播流管理")
public class StreamConfigController extends BaseAdminController {

	@Resource
	private StreamConfigBusiness streamConfigBusiness;

	@PostMapping(value = "/v1/getList")
	@ApiOperation("直播流列表")
	public ResultInfo<PageInfo<SysStreamConfigDto>> getList(@RequestBody PageReq pageReq) {
		return ResultInfo.ok(streamConfigBusiness.getList(pageReq.getPageNum(), pageReq.getPageSize()));
	}

	@Log("保存流信息")
	@PostMapping("/v1/save")
	@ApiOperation("保存流信息")
	public ResultInfo<String> save(@RequestBody SysStreamConfigDto sysStreamConfigDto) throws Exception {
		LoginUser admin = getLoginAdmin();
		streamConfigBusiness.save(sysStreamConfigDto, admin);
		return ResultInfo.ok();
	}

	@Log("流编辑")
	@PostMapping(value = "/v1/update")
	@ApiOperation("流编辑")
	public ResultInfo<Boolean> update(@RequestBody SysStreamConfigDto SysStreamConfigDto) {
		LoginUser loginUser = getLoginAdmin();
		streamConfigBusiness.update(SysStreamConfigDto, loginUser);
		return ResultInfo.ok();
	}

	@Log("流删除")
	@PostMapping(value = "/v1/delete")
	@ApiOperation("流删除")
	public ResultInfo<Boolean> delete(@RequestBody SysStreamConfigDto sysStreamConfigDto) {
		return ResultInfo.ok(streamConfigBusiness.delete(sysStreamConfigDto.getId()));
	}

	@PostMapping(value = "/v1/getCdnBusiness")
	@ApiOperation("查询cdn服务商")
	public ResultInfo<List<CDNDto>> getCdnBusiness() {
		return ResultInfo.ok(streamConfigBusiness.getCdnBusiness());
	}

}
