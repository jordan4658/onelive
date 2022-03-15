package com.onelive.manage.modules.advertising.controller;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.pagehelper.PageInfo;
import com.onelive.common.model.common.ResultInfo;
import com.onelive.common.model.dto.login.LoginUser;
import com.onelive.common.model.req.sys.IdReq;
import com.onelive.common.model.req.sys.SysAdvNoticeQueryReq;
import com.onelive.common.model.vo.sys.SysAdvNoticeVO;
import com.onelive.manage.common.annotation.Log;
import com.onelive.manage.modules.advertising.business.SysAdvNoticeBusiness;
import com.onelive.manage.modules.base.BaseAdminController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * @Description: 公告
 */
@RestController
@RequestMapping(value = "/advNotice")
@Api(tags = "平台管理-首页公告")
public class SysAdvNoticeController extends BaseAdminController {

	@Resource
	private SysAdvNoticeBusiness business;

	@GetMapping("/v1/getList")
	@ApiOperation("公告列表")
	public ResultInfo<PageInfo<SysAdvNoticeVO>> getList(SysAdvNoticeQueryReq req) {
		PageInfo<SysAdvNoticeVO> list = business.getList(req);
		return ResultInfo.ok(list);
	}

	@Log("添加公告")
	@PostMapping("/v1/add")
	@ApiOperation("添加公告")
	public ResultInfo<String> add(@RequestBody SysAdvNoticeVO req) {
		LoginUser admin = getLoginAdmin();
		business.add(req, admin);
		return ResultInfo.ok();
	}

	@Log("编辑公告")
	@PostMapping("/v1/update")
	@ApiOperation("编辑公告")
	public ResultInfo<String> update(@RequestBody SysAdvNoticeVO req) {
		LoginUser admin = getLoginAdmin();
		business.update(req, admin);
		return ResultInfo.ok();
	}

	@Log("删除公告")
	@PostMapping("/v1/delete")
	@ApiOperation("删除公告")
	public ResultInfo<String> switchStatus(@RequestBody IdReq req) {
		business.delete(req.getId());
		return ResultInfo.ok();
	}

}
