package com.onelive.manage.modules.mem.controller;

import com.github.pagehelper.PageInfo;
import com.onelive.common.model.common.PageReq;
import com.onelive.common.model.common.ResultInfo;
import com.onelive.common.model.dto.login.LoginUser;
import com.onelive.common.model.req.common.LongIdReq;
import com.onelive.common.model.req.mem.MemLevelVipSaveReq;
import com.onelive.common.model.vo.mem.MemUserLevelVipInfoVO;
import com.onelive.common.model.vo.mem.MemUserLevelSelectVO;
import com.onelive.common.model.vo.mem.MemUserLevelVO;
import com.onelive.manage.common.annotation.Log;
import com.onelive.manage.modules.base.BaseAdminController;
import com.onelive.manage.modules.mem.business.MemLevelVipBusiness;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * 用户等级
 */
@RestController
@RequestMapping(value = "/level")
@Api(tags = "用户管理-用户等级")
@Slf4j
public class MemLevelVipController extends BaseAdminController {

    @Resource
    private MemLevelVipBusiness business;

    @PostMapping(value = "/v1/getList")
    @ApiOperation("用户等级列表")
    public ResultInfo<PageInfo<MemUserLevelVO>> getList(@RequestBody PageReq req) {
        return ResultInfo.ok(business.getList(req));
    }

    @PostMapping(value = "/v1/getLevelInfo")
    @ApiOperation("查询用户等级信息")
    public ResultInfo<MemUserLevelVipInfoVO> getLevelInfo(@RequestBody LongIdReq req) {
        return ResultInfo.ok(business.getLevelInfo(req));
    }

    @GetMapping(value = "/v1/getSelectList")
    @ApiOperation("选择用户等级列表")
    public ResultInfo<PageInfo<MemUserLevelSelectVO>> getSelectList() {
        return business.getSelectList();
    }



    @Log("保存等级信息")
    @PostMapping("/v1/saveLevel")
    @ApiOperation("保存等级信息")
    public ResultInfo<Boolean> saveLevel(@RequestBody MemLevelVipSaveReq req) {
        LoginUser admin = getLoginAdmin();
        return business.saveLevel(req,admin);
    }

   /* @Log("更新等级信息")
    @PostMapping("/v1/updateLevel")
    @ApiOperation("更新等级信息")
    public ResultInfo<Boolean> updateLevel( MemLevelVipUpdateReq req) {
        LoginUser admin = getLoginAdmin();
        return business.updateLevel(req,admin);
    }
*/

    @GetMapping(value = "/v1/getMaxVipLevel")
    @ApiOperation("获取当前最大用户等级")
    public ResultInfo<Integer> getMaxVipLevel() {
        return business.getMaxVipLevel();
    }

}
