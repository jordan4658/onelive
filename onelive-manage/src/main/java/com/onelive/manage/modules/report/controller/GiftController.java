package com.onelive.manage.modules.report.controller;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.github.pagehelper.PageInfo;
import com.onelive.common.model.common.ResultInfo;
import com.onelive.common.model.dto.report.LiveGifReportDto;
import com.onelive.manage.modules.report.business.GiftBusiness;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/report/gift")
@Api(tags = "报表系统-礼物报表")
public class GiftController {

    @Resource
    private GiftBusiness giftBusiness;

    @PostMapping("/v1/getList")
    @ApiOperation("礼物报表数据")
    @ResponseBody
    public ResultInfo<PageInfo<LiveGifReportDto>> getList(@RequestBody LiveGifReportDto liveGifReportDto) {
        return ResultInfo.ok(giftBusiness.getList(liveGifReportDto));
    }

}
