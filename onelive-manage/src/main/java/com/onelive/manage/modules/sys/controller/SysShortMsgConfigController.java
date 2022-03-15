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
import com.onelive.common.model.dto.platform.MsgBusinessDto;
import com.onelive.common.model.req.sys.SysShortMsgConfigReq;
import com.onelive.common.model.req.sys.SysShortMsgConfigUpdateReq;
import com.onelive.common.model.req.sys.SysShortMsgSwitchReq;
import com.onelive.common.model.vo.sys.SysShortMsgConfigVO;
import com.onelive.manage.common.annotation.Log;
import com.onelive.manage.modules.base.BaseAdminController;
import com.onelive.manage.modules.sys.business.SysShortMsgConfigBusiness;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**
 * @author
 *@Description: 短信配置
 */
@RestController
@RequestMapping(value = "/shortMsgConfig")
@Api(tags = "平台管理-短信配置")
public class SysShortMsgConfigController extends BaseAdminController {

    @Resource
    private SysShortMsgConfigBusiness business;

    @GetMapping("/v1/getList")
    @ApiOperation("列表")
    public ResultInfo<PageInfo<SysShortMsgConfigVO>> getList( 
            @ApiParam("第几页") @RequestParam(name = "pageNum", required = false, defaultValue = "1") Integer pageNum,
            @ApiParam("每页最大页数") @RequestParam(name = "pageSize", required = false, defaultValue = "10") Integer pageSize) {
        PageInfo<SysShortMsgConfigVO> list = business.getList(pageNum,pageSize);
        return ResultInfo.ok(list);
    }

    @Log("添加")
    @PostMapping("/v1/add")
    @ApiOperation("添加")
    public ResultInfo<String> add( @RequestBody SysShortMsgConfigReq req) {
        LoginUser admin = getLoginAdmin();
        business.save(req, admin);
        return ResultInfo.ok();
    }
    

    @GetMapping("/v1/getById")
    @ApiOperation("根据id获取信息")
    public ResultInfo<SysShortMsgConfigVO> getById(@ApiParam("id") @RequestParam("id") Long id) {
    	SysShortMsgConfigVO vo = business.getById(id);
        return ResultInfo.ok(vo);
    }

    @Log("编辑")
    @PostMapping("/v1/update")
    @ApiOperation("编辑")
    public ResultInfo<String> update(@RequestBody SysShortMsgConfigUpdateReq req) {
        LoginUser admin = getLoginAdmin();
        business.update(req, admin);
        return ResultInfo.ok();
    }

    @Log("删除")
    @PostMapping("/v1/delete")
    @ApiOperation("删除")
    public ResultInfo<String> delete(@ApiParam("id") @RequestParam("id") Long  id) {
        LoginUser admin = getLoginAdmin();
        business.delete(id, admin.getAccLogin());
        return ResultInfo.ok();
    }
   
    @Log("关闭/开启")
    @PostMapping("/v1/switch")
    @ApiOperation("关闭/开启")
    public ResultInfo<String> switchStatus(@RequestBody SysShortMsgSwitchReq sysShortMsgSwitchReq) {
    	business.switchStatus(sysShortMsgSwitchReq);
    	return ResultInfo.ok();
    }
    
    
    @Log("查询短信服务商")
    @PostMapping("/v1/getBusiness")
    @ApiOperation("查询短信服务商")
    public ResultInfo<List<MsgBusinessDto>> getBusiness() {
    	return ResultInfo.ok(business.getBusiness());
    }
    

}
