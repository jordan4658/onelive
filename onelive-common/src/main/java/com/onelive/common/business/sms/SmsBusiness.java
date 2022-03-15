package com.onelive.common.business.sms;

import cn.hutool.core.date.DateUnit;
import cn.hutool.core.date.DateUtil;
import com.alibaba.excel.util.StringUtils;
import com.alibaba.fastjson.JSONObject;
import com.onelive.common.constants.business.RedisKeys;
import com.onelive.common.constants.sms.SmsConstants;
import com.onelive.common.enums.SmsStatusEnum;
import com.onelive.common.enums.StatusCode;
import com.onelive.common.exception.BusinessException;
import com.onelive.common.model.req.sms.SendSmsReq;
import com.onelive.common.model.req.sms.VerifyCodeReq;
import com.onelive.common.model.vo.sms.SendSmsAppVO;
import com.onelive.common.mybatis.entity.SeeSms;
import com.onelive.common.mybatis.entity.SeeSmsTemplate;
import com.onelive.common.mybatis.entity.SysShortMsg;
import com.onelive.common.mybatis.mapper.master.sys.SysShortMsgMapper;
import com.onelive.common.service.sms.SmsSendCommonUtils;
import com.onelive.common.service.sms.SmsSendUtil;
import com.onelive.common.service.sms.service.SmsCommonService;
import com.onelive.common.service.sms.service.SmsCommonTemplateService;
import com.onelive.common.utils.Login.LoginInfoUtil;
import com.onelive.common.utils.redis.RedisUtil;
import com.onelive.common.utils.redis.SmsRedisUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.List;

@Component
@Slf4j
public class SmsBusiness {

    @Autowired
    private SmsCommonService smsService;
    @Autowired
    private SmsCommonTemplateService smsTemplateService;
    @Resource
    private SysShortMsgMapper sysShortMsgMapper;


