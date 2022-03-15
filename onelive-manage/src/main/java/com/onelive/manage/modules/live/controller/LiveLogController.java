package com.onelive.manage.modules.live.controller;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.github.pagehelper.PageInfo;
import com.onelive.common.model.common.ResultInfo;
import com.onelive.common.model.dto.platform.LiveIncomeDetailDto;
import com.onelive.common.model.vo.live.LiveLogVO;
import com.onelive.manage.modules.live.business.LiveLogBusiness;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/livelog")
@Api(tags = "直播历史相关接口")
public class LiveLogController {

	@Resource
    private LiveLogBusiness liveLogBusiness;

	@PostMapping("/v1/getList")
    @ApiOperation("查询直播历史列表")
	@ResponseBody
    public ResultInfo<PageInfo<LiveLogVO>> getList(@RequestBody LiveLogVO param) {
        return ResultInfo.ok(liveLogBusiness.getList(param));
    }

	
	@PostMapping("/v1/detail")
	@ApiOperation("单场直播收入详情")
	@ResponseBody
	public ResultInfo<LiveIncomeDetailDto> detail(@RequestBody LiveIncomeDetailDto param) {
		return ResultInfo.ok(liveLogBusiness.detail(param));
	}

}
