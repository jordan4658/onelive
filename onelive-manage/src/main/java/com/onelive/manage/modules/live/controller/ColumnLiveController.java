package com.onelive.manage.modules.live.controller;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.pagehelper.PageInfo;
import com.onelive.common.enums.ColumnCodeEnum;
import com.onelive.common.model.common.ResultInfo;
import com.onelive.common.model.dto.index.LiveColumnDto;
import com.onelive.manage.common.annotation.Log;
import com.onelive.manage.modules.live.business.ColumnLiveBusiness;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/column")
@Api(tags = "直播首页《栏目》相关接口")
public class ColumnLiveController {

	@Resource
    private ColumnLiveBusiness columnLiveBusiness;

    @GetMapping("/v1/getList")
    @ApiOperation("查询栏目列表")
    public ResultInfo<PageInfo<LiveColumnDto>> getList(@RequestBody LiveColumnDto LiveColumn) {
        return ResultInfo.ok(columnLiveBusiness.getList(LiveColumn));
    }
    
    
    @Log("保存栏目信息")
    @PostMapping("/v1/save")
    @ApiOperation("保存栏目信息")
    public ResultInfo<Boolean> save(@RequestBody LiveColumnDto LiveColumn) throws Exception {
    	columnLiveBusiness.save(LiveColumn);
        return ResultInfo.ok();
    }
    
    @Log("栏目编辑")
    @PostMapping(value = "/v1/update")
    @ApiOperation("栏目编辑")
    public ResultInfo<Boolean> update(@RequestBody LiveColumnDto LiveColumn) {
    	columnLiveBusiness.update(LiveColumn);
        return ResultInfo.ok();
    }
    
    @Log("栏目启用/停用")
    @PostMapping(value = "/v1/enableOrDisable")
    @ApiOperation("栏目启用/停用")
    public ResultInfo<Boolean> enableOrDisable(@RequestBody LiveColumnDto LiveColumn) {
    	columnLiveBusiness.enableOrDisable(LiveColumn);
    	return ResultInfo.ok();
    }
    
    @PostMapping("/v1/codes")
    @ApiOperation("查询栏目code")
    public ResultInfo<ColumnCodeEnum[]> codes() {
        return ResultInfo.ok(ColumnCodeEnum.values());
    }

}
