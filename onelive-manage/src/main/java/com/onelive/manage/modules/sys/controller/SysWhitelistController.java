package com.onelive.manage.modules.sys.controller;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.github.pagehelper.PageInfo;
import com.onelive.common.model.common.ResultInfo;
import com.onelive.common.model.dto.login.LoginUser;
import com.onelive.common.model.req.sys.SysWhitelistReq;
import com.onelive.common.model.req.sys.SysWhitelistUpdateReq;
import com.onelive.common.model.vo.sys.SysWhitelistListVO;
import com.onelive.common.model.vo.sys.SysWhitelistVO;
import com.onelive.manage.common.annotation.Log;
import com.onelive.manage.modules.base.BaseAdminController;
import com.onelive.manage.modules.sys.business.SysWhitelistBusiness;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;

/**
 * @author
 *@Description: ip白名单
 */
@RestController
@RequestMapping(value = "/whitelist")
@Slf4j
@Api(tags = "员工管理-ip白名单")
public class SysWhitelistController extends BaseAdminController {

    @Resource
    private SysWhitelistBusiness business;

    @GetMapping("/v1/getList")
    @ApiOperation("列表")
    public ResultInfo<PageInfo<SysWhitelistListVO>> getList( 
            @ApiParam("搜索关键字") @RequestParam(name = "keyword", required = false) String keyword,
            @ApiParam("第几页") @RequestParam(name = "pageNum", required = false, defaultValue = "1") Integer pageNum,
            @ApiParam("每页最大页数") @RequestParam(name = "pageSize", required = false, defaultValue = "10") Integer pageSize) {
        PageInfo<SysWhitelistListVO> list = business.getList(keyword,pageNum,pageSize);
        return ResultInfo.ok(list);
    }

    @Log("添加")
    @PostMapping("/v1/add")
    @ApiOperation("添加")
    public ResultInfo<String> add(@RequestBody SysWhitelistReq req) {
        LoginUser admin = getLoginAdmin();
        business.save(req, admin);
        return ResultInfo.ok();
    }
    

    @GetMapping("/v1/getById")
    @ApiOperation("根据id获取信息")
    public ResultInfo<SysWhitelistVO> getById(@ApiParam("id") @RequestParam("id") Long id) {
    	SysWhitelistVO vo = business.getById(id);
        return ResultInfo.ok(vo);
    }

    @Log("编辑")
    @PostMapping("/v1/update")
    @ApiOperation("编辑")
    public ResultInfo<String> update(@RequestBody SysWhitelistUpdateReq req) {
        LoginUser admin = getLoginAdmin();
        business.update(req, admin);
        return ResultInfo.ok();
    }

    @Log("删除")
    @PostMapping("/v1/delete")
    @ApiOperation("删除")
    public ResultInfo<String> switchStatus(@ApiParam("id") @RequestParam("id")  Long id) {
        LoginUser admin = getLoginAdmin();
        business.delete(id, admin.getAccLogin());
        return ResultInfo.ok();
    }
   

}
