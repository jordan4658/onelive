package com.onelive.manage.modules.live.controller;

import com.github.pagehelper.PageInfo;
import com.onelive.common.model.common.ResultInfo;
import com.onelive.common.model.dto.login.LoginUser;
import com.onelive.common.model.req.common.LongIdReq;
import com.onelive.common.model.req.live.*;
import com.onelive.common.model.vo.live.game.*;
import com.onelive.manage.modules.base.BaseAdminController;
import com.onelive.manage.modules.live.business.LiveGameBusiness;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/live/game")
@Api(tags = "直播管理-游戏管理")
@Slf4j
public class LiveGameController extends BaseAdminController{
    @Resource
    private LiveGameBusiness business;

    @PostMapping(value = "/v1/getTagList")
    @ApiOperation("直播游戏标签列表")
    public ResultInfo<PageInfo<LiveGameTagListVO>> getTagList(@RequestBody LiveGameTagListReq req) {
        return ResultInfo.ok(business.getTagList(req));
    }


    @PostMapping("/v1/saveTag")
    @ApiOperation("直播游戏标签保存信息")
    public ResultInfo<String> saveTag(@RequestBody LiveGameTagSaveReq req) {
        LoginUser loginUser = getLoginAdmin();
        business.saveTag(req,loginUser);
        return ResultInfo.ok();
    }

    @PostMapping("/v1/getTag")
    @ApiOperation("直播游戏标签信息查询")
    public ResultInfo<LiveGameTagVO> getTag(@RequestBody LongIdReq req) {
        return ResultInfo.ok(business.getTag(req));
    }


    @PostMapping("/v1/delTag")
    @ApiOperation("直播游戏标签信息删除")
    public ResultInfo<String> delTag(@RequestBody LongIdReq req) {
        LoginUser loginUser = getLoginAdmin();
        business.delTag(req,loginUser);
        return ResultInfo.ok();
    }



    @PostMapping(value = "/v1/getGameList")
    @ApiOperation("直播分类游戏列表")
    public ResultInfo<PageInfo<LiveGameListVO>> getGameList(@RequestBody LiveGameListReq req) {
        return ResultInfo.ok(business.getGameList(req));
    }


    @PostMapping("/v1/saveGame")
    @ApiOperation("直播游戏保存信息")
    public ResultInfo<String> saveGame(@RequestBody LiveGameSaveReq req) {
        LoginUser loginUser = getLoginAdmin();
        business.saveGame(req,loginUser);
        return ResultInfo.ok();
    }

    @PostMapping("/v1/getGame")
    @ApiOperation("直播游戏信息查询")
    public ResultInfo<LiveGameVO> getGame(@RequestBody LongIdReq req) {
        return ResultInfo.ok(business.getGame(req));
    }


    @PostMapping("/v1/delGame")
    @ApiOperation("直播游戏信息删除")
    public ResultInfo<String> delGame(@RequestBody LongIdReq req) {
        LoginUser loginUser = getLoginAdmin();
        business.delGame(req,loginUser);
        return ResultInfo.ok();
    }

    @PostMapping("/v1/getSelectList")
    @ApiOperation("游戏列表-用于选择")
    public ResultInfo<List<LiveGameSelectVO>> getSelectList() {
        return ResultInfo.ok(business.getSelectList());
    }

    @PostMapping("/v1/updateGameStatus")
    @ApiOperation("直播游戏状态更新")
    public ResultInfo<String> updateGameStatus(@RequestBody LiveGameStatusUpdateReq req) {
        LoginUser loginUser = getLoginAdmin();
        business.updateGameStatus(req,loginUser);
        return ResultInfo.ok();
    }

}
