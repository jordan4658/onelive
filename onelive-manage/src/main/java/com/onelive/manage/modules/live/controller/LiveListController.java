package com.onelive.manage.modules.live.controller;

import com.github.pagehelper.PageInfo;
import com.onelive.common.exception.BusinessException;
import com.onelive.common.model.common.ResultInfo;
import com.onelive.common.model.dto.login.LoginUser;
import com.onelive.common.model.req.live.LiveBeginForAdminReq;
import com.onelive.common.model.req.live.LiveSortConfigReq;
import com.onelive.common.model.req.live.LiveStudioListReq;
import com.onelive.common.model.vo.live.LiveStudioListDetail;
import com.onelive.common.model.vo.live.LiveStudioListManegeVO;
import com.onelive.common.model.vo.live.LiveStudioSelectVO;
import com.onelive.common.utils.Login.LoginInfoUtil;
import com.onelive.manage.common.annotation.Log;
import com.onelive.manage.modules.base.BaseAdminController;
import com.onelive.manage.modules.live.business.LiveListBusiness;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/live")
@Api(tags = "直播列表相关接口")
public class LiveListController extends BaseAdminController {

	@Resource
	private LiveListBusiness liveListBusiness;

	@PostMapping("/v1/getList")
	@ApiOperation("查询实时直播列表")
	@ResponseBody
	public ResultInfo<PageInfo<LiveStudioListManegeVO>> getList(@RequestBody LiveStudioListReq param) {
		return liveListBusiness.getList(param);
	}

	@PostMapping("/v1/getSelectList")
	@ApiOperation("查询直播列表-用于选择")
	@ResponseBody
	public ResultInfo<List<LiveStudioSelectVO>> getSelectList() {
		return ResultInfo.ok(liveListBusiness.getSelectList());
	}

	@Log("设置当前直播为推荐")
	@PostMapping("/v1/first")
	@ApiOperation("设置当前直播为推荐")
	public ResultInfo<Boolean> first(@ApiParam("直播间id") @RequestParam("studioId") Integer studioId) {
		if (studioId == null) {
			throw new BusinessException("直播间id不能为空!");
		}
		LoginUser admin = getLoginAdmin();
		return liveListBusiness.first(studioId, LoginInfoUtil.getCountryCode(), admin);
	}

	@Log("设置当前直播为非推荐")
	@PostMapping("/v1/unFirst")
	@ApiOperation("设置当前直播为非推荐")
	@ResponseBody
	public ResultInfo<Boolean> unFirst(@ApiParam("直播间id") @RequestParam("studioId") Integer studioId) {
		if (studioId == null) {
			throw new BusinessException("直播间id不能为空!");
		}
		LoginUser admin = getLoginAdmin();
		return liveListBusiness.unFirst(studioId, LoginInfoUtil.getCountryCode(), admin);
	}

	@Log("固定位置")
	@PostMapping("/v1/fixed")
	@ApiOperation("固定位置")
	@ResponseBody
	public ResultInfo<Boolean> fixed(@ApiParam("直播间id") @RequestParam("studioId") Integer studioId,
			@ApiParam("固定位置") @RequestParam("sortNum") Integer sortNum) {
		if (studioId == null) {
			throw new BusinessException("直播间id不能为空!");
		}
		LoginUser admin = getLoginAdmin();
		return liveListBusiness.fixed(studioId, sortNum, admin);
	}

	@Log("取消固定位置")
	@PostMapping("/v1/unFixed")
	@ApiOperation("取消固定位置")
	@ResponseBody
	public ResultInfo<Boolean> unFixed(@ApiParam("直播间id") @RequestParam("studioId") Integer studioId) {
		if (studioId == null) {
			throw new BusinessException("直播间id不能为空!");
		}
		LoginUser admin = getLoginAdmin();
		return liveListBusiness.unFixed(studioId, admin);
	}

	@Log("位置置底")
	@PostMapping("/v1/bottom")
	@ApiOperation("位置置底")
	@ResponseBody
	public ResultInfo<Boolean> bottom(@ApiParam("直播间id") @RequestParam("studioId") Integer studioId,
			@ApiParam(" true是置底.,..false是取消置底") @RequestParam("isBottom") Boolean isBottom) {
		if (studioId == null) {
			throw new BusinessException("直播间id不能为空!");
		}
		LoginUser admin = getLoginAdmin();
		return liveListBusiness.bottom(studioId, isBottom, admin);
	}
	
	@Log("主播下播")
	@PostMapping("/v1/breakShow")
	@ApiOperation("主播下播")
	@ResponseBody
	public ResultInfo<Boolean> breakShow(@ApiParam("直播间id") @RequestParam("studioId") Integer studioId) {
		if (studioId == null) {
			throw new BusinessException("直播间id不能为空!");
		}
		LoginUser admin = getLoginAdmin();
		return liveListBusiness.breakShow(studioId, admin);
	}
	
	@Log("直播详情")
	@PostMapping("/v1/detail")
	@ApiOperation("直播详情")
	@ResponseBody
	public ResultInfo<LiveStudioListDetail> detail(@ApiParam("直播间id") @RequestParam("studioId") Integer studioId) {
		if (studioId == null) {
			throw new BusinessException("直播间id不能为空!");
		}
		return ResultInfo.ok(liveListBusiness.detail(studioId));
	}
	
	
	@Log("直播详情修改")
	@PostMapping("/v1/setDetail")
	@ApiOperation("直播详情")
	@ResponseBody
	public ResultInfo<Boolean> setDetail(@RequestBody LiveStudioListDetail liveStudioListDetail) {
		if (liveStudioListDetail.getStudioId() == null) {
			throw new BusinessException("直播间id不能为空!");
		}
		return ResultInfo.ok(liveListBusiness.setDetail(liveStudioListDetail));
	}
	
	@PostMapping("/v1/beginVideo")
	@ApiOperation("主播开播（给指定的主播播放视频）")
	@ResponseBody
	public ResultInfo<Boolean> beginVideo(@RequestBody LiveBeginForAdminReq req) {
		return ResultInfo.ok(liveListBusiness.beginVideo(req));
	}
	
	
	@PostMapping("/v1/updateSortConfig")
	@ApiOperation("推荐栏目直播间排序类型修改")
	@ResponseBody
	public ResultInfo<Boolean> updateSortConfig(@RequestBody LiveSortConfigReq req) {
		liveListBusiness.sortConfig(req);
		return ResultInfo.ok();
	}
	
	@PostMapping("/v1/getSortConfig")
	@ApiOperation("推荐栏目直播间排序类型修改")
	@ResponseBody
	public ResultInfo<LiveSortConfigReq> getSortConfig() {
		return ResultInfo.ok(liveListBusiness.getSortConfig());
	}

}
