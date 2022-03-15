package com.onelive.manage.modules.platform.controller;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.pagehelper.PageInfo;
import com.onelive.common.model.common.ResultInfo;
import com.onelive.common.model.dto.login.LoginUser;
import com.onelive.common.model.dto.platform.FloatTypeDto;
import com.onelive.common.model.dto.platform.LiveFloatDto;
import com.onelive.manage.common.annotation.Log;
import com.onelive.manage.modules.base.BaseAdminController;
import com.onelive.manage.modules.platform.business.LiveFloatBusiness;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping(value = "/platform/float")
@Api(tags = "直播间悬浮窗")
public class LiveFloatController extends BaseAdminController {

    @Resource
    private LiveFloatBusiness liveFloatBusiness;

    @PostMapping(value = "/v1/getList")
    @ApiOperation("悬浮窗列表")
    public ResultInfo<PageInfo<LiveFloatDto>> getList(LiveFloatDto liveFloatDto) {
        return ResultInfo.ok(liveFloatBusiness.getList(liveFloatDto));
    }
    
    @Log("保存悬浮窗")
    @PostMapping("/v1/save")
    @ApiOperation("保存悬浮窗信息")
    public ResultInfo<String> save(@RequestBody LiveFloatDto liveFloatDto) throws Exception {
        LoginUser admin = getLoginAdmin();
        liveFloatBusiness.save(liveFloatDto, admin);
        return ResultInfo.ok();
    }
    
    @Log("悬浮窗编辑")
    @PostMapping(value = "/v1/update")
    @ApiOperation("悬浮窗编辑")
    public ResultInfo<String> update(@RequestBody LiveFloatDto liveFloatDto) {
        LoginUser loginUser = getLoginAdmin();
        liveFloatBusiness.update(liveFloatDto, loginUser);
        return ResultInfo.ok();
    }
    
    @Log("悬浮窗删除")
    @PostMapping(value = "/v1/delete")
    @ApiOperation("悬浮窗删除")
    public ResultInfo<String> delete(@RequestBody LiveFloatDto liveFloatDto) {
    	LoginUser loginUser = getLoginAdmin();
    	liveFloatBusiness.delete(liveFloatDto, loginUser);
    	return ResultInfo.ok();
    }
    
    @PostMapping(value = "/v1/getFloatTypes")
    @ApiOperation("查询悬浮窗类型")
    public ResultInfo<List<FloatTypeDto>> getFloatTypes() {
        return ResultInfo.ok(liveFloatBusiness.getFloatTypes());
    }
    

}
