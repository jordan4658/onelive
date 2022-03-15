package com.onelive.manage.modules.game.controller;

import com.github.pagehelper.PageInfo;
import com.onelive.common.model.common.PageReq;
import com.onelive.common.model.common.ResultInfo;
import com.onelive.common.model.dto.login.LoginUser;
import com.onelive.common.model.req.common.LongIdReq;
import com.onelive.common.model.req.game.platform.GamePlatformSaveReq;
import com.onelive.common.model.req.game.platform.GamePlatformUpdateStatusReq;
import com.onelive.common.model.vo.game.platform.GamePlatformSelectListVO;
import com.onelive.common.model.vo.game.platform.GamePlatformVO;
import com.onelive.common.model.vo.game.platform.GameThirdPlatformListVO;
import com.onelive.common.model.vo.game.platform.GameThirdPlatformSelectListVO;
import com.onelive.manage.modules.base.BaseAdminController;
import com.onelive.manage.modules.game.business.GameThirdPlatformBusiness;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Description: 第三方游戏
 */
@RestController
@RequestMapping(value = "/gamePlatform")
@Api(tags = "第三方游戏管理-第三方游戏平台管理")
@Slf4j
public class GameThirdPlatformController extends BaseAdminController {

    @Resource
    private GameThirdPlatformBusiness business;

    @PostMapping(value = "/v1/getPlatformList")
    @ApiOperation("第三方游戏平台列表")
    public ResultInfo<PageInfo<GameThirdPlatformListVO>> getPlatformList(@RequestBody PageReq req) {
        return ResultInfo.ok(business.getPlatformList(req));
    }
    @PostMapping(value = "/v1/getPlatformSelectList")
    @ApiOperation("获取下拉选项列表")
    public ResultInfo<List<GameThirdPlatformSelectListVO>> getPlatformSelectList() {
        return ResultInfo.ok(business.getPlatformSelectList());
    }

    @PostMapping("/v1/savePlatform")
    @ApiOperation("第三方游戏平台保存信息")
    public ResultInfo<String> savePlatform(@RequestBody GamePlatformSaveReq req) {
        LoginUser loginUser = getLoginAdmin();
        business.savePlatform(req,loginUser);
        return ResultInfo.ok();
    }

    @PostMapping("/v1/updatePlatformStatus")
    @ApiOperation("第三方游戏平台状态更新")
    public ResultInfo<Boolean> updatePlatformStatus(@RequestBody GamePlatformUpdateStatusReq req) {
        LoginUser loginUser = getLoginAdmin();
        return ResultInfo.ok(business.updatePlatformStatus(req,loginUser));
    }

    @PostMapping("/v1/getPlatform")
    @ApiOperation("第三方游戏平台信息查询")
    public ResultInfo<GamePlatformVO> getPlatform(@RequestBody LongIdReq req) {
        return ResultInfo.ok(business.getPlatform(req));
    }


    @PostMapping("/v1/delPlatform")
    @ApiOperation("第三方游戏平台信息删除")
    public ResultInfo<String> delPlatform(@RequestBody LongIdReq req) {
        LoginUser loginUser = getLoginAdmin();
        business.delPlatform(req,loginUser);
        return ResultInfo.ok();
    }


    @PostMapping("/v1/getSelectList")
    @ApiOperation("获取第三方游戏平台列表-用于选择")
    @ApiIgnore
    public ResultInfo<List<GamePlatformSelectListVO>> getSelectList() {
        return ResultInfo.ok(business.getSelectList());
    }

}
