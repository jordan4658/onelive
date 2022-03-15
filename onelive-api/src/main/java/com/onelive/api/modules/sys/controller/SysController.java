package com.onelive.api.modules.sys.controller;

import com.onelive.api.modules.sys.business.SysBusiness;
import com.onelive.api.util.ApiBusinessRedisUtils;
import com.onelive.common.annotation.AllowAccess;
import com.onelive.common.model.common.ResultInfo;
import com.onelive.common.model.req.login.CheckImgReq;
import com.onelive.common.model.req.login.CheckSmsReq;
import com.onelive.common.model.req.login.SmsReq;
import com.onelive.common.model.vo.common.SelectStringVO;
import com.onelive.common.model.vo.login.CaptchaVo;
import com.onelive.common.model.vo.sys.*;
import com.onelive.common.utils.img.CaptchaUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;
import java.util.UUID;

/**
 * @ClassName SysController
 * @Desc 系统配置基础类
 * @Date 2021/10/14 14:11
 */
@RestController
@RequestMapping("/sys")
@Api(tags = "系统配置基础接口")
@Slf4j
public class SysController {

    @Resource
    private SysBusiness sysBusiness;

    @ApiOperation("获取APP语言列表")
    @PostMapping(name = "获取APP语言列表", value = "/queryAllLanguage")
    @AllowAccess
    public ResultInfo<List<SelectStringVO>> queryAllLang() throws Exception {
        return ResultInfo.ok(sysBusiness.queryAllLanguage());
    }

    @ApiOperation("获取所有地区列表")
    @PostMapping(name = "获取所有地区列表", value = "/queryAllCountry")
    @AllowAccess
    public ResultInfo<List<CountryVO>> queryAllCountry() throws Exception {
        return ResultInfo.ok(sysBusiness.queryAllCountry());
    }

//    @ApiOperation(value = "发送短信", httpMethod = "POST")
    @PostMapping(value = {"/app/v1/sendSmsCode", "/pc/v1/sendSmsCode"})
    @AllowAccess
    public ResultInfo<Long> sendSmsCode(@RequestBody(required = false) SmsReq req) {
        Long countDown = sysBusiness.sendSmsCode(req);
        return ResultInfo.ok(countDown);
    }

//    @ApiOperation(value = "验证手机验证码", httpMethod = "POST")
    @PostMapping(value = {"/app/v1/checkSmsCode", "/pc/v1/checkSmsCode"})
    @AllowAccess
    public ResultInfo<String> checkSmsCode(@RequestBody(required = false) CheckSmsReq req) throws Exception{
        sysBusiness.checkSmsCode(req);
        return ResultInfo.ok();
    }

    @ApiOperation(value = "验证图片验证码", httpMethod = "POST")
    @PostMapping(value = {"/app/v1/checkImgCode", "/pc/v1/checkImgCode"})
    @AllowAccess
    public ResultInfo<String> checkImgCode(@RequestBody(required = false) CheckImgReq req) throws Exception{
        sysBusiness.checkImgCode(req);
        return ResultInfo.ok();
    }


    @ApiOperation(value = "随机生成图片", httpMethod = "POST")
    @PostMapping(value = "/v1/randCode")
    @AllowAccess
    public ResultInfo<CaptchaVo> randCode() throws Exception {
        CaptchaVo vo = CaptchaUtils.genCaptchaBase64();
        vo.setCaptchaKey(UUID.randomUUID().toString());
        //五分钟后失效
        ApiBusinessRedisUtils.setCaptchaKey(vo);
        log.info("CaptchaKey--{}--ImgCode:{}", vo.getCaptchaKey(), vo.getImgCode());
        return ResultInfo.ok(vo);
    }

    @ApiOperation("获取APP版本信息")
    @PostMapping(name = "获取APP版本信息", value = "/queryAppVersion")
    @AllowAccess
    public ResultInfo<SysAppVersionVO> queryAppVersion(/*@RequestBody SysAppVersionQueryReq req */) throws Exception {
        return ResultInfo.ok(sysBusiness.queryAppVersion());
    }

    @PostMapping("/v1/getAreaCodeList")
    @ApiOperation("获取所有地区区号列表")
    @AllowAccess
    public ResultInfo<List<SysCountryAreaCodeVO>> getAreaCodeList() {
        List<SysCountryAreaCodeVO> list = sysBusiness.getAreaCodeList();
        return ResultInfo.ok(list);
    }


    @PostMapping("/v1/getServerTime")
    @ApiOperation("获取服务器时间")
    @AllowAccess
    public ResultInfo<SysDateTimeVO> getServerTime() {
        SysDateTimeVO vo = new SysDateTimeVO();
        vo.setTime(System.currentTimeMillis());
        return ResultInfo.ok(vo);
    }


    @PostMapping("/v1/getPublicConfig")
    @ApiOperation("获取公共配置")
    @AllowAccess
    public ResultInfo<PublicConfigVO> getPublicConfig() {
        return ResultInfo.ok(sysBusiness.getPublicConfig());
    }
}
    