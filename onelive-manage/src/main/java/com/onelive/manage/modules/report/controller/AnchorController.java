package com.onelive.manage.modules.report.controller;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.github.pagehelper.PageInfo;
import com.onelive.common.model.common.ResultInfo;
import com.onelive.common.model.dto.report.AnchorReportDto;
import com.onelive.manage.modules.report.business.AnchorBusiness;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;


@RestController
@RequestMapping("/report/anchor")
@Api(tags = "报表系统-主播报表")
public class AnchorController {

    @Resource
    private AnchorBusiness anchorBusiness;

    @PostMapping("/v1/getList")
    @ApiOperation("主播报表")
    @ResponseBody
    public ResultInfo<PageInfo<AnchorReportDto>> getList(@RequestBody AnchorReportDto anchorReportDto) {
        return ResultInfo.ok(anchorBusiness.getList(anchorReportDto));
    }

}
