package com.onelive.manage.modules.game.controller;

import com.github.pagehelper.PageInfo;
import com.onelive.common.model.common.ResultInfo;
import com.onelive.common.model.dto.login.LoginUser;
import com.onelive.common.model.req.common.LongIdListReq;
import com.onelive.common.model.req.common.LongIdReq;
import com.onelive.common.model.req.game.GameCenterGameListReq;
import com.onelive.common.model.req.game.GameCenterGameSaveReq;
import com.onelive.common.model.req.game.GameTagListReq;
import com.onelive.common.model.req.game.GameTagSaveReq;
import com.onelive.common.model.req.game.gamecenter.GameThirdSelectListReq;
import com.onelive.common.model.req.game.tag.GameTagUpdateStatusReq;
import com.onelive.common.model.req.game.third.GameTagSelectListReq;
import com.onelive.common.model.vo.game.*;
import com.onelive.common.model.vo.game.gamecenter.GameThirdSelectListVO;
import com.onelive.common.model.vo.game.third.GameTagSelectListVO;
import com.onelive.manage.modules.base.BaseAdminController;
import com.onelive.manage.modules.game.business.GameCenterBusiness;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Description: 第三方游戏
 */
@RestController
@RequestMapping(value = "/gameCenter")
@Api(tags = "第三方游戏管理-游戏中心管理")
@Slf4j
public class GameCenterController extends BaseAdminController {

    @Resource
    private GameCenterBusiness business;

    @PostMapping(value = "/v1/getTagList")
    @ApiOperation("游戏标签列表")
    public ResultInfo<PageInfo<GameTagListVO>> getTagList(@RequestBody GameTagListReq req) {
        return ResultInfo.ok(business.getTagList(req));
    }

    @PostMapping(value = "/v1/getTagSelectList")
    @ApiOperation("游戏标签列表-用于选择")
    public ResultInfo<List<GameTagSelectListVO>> getTagSelectList(@RequestBody GameTagSelectListReq req) {
        return ResultInfo.ok(business.getTagSelectList(req));
    }


    @PostMapping("/v1/saveTag")
    @ApiOperation("游戏标签保存信息")
    public ResultInfo<String> saveTag(@RequestBody GameTagSaveReq req) {
        LoginUser loginUser = getLoginAdmin();
        business.saveTag(req,loginUser);
        return ResultInfo.ok();
    }

    @PostMapping("/v1/updateTagStatus")
    @ApiOperation("游戏标签状态修改")
    public ResultInfo<Boolean> updateTagStatus(@RequestBody GameTagUpdateStatusReq req) {
        LoginUser loginUser = getLoginAdmin();
        return ResultInfo.ok(business.updateTagStatus(req,loginUser));
    }

    @PostMapping("/v1/getTag")
    @ApiOperation("游戏标签信息查询")
    public ResultInfo<GameTagVO> getTag(@RequestBody LongIdReq req) {
        return ResultInfo.ok(business.getTag(req));
    }


    @PostMapping("/v1/delTag")
    @ApiOperation("游戏标签信息删除")
    public ResultInfo<String> delTag(@RequestBody LongIdReq req) {
        LoginUser loginUser = getLoginAdmin();
        business.delTag(req,loginUser);
        return ResultInfo.ok();
    }


    @PostMapping("/v1/getSelectList")
    @ApiOperation("获取第三方分类和游戏列表-用于选择")
    public ResultInfo<List<GameSelectListVO>> getSelectList() {
        return ResultInfo.ok(business.getSelectList());
    }


    @PostMapping("/v1/getCategorySelectList")
    @ApiOperation("获取第三方分类列表-用于选择")
    public ResultInfo<List<GameCategorySelectListVO>> getCategorySelectList() {
        return ResultInfo.ok(business.getCategorySelectList());
    }

    @PostMapping(value = "/v1/getGameList")
    @ApiOperation("分类游戏列表")
    public ResultInfo<PageInfo<GameCenterGameListVO>> getGameList(@RequestBody GameCenterGameListReq req) {
        return ResultInfo.ok(business.getGameList(req));
    }

    @PostMapping(value = "/v1/getGameSelectList")
    @ApiOperation("第三方游戏列表-用于选择")
    public ResultInfo<List<GameThirdSelectListVO>> getGameSelectList(@RequestBody GameThirdSelectListReq req) {
        return ResultInfo.ok(business.getGameSelectList(req));
    }


    @PostMapping("/v1/saveGame")
    @ApiOperation("游戏保存信息")
    public ResultInfo<String> saveGame(@RequestBody GameCenterGameSaveReq req) {
        LoginUser loginUser = getLoginAdmin();
        business.saveGame(req,loginUser);
        return ResultInfo.ok();
    }

    @PostMapping("/v1/getGame")
    @ApiOperation("游戏信息查询")
    public ResultInfo<GameCenterGameVO> getGame(@RequestBody LongIdReq req) {
        return ResultInfo.ok(business.getGame(req));
    }


    @PostMapping("/v1/delGame")
    @ApiOperation("游戏信息删除")
    public ResultInfo<String> delGame(@RequestBody LongIdListReq req) {
        LoginUser loginUser = getLoginAdmin();
        business.delGame(req,loginUser);
        return ResultInfo.ok();
    }

}
