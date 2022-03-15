package com.onelive.manage.modules.platform.controller;

import com.github.pagehelper.PageInfo;
import com.onelive.common.model.common.ResultInfo;
import com.onelive.common.model.dto.login.LoginUser;
import com.onelive.common.model.req.common.LongIdReq;
import com.onelive.common.model.req.platform.GameUserCenterListReq;
import com.onelive.common.model.req.platform.GameUserCenterSaveReq;
import com.onelive.common.model.vo.platform.GameUserCenterListVO;
import com.onelive.common.model.vo.platform.GameUserCenterVO;
import com.onelive.manage.modules.base.BaseAdminController;
import com.onelive.manage.modules.platform.business.GameUserCenterBusiness;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 用户中心游戏配置
 */
@RestController
@RequestMapping("/platform/userCenter/game")
@Api(tags = "平台管理-用户中心游戏配置")
@Slf4j
public class GameUserCenterController extends BaseAdminController{
    @Resource
    private GameUserCenterBusiness business;

    @PostMapping(value = "/v1/getGameList")
    @ApiOperation("用户中心游戏列表")
    public ResultInfo<PageInfo<GameUserCenterListVO>> getGameList(@RequestBody GameUserCenterListReq req) {
        return ResultInfo.ok(business.getGameList(req));
    }


    @PostMapping("/v1/saveGame")
    @ApiOperation("用户中心游戏保存信息")
    public ResultInfo<String> saveGame(@RequestBody GameUserCenterSaveReq req) {
        LoginUser loginUser = getLoginAdmin();
        business.saveGame(req,loginUser);
        return ResultInfo.ok();
    }

    @PostMapping("/v1/getGame")
    @ApiOperation("用户中心游戏信息查询")
    public ResultInfo<GameUserCenterVO> getGame(@RequestBody LongIdReq req) {
        return ResultInfo.ok(business.getGame(req));
    }


    @PostMapping("/v1/delGame")
    @ApiOperation("用户中心游戏信息删除")
    public ResultInfo<String> delGame(@RequestBody LongIdReq req) {
        LoginUser loginUser = getLoginAdmin();
        business.delGame(req,loginUser);
        return ResultInfo.ok();
    }

}
