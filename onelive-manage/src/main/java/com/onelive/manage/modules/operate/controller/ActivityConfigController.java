package com.onelive.manage.modules.operate.controller;

import com.github.pagehelper.PageInfo;
import com.onelive.common.model.common.ResultInfo;
import com.onelive.common.model.dto.login.LoginUser;
import com.onelive.common.model.req.common.LongIdReq;
import com.onelive.common.model.req.operate.FirstRechargeActivityConfigSaveReq;
import com.onelive.common.model.req.operate.PopularizeActivityConfigSaveReq;
import com.onelive.common.model.req.operate.RedPacketActivityConfigSaveReq;
import com.onelive.common.model.req.operate.RegisterReturnActivityConfigSaveReq;
import com.onelive.common.model.vo.operate.ActivityConfigListVo;
import com.onelive.common.model.vo.operate.ActivityConfigSelectListVo;
import com.onelive.common.model.vo.operate.ActivityConfigVo;
import com.onelive.manage.common.annotation.Log;
import com.onelive.manage.modules.base.BaseAdminController;
import com.onelive.manage.modules.operate.business.ActivityConfigBusiness;
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
 * 后台活动管理
 */
@RestController
@RequestMapping(value = "/operate")
@Api(tags = "运营管理-活动配置")
@Slf4j
public class ActivityConfigController extends BaseAdminController {
    @Resource
    private ActivityConfigBusiness business;


    @PostMapping("/v1/getConfigList")
    @ApiOperation("查询活动配置列表")
    public ResultInfo<PageInfo<ActivityConfigListVo>> getList() {
        return ResultInfo.ok(business.getConfigList());
    }

    @PostMapping("/v1/getConfigSelectList")
    @ApiOperation("查询用于选择的活动配置列表")
    public ResultInfo<List<ActivityConfigSelectListVo>> getConfigSelectList() {
        return ResultInfo.ok(business.getConfigSelectList());
    }

    @PostMapping("/v1/getConfig")
    @ApiOperation("查询活动配置内容")
    public ResultInfo<ActivityConfigVo> getConfig(@RequestBody LongIdReq req) {
        return ResultInfo.ok(business.getConfig(req.getId()));
    }

    @Log("保存推广返现活动配置")
    @PostMapping("/v1/saveConfig1")
    @ApiOperation("保存推广返现活动配置")
    public ResultInfo<String> saveConfig1(@RequestBody PopularizeActivityConfigSaveReq req) {
        LoginUser admin = getLoginAdmin();
        business.saveConfig1(req,admin);
        return ResultInfo.ok();
    }



    @Log("保存首充返现活动配置")
    @PostMapping("/v1/saveConfig3")
    @ApiOperation("保存首充返现活动配置")
    public ResultInfo<String> saveConfig3(@RequestBody FirstRechargeActivityConfigSaveReq req) {
        LoginUser admin = getLoginAdmin();
        business.saveConfig3(req,admin);
        return ResultInfo.ok();
    }

    @Log("保存红包活动配置")
    @PostMapping("/v1/saveConfig4")
    @ApiOperation("保存红包活动配置")
    public ResultInfo<String> saveConfig4(@RequestBody RedPacketActivityConfigSaveReq req) {
        LoginUser admin = getLoginAdmin();
        business.saveConfig2(req,admin);
        return ResultInfo.ok();
    }

    @Log("保存注册返现活动配置")
    @PostMapping("/v1/saveConfig5")
    @ApiOperation("保存注册返现活动配置")
    public ResultInfo<String> saveConfig5(@RequestBody RegisterReturnActivityConfigSaveReq req) {
        LoginUser loginUser = getLoginAdmin();
        business.saveConfig5(req,loginUser);
        return ResultInfo.ok();
    }


}
