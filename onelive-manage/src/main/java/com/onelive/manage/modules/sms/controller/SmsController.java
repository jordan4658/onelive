package com.onelive.manage.modules.sms.controller;

import com.github.pagehelper.PageInfo;
import com.onelive.common.model.common.ResultInfo;
import com.onelive.common.model.dto.login.LoginUser;
import com.onelive.common.model.req.sms.SmsAddReq;
import com.onelive.common.model.req.sms.SmsQueryReq;
import com.onelive.common.model.req.sms.SmsUpdateReq;
import com.onelive.common.model.vo.sms.SeeSmsVo;
import com.onelive.manage.modules.base.BaseAdminController;
import com.onelive.manage.modules.sms.business.SmsBusiness;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequestMapping("/sms")
@Api(tags = "运营管理-短信方式管理-api")
public class SmsController  extends BaseAdminController  {

    @Autowired
    private SmsBusiness smsBusiness;


    @ApiOperation("查询短信方式列表")
    @GetMapping("/v1/list")
    public ResultInfo<PageInfo<SeeSmsVo>> list(@ModelAttribute SmsQueryReq smsQueryReq) {
        PageInfo<SeeSmsVo> info=smsBusiness.list(smsQueryReq);
        return ResultInfo.ok(info);
    }

    @ApiOperation("新增短信方式")
    @PostMapping("/v1/add")
    public ResultInfo<String> add(@Validated  @RequestBody SmsAddReq smsAddReq) {
        LoginUser user=getLoginAdmin();
        smsBusiness.add(smsAddReq,user);
        return ResultInfo.ok();
    }

    @ApiOperation("更新短信方式")
    @PostMapping("/v1/update")
    public ResultInfo<String> update(@Validated  @RequestBody SmsUpdateReq smsUpdateReq) {
        LoginUser user=getLoginAdmin();
        smsBusiness.update(smsUpdateReq,user);
        return ResultInfo.ok();
    }

    @ApiOperation("删除短信方式")
    @GetMapping("/v1/delete")
    public ResultInfo<String> delete(@ApiParam(value = "短信方式id", required = true) @RequestParam Long id) {
        LoginUser user=getLoginAdmin();
        smsBusiness.delete(id,user);
        return ResultInfo.ok();
    }

    @ApiOperation("启用、关闭短信方式")
    @GetMapping("/v1/openOrClose")
    public ResultInfo<String> openOrClose(@ApiParam(value = "短信方式id", required = true) @RequestParam Long id,
                                          @ApiParam(value = "启用或关闭:true，false", required = true) @RequestParam Boolean isOpen) {
        smsBusiness.openOrClose(id, getLoginAdmin(),isOpen);
        return ResultInfo.ok();
    }

}
