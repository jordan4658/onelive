package com.onelive.manage.modules.sys.controller;

import com.github.pagehelper.PageInfo;
import com.onelive.common.model.common.ResultInfo;
import com.onelive.common.model.dto.login.LoginUser;
import com.onelive.common.model.req.sys.SysParameterReq;
import com.onelive.common.model.req.sys.SysParameterSwitchReq;
import com.onelive.common.model.vo.sys.SysParameterByCodeVO;
import com.onelive.common.model.vo.sys.SysParameterByIdVO;
import com.onelive.common.model.vo.sys.SysParameterListVO;
import com.onelive.manage.common.annotation.Log;
import com.onelive.manage.modules.base.BaseAdminController;
import com.onelive.manage.modules.sys.business.SysParamBusiness;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.annotation.Resource;

/**
 * @Description: 系统参数
 * @date 2021/3/17
 */

@RestController
@RequestMapping(value = "/sysparam")
@Api(tags = "系统管理-系统参数")
@Slf4j
public class SysParamController extends BaseAdminController {

    @Resource
    private SysParamBusiness sysParamBusiness;

    @GetMapping(value = "/v1/getList")
    @ApiOperation("系统参数列表")
    public ResultInfo<PageInfo<SysParameterListVO>> getList(
            @ApiParam("系统参数code名称") @RequestParam(name = "paramCode", required = false) String paramCode,
            @ApiParam("第几页") @RequestParam(name = "pageNum", required = false, defaultValue = "1") Integer pageNum,
            @ApiParam("每页最大页数") @RequestParam(name = "pageSize", required = false, defaultValue = "10") Integer pageSize) {
        return ResultInfo.ok(sysParamBusiness.getList(paramCode, pageNum, pageSize));
    }

    @Log("系统参数编辑")
    @PostMapping(value = "/v1/updateSysParameter")
    @ApiOperation("系统参数编辑")
    public ResultInfo<String> updateSysParameter(@ModelAttribute SysParameterReq req) {
        LoginUser loginUser = getLoginAdmin();
        sysParamBusiness.updateSysParameter(req, loginUser);
        return ResultInfo.ok();
    }

    @Log("编辑系统参数开关")
    @PostMapping(value = "/v1/updateSwitchStatus")
    @ApiOperation("系统参数开关")
    public ResultInfo<String> updateSwitchStatus(@ModelAttribute SysParameterSwitchReq req) {
        LoginUser loginUser = getLoginAdmin();
        sysParamBusiness.updateSwitchStatus(req, loginUser);
        return ResultInfo.ok();
    }

    @GetMapping("/v1/getByCode")
    @ApiOperation("根据code获取系统参数")
    @ApiIgnore
    public ResultInfo<SysParameterByCodeVO> getByCode(@ApiParam("系统参数代码") @RequestParam("paramCode") String code) {
        SysParameterByCodeVO details = sysParamBusiness.getByCode(code);
        return ResultInfo.ok(details);
    }

    @GetMapping("/v1/getById")
    @ApiOperation("根据id获取系统参数")
    public ResultInfo<SysParameterByIdVO> getById(@ApiParam("系统参数id") @RequestParam("paramId") Long id) {
        SysParameterByIdVO details = sysParamBusiness.getById(id);
        return ResultInfo.ok(details);
    }

    @Log("删除系统参数")
    @GetMapping("/v1/updateDeleteStatus")
    @ApiOperation("删除系统参数")
    public ResultInfo updateDeleteStatus(@ApiParam("系统参数id") @RequestParam("paramId") Long id) {
        sysParamBusiness.updateDeleteStatus(id);
        return ResultInfo.ok();
    }

}
