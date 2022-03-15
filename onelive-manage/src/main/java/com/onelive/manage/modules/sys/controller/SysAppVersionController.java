package com.onelive.manage.modules.sys.controller;

import com.github.pagehelper.PageInfo;
import com.onelive.common.model.common.ResultInfo;
import com.onelive.common.model.dto.login.LoginUser;
import com.onelive.common.model.req.sys.appversion.*;
import com.onelive.common.model.vo.sys.AppPackageInfoVO;
import com.onelive.common.model.vo.sys.SysAppVersionListVO;
import com.onelive.common.model.vo.sys.SysAppVersionVO;
import com.onelive.manage.common.annotation.Log;
import com.onelive.manage.modules.base.BaseAdminController;
import com.onelive.manage.modules.sys.business.SysAppVersionBusiness;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;

/**
 * @author
 *@Description: app版本管理
 */
@RestController
@RequestMapping(value = "/appVersion")
@Slf4j
@Api(tags = "平台管理-app版本管理")
public class SysAppVersionController extends BaseAdminController {

    @Resource
    private SysAppVersionBusiness business;

    @PostMapping("/v1/getList")
    @ApiOperation("列表")
    public ResultInfo<PageInfo<SysAppVersionListVO>> getList(@RequestBody SysAppVersionListReq req) {
        PageInfo<SysAppVersionListVO> list = business.getList(req);
        return ResultInfo.ok(list);
    }

    @Log("添加")
    @PostMapping("/v1/add")
    @ApiOperation("添加")
    public ResultInfo<String> add(@RequestBody SysAppVersionAddReq req) {
        LoginUser admin = getLoginAdmin();
        business.add(req, admin);
        return ResultInfo.ok();
    }

    @Log("编辑")
    @PostMapping("/v1/update")
    @ApiOperation("编辑")
    public ResultInfo<String> update(@RequestBody SysAppVersionUpdateReq req) {
        LoginUser admin = getLoginAdmin();
        business.update(req, admin);
        return ResultInfo.ok();
    }

    @Log("更新状态")
    @PostMapping("/v1/updateStatus")
    @ApiOperation("更新状态")
    public ResultInfo<String> updateStatus(@RequestBody SysAppVersionUpdateStatusReq req) {
        LoginUser admin = getLoginAdmin();
        business.updateStatus(req, admin);
        return ResultInfo.ok();
    }



    @Log("查询")
    @PostMapping("/v1/getInfo")
    @ApiOperation("查询")
    public ResultInfo<SysAppVersionVO> getInfo(@RequestBody SysAppVersionIdReq req) {
        return ResultInfo.ok(business.getInfo(req));
    }

    @ApiOperation("上传app文件")
    @PostMapping(name = "上传app文件", value = "/v1/uploadAppFile")
    public ResultInfo<AppPackageInfoVO> uploadSingleFile(@RequestParam("file") MultipartFile multipartFile) throws Exception {
        return ResultInfo.ok(business.uploadAppPackageFile(multipartFile));
    }

}
