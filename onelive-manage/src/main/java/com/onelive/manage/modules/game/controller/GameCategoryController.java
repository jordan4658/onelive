package com.onelive.manage.modules.game.controller;

import com.github.pagehelper.PageInfo;
import com.onelive.common.model.common.PageReq;
import com.onelive.common.model.common.ResultInfo;
import com.onelive.common.model.dto.login.LoginUser;
import com.onelive.common.model.req.common.LongIdReq;
import com.onelive.common.model.req.game.GameCategorySaveReq;
import com.onelive.common.model.req.game.GameThirdListReq;
import com.onelive.common.model.req.game.GameThirdSaveReq;
import com.onelive.common.model.req.game.third.GameInfoSelectReq;
import com.onelive.common.model.vo.game.*;
import com.onelive.common.model.vo.game.category.GamePlatformSelectVO;
import com.onelive.common.model.vo.game.third.GameInfoSelectVO;
import com.onelive.common.model.vo.game.third.GamePlatformAndCategoryVO;
import com.onelive.manage.modules.base.BaseAdminController;
import com.onelive.manage.modules.game.business.GameCategoryBusiness;
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
@RequestMapping(value = "/game")
@Api(tags = "第三方游戏管理-游戏分类管理")
@Slf4j
public class GameCategoryController extends BaseAdminController {

    @Resource
    private GameCategoryBusiness business;

    @PostMapping(value = "/v1/getCategoryList")
    @ApiOperation("第三方游戏分类列表")
    public ResultInfo<PageInfo<GameCategoryListVO>> getCategoryList(@RequestBody PageReq req) {
        return ResultInfo.ok(business.getCategoryList(req));
    }

    @PostMapping(value = "/v1/getCategorySelectList")
    @ApiOperation("第三方游戏分类列表-用于选择")
    public ResultInfo<List<GameCategorySelectListVO>> getCategorySelectList() {
        return ResultInfo.ok(business.getCategorySelectList());
    }

    @PostMapping("/v1/saveCategory")
    @ApiOperation("游戏分类保存信息")
    public ResultInfo<String> saveCategory(@RequestBody GameCategorySaveReq req) {
        LoginUser loginUser = getLoginAdmin();
        business.saveCategory(req,loginUser);
        return ResultInfo.ok();
    }

    @PostMapping("/v1/getCategory")
    @ApiOperation("游戏分类信息查询")
    public ResultInfo<GameCategoryVO> getCategory(@RequestBody LongIdReq req) {
        return ResultInfo.ok(business.getCategory(req));
    }


    @PostMapping("/v1/delCategory")
    @ApiOperation("游戏分类信息删除")
    public ResultInfo<String> delCategory(@RequestBody LongIdReq req) {
        LoginUser loginUser = getLoginAdmin();
        business.delCategory(req,loginUser);
        return ResultInfo.ok();
    }

    @PostMapping("/v1/getGameThirdCategoryTypeSelectList")
    @ApiOperation("第三方游戏分类下拉选项列表")
    @ApiIgnore
    public ResultInfo<List<GameThirdCategoryTypeSelectVO>> getGameThirdCategoryTypeSelectList() {
        //1.第三方游戏分类
        return ResultInfo.ok(business.getGameThirdCategoryTypeSelectList());
    }

    @PostMapping("/v1/getGameWalletSelectList")
    @ApiOperation("第三方游戏钱包下拉选项列表")
    @ApiIgnore
    public ResultInfo<List<GameWalletSelectVO>> getGameWalletSelectList() {
        //2.钱包类型
        return ResultInfo.ok(business.getGameWalletSelectList());
    }

    @PostMapping("/v1/getGamePlatformSelectList")
    @ApiOperation("获取分类中所有下拉选项列表")
    public ResultInfo<List<GamePlatformSelectVO>> getGamePlatformSelectList() {
        return ResultInfo.ok(business.getGamePlatformSelectList());
    }

    @PostMapping("/v1/getGamePlatformAndCategorySelectList")
    @ApiOperation("获取游戏中平台和分类下拉选项列表")
    public ResultInfo<List<GamePlatformAndCategoryVO>> getGamePlatformAndCategorySelectList() {
        return ResultInfo.ok(business.getGamePlatformAndCategorySelectList());
    }

    @PostMapping(value = "/v1/getGameSelectList")
    @ApiOperation("根据分类获取第三方游戏列表-用于选择")
    public ResultInfo<List<GameInfoSelectVO>> getGameSelectList(@RequestBody GameInfoSelectReq req) {
        return ResultInfo.ok(business.getGameSelectList(req));
    }

    @PostMapping(value = "/v1/getGameList")
    @ApiOperation("第三方游戏列表")
    public ResultInfo<PageInfo<GameThirdListVO>> getGameList(@RequestBody GameThirdListReq req) {
        return ResultInfo.ok(business.getGameList(req));
    }


    @PostMapping("/v1/saveGame")
    @ApiOperation("第三方游戏保存信息")
    public ResultInfo<String> saveGame(@RequestBody GameThirdSaveReq req) {
        LoginUser loginUser = getLoginAdmin();
        business.saveGame(req,loginUser);
        return ResultInfo.ok();
    }

    @PostMapping("/v1/getGame")
    @ApiOperation("第三方游戏信息查询")
    public ResultInfo<GameThirdVO> getGame(@RequestBody LongIdReq req) {
        return ResultInfo.ok(business.getGame(req));
    }


    @PostMapping("/v1/delGame")
    @ApiOperation("第三方游戏信息删除")
    public ResultInfo<String> delGame(@RequestBody LongIdReq req) {
        LoginUser loginUser = getLoginAdmin();
        business.delGame(req,loginUser);
        return ResultInfo.ok();
    }

}
