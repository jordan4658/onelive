package com.onelive.manage.modules.report.controller;

import com.github.pagehelper.PageInfo;
import com.onelive.common.model.common.ResultInfo;
import com.onelive.common.model.req.report.UserReportDetailReq;
import com.onelive.common.model.req.report.UserReportReq;
import com.onelive.common.model.vo.report.UserReportDetailAllVO;
import com.onelive.common.model.vo.report.UserReportVO;
import com.onelive.manage.modules.base.BaseAdminController;
import com.onelive.manage.modules.report.business.UserReportBusiness;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;


@RestController
@RequestMapping("/userReport")
@Api(tags = "统计-会员报表")
@Slf4j
public class UserReportController extends BaseAdminController {

    @Resource
    private UserReportBusiness userReportBusiness;

    @ApiOperation("会员报表")
    @GetMapping("/v1/queryUserReport")
    public ResultInfo<PageInfo<UserReportVO>> queryUserReport(@ModelAttribute UserReportReq req) {
        return ResultInfo.ok(userReportBusiness.queryUserReport(req));
    }

    @ApiOperation("会员明细")
    @GetMapping("/v1/queryUserReportDetail")
    public ResultInfo<UserReportDetailAllVO> queryUserReportDetail(@ModelAttribute UserReportDetailReq req) {
        return ResultInfo.ok(userReportBusiness.queryUserDetail(req));
    }

}
