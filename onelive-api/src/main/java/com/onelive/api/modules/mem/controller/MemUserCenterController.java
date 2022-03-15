package com.onelive.api.modules.mem.controller;


import java.util.List;

import javax.annotation.Resource;

import com.onelive.common.business.user.SetPayPasswordBusiness;
import com.onelive.common.model.req.mem.*;
import com.onelive.common.mybatis.entity.MemWallet;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.pagehelper.PageInfo;
import com.onelive.api.modules.mem.business.MemUserCenterBusiness;
import com.onelive.common.annotation.AllowAccess;
import com.onelive.common.base.BaseController;
import com.onelive.common.model.common.ResultInfo;
import com.onelive.common.model.req.game.GameRecordDetailReq;
import com.onelive.common.model.req.mem.usercenter.ActivityListReq;
import com.onelive.common.model.vo.game.GameRecordListVO;
import com.onelive.common.model.vo.live.AppLiveBagVO;
import com.onelive.common.model.vo.live.AppUserLiveBagVO;
import com.onelive.common.model.vo.mem.AppGameUserCenterListVO;
import com.onelive.common.model.vo.mem.MemUserInfoVO;
import com.onelive.common.model.vo.mem.MemUserLevelInfoVO;
import com.onelive.common.model.vo.mem.MemUserOccupationListVO;
import com.onelive.common.model.vo.sys.AppCountryAddrVO;
import com.onelive.common.model.vo.sys.SysActivityListVO;
import com.onelive.common.utils.Login.LoginInfoUtil;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

/**
 * @Description: 用户中心接口
 */
@RestController
@RequestMapping("/userCenter")
@Api(tags = "用户中心接口")
@Slf4j
public class MemUserCenterController extends BaseController {

    @Resource
    private MemUserCenterBusiness memUserCenterBusiness;
    @Resource
    private SetPayPasswordBusiness setPayPasswordBusiness;

    @ApiOperation("编辑用户昵称")
    @PostMapping(value = {"/app/v1/updateNickName", "/pc/v1/updateNickName"})
    public ResultInfo<String> updateNickName(@RequestBody MemUserUpdateNickNameReq req) {
        memUserCenterBusiness.updateNickName(req.getNickName());
        return ResultInfo.ok();
    }

    @ApiOperation("编辑用户签名")
    @PostMapping(value = {"/app/v1/updateSignature", "/pc/v1/updateSignature"})
    public ResultInfo<String> updateSignature(@RequestBody MemUserUpdateSignatureReq req) {

        memUserCenterBusiness.updateSignature(req.getSignature());

        return ResultInfo.ok();
    }

    @ApiOperation("编辑用户信息")
    @PostMapping(value = {"/app/v1/updateUserInfo", "/pc/v1/updateUserInfo"})
    public ResultInfo<MemUserInfoVO> updateUserInfo(@RequestBody MemUserInfoEditReq req) {
        return ResultInfo.ok(memUserCenterBusiness.updateUserInfo(req));
    }

    @ApiOperation("设置支付密码")
    @PostMapping(value = {"/app/v1/setPayPassword"})
    public ResultInfo<String> setPayPassword(@RequestBody SetWithdrawPasswordReq req) {
        setPayPasswordBusiness.setPayPassword(req, LoginInfoUtil.getUserId());
        return ResultInfo.ok();
    }

    @ApiOperation("获取用户基本信息")
    @PostMapping(value = {"/app/v1/getUserInfo", "/pc/v1/getUserInfo"})
    public ResultInfo<MemUserInfoVO> getUserInfo() {
        return ResultInfo.ok(memUserCenterBusiness.getUserInfo());
    }


    @ApiOperation("获取用户职业列表信息")
    @AllowAccess
    @PostMapping(value = {"/app/v1/getUserOccupationList", "/pc/v1/getUserOccupationList"})
    public ResultInfo<List<MemUserOccupationListVO>> getUserOccupationList() {
        return ResultInfo.ok(memUserCenterBusiness.getUserOccupationList());
    }


