package com.onelive.manage.modules.sms.controller;

import com.github.pagehelper.PageInfo;
import com.onelive.common.model.common.ResultInfo;
import com.onelive.common.model.dto.login.LoginUser;
import com.onelive.common.model.req.sms.SmsTemplateAddReq;
import com.onelive.common.model.req.sms.SmsTemplateQueryReq;
import com.onelive.common.model.req.sms.SmsTemplateUpdateReq;
import com.onelive.common.model.vo.sms.SmsTemplateVo;
import com.onelive.manage.modules.base.BaseAdminController;
import com.onelive.manage.modules.sms.business.SmsTemplateBusiness;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequestMapping("/smsTemplate")
@Api(tags = "运营管理-短信模板管理-api")
public class SmsTemplateController extends BaseAdminController {

    @Autowired
    private SmsTemplateBusiness smsTemplateBusiness;

    @ApiOperation("查询短信模板列表")
    @GetMapping("/v1/list")
    public ResultInfo<PageInfo<SmsTemplateVo>> list(@ModelAttribute SmsTemplateQueryReq smsTemplateQueryReq) {
        PageInfo<SmsTemplateVo> info = smsTemplateBusiness.list(smsTemplateQueryReq);
        return ResultInfo.ok(info);
    }

    @ApiOperation("新增短信模板")
    @PostMapping("/v1/add")
    public ResultInfo add(@Validated @RequestBody SmsTemplateAddReq smsTemplateAddReq) {
        LoginUser user = getLoginAdmin();
        smsTemplateBusiness.add(smsTemplateAddReq, user);
        return ResultInfo.ok();
    }

    @ApiOperation("更新短信模板")
    @PostMapping("/v1/update")
    public ResultInfo update(@Validated @RequestBody SmsTemplateUpdateReq smsTemplateUpdateReq) {
        LoginUser user = getLoginAdmin();
        smsTemplateBusiness.update(smsTemplateUpdateReq, user);
        return ResultInfo.ok();
    }

    @ApiOperation("删除短信模板")
    @GetMapping("/v1/delete")
    public ResultInfo delete(@ApiParam(value = "短信模板ID", required = true) @RequestParam Long id) {
        LoginUser user = getLoginAdmin();
        smsTemplateBusiness.delete(id, user);
        return ResultInfo.ok();
    }

    @ApiOperation("启用、关闭短信模板")
    @GetMapping("/v1/openOrClose")
    public ResultInfo<String> openOrClose(@ApiParam(value = "短信方式id", required = true) @RequestParam Long id,
                                          @ApiParam(value = "启用或关闭:true，false", required = true) @RequestParam Boolean isOpen) {
        smsTemplateBusiness.openOrClose(id, getLoginAdmin(),isOpen);
        return ResultInfo.ok();
    }

}
