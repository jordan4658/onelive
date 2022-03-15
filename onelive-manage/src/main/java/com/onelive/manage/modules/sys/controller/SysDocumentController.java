package com.onelive.manage.modules.sys.controller;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.onelive.common.model.common.ResultInfo;
import com.onelive.common.model.dto.login.LoginUser;
import com.onelive.common.model.req.sys.SysDocumentReq;
import com.onelive.common.model.req.sys.SysDocumentUpdateReq;
import com.onelive.common.model.vo.sys.SysDocumentListVO;
import com.onelive.manage.common.annotation.Log;
import com.onelive.manage.modules.base.BaseAdminController;
import com.onelive.manage.modules.sys.business.SysDocumentBusiness;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;

///**
// * @author
// *@Description: 文章管理
// */
//@RestController
//@RequestMapping(value = "/sysDoc")
//@Slf4j
//@Api(tags = "运营管理-文章管理")
//public class SysDocumentController extends BaseAdminController {
//
//    @Resource
//    private SysDocumentBusiness business;
//
//    @GetMapping("/v1/getList")
//    @ApiOperation("列表")
//    public ResultInfo<List<SysDocumentListVO>> getList() {
//    	List<SysDocumentListVO> list = business.getList();
//        return ResultInfo.ok(list);
//    }
//
//    @Log("添加")
//    @PostMapping("/v1/add")
//    @ApiOperation("添加")
//    public ResultInfo<String> add(@RequestBody SysDocumentReq req) {
//        LoginUser admin = getLoginAdmin();
//        business.add(req, admin);
//        return ResultInfo.ok();
//    }
//
//    @Log("编辑")
//    @PostMapping("/v1/update")
//    @ApiOperation("编辑")
//    public ResultInfo<String> update(@RequestBody SysDocumentUpdateReq req) {
//        LoginUser admin = getLoginAdmin();
//        business.update(req, admin);
//        return ResultInfo.ok();
//    }
//
//    @Log("删除")
//    @PostMapping("/v1/delete")
//    @ApiOperation("删除")
//    public ResultInfo<String> switchStatus(@ApiParam("id") @RequestParam("id") Integer  id) {
//        LoginUser admin = getLoginAdmin();
//        business.delete(Long.valueOf(id), admin.getAccLogin());
//        return ResultInfo.ok();
//    }
//
//
//}
