package com.onelive.manage.modules.finance.controller;


import com.github.pagehelper.PageInfo;
import com.onelive.common.model.common.ResultInfo;
import com.onelive.common.model.req.pay.PayFindExchangeCfgAddReq;
import com.onelive.common.model.req.pay.PayFindExchangeCfgUpdateReq;
import com.onelive.common.model.vo.pay.PayFindExchangeCfgVO;
import com.onelive.common.model.vo.pay.QueryExchangeVO;
import com.onelive.manage.modules.base.BaseAdminController;
import com.onelive.manage.modules.finance.business.PayFindExchangeCfgBusiness;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/finance/exchange/key")
@Api(tags = "财务管理-汇率管理-查询汇率key列表")
public class PayFindExchangeCfgController extends BaseAdminController {

    @Resource
    private PayFindExchangeCfgBusiness payFindExchangeCfgBusiness;

    @GetMapping(value = "/v1/pageList")
    @ApiOperation("key列表（分页）")
    public ResultInfo<PageInfo<PayFindExchangeCfgVO>> pageList(
            @ApiParam(value = "条数默认10", defaultValue = "10") @RequestParam(value = "pageSize", required = false, defaultValue = "10") Integer pageSize,
            @ApiParam(value = "页数默认1", defaultValue = "1") @RequestParam(value = "pageNum", required = false, defaultValue = "1") Integer pageNum,
            @ApiParam(value = "请求key") @RequestParam(value = "exchangeKey", required = false) String exchangeKey,
            @ApiParam(value = "汇率来源") @RequestParam(value = "exchangeDataSourceCode", required = false) String exchangeDataSourceCode
    ) {
        return ResultInfo.ok(payFindExchangeCfgBusiness.pageList(pageSize, pageNum, exchangeKey,exchangeDataSourceCode));
    }


    @PostMapping(value = "/v1/add")
    @ApiOperation("新增key配置信息")
    public ResultInfo<Boolean> save(@RequestBody PayFindExchangeCfgAddReq req) {
        payFindExchangeCfgBusiness.save(req, getLoginAdmin());
        return ResultInfo.ok();
    }

    @PostMapping(value = "/v1/update")
    @ApiOperation("更新key配置信息")
    public ResultInfo<String> update(@RequestBody PayFindExchangeCfgUpdateReq req) {
        payFindExchangeCfgBusiness.update(req, getLoginAdmin());
        return ResultInfo.ok();
    }

    @PostMapping(value = "/v1/delete")
    @ApiOperation("删除key配置信息")
    public ResultInfo<String> delete(@ApiParam(value = "key Ids", required = true) @RequestBody List<Long> ids) {
        payFindExchangeCfgBusiness.delete(ids, getLoginAdmin());
        return ResultInfo.ok();
    }

    @GetMapping(value = "/v1/exchangeDataSourceList")
    @ApiOperation("汇率查询数据来源列表")
    public ResultInfo<List<QueryExchangeVO>> exchangeDataSourceList() {
        return ResultInfo.ok(payFindExchangeCfgBusiness.exchangeDataSourceList());
    }

}
