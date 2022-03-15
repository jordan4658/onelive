package com.onelive.manage.modules.sys.controller;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.pagehelper.PageInfo;
import com.onelive.common.model.common.ResultInfo;
import com.onelive.common.model.req.sys.ShortMsgReq;
import com.onelive.common.model.vo.common.SelectVO;
import com.onelive.common.model.vo.sys.ShortMsgVO;
import com.onelive.manage.modules.base.BaseAdminController;
import com.onelive.manage.modules.sys.business.SysShortMsgBusiness;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/shortMsg")
@Api(tags = "系统管理-短信管理")
public class SysShortMsgController extends BaseAdminController {

	@Resource
	private SysShortMsgBusiness sysShortMsgBusiness;

	@GetMapping("/v1/list")
	@ApiOperation("获取短信列表信息")
	public ResultInfo<PageInfo<ShortMsgVO>> queryShortMsgList(@ModelAttribute ShortMsgReq req) {
		PageInfo<ShortMsgVO> list = sysShortMsgBusiness.queryShortMsgList(req);
		return ResultInfo.ok(list);
	}

	@GetMapping("/v1/selectMsgStatus")
	@ApiOperation("获取短信状态下拉框")
	public ResultInfo<List<SelectVO>> selectMsgStatus() {
		return ResultInfo.ok(SysShortMsgBusiness.shortMsgStatusList);
	}

	@GetMapping("/v1/shortMsgType")
	@ApiOperation("获取短信类型下拉框")
	public ResultInfo<List<SelectVO>> selectMsgType() {
		return ResultInfo.ok(SysShortMsgBusiness.shortMsgTypeList);
	}
}
