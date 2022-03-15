package com.onelive.anchor.modules.account.controller;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.github.pagehelper.PageInfo;
import com.onelive.anchor.modules.account.business.AccountBusiness;
import com.onelive.common.annotation.AllowAccess;
import com.onelive.common.client.OneliveApiFeignClient;
import com.onelive.common.model.common.ResultInfo;
import com.onelive.common.model.dto.mem.AnchorAssets;
import com.onelive.common.model.dto.platformConfig.CustomerSericeLangDto;
import com.onelive.common.model.req.family.FamilyLoginReq;
import com.onelive.common.model.req.live.RankingAnchorReq;
import com.onelive.common.model.req.login.ResetAnchorPasswordReq;
import com.onelive.common.model.req.mem.AnchorAvatarReq;
import com.onelive.common.model.req.mem.AnchorIncomeMonthReq;
import com.onelive.common.model.req.mem.AnchorNameReq;
import com.onelive.common.model.req.mem.MemUserIdReq;
import com.onelive.common.model.req.mem.UserAnchorReq;
import com.onelive.common.model.vo.agent.AgentInviteForIndexVo;
import com.onelive.common.model.vo.live.LiveHistoryVo;
import com.onelive.common.model.vo.live.LiveLogForApiVO;
import com.onelive.common.model.vo.login.AnchorLoginTokenVo;
import com.onelive.common.model.vo.mem.AnchorIncomeDetailsVO;
import com.onelive.common.model.vo.mem.AnchorIncomeMonthVO;
import com.onelive.common.model.vo.mem.MemAnchorInfoVO;
import com.onelive.common.model.vo.ranking.RankingVo;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping(value = "/account")
@Api(tags = "家族/主播账户相关接口")
public class AccountController {

	@Resource
	private AccountBusiness accountBusiness;
	
	@Resource
	private OneliveApiFeignClient oneliveApiFeignClient;

	@AllowAccess
	@ResponseBody
	@ApiOperation("家长/主播登录")
	@PostMapping("/app/v1/login")
	public ResultInfo<AnchorLoginTokenVo> login(@RequestBody FamilyLoginReq req) {
		return accountBusiness.login(req);
	}

	@ResponseBody
	@ApiOperation("家长/主播修改自己的密码")
	@PostMapping("/app/v1/resetPassword")
	public ResultInfo<String> resetPassword(@RequestBody ResetAnchorPasswordReq req) {
		return accountBusiness.resetPassword(req);
	}

	@PostMapping("/app/v1/anchorIncomeDetails")
	@ApiOperation("主播收入详情")
	@ResponseBody
	public ResultInfo<PageInfo<AnchorIncomeDetailsVO>> anchorIncomeDetails(@RequestBody UserAnchorReq userAnchorReq) {
		return ResultInfo.ok(accountBusiness.anchorIncomeDetails(userAnchorReq));
	}

	@PostMapping("/app/v1/anchorIncomeMonth")
	@ApiOperation("主播月收入/支出统计")
	@ResponseBody
	public ResultInfo<AnchorIncomeMonthVO> anchorIncomeMonth(@RequestBody AnchorIncomeMonthReq anchorIncomeMonthReq) {
		return ResultInfo.ok(accountBusiness.anchorIncomeMonth(anchorIncomeMonthReq));
	}

	@ResponseBody
	@ApiOperation("家长或主播查询自己的资产，包括今日收入，当月收入，总订阅收入，，总礼物收入，总佣金，其他（弹幕）")
	@PostMapping("/app/v1/assets")
	public ResultInfo<AnchorAssets> assets() {
		return accountBusiness.assets();
	}

	@ResponseBody
	@ApiOperation("家长或主播查询当日直播时长")
	@PostMapping("/app/v1/liveTime")
	public ResultInfo<LiveLogForApiVO> liveTime(@RequestBody(required = false) MemUserIdReq memUserIdReq) {
		return accountBusiness.liveTime(memUserIdReq);
	}

	@ResponseBody
	@ApiOperation("主播修改自己的昵称")
	@PostMapping("/app/v1/modifNickName")
	public ResultInfo<Boolean> modifNickName(@RequestBody AnchorNameReq anchorNameReq) {
		return ResultInfo.ok(accountBusiness.modifNickName(anchorNameReq));
	}

	@ResponseBody
	@ApiOperation("主播修改自己的头像")
	@PostMapping("/app/v1/updateAvatar")
	public ResultInfo<Boolean> updateAvatar(@RequestBody AnchorAvatarReq anchorAvatarReq) {
		return ResultInfo.ok(accountBusiness.updateAvatar(anchorAvatarReq));
	}

	@ResponseBody
	@ApiOperation("推广详情")
	@PostMapping("/app/v1/inviteDetail")
	public ResultInfo<AgentInviteForIndexVo> inviteDetail() {
		return ResultInfo.ok(accountBusiness.inviteDetail());
	}

	@ResponseBody
	@ApiOperation("主播查看自己的直播记录，最近三十天")
	@PostMapping("/app/v1/liveHistory")
	public ResultInfo<List<LiveHistoryVo>> liveHistory() {
		return ResultInfo.ok(accountBusiness.liveHistory());
	}
	
    @ApiOperation("平台主播排行榜")
	@PostMapping("/app/v1/rankingAnchor")
	@ResponseBody
	public ResultInfo<List<RankingVo>> rankingAnchor(@RequestBody RankingAnchorReq rankingReq) {
		return ResultInfo.ok(oneliveApiFeignClient.rankingAnchor(rankingReq));
	}


    @ApiOperation("主播/家族长基本信息")
    @PostMapping("/app/v1/getUserInfo")
    @ResponseBody
    public ResultInfo<MemAnchorInfoVO> getUserInfo() {
        return ResultInfo.ok(accountBusiness.getUserInfo());
    }
    
    
    @PostMapping("/app/v1/getOnlineService")
	@ApiOperation("获取当前语言的客服")
    @ResponseBody
	public ResultInfo<CustomerSericeLangDto> getOnlineService() {
		return ResultInfo.ok(accountBusiness.getOnlineService());
	}
    
    
}
