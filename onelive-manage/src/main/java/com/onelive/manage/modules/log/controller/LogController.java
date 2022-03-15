package com.onelive.manage.modules.log.controller;


import javax.annotation.Resource;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.github.pagehelper.PageInfo;
import com.onelive.common.model.common.ResultInfo;
import com.onelive.common.model.req.log.LogQueryParam;
import com.onelive.common.model.vo.log.SysLogListVO;
import com.onelive.common.model.vo.log.SysLogVO;
import com.onelive.manage.common.annotation.Log;
import com.onelive.manage.modules.log.business.LogBusiness;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

;

/**
 * @author lorenzo
 * @Description: 日志管理控制器
 * @date 2021/4/2
 */
@RestController
@RequestMapping("/api/logs")
@Api(tags = "系统管理-系统日志")
public class LogController {

    @Resource
    private LogBusiness logBusiness;

    @GetMapping("/v1/getLogList")
    @ApiOperation("查询日志分页")
    public ResultInfo<PageInfo<SysLogVO>> getLogList(@ModelAttribute LogQueryParam param) {
        return ResultInfo.ok(logBusiness.getLogList(param));
    }

    @GetMapping("/v1/getLog")
    @ApiOperation("查询日志详情")
    public ResultInfo<SysLogVO> getLog(@RequestParam Long id) {

        return ResultInfo.ok(logBusiness.getLog(id));
    }

    @Log("删除所有ERROR日志")
    @PostMapping("/v1/delAllErrorLog")
    @ApiOperation("删除所有ERROR日志")
    public ResultInfo delAllErrorLog() {
        logBusiness.delAllErrorLog();
        return ResultInfo.ok();
    }

    @Log("删除所有INFO日志")
    @PostMapping("/v1/delAllInfoLog")
    @ApiOperation("删除所有INFO日志")
    public ResultInfo delAllInfoLog() {
        logBusiness.delAllInfoLog();
        return ResultInfo.ok();
    }
    
    

    @GetMapping("/v1/getList")
    @ApiOperation("查询操作日志分页")
    public ResultInfo<PageInfo<SysLogListVO>> getList(
            @ApiParam("操作用户") @RequestParam(name = "operator", required = false) String operator,
            @ApiParam("第几页") @RequestParam(name = "pageNum", required = false, defaultValue = "1") Integer pageNum,
            @ApiParam("每页最大页数") @RequestParam(name = "pageSize", required = false, defaultValue = "10") Integer pageSize) {
        return ResultInfo.ok(logBusiness.getList(operator,pageNum,pageSize));
    }
}
