package com.onelive.manage.modules.report.controller;

import com.github.pagehelper.PageInfo;
import com.onelive.common.model.common.ResultInfo;
import com.onelive.common.model.req.report.GameDetailReportReq;
import com.onelive.common.model.req.report.GameReportReq;
import com.onelive.common.model.req.report.GameSelectReq;
import com.onelive.common.model.vo.report.ColumnSelectVO;
import com.onelive.common.model.vo.report.GameDetailReportVO;
import com.onelive.common.model.vo.report.GameReportVO;
import com.onelive.common.model.vo.report.GameSelectVO;
import com.onelive.manage.modules.report.business.GameReportBusiness;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * 游戏报表
 */
@RestController
@RequestMapping("/gameReport")
@Api(tags = "报表系统-游戏报表")
@Slf4j
public class GameReportController {

    @Resource
    private GameReportBusiness business;

    @ApiOperation("游戏报表数据统计列表")
    @PostMapping("/v1/queryGameReport")
    public ResultInfo<PageInfo<GameReportVO>> queryGameReport(@RequestBody GameReportReq req) {
        return ResultInfo.ok(business.queryGameReport(req));
    }

    @ApiOperation("查询指定平台游戏数据详情")
    @PostMapping("/v1/queryGameDetailReport")
    public ResultInfo<PageInfo<GameDetailReportVO>> queryGameDetailReport(@RequestBody GameDetailReportReq req) {
        return ResultInfo.ok(business.queryGameDetailReport(req));
    }

    @ApiOperation("查询指定平台游戏下拉游戏选项")
    @PostMapping("/v1/queryGameSelect")
    public ResultInfo<List<GameSelectVO>> queryGameSelect(@RequestBody GameSelectReq req) {
        return ResultInfo.ok(business.queryGameSelect(req));
    }

    @ApiOperation("查询下拉项目选项")
    @PostMapping("/v1/queryColumnSelect")
    public ResultInfo<List<ColumnSelectVO>> queryColumnSelect() {
        return ResultInfo.ok(business.queryColumnSelect());
    }

}
