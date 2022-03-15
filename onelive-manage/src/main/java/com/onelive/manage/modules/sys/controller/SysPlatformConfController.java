package com.onelive.manage.modules.sys.controller;

import com.onelive.common.model.common.ResultInfo;
import com.onelive.common.model.dto.login.LoginUser;
import com.onelive.common.model.req.sys.SysBargainingChipConfigListReq;
import com.onelive.common.model.req.sys.SysBargainingChipConfigStatusReq;
import com.onelive.common.model.vo.sys.SysBargainingChipConfigListVO;
import com.onelive.manage.common.annotation.Log;
import com.onelive.manage.modules.base.BaseAdminController;
import com.onelive.manage.modules.sys.business.SysPlatformConfBusiness;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * 平台配置
 */
@RestController
@RequestMapping("/platform/conf")
@Api(tags = "平台管理-平台配置")
@Slf4j
public class SysPlatformConfController extends BaseAdminController {
    @Resource
    private SysPlatformConfBusiness sysPlatformConfBusiness;


    @Log("保存筹码配置")
    @PostMapping(value = "/v1/saveBargainingChipConfig")
    @ApiOperation("保存筹码配置")
    public ResultInfo<String> saveBargainingChipConfig(@RequestBody SysBargainingChipConfigListReq req) {
        LoginUser loginUser = getLoginAdmin();
        sysPlatformConfBusiness.saveBargainingChipConfig(req,loginUser);
        return ResultInfo.ok();
    }


    @GetMapping("/v1/getBargainingChipConfigList")
    @ApiOperation("筹码配置列表")
    public ResultInfo<List<SysBargainingChipConfigListVO>> getBargainingChipConfigList() {
        List<SysBargainingChipConfigListVO> list = sysPlatformConfBusiness.getBargainingChipConfigList();
        return ResultInfo.ok(list);
    }



    @Log("保存筹码配置")
    @PostMapping(value = "/v1/switchBargainingChipConfigStatus")
    @ApiOperation("切换筹码配置状态")
    public ResultInfo<String> switchBargainingChipConfigStatus(@RequestBody SysBargainingChipConfigStatusReq req) {
        LoginUser loginUser = getLoginAdmin();
        sysPlatformConfBusiness.switchBargainingChipConfigStatus(req,loginUser);
        return ResultInfo.ok();
    }

}
