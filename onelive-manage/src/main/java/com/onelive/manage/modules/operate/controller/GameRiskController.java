package com.onelive.manage.modules.operate.controller;

import com.github.pagehelper.PageInfo;
import com.onelive.common.model.common.ResultInfo;
import com.onelive.common.model.req.operate.GameRiskReq;
import com.onelive.common.model.vo.operate.GameRiskListVO;
import com.onelive.manage.modules.operate.business.GameRiskBusiness;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 游戏风控
 */
@RestController
@RequestMapping(value = "/operate")
@Api(tags = "运营管理-游戏风控")
@Slf4j
public class GameRiskController {
    @Resource
    private GameRiskBusiness business;

    @PostMapping("/v1/getGameRiskList")
    @ApiOperation("查询游戏风控数据列表")
    public ResultInfo<PageInfo<GameRiskListVO>> getGameRiskList(@RequestBody GameRiskReq req) {
        return ResultInfo.ok(business.getGameRiskList(req));
    }
}
