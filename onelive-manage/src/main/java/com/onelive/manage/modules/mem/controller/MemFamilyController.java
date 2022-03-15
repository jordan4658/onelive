package com.onelive.manage.modules.mem.controller;

import com.github.pagehelper.PageInfo;
import com.onelive.common.model.common.ResultInfo;
import com.onelive.common.model.dto.login.LoginUser;
import com.onelive.common.model.req.common.LongIdReq;
import com.onelive.common.model.req.mem.family.MemFamilyListReq;
import com.onelive.common.model.req.mem.family.MemFamilySaveReq;
import com.onelive.common.model.vo.mem.MemFamilyListVO;
import com.onelive.common.model.vo.mem.MemFamilyVO;
import com.onelive.manage.common.annotation.Log;
import com.onelive.manage.modules.base.BaseAdminController;
import com.onelive.manage.modules.mem.business.MemFamilyBusiness;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @Description: 家族管理
 */
@RestController
@RequestMapping(value = "/family")
@Api(tags = "直播管理-家族管理")
public class MemFamilyController extends BaseAdminController {

    @Resource
    private MemFamilyBusiness memFamilyBusiness;

    @PostMapping(value = "/v1/getList")
    @ApiOperation("家族列表")
    public ResultInfo<PageInfo<MemFamilyListVO>> getList(@RequestBody MemFamilyListReq req) {
        return ResultInfo.ok(memFamilyBusiness.getList(req));
    }
    
    @Log("保存家族信息")
    @PostMapping("/v1/saveFamily")
    @ApiOperation("保存家族信息")
    public ResultInfo<String> saveFamily(@RequestBody MemFamilySaveReq req) throws Exception {
        LoginUser admin = getLoginAdmin();
        memFamilyBusiness.saveFamily(req, admin);
        return ResultInfo.ok();
    }
    
    @Log("家族编辑")
    @PostMapping(value = "/v1/updateFamily")
    @ApiOperation("家族编辑")
    public ResultInfo<String> updateFamily(@RequestBody MemFamilySaveReq req) {
        LoginUser loginUser = getLoginAdmin();
        memFamilyBusiness.updateFamily(req, loginUser);
        return ResultInfo.ok();
    }

    @GetMapping("/v1/getById")
    @ApiOperation("根据id获取家族信息")
    public ResultInfo<MemFamilyVO> getById(@RequestBody LongIdReq req) {
    	MemFamilyVO vo = memFamilyBusiness.getById(req.getId());
        return ResultInfo.ok(vo);
    }

//    @Log("删除家族")
//    @PostMapping("/v1/updateDeleteStatus")
//    @ApiOperation("删除家族")
//    public ResultInfo updateDeleteStatus(@RequestBody Long id) {
//    	memFamilyBusiness.updateDeleteStatus(id);
//        return ResultInfo.ok();
//    }

}
