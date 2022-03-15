package com.onelive.manage.modules.mem.controller;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.github.pagehelper.PageInfo;
import com.onelive.common.model.common.ResultInfo;
import com.onelive.common.model.vo.mem.UserRiskListVO;
import com.onelive.manage.modules.base.BaseAdminController;
import com.onelive.manage.modules.mem.business.UserRiskBusiness;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;

/**
 * @Description: 家族管理
 */
@RestController
@RequestMapping(value = "/userRisk")
@Api(tags = "运营管理-用户风控")
@Slf4j
public class UserRiskController extends BaseAdminController {

    @Resource
    private UserRiskBusiness userRiskBusiness;

    @GetMapping(value = "/v1/getList")
    @ApiOperation("列表")
    public ResultInfo<PageInfo<UserRiskListVO>> getList(
            @ApiParam("开始日期") @RequestParam(name = "startDate", required = false) String startDate,
            @ApiParam("结束日期") @RequestParam(name = "endDate", required = false) String endDate,
            @ApiParam("地区") @RequestParam(name = "countryId", required = false) Long countryId,
            @ApiParam("设备标识码") @RequestParam(name = "equip", required = false) String equip,
            @ApiParam("ip") @RequestParam(name = "ip", required = false) String ip,
            @ApiParam("用户账号") @RequestParam(name = "userAccount", required = false) String userAccount,
            @ApiParam("第几页") @RequestParam(name = "pageNum", required = false, defaultValue = "1") Integer pageNum,
            @ApiParam("每页最大页数") @RequestParam(name = "pageSize", required = false, defaultValue = "10") Integer pageSize) {
        return ResultInfo.ok(userRiskBusiness.getRiskList(startDate,endDate,countryId, equip,userAccount,ip,pageNum, pageSize));
    }

}
