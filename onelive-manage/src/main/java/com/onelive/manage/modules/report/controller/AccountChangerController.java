package com.onelive.manage.modules.report.controller;


import com.github.pagehelper.PageInfo;
import com.onelive.common.model.common.ResultInfo;
import com.onelive.common.model.vo.mem.AccountChangeTypeVO;
import com.onelive.common.model.vo.report.MemGoldChangeBackVO;
import com.onelive.common.utils.others.DateInnerUtil;
import com.onelive.manage.modules.report.business.AccountChangerBusiness;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/accountChanger")
@Api(tags = "统计-帐变记录")
@Slf4j
public class AccountChangerController {


    @Resource
    private AccountChangerBusiness accountChangerBusiness;

    @ApiOperation("获取 账变类型列表")
    @GetMapping("/getAccountChangeTypeList")
    public ResultInfo<List<AccountChangeTypeVO>> getAccountChangeTypeList() {
        List<AccountChangeTypeVO> list=accountChangerBusiness.getAccountChangeTypeList();
        return ResultInfo.ok(list);
    }



    @ApiOperation("查询-用户账变记录")
    @GetMapping("/listPage")
    public ResultInfo<PageInfo<MemGoldChangeBackVO>> listPage(
            @ApiParam("页数默认1") @RequestParam(value = "pageNum", required = false, defaultValue = "1") Integer pageNum,
            @ApiParam("每页调试默认10") @RequestParam(value = "pageSize", required = false, defaultValue = "10") Integer pageSize,
            @ApiParam("开始时间") @RequestParam(value = "startTime", required = false) String startTime,
            @ApiParam("结束时间") @RequestParam(value = "endTime", required = false) String endTime,
            @ApiParam("用户名称") @RequestParam(value = "nickName", required = false) String nickName,
            @ApiParam("用户账号") @RequestParam(value = "account", required = false) String account,
            @ApiParam("帐变类型") @RequestParam(value = "changeType", required = false) Integer changeType) {

        PageInfo<MemGoldChangeBackVO> pageInfo = accountChangerBusiness.listPage(pageNum,pageSize, DateInnerUtil.parseDate(startTime),DateInnerUtil.parseDate(endTime), nickName, account, changeType);
        return ResultInfo.ok(pageInfo);
    }

}
