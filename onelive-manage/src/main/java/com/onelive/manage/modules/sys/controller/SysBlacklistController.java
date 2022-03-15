package com.onelive.manage.modules.sys.controller;

import java.util.List;

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
import com.onelive.common.model.req.sys.SysBlacklistReq;
import com.onelive.common.model.req.sys.SysBlacklistUpdateReq;
import com.onelive.common.model.vo.sys.SysBlacklistListVO;
import com.onelive.common.model.vo.sys.SysBlacklistVO;
import com.onelive.manage.common.annotation.Log;
import com.onelive.manage.modules.base.BaseAdminController;
import com.onelive.manage.modules.sys.business.SysBlacklistBusiness;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;

/**
 * @author
 *@Description: 用户黑名单
 */
@RestController
@RequestMapping(value = "/blacklist")
@Slf4j
@Api(tags = "平台管理-用户黑名单")
public class SysBlacklistController extends BaseAdminController {

    @Resource
    private SysBlacklistBusiness business;

    @GetMapping("/v1/getList")
    @ApiOperation("列表")
    public ResultInfo<PageInfo<SysBlacklistListVO>> getList( 
            @ApiParam("搜索关键字") @RequestParam(name = "keyword", required = false) String keyword,
            @ApiParam("第几页") @RequestParam(name = "pageNum", required = false, defaultValue = "1") Integer pageNum,
            @ApiParam("每页最大页数") @RequestParam(name = "pageSize", required = false, defaultValue = "10") Integer pageSize) {
        PageInfo<SysBlacklistListVO> list = business.getList(keyword,pageNum,pageSize);
        return ResultInfo.ok(list);
    }

    @Log("添加")
    @PostMapping("/v1/add")
    @ApiOperation("添加")
    public ResultInfo<String> add(@RequestBody SysBlacklistReq req) {
        LoginUser admin = getLoginAdmin();
        business.save(req, admin);
        return ResultInfo.ok();
    }
    

    @GetMapping("/v1/getById")
    @ApiOperation("根据id获取信息")
    public ResultInfo<SysBlacklistVO> getById(@ApiParam("id") @RequestParam("id") Long id) {
    	SysBlacklistVO vo = business.getById(id);
        return ResultInfo.ok(vo);
    }

    @Log("编辑")
    @PostMapping("/v1/update")
    @ApiOperation("编辑")
    public ResultInfo<String> update(@RequestBody SysBlacklistUpdateReq req) {
        LoginUser admin = getLoginAdmin();
        business.update(req, admin);
        return ResultInfo.ok();
    }

    @Log("删除")
    @PostMapping("/v1/delete")
    @ApiOperation("删除")
    public ResultInfo<String> switchStatus(@RequestBody SysBlacklistUpdateReq req) {
        LoginUser admin = getLoginAdmin();
        business.delete(req.getIds(), admin.getAccLogin());
        return ResultInfo.ok();
    }
   

}
