package com.onelive.api.modules.index.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.onelive.api.modules.index.business.IndexBusiness;
import com.onelive.common.annotation.AllowAccess;
import com.onelive.common.model.common.ResultInfo;
import com.onelive.common.model.req.live.LiveColumnCodeReq;
import com.onelive.common.model.req.live.LiveIndexSearchReq;
import com.onelive.common.model.req.live.LiveListReq;
import com.onelive.common.model.req.sys.SysAdvFlashviewQueryReq;
import com.onelive.common.model.vo.index.IndexTopGameListVO;
import com.onelive.common.model.vo.index.LiveColumnVO;
import com.onelive.common.model.vo.live.LiveAdvListVO;
import com.onelive.common.model.vo.live.LiveStudioListForIndexVO;
import com.onelive.common.model.vo.mem.MemUserAnchorSearchVO;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * 首页相关接口
 */
@RestController
@RequestMapping("/index")
@Api(tags = "首页相关接口")
public class IndexController {

	@Autowired
	private IndexBusiness indexBusiness;

	@PostMapping(value = {"/app/v1/getColumn", "/pc/v1/getColumn"})
	@ApiOperation("首页栏目表信息")
	@ResponseBody
	@AllowAccess
	public ResultInfo<List<LiveColumnVO>> getLiveColumnList() {
		return ResultInfo.ok(indexBusiness.getLiveColumnList());
	}

	@PostMapping(value = {"/app/v1/getRecommendByHeat", "/pc/v1/getRecommendByHeat"})
	@ApiOperation("推荐标签直播间列表:后台配置排序规则(排除推荐区的四个推荐和光年推荐的四个）")
	@ResponseBody
	@AllowAccess
	public ResultInfo<List<LiveStudioListForIndexVO>> getRecommendByHeat(@RequestBody LiveListReq req) {
		return ResultInfo.ok(indexBusiness.getRecommendByHeat(req));
	}
	
	@PostMapping(value = {"/app/v1/getRecommend", "/pc/v1/getRecommend"})
	@ApiOperation("推荐区的四个推荐直播")
	@ResponseBody
	@AllowAccess
	public ResultInfo<List<LiveStudioListForIndexVO>> getRecommend() {
		return ResultInfo.ok(indexBusiness.getRecommend());
	}
	
	@PostMapping(value = {"/app/v1/getPromotion", "/pc/v1/getPromotion"})
	@ApiOperation("光年推荐(最多四个，人数前四的游戏直播间)")
	@ResponseBody
	@AllowAccess
	public ResultInfo<List<LiveStudioListForIndexVO>> getPromotion() {
		return ResultInfo.ok(indexBusiness.getPromotion());
	}

	@ApiOperation("关注的直播房间列表")
	@PostMapping(value = {"/app/v1/getLiveFocusList", "/pc/v1/getLiveFocusList"})
	@ResponseBody
	public ResultInfo<List<LiveStudioListForIndexVO>> getLiveFocusList(@RequestBody LiveListReq req) {
		return ResultInfo.ok(indexBusiness.getLiveFocusList(req));
	}
	
	@ApiOperation("关注页推荐的直播房间列表,不包含已经关注的房间")
	@PostMapping(value = {"/app/v1/getLiveFocusRecommendList", "/pc/v1/getLiveFocusRecommendList"})
	@ResponseBody
	@AllowAccess
	public ResultInfo<List<LiveStudioListForIndexVO>> getLiveFocusRecommendList() {
		return ResultInfo.ok(indexBusiness.getLiveFocusRecommendList());
	}
	
	@ApiOperation("根据栏目code查询直播房间列表(除了推荐和关注栏目外)")
	@PostMapping(value = {"/app/v1/getLiveByColumnCode", "/pc/v1/getLiveByColumnCode"})
	@ResponseBody
	@AllowAccess
	public ResultInfo<List<LiveStudioListForIndexVO>> getLiveByColumnCode(@RequestBody LiveColumnCodeReq req) {
		return ResultInfo.ok(indexBusiness.getLiveByColumnCode(req));
	}
	
	@ApiOperation("推广的直播房间列表")
	@PostMapping(value = {"/app/v1/getLivePopList", "/pc/v1/getLivePopList"})
	@ResponseBody
	@AllowAccess
	public ResultInfo<List<LiveStudioListForIndexVO>> getLivePopList() {
		return ResultInfo.ok(indexBusiness.getLivePopList());
	}
	
	@ApiOperation("广告位查询，根据code")
	@PostMapping(value = {"/app/v1/getAdvByCode", "/pc/v1/getAdvByCode"})
	@ResponseBody
	@AllowAccess
	public ResultInfo<List<LiveAdvListVO>> getGameAdv(@RequestBody SysAdvFlashviewQueryReq sysAdvFlashviewQueryReq) {
		return ResultInfo.ok(indexBusiness.getAdvByType(sysAdvFlashviewQueryReq.getFlashviewCode()));
	}
	
	@ApiOperation("搜索主播")
	@PostMapping(value = {"/app/v1/search", "/pc/v1/search"})
	@ResponseBody
	@AllowAccess
	public ResultInfo<MemUserAnchorSearchVO> search(@RequestBody LiveIndexSearchReq req){
		return ResultInfo.ok(indexBusiness.search(req));
	}
	
	@ApiOperation("浏览直播间历史查询，只查询最近且在线的四个")
	@PostMapping(value = { "/app/v1/browseHistory", "/pc/v1/browseHistory" })
	@ResponseBody
	public ResultInfo<List<LiveStudioListForIndexVO>> browseHistory() {
		return ResultInfo.ok(indexBusiness.browseHistory());
	}

