//package com.onelive.api.modules.index.controller;
//
//import java.util.List;
//
//import javax.annotation.Resource;
//
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import com.onelive.api.modules.index.business.ShareBusiness;
//import com.onelive.common.model.common.ResultInfo;
//import com.onelive.common.model.dto.platform.ShareForIndexDto;
//
//import io.swagger.annotations.Api;
//import io.swagger.annotations.ApiOperation;
//

/// TODO 二期做
//@RestController
//@RequestMapping(value = "/share")
//@Api(tags = "分享相关接口")
//public class ShareController {
//
//	@Resource
//	private ShareBusiness shareBusiness;
//
//	@PostMapping(value = {"/app/v1/getList", "/pc/v1/getList"})
//	@ApiOperation("分享列表查询")
//	public ResultInfo<List<ShareForIndexDto>> getList() {
//		return ResultInfo.ok(shareBusiness.getList());
//	}
//
////	@PostMapping(value = {"/app/v1/succeed", "/pc/v1/succeed"})
////	@ApiOperation("分享编辑")
////	public ResultInfo<Boolean> update(@RequestBody PlatformShareConfigDto platformShareConfigDto) {
////		shareBusiness.update(platformShareConfigDto);
////		return ResultInfo.ok();
////	}
//
//}