    /**
     * 发送短信
     * @param sendSmsReq
     * @param response
     * @return 返回下次短信发送倒计时 单位：秒
     */
    public SendSmsAppVO sendSms(SendSmsReq sendSmsReq, HttpServletResponse response,Boolean smsSwitch) {
        if (StringUtils.isEmpty(sendSmsReq.getPhone())) {
            throw new BusinessException("手机号码不能为空！");
        }
        if (StringUtils.isEmpty(sendSmsReq.getAreaCode())) {
            throw new BusinessException("国家区号不能为空！");
        }
        if (sendSmsReq.getSendType() == null) {
            throw new BusinessException("短信类型不能为空！");
        }
        SendSmsAppVO vo=new SendSmsAppVO();
        Date nowDate = new Date();
        //系统配置短信条数限制 TODO  后期考虑写到配置表
        Integer limit = 9000;

        // 系统配置短信倒计时 单位秒 默认1分钟 TODO 后期考虑写到配置表
        Integer countDown = 60;
        vo.setCountDown(countDown);

        //系统配置短信有效时间 单位秒 默认5分钟 TODO  后期考虑写到配置表
        Integer validTime = SmsSendUtil.validRange;

        //1、判断最新一条手机验证码是否过期
        Date sendDate = SmsRedisUtils.getLastSendTime(sendSmsReq.getAreaCode() + sendSmsReq.getPhone() + sendSmsReq.getSendType());
        if (sendDate != null) {
            long seconds = DateUtil.between(DateUtil.offsetSecond(sendDate, countDown), nowDate, DateUnit.SECOND);
            if (seconds > 0) {
                vo.setCountDown(seconds);
                return vo;
            }
        }
        //2、ip限制次数
        String ip = LoginInfoUtil.getIp();
        long ipCount = SmsRedisUtils.limitByCurrentDay(RedisKeys.IP_PHONE, ip);
        if (ipCount > limit) {
            throw new BusinessException(StatusCode.SMS_PHONE_LIMIT);
        }
        //3、判断手机验证码是否超过当天条数
        ipCount = SmsRedisUtils.limitByCurrentDay(RedisKeys.IP_PHONE, sendSmsReq.getAreaCode() + sendSmsReq.getPhone());
        if (ipCount > limit) {
            throw new BusinessException(StatusCode.SMS_PHONE_LIMIT);
        }

        String codeType = SmsSendCommonUtils.getCodeType(sendSmsReq.getSendType());
        String key = String.format(SmsConstants.Key.RANDCODE, codeType, sendSmsReq.getAreaCode() + sendSmsReq.getPhone());
        RedisUtil.del(key);
        String smsCode = SmsSendCommonUtils.randomCode();//生成验证码
        if (smsSwitch) {
            response.addHeader("smscode", smsCode);
            log.info("短信发送验证码：{}",smsCode);
        }
//        //根据短信类型查询对应模板，现在只有一个通用模板。
//        SeeSmsTemplate template = smsTemplateService.getByTemplateCode(sendSmsReq.getSendType());
//        log.info("====查询短信模板:" + JSONObject.toJSONString(template));
//        if (template == null) {
//            throw new BusinessException("短信模板类型=" + sendSmsReq.getSendType() + " 未查询到模板！");
//        }
        //查询短信开启的发送方式。
        List<SeeSms> listSms = smsService.getOpenSms();
        log.info("====查询短信开启的发送方式:" + JSONObject.toJSONString(listSms));
        Integer statusCode = SmsStatusEnum.fail.getCode();
        for (SeeSms sms : listSms) {
            Boolean flag = true;
            if (sms.getSmsCode().equals(SmsConstants.smsCode.VERIFY3_CODE.getValue())) {
                validTime = sms.getValidTime();
                flag = SmsSendCommonUtils.sendVerify3(sms, null, sendSmsReq.getAreaCode()+sendSmsReq.getPhone(), smsCode);
                if (flag) {
                    RedisUtil.set(key, smsCode, Long.valueOf(sms.getValidTime() * 60 * 1000));//保存验证码
                    statusCode = SmsStatusEnum.success.getCode();
                    break;
                }
                throw new BusinessException("短信发送失败！");
            }

        }

        //5、保存到数据库
        SysShortMsg sysShortMsg = new SysShortMsg();
        sysShortMsg.setAreaCode(sendSmsReq.getAreaCode());
        sysShortMsg.setMasBody(smsCode);
        sysShortMsg.setMobilePhone(sendSmsReq.getPhone());
        sysShortMsg.setMasCode(smsCode);
        sysShortMsg.setSendDate(nowDate);
        sysShortMsg.setValidDate(DateUtil.offsetSecond(nowDate, validTime));
        sysShortMsg.setMsgType(sendSmsReq.getSendType());
        sysShortMsg.setMasStatus(statusCode);
        sysShortMsg.setSendIp(ip);
        sysShortMsg.setUserAccount(LoginInfoUtil.getUserAccount());
        sysShortMsg.setDeviceId(LoginInfoUtil.getDeviceId());
        sysShortMsg.setDeviceName(LoginInfoUtil.getDevices());
        sysShortMsgMapper.insert(sysShortMsg);
        //6、写入redis缓存
        SmsRedisUtils.setLastSendTime(sendSmsReq.getAreaCode() + sendSmsReq.getPhone() + sendSmsReq.getSendType(), nowDate, Long.valueOf(countDown));
        return vo;
    }

    /**
     * @param verifyCodeReq
     * @return
     */
    public Boolean checkoutVerifyCode(VerifyCodeReq verifyCodeReq) {
        Boolean flag = SmsSendCommonUtils.getVerificationCode(verifyCodeReq.getAreaCode() + verifyCodeReq.getPhone(), verifyCodeReq.getType(),verifyCodeReq.getCode());
        if (!flag) {
            log.info("验证码错误！");
            return false;
        }
        return true;
    }
}
