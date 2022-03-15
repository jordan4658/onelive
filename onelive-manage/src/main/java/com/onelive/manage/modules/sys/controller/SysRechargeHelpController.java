package com.onelive.manage.modules.sys.controller;

import java.util.List;

import javax.annotation.Resource;

import com.onelive.common.model.req.sys.SysCommonReq;
import com.onelive.common.model.vo.sys.SysHelpInfoLangVO;
import com.onelive.common.model.vo.sys.SysRechargeHelpLangVO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.onelive.common.model.common.ResultInfo;
import com.onelive.common.model.dto.login.LoginUser;
import com.onelive.common.model.req.sys.SysRechargeHelpReq;
import com.onelive.common.model.req.sys.SysRechargeHelpUpdateReq;
import com.onelive.common.model.vo.sys.SysRechargeHelpListVO;
import com.onelive.manage.common.annotation.Log;
import com.onelive.manage.modules.base.BaseAdminController;
import com.onelive.manage.modules.sys.business.SysRechargeHelpBusiness;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;

/**
 * @author
 *@Description: 充值说明
 */
@RestController
@RequestMapping(value = "/sysRechargeHelp")
@Slf4j
@Api(tags = "运营管理-充值说明")
public class SysRechargeHelpController extends BaseAdminController {

    @Resource
    private SysRechargeHelpBusiness business;

    @GetMapping("/v1/getList")
    @ApiOperation("列表")
    public ResultInfo<List<SysRechargeHelpListVO>> getList() {
    	List<SysRechargeHelpListVO> list = business.getList();
        return ResultInfo.ok(list);
    }


    @GetMapping("/v1/getDetailList")
    @ApiOperation("充值说明详情列表")
    public ResultInfo<List<SysRechargeHelpLangVO>> getDetailList(@ApiParam("id") @RequestParam Long id) {
        List<SysRechargeHelpLangVO> list = business.getDetailList(id);
        return ResultInfo.ok(list);
    }

    @Log("添加")
    @PostMapping("/v1/add")
    @ApiOperation("添加")
    public ResultInfo<String> add(@RequestBody SysRechargeHelpReq req) {
        LoginUser admin = getLoginAdmin();
        business.add(req, admin);
        return ResultInfo.ok();
    }

    @Log("编辑")
    @PostMapping("/v1/update")
    @ApiOperation("编辑")
    public ResultInfo<String> update(@RequestBody SysRechargeHelpUpdateReq req) {
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
