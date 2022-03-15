package com.onelive.api.modules.game.controller;

import com.ob.resp.*;
import com.onelive.api.modules.game.business.GameBusiness;
import com.onelive.api.modules.game.business.GameZRBusiness;
import com.onelive.common.annotation.AllowAccess;
import com.onelive.common.model.common.ResultInfo;
import com.onelive.common.model.req.game.*;
import com.onelive.common.model.vo.game.GameLoginVO;
import com.onelive.common.model.vo.game.app.AppGameCenterGameListVO;
import com.onelive.common.model.vo.game.app.AppGameTagListDataVO;
import com.onelive.common.model.vo.game.app.AppGameTagListVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.annotation.Resource;
import java.util.List;

/**
 * 第三方游戏
 */
@RestController
@RequestMapping("/game")
@Api(tags = "第三方游戏")
public class GameController {
    @Resource
    private GameBusiness gameBusiness;
    @Resource
    private GameZRBusiness gameZRBusiness;

    @PostMapping(value = { "/app/v1/login", "/pc/v1/login" })
    @ApiOperation("登陆")
    @ResponseBody
    public ResultInfo<GameLoginVO> login(@RequestBody GameLoginReq req) throws Exception {
        return ResultInfo.ok(gameBusiness.login(req));
    }

    @PostMapping(value = { "/app/v1/deposit", "/pc/v1/deposit" })
    @ApiOperation("上分")
    @ResponseBody
    public ResultInfo<String> deposit(@RequestBody GameTransferReq req) throws Exception {
        gameBusiness.gameDeposit(req);
        return ResultInfo.ok();
    }

    @PostMapping(value = { "/app/v1/withdraw", "/pc/v1/withdraw" })
    @ApiOperation("下分")
    @ResponseBody
    public ResultInfo<String> withdraw(@RequestBody GameTransferReq req) throws Exception {
        gameBusiness.gameWithdraw(req);
        return ResultInfo.ok();
    }

    @PostMapping(value = { "/app/v1/queryBalance", "/pc/v1/queryBalance" })
    @ApiOperation("查询游戏余额")
    @ResponseBody
    public ResultInfo<BalanceResp> queryBalance(@RequestBody GameBalanceReq req) throws Exception {
        return ResultInfo.ok( gameBusiness.queryBalance(req));
    }

    @PostMapping(value = { "/app/v1/queryWalletBalance", "/pc/v1/queryWalletBalance" })
    @ApiOperation("根据钱包类型查询游戏钱包余额")
    @ResponseBody
    public ResultInfo<BalanceResp> queryWalletBalance(@RequestBody GameWalletReq req) throws Exception {
        return ResultInfo.ok( gameBusiness.queryWalletBalance(req));
    }

    @PostMapping(value = { "/app/v1/queryTransfer", "/pc/v1/queryTransfer" })
    @ApiOperation("查询转账状态")
    @ResponseBody
    @ApiIgnore
    public ResultInfo<QueryTransferResp> queryTransfer(@RequestBody GameTransferStatusReq req) throws Exception {
        return ResultInfo.ok( gameBusiness.queryTransfer(req));
    }

    @PostMapping(value = { "/app/v1/queryTransferList", "/pc/v1/queryTransferList" })
    @ApiOperation("查询转账列表: 体育、彩票")
    @ResponseBody
    @ApiIgnore
    public ResultInfo<BaseCommonResp> queryTransferList(@RequestBody GameTransferListReq req) throws Exception {
        return ResultInfo.ok(gameBusiness.queryTransferList(req));
    }

    @PostMapping(value = { "/app/v1/queryBetRecordList", "/pc/v1/queryBetRecordList" })
    @ApiOperation("查询游戏注单列表")
    @ResponseBody
    @ApiIgnore
    public ResultInfo<QueryBetRecordListResp> queryBetRecordList(@RequestBody GameBetRecordListReq req) throws Exception {
        return ResultInfo.ok(gameBusiness.queryBetRecordList(req));
    }


    @PostMapping(value = { "/app/v1/checkMaintenance", "/pc/v1/checkMaintenance" })
    @ApiOperation("查询真人游戏维护状态")
    @ResponseBody
    public ResultInfo<CheckMaintenanceResp> checkMaintenance(@RequestBody GameZRCheckMaintenanceReq req) throws Exception {
        return ResultInfo.ok(gameZRBusiness.checkMaintenance(req));
    }


    @PostMapping(value = { "/app/v1/queryGameTagList", "/pc/v1/queryGameTagList" })
    @ApiOperation("查询游戏分类列表")
    @ResponseBody
    @AllowAccess
    public ResultInfo<List<AppGameTagListVO>> queryGameTagList() throws Exception {
        return ResultInfo.ok(gameBusiness.queryGameTagList());
    }

    @PostMapping(value = { "/app/v1/queryGameListByTag", "/pc/v1/queryGameListByTag" })
    @ApiOperation("根据标签code查询游戏列表")
    @ResponseBody
    @AllowAccess
    public ResultInfo<List<AppGameCenterGameListVO>> queryGameListByTag(@RequestBody GameListByTagReq req) throws Exception {
        return ResultInfo.ok(gameBusiness.queryGameListByTag(req));
    }


    @PostMapping(value = { "/app/v1/list", "/pc/v1/list" })
    @ApiOperation("查询游戏分类列表和分类下的游戏列表")
    @ResponseBody
    @AllowAccess
    public ResultInfo<List<AppGameTagListDataVO>> list() throws Exception {
        return ResultInfo.ok(gameBusiness.list());
    }

/*
    @PostMapping(value = { "/app/v1/listGame" })
    @ApiOperation("查询游戏列表")
    @ResponseBody
    @AllowAccess
    public ResultInfo<String> listGame(@RequestBody GameListSyncReq req) throws Exception {
        gameBusiness.listGame(req);
        return ResultInfo.ok();
    }*/
}
