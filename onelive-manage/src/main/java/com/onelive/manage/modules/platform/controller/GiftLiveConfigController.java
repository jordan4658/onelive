package com.onelive.manage.modules.platform.controller;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.pagehelper.PageInfo;
import com.onelive.common.model.common.ResultInfo;
import com.onelive.common.model.dto.login.LoginUser;
import com.onelive.common.model.dto.platform.GifTypeDto;
import com.onelive.common.model.dto.platform.LiveGiftDto;
import com.onelive.manage.common.annotation.Log;
import com.onelive.manage.modules.base.BaseAdminController;
import com.onelive.manage.modules.platform.business.GiftLiveConfigBusiness;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping(value = "/platform/gift")
@Api(tags = "直播礼物管理")
public class GiftLiveConfigController extends BaseAdminController {

    @Resource
    private GiftLiveConfigBusiness giftLiveConfigBusiness;

    @PostMapping(value = "/v1/getList")
    @ApiOperation("直播礼物列表")
    public ResultInfo<PageInfo<LiveGiftDto>> getList(LiveGiftDto liveGiftDto) {
        return ResultInfo.ok(giftLiveConfigBusiness.getList(liveGiftDto));
    }
    
    @Log("保存礼物信息")
    @PostMapping("/v1/save")
    @ApiOperation("保存礼物信息")
    public ResultInfo<String> save(@RequestBody LiveGiftDto liveGiftDto) throws Exception {
        LoginUser admin = getLoginAdmin();
        giftLiveConfigBusiness.save(liveGiftDto, admin);
        return ResultInfo.ok();
    }
    
    @Log("礼物编辑")
    @PostMapping(value = "/v1/update")
    @ApiOperation("礼物编辑")
    public ResultInfo<String> update(@RequestBody LiveGiftDto liveGiftDto) {
        LoginUser loginUser = getLoginAdmin();
        giftLiveConfigBusiness.update(liveGiftDto, loginUser);
        return ResultInfo.ok();
    }
    
    @Log("礼物删除")
    @PostMapping(value = "/v1/delete")
    @ApiOperation("礼物删除")
    public ResultInfo<String> delete(@RequestBody LiveGiftDto liveGiftDto) {
    	LoginUser loginUser = getLoginAdmin();
    	giftLiveConfigBusiness.delete(liveGiftDto, loginUser);
    	return ResultInfo.ok();
    }
    
    @PostMapping(value = "/v1/getGiftTypes")
    @ApiOperation("查询礼物类型")
    public ResultInfo<List<GifTypeDto>> getGiftTypes() {
        return ResultInfo.ok(giftLiveConfigBusiness.getGiftTypes());
    }
    

}
