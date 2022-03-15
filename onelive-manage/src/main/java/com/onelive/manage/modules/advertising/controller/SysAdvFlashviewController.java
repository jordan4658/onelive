package com.onelive.manage.modules.advertising.controller;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.pagehelper.PageInfo;
import com.onelive.common.model.common.ResultInfo;
import com.onelive.common.model.dto.login.LoginUser;
import com.onelive.common.model.dto.platform.FlashviewTypeDto;
import com.onelive.common.model.req.sys.IdReq;
import com.onelive.common.model.req.sys.SysAdvFlashviewReq;
import com.onelive.common.model.vo.sys.SysAdvFlashviewVO;
import com.onelive.manage.common.annotation.Log;
import com.onelive.manage.modules.advertising.business.SysAdvFlashviewBusiness;
import com.onelive.manage.modules.base.BaseAdminController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * @author
 *@Description:首页轮播 广告
 */
@RestController
@RequestMapping(value = "/advFlashview")
@Api(tags = "平台管理-首页轮播")
public class SysAdvFlashviewController extends BaseAdminController {

    @Resource
    private SysAdvFlashviewBusiness business;

    @GetMapping("/v1/getList")
    @ApiOperation("轮播列表")
    public ResultInfo<PageInfo<SysAdvFlashviewVO>> getList(SysAdvFlashviewReq sysAdvFlashviewVO) {
        return ResultInfo.ok(business.getList(sysAdvFlashviewVO));
    }

    @Log("添加轮播")
    @PostMapping("/v1/add")
    @ApiOperation("添加轮播")
    public ResultInfo<Boolean> add(@RequestBody SysAdvFlashviewVO req) {
        LoginUser admin = getLoginAdmin();
        business.add(req, admin);
        return ResultInfo.ok();
    }

    @Log("编辑轮播")
    @PostMapping("/v1/update")
    @ApiOperation("编辑轮播")
    public ResultInfo<String> update(@RequestBody  SysAdvFlashviewVO req) {
        LoginUser admin = getLoginAdmin();
        business.update(req, admin);
        return ResultInfo.ok();
    }

    @Log("删除轮播")
    @PostMapping("/v1/delete")
    @ApiOperation("删除轮播")
    public ResultInfo<String> switchStatus(@RequestBody  IdReq req) {
        LoginUser admin = getLoginAdmin();
        business.delete(req.getId(), admin.getAccLogin());
        return ResultInfo.ok();
    }
    
    
    @PostMapping(value = "/v1/getFlashviewTypes")
    @ApiOperation("查询轮播图类型")
    public ResultInfo<List<FlashviewTypeDto>> getFlashviewTypes() {
        return ResultInfo.ok(business.getFlashviewTypes());
    }
   

}
