package com.onelive.manage.modules.report.controller;

import com.alibaba.fastjson.JSONObject;
import com.onelive.common.model.common.ResultInfo;
import com.onelive.common.model.req.report.FundsReportReq;
import com.onelive.common.model.vo.report.FundsReportVO;
import com.onelive.manage.modules.base.BaseAdminController;
import com.onelive.manage.modules.report.business.FundsReportBusiness;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;


@RestController
@RequestMapping("/fundsReport")
@Api(tags = "报表系统-资金报表")
@Slf4j
public class FundsReportController extends BaseAdminController {

    @Resource
    private FundsReportBusiness fundsReportBusiness;

    @ApiOperation("查询资金报表")
    @GetMapping("/v1/queryFundsReport")
//    @Cached(name = "fundsReport_queryFundsReport", key = "#req.beginDate+ '_' + #req.endDate",
//            cacheType = CacheType.LOCAL, expire = 10, timeUnit = TimeUnit.SECONDS, cacheNullValue = true)
    public ResultInfo<FundsReportVO> queryFundsReport(FundsReportReq req) {
        log.info("进入/v1/queryFundsReport,param:{}", JSONObject.toJSONString(req));
        FundsReportVO vo = fundsReportBusiness.queryFundsReport(req);
        return ResultInfo.ok(vo);
    }


}
