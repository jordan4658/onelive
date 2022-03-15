package com.onelive.manage.modules.platform.controller;

import com.github.pagehelper.PageInfo;
import com.onelive.common.model.common.ResultInfo;
import com.onelive.common.model.dto.login.LoginUser;
import com.onelive.common.model.req.common.LongIdReq;
import com.onelive.common.model.req.platform.GameIndexListReq;
import com.onelive.common.model.req.platform.GameIndexSaveReq;
import com.onelive.common.model.vo.platform.AppRouteVO;
import com.onelive.common.model.vo.platform.GameIndexListVO;
import com.onelive.common.model.vo.platform.GameIndexVO;
import com.onelive.manage.modules.base.BaseAdminController;
import com.onelive.manage.modules.platform.business.GameIndexBusiness;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * 首页游戏配置
 */
@RestController
@RequestMapping("/platform/index/game")
@Api(tags = "平台管理-首页游戏配置")
@Slf4j
public class GameIndexController extends BaseAdminController{
    @Resource
    private GameIndexBusiness business;

    @PostMapping(value = "/v1/getGameList")
    @ApiOperation("首页游戏列表")
    public ResultInfo<PageInfo<GameIndexListVO>> getGameList(@RequestBody GameIndexListReq req) {
        return ResultInfo.ok(business.getGameList(req));
    }


    @PostMapping("/v1/saveGame")
    @ApiOperation("首页游戏保存信息")
    public ResultInfo<String> saveGame(@RequestBody GameIndexSaveReq req) {
        LoginUser loginUser = getLoginAdmin();
        business.saveGame(req,loginUser);
        return ResultInfo.ok();
    }

    @PostMapping("/v1/getGame")
    @ApiOperation("首页游戏信息查询")
    public ResultInfo<GameIndexVO> getGame(@RequestBody LongIdReq req) {
        return ResultInfo.ok(business.getGame(req));
    }


    @PostMapping("/v1/delGame")
    @ApiOperation("首页游戏信息删除")
    public ResultInfo<String> delGame(@RequestBody LongIdReq req) {
        LoginUser loginUser = getLoginAdmin();
        business.delGame(req,loginUser);
        return ResultInfo.ok();
    }

    @GetMapping("/v1/listAppPage")
    @ApiOperation("APP内页面-用于选择")
    public ResultInfo<List<AppRouteVO>> listAppPage() {
        return ResultInfo.ok(business.listAppPage());
    }




}
