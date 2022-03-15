package com.onelive.api.modules.mem.controller;


import javax.annotation.Resource;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.onelive.api.modules.mem.business.MemUserBusiness;
import com.onelive.common.base.BaseController;
import com.onelive.common.model.common.ResultInfo;
import com.onelive.common.model.dto.login.AppLoginUser;
import com.onelive.common.model.req.mem.MemUserDetailReq;
import com.onelive.common.model.req.mem.MemUserFocusUserReq;
import com.onelive.common.model.req.mem.UserWalletReq;
import com.onelive.common.model.vo.live.LiveUserDetailVO;
import com.onelive.common.model.vo.mem.MemWalletVO;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**
 * @Description:  用户信息接口
 */
@RestController
@RequestMapping("/user")
@Api(tags = "用户信息接口")
public class MemUserController extends BaseController {

    @Resource
    private MemUserBusiness memUserBusiness;

    @ApiOperation("关注用户")
    @PostMapping(value = {"/app/v1/focusUser", "/pc/v1/focusUser"})
    public ResultInfo<String> focusUser(@RequestBody MemUserFocusUserReq req) {
       return memUserBusiness.focusUser(req);
    }

    @ApiOperation("取消关注用户")
    @PostMapping(value = {"/app/v1/cancleUser", "/pc/v1/cancleUser"})
    public ResultInfo<String> cancleUser(@RequestBody MemUserFocusUserReq req) {
        return memUserBusiness.cancleUser(req);
    }

    @ApiOperation("获取直播间用户/主播名片")
    @PostMapping(value = {"/app/v1/getUserDetailInfo", "/pc/v1/getUserDetailInfo"})
    public ResultInfo<LiveUserDetailVO> getUserDetailInfo(@RequestBody MemUserDetailReq req) {
        return ResultInfo.ok(memUserBusiness.getUserDetailInfo(req.getUserId()));
    }


    @ApiOperation("获取用户钱包信息-接口")
    @PostMapping("/app/v1/getUserWallet")
    public ResultInfo<MemWalletVO> getUserWallet(@RequestBody UserWalletReq req) {
        AppLoginUser user = getLoginUserAPP();
        return ResultInfo.ok(memUserBusiness.getUserWalletByUserId(user.getId(),req.getWalletType()));
    }

    @ApiOperation("根据用户ID获取用户钱包信息-接口")
    @PostMapping("/app/v1/getUserWalletByUserId")
    public ResultInfo<MemWalletVO> getUserWalletByUserId(@ApiParam(value = "用户Id",required = true)@RequestParam("userId") Long userId,
                                                         @ApiParam(value = "钱包类型:1=平台钱包,请参考钱包类型枚举",required = true) @RequestParam("walletType") Integer walletType) {
        return ResultInfo.ok(memUserBusiness.getUserWalletByUserId(userId,walletType));
    }
    @ApiOperation("根据用户账号获取用户钱包信息-接口")
    @PostMapping("/app/v1/getUserWalletByUserAccount")
    public ResultInfo<MemWalletVO> getUserWalletByUserAccount(@ApiParam(value = "用户账号",required = true) @RequestParam("userAccount") String userAccount,
                                                            @ApiParam(value = "钱包类型:1=平台钱包,请参考钱包类型枚举",required = true) @RequestParam("walletType") Integer walletType ) {
        return ResultInfo.ok(memUserBusiness.getUserWalletByUserAccount(userAccount,walletType));
    }

}
