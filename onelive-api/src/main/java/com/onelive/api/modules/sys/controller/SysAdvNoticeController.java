package com.onelive.api.modules.sys.controller;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.onelive.api.modules.sys.business.SysAdvNoticeBusiness;
import com.onelive.common.model.common.ResultInfo;
import com.onelive.common.model.dto.sys.SysAdvNoticeDto;
import com.onelive.common.model.req.sys.SysAdvNoticeTypeReq;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 *@Description: 公告
 */
@RestController
@RequestMapping(value = "/advNotice")
@Api(tags = "公告相关接口")
public class SysAdvNoticeController {

    @Resource
    private SysAdvNoticeBusiness business;

	@PostMapping(value = { "/app/v1/getByType", "/pc/v1/getByType" })
    @ApiOperation("直播列表推荐栏目公告，一条数据")
	@ResponseBody
    public ResultInfo<SysAdvNoticeDto> getByType(@RequestBody SysAdvNoticeTypeReq sysAdvNoticeTypeReq) {
        return ResultInfo.ok(business.getByType(sysAdvNoticeTypeReq.getType()));
    }

}
