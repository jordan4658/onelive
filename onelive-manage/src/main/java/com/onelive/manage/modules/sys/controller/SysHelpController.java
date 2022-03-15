package com.onelive.manage.modules.sys.controller;

import java.util.List;

import javax.annotation.Resource;

import com.onelive.common.model.req.sys.SysCommonReq;
import com.onelive.common.model.vo.sys.SysHelpInfoLangVO;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.onelive.common.model.common.ResultInfo;
import com.onelive.common.model.dto.login.LoginUser;
import com.onelive.common.model.req.sys.SysHelpInfoReq;
import com.onelive.common.model.req.sys.SysHelpInfoUpdateReq;
import com.onelive.common.model.vo.sys.SysHelpInfoListVO;
import com.onelive.manage.common.annotation.Log;
import com.onelive.manage.modules.base.BaseAdminController;
import com.onelive.manage.modules.sys.business.SysHelpBusiness;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;

/**
 * @author
 *@Description: 帮助中心
 */
@RestController
@RequestMapping(value = "/sysHelp")
@Slf4j
@Api(tags = "运营管理-帮助中心")
public class SysHelpController extends BaseAdminController {

    @Resource
    private SysHelpBusiness business;

    @GetMapping("/v1/getList")
    @ApiOperation("列表")
    public ResultInfo<List<SysHelpInfoListVO>> getList() {
    	List<SysHelpInfoListVO> list = business.getList();
        return ResultInfo.ok(list);
    }

    @GetMapping("/v1/getDetailList")
    @ApiOperation("帮助说明详情列表")
    public ResultInfo<List<SysHelpInfoLangVO>> getDetailList(@ApiParam("id") @RequestParam Long id) {
        List<SysHelpInfoLangVO> list = business.getDetailList(id);
        return ResultInfo.ok(list);
    }

    @Log("添加")
    @PostMapping("/v1/add")
    @ApiOperation("添加")
    public ResultInfo<String> add(@RequestBody SysHelpInfoReq req) {
        LoginUser admin = getLoginAdmin();
        business.add(req, admin);
        return ResultInfo.ok();
    }

    @Log("编辑")
    @PostMapping("/v1/update")
    @ApiOperation("编辑")
    public ResultInfo<String> update(@RequestBody SysHelpInfoUpdateReq req) {
        LoginUser admin = getLoginAdmin();
        business.update(req, admin);
        return ResultInfo.ok();
    }

    @Log("删除")
    @PostMapping("/v1/delete")
    @ApiOperation("删除")
    public ResultInfo<String> delete(@RequestBody SysCommonReq req) {
        LoginUser admin = getLoginAdmin();
        business.delete(req.getId(), admin.getAccLogin());
        return ResultInfo.ok();
    }
   

}
