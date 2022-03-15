package com.onelive.manage.modules.finance.controller;


import com.github.pagehelper.PageInfo;
import com.onelive.common.model.common.ResultInfo;
import com.onelive.common.model.req.pay.PayExchangeRateCfgAddReq;
import com.onelive.common.model.req.pay.PayExchangeRateCfgUpdateReq;
import com.onelive.common.model.vo.pay.PayExchangeRateCfgVO;
import com.onelive.manage.modules.base.BaseAdminController;
import com.onelive.manage.modules.finance.business.PayExchangeRateCfgBusiness;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/finance/exchange/rateCfg")
@Api(tags = "财务管理-汇率管理-汇率配置")
public class PayExchangeRateCfgController extends BaseAdminController {

    @Resource
    private PayExchangeRateCfgBusiness payExchangeRateCfgBusiness;


    @GetMapping(value = "/v1/pageList")
    @ApiOperation("汇率配置列表（分页）")
    public ResultInfo<PageInfo<PayExchangeRateCfgVO>> pageList(
            @ApiParam(value = "条数默认10", defaultValue = "10") @RequestParam(value = "pageSize", required = false, defaultValue = "10") Integer pageSize,
            @ApiParam(value = "页数默认1", defaultValue = "1") @RequestParam(value = "pageNum", required = false, defaultValue = "1") Integer pageNum,
            @ApiParam(value = "国家编码") @RequestParam(value = "currencyCode", required = false) String currencyCode
    ) {
        return ResultInfo.ok(payExchangeRateCfgBusiness.pageList(pageSize, pageNum, currencyCode));
    }


    @PostMapping(value = "/v1/add")
    @ApiOperation("新增汇率配置信息")
    public ResultInfo<Boolean> save(@RequestBody PayExchangeRateCfgAddReq req) {
        payExchangeRateCfgBusiness.save(req, getLoginAdmin());
        return ResultInfo.ok();
    }

    @PostMapping(value = "/v1/update")
    @ApiOperation("更新汇率配置信息")
    public ResultInfo<String> update(@RequestBody PayExchangeRateCfgUpdateReq req) {
        payExchangeRateCfgBusiness.update(req, getLoginAdmin());
        return ResultInfo.ok();
    }

    @PostMapping(value = "/v1/delete")
    @ApiOperation("删除汇率配置信息")
    public ResultInfo<Boolean> delete(@ApiParam(value = "汇率配置信息IDs", required = true) @RequestBody List<Long> ids) {
        payExchangeRateCfgBusiness.delete(ids, getLoginAdmin());
        return ResultInfo.ok();
    }
}
