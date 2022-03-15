package com.onelive.manage.modules.live.controller;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.pagehelper.PageInfo;
import com.onelive.common.model.common.ResultInfo;
import com.onelive.common.model.dto.live.RoomFastWordsDto;
import com.onelive.manage.common.annotation.Log;
import com.onelive.manage.modules.live.business.RoomFastWordsBusiness;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/fastWords")
@Api(tags = "直播间快捷回复文字相关接口")
public class RoomFastWordsController {

	@Resource
	private RoomFastWordsBusiness roomFastWordsBusiness;

	@PostMapping("/v1/getList")
	@ApiOperation("查询快捷回复列表")
	public ResultInfo<PageInfo<RoomFastWordsDto>> getList(@RequestBody RoomFastWordsDto roomFastWordsDto) {
		return ResultInfo.ok(roomFastWordsBusiness.getList(roomFastWordsDto));
	}

	@Log("保存快捷回复信息")
	@PostMapping("/v1/save")
	@ApiOperation("保存快捷回复信息")
	public ResultInfo<Boolean> save(@RequestBody RoomFastWordsDto roomFastWordsDto) throws Exception {
		roomFastWordsBusiness.save(roomFastWordsDto);
		return ResultInfo.ok();
	}

	@Log("快捷回复编辑")
	@PostMapping(value = "/v1/update")
	@ApiOperation("快捷回复编辑")
	public ResultInfo<Boolean> update(@RequestBody RoomFastWordsDto roomFastWordsDto) {
		roomFastWordsBusiness.update(roomFastWordsDto);
		return ResultInfo.ok();
	}

	@Log("快捷回复启用/停用")
	@PostMapping(value = "/v1/enableOrDisable")
	@ApiOperation("快捷回复启用/停用")
	public ResultInfo<Boolean> enableOrDisable(@RequestBody RoomFastWordsDto roomFastWordsDto) {
		roomFastWordsBusiness.enableOrDisable(roomFastWordsDto);
		return ResultInfo.ok();
	}
	
	@Log("快捷回复删除")
	@PostMapping(value = "/v1/delete")
	@ApiOperation("快捷回复删除")
	public ResultInfo<Boolean> delete(@RequestBody RoomFastWordsDto roomFastWordsDto) {
		roomFastWordsBusiness.delete(roomFastWordsDto);
		return ResultInfo.ok();
	}

}
