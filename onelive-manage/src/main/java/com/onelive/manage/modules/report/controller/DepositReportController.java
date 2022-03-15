package com.onelive.manage.modules.report.controller;

import com.alicp.jetcache.anno.CacheType;
import com.alicp.jetcache.anno.Cached;
import com.onelive.common.model.common.ResultInfo;
import com.onelive.common.model.req.report.DepositOnlineReq;
import com.onelive.common.model.req.report.DepositReq;
import com.onelive.common.model.vo.report.DepositOnlineVO;
import com.onelive.common.model.vo.report.DepositVO;
import com.onelive.manage.modules.base.BaseAdminController;
import com.onelive.manage.modules.report.business.DepositReportBusiness;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;
import java.util.concurrent.TimeUnit;


@RestController
@RequestMapping("/depositReport")
@Api(tags = "统计-入款报表")
@Slf4j
public class DepositReportController extends BaseAdminController {

    @Resource
    private DepositReportBusiness depositReportBusiness;

    @ApiOperation("查询入款报表")
    @GetMapping("/v1/queryList")
    @Cached(name = "depositReport_queryList", key = "#req.beginDate+ '_' + #req.endDate",
            cacheType = CacheType.LOCAL, expire = 10, timeUnit = TimeUnit.SECONDS, cacheNullValue = true)
    public ResultInfo<List<DepositVO>> queryList(DepositReq req) {
        return ResultInfo.ok(depositReportBusiness.queryList(req));
    }

    @ApiOperation("查询入款-线上支付报表")
    @GetMapping("/v1/queryOnlineList")
//    @Cached(name = "depositReport_queryOnlineList", key = "#req.beginDate+ '_' + #req.endDate + '_' + #req.providerName",
//            cacheType = CacheType.LOCAL, expire = 10, timeUnit = TimeUnit.SECONDS, cacheNullValue = true)
    public ResultInfo<List<DepositOnlineVO>> queryOnlineList(DepositOnlineReq req) {
        return ResultInfo.ok(depositReportBusiness.queryOnlineList(req));
    }

}
