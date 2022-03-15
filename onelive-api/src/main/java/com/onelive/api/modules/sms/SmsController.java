package com.onelive.api.modules.sms;


import com.onelive.common.annotation.AllowAccess;
import com.onelive.common.business.sms.SmsBusiness;
import com.onelive.common.model.common.ResultInfo;
import com.onelive.common.model.req.sms.SendSmsReq;
import com.onelive.common.model.req.sms.VerifyCodeReq;
import com.onelive.common.model.vo.sms.SendSmsAppVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/sms")
@Api(tags = "短信API")
public class SmsController {

    @Resource
    private SmsBusiness smsBusiness;

    /**
     * 发送短信是否在header里面返回短信验证码（测试环境启用）
     */
    @Value("${businessSwitch.smsSwitch}")
    private Boolean smsSwitch;


    @AllowAccess
    @ApiOperation("发送短信验证码")
    @PostMapping("/v1/sendSms")
    public ResultInfo<SendSmsAppVO> sendSms(@RequestBody SendSmsReq sendSmsReq, HttpServletResponse response) {
        return ResultInfo.ok(smsBusiness.sendSms(sendSmsReq, response,smsSwitch));
    }

    @AllowAccess
    @ApiOperation("校验验证码，验证成功删除验证码，失败不删除验证码")
    @PostMapping("/v1/checkoutVerifyCode")
    public ResultInfo<Boolean> checkoutVerifyCode(@RequestBody VerifyCodeReq verifyCodeReq) {
        return ResultInfo.ok(smsBusiness.checkoutVerifyCode(verifyCodeReq));
    }

    @AllowAccess
    @ApiOperation("校验验证码，验证成功删除验证码，失败不删除验证码。")
    @PostMapping("/v1/checkoutVerifyCodeNotDel")
    public ResultInfo<Boolean> checkoutVerifyCodeNotDel(@RequestBody VerifyCodeReq verifyCodeReq) {
        return ResultInfo.ok(smsBusiness.checkoutVerifyCode(verifyCodeReq));
    }
}
