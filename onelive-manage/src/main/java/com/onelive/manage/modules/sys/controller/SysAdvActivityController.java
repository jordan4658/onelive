package com.onelive.manage.modules.sys.controller;

import com.github.pagehelper.PageInfo;
import com.onelive.common.model.common.PageReq;
import com.onelive.common.model.common.ResultInfo;
import com.onelive.common.model.dto.login.LoginUser;
import com.onelive.common.model.req.common.LongIdReq;
import com.onelive.common.model.req.sys.SysAdvActivitySaveReq;
import com.onelive.common.model.vo.sys.SysAdvActivityListVO;
import com.onelive.common.model.vo.sys.SysAdvActivitySelectVO;
import com.onelive.common.model.vo.sys.SysAdvActivityVO;
import com.onelive.manage.common.annotation.Log;
import com.onelive.manage.modules.base.BaseAdminController;
import com.onelive.manage.modules.sys.business.SysAdvActivityBusiness;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author
 *@Description: 活动管理
 */
@RestController
@RequestMapping(value = "/advActivity")
@Slf4j
@Api(tags = "平台管理-活动管理")
public class SysAdvActivityController extends BaseAdminController {


    @Resource
    private SysAdvActivityBusiness business;

    @PostMapping("/v1/getActivityList")
    @ApiOperation("活动列表")
    public ResultInfo<PageInfo<SysAdvActivityListVO>> getActivityList(@RequestBody PageReq req) {
        PageInfo<SysAdvActivityListVO> list = business.getActivityList(req);
        return ResultInfo.ok(list);
    }

    @PostMapping("/v1/getSelectList")
    @ApiOperation("活动列表-用于选择")
    public ResultInfo<List<SysAdvActivitySelectVO>> getSelectList() {
        return ResultInfo.ok(business.getSelectList());
    }

    @Log("编辑活动")
    @PostMapping("/v1/save")
    @ApiOperation("编辑活动")
    public ResultInfo<String> save(@RequestBody SysAdvActivitySaveReq req) {
        LoginUser admin = getLoginAdmin();
        business.save(req,admin);
        return ResultInfo.ok();
    }

    @Log("删除活动")
    @PostMapping("/v1/delete")
    @ApiOperation("删除活动")
    public ResultInfo<String> delete(@RequestBody LongIdReq req) {
        LoginUser admin = getLoginAdmin();
        business.deleteActivity(req.getId(),admin);
        return ResultInfo.ok();
    }


    @PostMapping("/v1/getActivity")
    @ApiOperation("根据ID获取活动详情")
    public ResultInfo<SysAdvActivityVO> getActivity(@RequestBody LongIdReq req) {
        SysAdvActivityVO vo = business.getActivity(req.getId());
        return ResultInfo.ok(vo);
    }

}
