package com.onelive.api.modules.ranking.controller;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.onelive.api.modules.ranking.business.RankingBusiness;
import com.onelive.common.annotation.AllowAccess;
import com.onelive.common.base.BaseController;
import com.onelive.common.model.common.ResultInfo;
import com.onelive.common.model.req.live.RankingReq;
import com.onelive.common.model.vo.ranking.RankingVo;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/ranking")
@Api(tags = "排行榜接口")
public class RankingController extends BaseController {

	@Resource
	private RankingBusiness rankingBusiness;

	@ApiOperation("直播间内用户排行榜 ")
	@PostMapping(value = { "/app/v1/room", "/pc/v1/room" })
	@AllowAccess
	@ResponseBody
	public ResultInfo<List<RankingVo>> room(@RequestBody RankingReq rankingReq) {
		return ResultInfo.ok(rankingBusiness.room(rankingReq));
	}

	@ApiOperation("平台主播排行榜")
	@PostMapping(value = { "/app/v1/anchor", "/pc/v1/anchor" })
	@AllowAccess
	@ResponseBody
	public ResultInfo<List<RankingVo>> anchor(@RequestBody RankingReq rankingReq) {
		return ResultInfo.ok(rankingBusiness.anchor(rankingReq));
	}

	@ApiOperation("平台土豪榜排行榜 ")
	@PostMapping(value = { "/app/v1/user", "/pc/v1/user" })
	@AllowAccess
	@ResponseBody
	public ResultInfo<List<RankingVo>> user(@RequestBody RankingReq rankingReq) {
		return ResultInfo.ok(rankingBusiness.user(rankingReq));
	}

////	@RequestMapping(value = "/getHostContribution", method = { RequestMethod.POST })
////	@ApiOperation("主播小时榜V2.0")
////	@ResponseBody
////	@ApiImplicitParams({
////			@ApiImplicitParam(paramType = "query", name = "hostId", required = true, value = "主播ID", dataType = "String"),
////			@ApiImplicitParam(paramType = "query", name = "pageNum", required = true, value = "页码，默认为1", dataType = "Integer"),
////			@ApiImplicitParam(paramType = "query", name = "pageSize", required = true, value = "条数，默认10", dataType = "Integer") })
////	public Result<Object> getHostHourContribution(String hostId, Integer pageNum, Integer pageSize) {
////		logger.info("调用--主播小时榜--getHostHourContribution-接口---参数----hostId：" + hostId + "-----pageNum:" + pageNum
////				+ "-----pageSize:" + pageSize);
////		Result<Object> result = giftGivingService.getHostHourContribution(hostId, pageNum, pageSize);
////		logger.info("调用--主播小时榜-getHostHourContribution-接口---返回：" + JSONObject.toJSONString(result));
////		return result;
////	}
//
////	@RequestMapping(value = "/getHostHomeCourseContribution", method = { RequestMethod.POST })
////	@ApiOperation("主播直播间本场打赏榜V2.0")
////	@ResponseBody
////	@ApiImplicitParams({
////			@ApiImplicitParam(paramType = "query", name = "hostId", required = true, value = "主播ID", dataType = "String"),
////			@ApiImplicitParam(paramType = "query", name = "userId", required = false, value = "用户ID", dataType = "String"),
////			@ApiImplicitParam(paramType = "query", name = "pageNum", required = true, value = "页码，默认为1", dataType = "Integer"),
////			@ApiImplicitParam(paramType = "query", name = "pageSize", required = true, value = "条数，默认10", dataType = "Integer") })
////	public Result<Object> getHostHomeCourseContribution(String hostId, String userId, Integer pageNum,
////			Integer pageSize) {
////		logger.info("调用--主播直播间本场打赏榜--getHostHomeCourseContribution-接口---参数----hostId：" + hostId + "----userId:" + userId
////				+ "-----pageNum:" + pageNum + "-----pageSize:" + pageSize);
////		Result<Object> result = giftGivingService.getHostHomeCourseContribution(hostId, userId, pageNum, pageSize);
////		logger.info("调用--主播直播间本场打赏榜-getHostHomeCourseContribution-接口---返回：" + JSONObject.toJSONString(result));
////		return result;
////	}
//	
////	@RequestMapping(value = "/getPlatformAllContribution", method = { RequestMethod.POST })
////	@ApiOperation("查询平台总排行榜V2.0")
////	@ResponseBody
////	@ApiImplicitParams({
////			@ApiImplicitParam(paramType = "query", name = "pageNum", required = true, value = "页码，默认为1", dataType = "Integer"),
////			@ApiImplicitParam(paramType = "query", name = "pageSize", required = true, value = "条数，默认10", dataType = "Integer"),
////			@ApiImplicitParam(paramType = "query", name = "userId", required = true, value = "用户ID", dataType = "String"),
////			})
////	public Result<Object> getPlatformAllContribution(Integer pageNum,Integer pageSize,String userId) {
////		logger.info("调用--查询平台总排行榜V2.0--getPlatformAllContribution-接口---参数"
////				+ "-----pageNum:" + pageNum + "-----pageSize:" + pageSize+"-----userId:"+userId);
////		Result<Object> result = giftGivingService.getPlatformAllContribution( pageNum, pageSize,userId);
////		logger.info("调用--查询平台总排行榜V2.0-getPlatformAllContribution-接口---返回：" + JSONObject.toJSONString(result));
////		return result;
////	}
//	

}
