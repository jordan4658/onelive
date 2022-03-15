package com.onelive.manage.modules.mem.controller;

import com.github.pagehelper.PageInfo;
import com.onelive.common.model.common.ResultInfo;
import com.onelive.common.model.dto.login.LoginUser;
import com.onelive.common.model.req.mem.anchor.MemUserAnchorListReq;
import com.onelive.common.model.req.mem.anchor.MemUserAnchorSaveReq;
import com.onelive.common.model.req.mem.anchor.MemUserAnchorSearchReq;
import com.onelive.common.model.req.mem.anchor.MemUserAnchorStatusReq;
import com.onelive.common.model.vo.mem.AnchorForAccVO;
import com.onelive.common.model.vo.mem.MemUserAnchorVO;
import com.onelive.manage.common.annotation.Log;
import com.onelive.manage.modules.base.BaseAdminController;
import com.onelive.manage.modules.mem.business.MemAnchorBusiness;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping(value = "/anchor")
@Api(tags = "直播管理-主播管理")
public class MemAnchorController extends BaseAdminController {

    @Resource
    private MemAnchorBusiness memAnchorBusiness;

    @PostMapping(value = "/v1/getList")
    @ApiOperation("主播列表")
    @ResponseBody
    public ResultInfo<PageInfo<MemUserAnchorVO>> getList(@RequestBody MemUserAnchorListReq req) {
        return ResultInfo.ok(memAnchorBusiness.getList(req));
    }
    
    @Log("保存主播信息")
    @PostMapping("/v1/save")
    @ApiOperation("保存主播信息")
    @ResponseBody
    public ResultInfo<String> save(@RequestBody MemUserAnchorSaveReq memUserAnchorReq) throws Exception {
        LoginUser admin = getLoginAdmin();
        memAnchorBusiness.save(memUserAnchorReq, admin);
        return ResultInfo.ok();
    }
    
    @Log("主播编辑")
    @PostMapping(value = "/v1/update")
    @ApiOperation("主播编辑")
    @ResponseBody
    public ResultInfo<String> update(@RequestBody MemUserAnchorSaveReq req) {
        LoginUser loginUser = getLoginAdmin();
        memAnchorBusiness.update(req, loginUser);
        return ResultInfo.ok();
    }
    
    @Log("主播账号启用/停用")
    @PostMapping(value = "/v1/enableOrDisable")
    @ApiOperation("主播账号启用/停用")
    @ResponseBody
    public ResultInfo<String> enableOrDisable(@RequestBody MemUserAnchorStatusReq req) {
    	LoginUser loginUser = getLoginAdmin();
    	memAnchorBusiness.enableOrDisable(req, loginUser);
    	return ResultInfo.ok();
    }
    
    @PostMapping(value = "/v1/getByAcc")
    @ApiOperation("根据账号查询主播")
    @ResponseBody
    public ResultInfo<AnchorForAccVO> getByUserAccount(@RequestBody MemUserAnchorSearchReq req) {
        return ResultInfo.ok(memAnchorBusiness.getByAcc(req));
    }

}
