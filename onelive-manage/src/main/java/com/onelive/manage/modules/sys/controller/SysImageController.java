package com.onelive.manage.modules.sys.controller;

import java.util.List;

import javax.annotation.Resource;

import com.github.pagehelper.PageInfo;
import com.onelive.common.model.req.sys.SysCommonReq;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.onelive.common.model.common.ResultInfo;
import com.onelive.common.model.dto.login.LoginUser;
import com.onelive.common.model.req.sys.SysImageUpdateReq;
import com.onelive.common.model.vo.sys.SysImageListVO;
import com.onelive.manage.common.annotation.Log;
import com.onelive.manage.modules.base.BaseAdminController;
import com.onelive.manage.modules.sys.business.SysImageBusiness;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;

/**
 * @author
 *@Description: 图片管理
 */
@RestController
@RequestMapping(value = "/sysImage")
@Slf4j
@Api(tags = "运营管理-图片管理")
public class SysImageController extends BaseAdminController {

    @Resource
    private SysImageBusiness business;

    @GetMapping("/v1/getList")
    @ApiOperation("列表")
    public ResultInfo<PageInfo<SysImageListVO>> getList(
            @ApiParam("第几页") @RequestParam(name = "pageNum", required = false, defaultValue = "1") Integer pageNum,
            @ApiParam("每页最大页数") @RequestParam(name = "pageSize", required = false, defaultValue = "10") Integer pageSize) {
        PageInfo<SysImageListVO> list =  business.getList(pageNum,pageSize);
        return ResultInfo.ok(list);
    }

    @Log("添加")
    @PostMapping("/v1/add")
    @ApiOperation("添加")
    public ResultInfo<String> add(@RequestBody SysImageUpdateReq req) {
        LoginUser admin = getLoginAdmin();
        business.add(req, admin);
        return ResultInfo.ok();
    }

    @Log("编辑")
    @PostMapping("/v1/update")
    @ApiOperation("编辑")
    public ResultInfo<String> update(@RequestBody SysImageUpdateReq req) {
        LoginUser admin = getLoginAdmin();
        business.update(req, admin);
        return ResultInfo.ok();
    }

    @Log("删除")
    @PostMapping("/v1/delete")
    @ApiOperation("删除")
    public ResultInfo<String> switchStatus(@RequestBody SysCommonReq req) {
        LoginUser admin = getLoginAdmin();
        business.delete(req.getId(), admin.getAccLogin());
        return ResultInfo.ok();
    }
   

}
