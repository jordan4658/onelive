package com.onelive.manage.modules.live.controller;

import com.onelive.common.model.common.ResultInfo;
import com.onelive.common.model.dto.login.LoginUser;
import com.onelive.common.model.req.common.LongIdReq;
import com.onelive.common.model.req.lottery.LiveNoticeTextSaveReq;
import com.onelive.common.model.vo.live.LiveConfigVo;
import com.onelive.common.model.vo.live.notice.LiveNoticeTextListVO;
import com.onelive.common.model.vo.live.notice.LiveNoticeTextVO;
import com.onelive.manage.common.annotation.Log;
import com.onelive.manage.modules.base.BaseAdminController;
import com.onelive.manage.modules.live.business.LiveConfigBusiness;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/liveConfig")
@Api(tags = "直播基础配置接口")
public class LiveConfigController extends BaseAdminController {

	@Resource
	private LiveConfigBusiness business;

	@GetMapping("/v1/getList")
	@ApiOperation("查询配置列表")
	public ResultInfo<List<LiveConfigVo>> getList() {
		return ResultInfo.ok(business.getList());
	}

	@Log("编辑配置列表")
	@PostMapping(value = "/v1/update")
	@ApiOperation("编辑配置列表")
	public ResultInfo<Boolean> update(@RequestBody List<LiveConfigVo> LiveConfigVos) {
		business.update(LiveConfigVos);
		return ResultInfo.ok();
	}

	@PostMapping("/v1/getNoticeList")
	@ApiOperation("查询中奖公告配置列表")
	public ResultInfo<List<LiveNoticeTextListVO>> getNoticeList() {
		return ResultInfo.ok(business.getNoticeList());
	}

	@PostMapping("/v1/getNotice")
	@ApiOperation("查询中奖公告配置信息")
	public ResultInfo<LiveNoticeTextVO> getNotice(@RequestBody LongIdReq req) {
		return ResultInfo.ok(business.getNotice(req));
	}

	@PostMapping(value = "/v1/saveNotice")
	@ApiOperation("编辑公告配置")
	public ResultInfo<Boolean> saveNotice(@RequestBody LiveNoticeTextSaveReq req) {
		LoginUser loginUser = getLoginAdmin();
		business.saveNotice(req,loginUser);
		return ResultInfo.ok();
	}

	@PostMapping("/v1/delNotice")
	@ApiOperation("删除中奖公告配置信息")
	public ResultInfo<Boolean> delNotice(@RequestBody LongIdReq req) {
		LoginUser loginUser = getLoginAdmin();
		business.delNotice(req,loginUser);
		return ResultInfo.ok();
	}
}
