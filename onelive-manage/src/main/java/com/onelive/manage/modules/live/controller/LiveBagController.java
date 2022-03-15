package com.onelive.manage.modules.live.controller;

import com.github.pagehelper.PageInfo;
import com.onelive.common.model.common.ResultInfo;
import com.onelive.common.model.dto.login.LoginUser;
import com.onelive.common.model.req.live.LiveBagIdReq;
import com.onelive.common.model.req.live.LiveBagListReq;
import com.onelive.common.model.req.live.LiveBagSaveReq;
import com.onelive.common.model.vo.live.LiveBagListVO;
import com.onelive.common.model.vo.live.LiveBagVO;
import com.onelive.manage.common.annotation.Log;
import com.onelive.manage.modules.base.BaseAdminController;
import com.onelive.manage.modules.live.business.LiveBagBusiness;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/live/bag")
@Api(tags = "直播管理-背包管理")
@Slf4j
public class LiveBagController extends BaseAdminController{

	@Resource
    private LiveBagBusiness liveBagBusiness;

    @PostMapping("/v1/getList")
    @ApiOperation("查询背包列表")
    public ResultInfo<PageInfo<LiveBagListVO>> getList(@RequestBody LiveBagListReq param) {
        return liveBagBusiness.getList(param);
    }

    @Log("保存背包内容")
    @PostMapping(value = "/v1/saveBag")
    @ApiOperation("保存背包内容")
    public ResultInfo<String> saveBag(@RequestBody LiveBagSaveReq req) {
        LoginUser loginUser = getLoginAdmin();
        liveBagBusiness.saveBag(req,loginUser);
        return ResultInfo.ok();
    }


    @PostMapping("/v1/getBag")
    @ApiOperation("查询背包信息")
    public ResultInfo<LiveBagVO> getBag(@RequestBody LiveBagIdReq req) {
        return ResultInfo.ok(liveBagBusiness.getBag(req.getId()));
    }


    @Log("删除背包内容")
    @PostMapping(value = "/v1/deleteBag")
    @ApiOperation("删除背包内容")
    public ResultInfo<String> deleteBag(@RequestBody LiveBagIdReq req) {
        LoginUser loginUser = getLoginAdmin();
        liveBagBusiness.deleteBag(req.getId(),loginUser);
        return ResultInfo.ok();
    }


}