	@ApiOperation("查询首页顶部游戏配置列表")
	@PostMapping(value = { "/app/v1/queryIndexTopGameList", "/pc/v1/queryIndexTopGameList" })
	@ResponseBody
	@AllowAccess
	public ResultInfo<List<IndexTopGameListVO>> queryIndexTopGameList() {
		return ResultInfo.ok(indexBusiness.queryIndexTopGameList());
	}



//	@RequestMapping(value = {"/app/v1/cdn_validation", "/pc/v1/cdn_validation"}, method = { RequestMethod.POST ,RequestMethod.GET})
//	@ApiOperation("cdn拉流验证接口V2.0(明创or金山云)")
//	@ResponseBody
//	@ApiImplicitParams({
//		@ApiImplicitParam(paramType = "query", name = "code", required = false, value = "验证码", dataType = "String")
//	})
//	public ResultInfo<Object> cdn_validation(String code) {
////		Map<String,Object> ResultInfo= new HashMap<String,Object>();
////		ResultInfo.put("status", true);
////		return ResultInfo.success(ResultInfo);
//		log.info("调用--cdn拉流验证(明创or金山云)接口--cdn_validation-接口---参数----code：" + code );
//		ResultInfo<Object> ResultInfo = liveStudioListService.cdn_validation(code);
//		log.info("调用--cdn拉流验证(明创or金山云)接口-cdn_validation-接口---返回："+JSONObject.toJSONString(ResultInfo));
//		return ResultInfo;
//	}

//	@RequestMapping(value = "/jinShanYun_cdn_recordCallback", method = { RequestMethod.POST ,RequestMethod.GET})
//	@ApiOperation("金山云cdn录制回调接口V2.0")
//	@ResponseBody
//	public ResultInfo<Object> jinShanYun_cdn_recordCallback(HttpServletRequest request ) {
//		
//		Object body=Utils.getRequestBody(request);
//	    log.info("cdn预设录制回调接口--参数-------Body内容："+body.toString());
//		ResultInfo<Object> ResultInfo = liveStudioListService.jinShanYun_cdn_recordCallback(body.toString());
//		log.info("调用--cdn预设录制回调接口-cdn_recordCallback-接口---返回："+JSONObject.toJSONString(ResultInfo));
//		return ResultInfo.success();
//	}

//	@RequestMapping(value = "/cdn_recordCallback", method = { RequestMethod.POST ,RequestMethod.GET})
//	@ApiOperation("cdn预设录制回调接口")
//	@ResponseBody
//	@ApiImplicitParams({
//		@ApiImplicitParam(paramType = "query", name = "message_type", required = false, value = "回调类型：ws_record_start=开始，ws_record_finish=结束", dataType = "String")
//	})
//	public ResultInfo<Object> cdn_recordCallback(String message_type,HttpServletRequest request ) {
//		Object body=Utils.getRequestBody(request);
//		log.info("cdn预设录制回调接口--参数message_type:"+message_type+"-------Body内容："+body.toString());
//		ResultInfo<Object> ResultInfo = liveStudioListService.cdn_recordCallback(message_type,body.toString());
//		log.info("调用--cdn预设录制回调接口-cdn_recordCallback-接口---返回："+JSONObject.toJSONString(ResultInfo));
//		return ResultInfo.success();
//	}
	
	
//	@RequestMapping(value = "/selectHostIsStart", method = { RequestMethod.POST })
//	@ApiOperation("查询主播是否开播")
//	@ResponseBody
//	@ApiImplicitParams({
//		@ApiImplicitParam(paramType = "query", name = "hostId", required = true, value = "主播ID", dataType = "String")
//	})
//	public ResultInfo<Object> selectHostIsStart(String hostId) {
//		log.info("调用--查询主播是否开播--selectHostIsStart-接口---参数----hostId：" + hostId );
//		ResultInfo<Object> ResultInfo = liveStudioListService.selectHostIsStart(hostId);
//		log.info("调用--查询主播是否开播-selectHostIsStart-接口---返回："+JSONObject.toJSONString(ResultInfo));
//		return ResultInfo;
//	}

//	@RequestMapping(value = "/getStartImg", method = { RequestMethod.POST })
//	@ApiOperation("查询应用启动页图片")
//	@ResponseBody
//	public ResultInfo<Object> getStartImg() {
//		log.info("调用--查询应用启动页图片--selectHostIsStart-接口--" );
//		ResultInfo<Object> ResultInfo = liveStudioListService.getStartImg();
//		log.info("调用--查询应用启动页图片-selectHostIsStart-接口---返回："+JSONObject.toJSONString(ResultInfo));
//		return ResultInfo;
//	}
	
//	@RequestMapping(value = "/getAppServiceDateTime", method = { RequestMethod.POST })
//	@ApiOperation("app服务器当前时间")
//	@ResponseBody
//	public ResultInfo<Object> getAppServiceDateTime() {
//		log.info("调用--app服务器当前时间--getAppServiceDateTime-接口--" );
//		ResultInfo<Object> ResultInfo = ResultInfo.success(System.currentTimeMillis());
//		log.info("调用--app服务器当前时间-getAppServiceDateTime-接口---返回："+JSONObject.toJSONString(ResultInfo));
//		return ResultInfo;
//	}
	
//	@RequestMapping(value = "/getAppRecommendedH5Url", method = { RequestMethod.POST })
//	@ApiOperation("app推荐页H5链接")
//	@ResponseBody
//	public ResultInfo<Object> getAppRecommendedH5Url() {
//		log.info("调用--app推荐页H5链接--getAppRecommendedH5Url-接口--" );
//		String ResultInfo =sysDictService.get_app_tuijian_h5_url();
//		log.info("调用--app推荐页H5链接-getAppRecommendedH5Url-接口---返回："+JSONObject.toJSONString(ResultInfo));
//		return ResultInfo.success(ResultInfo);
//	}
}
