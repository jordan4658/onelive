package com.onelive.manage.modules.report.controller;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import com.onelive.common.annotation.AllowAccess;
import com.onelive.common.model.common.ResultInfo;
import com.onelive.common.model.vo.report.StatReportVO;
import com.onelive.manage.modules.base.BaseAdminController;
import com.onelive.manage.modules.report.business.ReportStatisticsBusiness;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author lorenzo
 * @Description 统计-报表统计
 * @Date 2021/5/25 9:57
 */
@RestController
@RequestMapping("/reportStatistics")
@Api(tags = "统计-报表统计")
@Slf4j
public class ReportStatisticsController extends BaseAdminController {

    @Resource
    private ReportStatisticsBusiness reportStatisticsBusiness;

    @GetMapping("/getReport")
    @AllowAccess
    public ResultInfo<StatReportVO> getReport(@ApiParam("开始时间") @RequestParam String startTimeStr, @ApiParam("结束时间") @RequestParam String endTimeStr) {
        DateTime startTime = DateUtil.parseDateTime(startTimeStr);
        DateTime endTime = DateUtil.parseDateTime(endTimeStr);
        return ResultInfo.ok(reportStatisticsBusiness.getReport(startTime, endTime));
    }

}