    @ApiOperation("获取用户游戏记录列表")
    @PostMapping(value = {"/app/v1/getGameRecordList", "/pc/v1/getGameRecordList"})
    public ResultInfo<GameRecordListVO> getGameRecordList(@RequestBody MemUserGameRecordListReq req) {
        return ResultInfo.ok(memUserCenterBusiness.getGameRecordList(req));
    }

    @ApiOperation("获取指定游戏记录详情")
    @PostMapping(value = {"/app/v1/getGameRecordDetail", "/pc/v1/getGameRecordDetail"})
    public ResultInfo<GameRecordListVO> getGameRecordDetail(@RequestBody GameRecordDetailReq req) {
        return ResultInfo.ok(memUserCenterBusiness.getGameRecordDetail(req));
    }


    @ApiOperation("查询用户当前等级信息")
    @PostMapping(value = {"/app/v1/getUserLevelInfo", "/pc/v1/getUserLevelInfo"})
    public ResultInfo<MemUserLevelInfoVO> getUserLevelInfo() {
        return ResultInfo.ok(memUserCenterBusiness.getUserLevelInfo());
    }


    /*@AllowAccess
    @ApiOperation("获取游戏列表")
    @PostMapping(value = {"/app/v1/getGameList", "/pc/v1/getGameList"})
    public ResultInfo<List<GameInfoVO>> getGameList() {
        return ResultInfo.ok(memUserCenterBusiness.getGameList());
    }

*/

    @AllowAccess
    @ApiOperation("获取活动列表")
    @PostMapping(value = {"/app/v1/getActivityList", "/pc/v1/getActivityList"})
    public ResultInfo<SysActivityListVO> getActivityList(@RequestBody ActivityListReq req) {
        return ResultInfo.ok(memUserCenterBusiness.getActivityList(req));
    }


    @ApiOperation("获取背包列表")
    @PostMapping(value = {"/app/v1/getBagList", "/pc/v1/getBagList"})
    public ResultInfo<PageInfo<AppLiveBagVO>> getBagList() {
        return ResultInfo.ok(memUserCenterBusiness.getBagList());
    }

    @ApiOperation("获取用户背包列表")
    @PostMapping(value = {"/app/v1/getUserBagList", "/pc/v1/getUserBagList"})
    public ResultInfo<PageInfo<AppUserLiveBagVO>> getUserBagList() {
        return ResultInfo.ok(memUserCenterBusiness.getUserBagList());
    }


    @ApiOperation("获取国家列表")
    @PostMapping(value = {"/app/v1/getCountryList", "/pc/v1/getCountryList"})
    public ResultInfo<PageInfo<AppCountryAddrVO>> getCountryList() {
        return ResultInfo.ok(memUserCenterBusiness.getCountryList());
    }

    @ApiOperation("获取国家省市列表")
    @PostMapping(value = {"/app/v1/getCityList", "/pc/v1/getCityList"})
    public ResultInfo<PageInfo<AppCountryAddrVO>> getCityList() {
        return ResultInfo.ok(memUserCenterBusiness.getCityList(LoginInfoUtil.getLang()));
    }


    @AllowAccess
    @ApiOperation("获取第三方游戏列表")
    @PostMapping(value = {"/app/v1/getGameList", "/pc/v1/getGameList"})
    public ResultInfo<List<AppGameUserCenterListVO>> getGameList() {
        return ResultInfo.ok(memUserCenterBusiness.getGameList());
    }

    @ApiOperation("获取用户钱包列表")
    @PostMapping(value = {"/app/v1/getUserWalletList"})
    public ResultInfo<List<MemWallet>> getUserWalletList() {
        return ResultInfo.ok(memUserCenterBusiness.getUserWalletList(LoginInfoUtil.getLang()));
    }

    @ApiOperation("用户钱包与钱包转入（平台钱包转入到其他钱包）、转出（转到平台钱包）")
    @PostMapping(value = {"/app/v1/userWalletChange"})
    public ResultInfo userWalletChange(@RequestBody UserWalletChangeReq req) {
        memUserCenterBusiness.userWalletChange(req);
        return ResultInfo.ok();
    }

}
