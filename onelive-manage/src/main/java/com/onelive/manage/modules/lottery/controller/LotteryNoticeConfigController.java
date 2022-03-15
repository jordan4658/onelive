package com.onelive.manage.modules.lottery.controller;

import com.onelive.common.model.common.ResultInfo;
import com.onelive.common.model.dto.login.LoginUser;
import com.onelive.common.model.req.lottery.LotteryNoticeConfigSaveReq;
import com.onelive.common.model.vo.lottery.LotteryNoticeConfigVO;
import com.onelive.manage.common.annotation.Log;
import com.onelive.manage.modules.base.BaseAdminController;
import com.onelive.manage.modules.lottery.business.LotteryNoticeConfigBusiness;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping(value = "/lotteryNoticeConfig")
@Api(tags = "游戏管理-中奖公告配置")
@Slf4j
public class LotteryNoticeConfigController extends BaseAdminController{
    @Resource
    private LotteryNoticeConfigBusiness business;


    @GetMapping("/v1/getConfig")
    @ApiOperation("查询配置列表")
    public ResultInfo<LotteryNoticeConfigVO> getConfig() {
        return ResultInfo.ok(business.getConfig());
    }

    @Log("保存配置信息")
    @PostMapping("/v1/saveConfig")
    @ApiOperation("保存配置信息")
    public ResultInfo<String> saveConfig(@RequestBody LotteryNoticeConfigSaveReq req) {
        LoginUser loginUser = getLoginAdmin();
        business.saveConfig(req,loginUser);
        return ResultInfo.ok();
    }
}
