package com.onelive.common.service.sms;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.nacos.common.utils.Md5Utils;
import com.onelive.common.constants.sms.SmsConstants;
import com.onelive.common.mybatis.entity.SeeSms;
import com.onelive.common.mybatis.entity.SeeSmsTemplate;
import com.onelive.common.utils.http.HttpClient_Fh_Util;
import com.onelive.common.utils.others.SecurityUtils;
import com.onelive.common.utils.others.StringUtils;
import com.onelive.common.utils.redis.RedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.StandardCharsets;
import java.util.Random;

@Slf4j
@Component
public class SmsSendCommonUtils {

    public static void main(String[] args) {
        String str = "{\"SmsPerMessage\":0,\"Code\":100,\"Message\":\"Other: 73\"}";
        JSONObject json = JSONObject.parseObject(str);
        System.out.println("code:" + json.get("Code"));


    }


    public static Boolean sendVerify3(SeeSms sms, SeeSmsTemplate template, String phone, String code) {
        try {
            StringBuffer httpUrl = new StringBuffer();
            httpUrl.append(sms.getSendUrl());
            httpUrl.append("?");
            httpUrl.append("loginName=").append(sms.getApiKey()).append("&");
            httpUrl.append("sign=").append(SecurityUtils.MD5(sms.getSecretKey())).append("&");
            httpUrl.append("serviceTypeId=").append("30").append("&");
            httpUrl.append("phoneNumber=").append(phone).append("&");
            httpUrl.append("message=").append(code).append("&");
            httpUrl.append("brandName=").append(sms.getSmsCode()).append("&");
            httpUrl.append("callBack=").append("false").append("&");
            httpUrl.append("smsGuid=").append("1").append("&");
            log.info("短信请求URl:"+httpUrl.toString());
            //{"SmsPerMessage":0,"Code":100,"Message":"Other: 73"}
            String result = HttpClient_Fh_Util.doGetRequest(httpUrl.toString(), null, null, null);
            JSONObject resultJson = JSONObject.parseObject(result);
            if (StringUtils.isEmpty(resultJson.get("Code").toString()) || !"106".equals(resultJson.get("Code").toString())) {
                log.error("手机号：{} 发送短信失败！", phone);
                return false;
            }
            return true;
        } catch (Exception e) {
            log.error("Verify3--发送短信失败：" + e.getMessage());
            return false;
        }

    }

    /**
     * 获取验证码，并立即删除验证码
     *
     * @param phone 手机号码
     * @param type  类型参考 SmsConstants.smsType 枚举
     * @return
     */
    public static String getVerificationCode(String phone, Integer type) {
        String codeType = getCodeType(type);
        String key = String.format(SmsConstants.Key.RANDCODE, codeType, phone);
        String code = RedisUtil.get(key);
        RedisUtil.del(key);
        return code;
    }

    /**
     * 校验验证码，验证成功删除验证码，失败不删除验证码。
     *
     * @param phone    手机号码
     * @param type     类型参考 SmsConstants.smsType 枚举
     * @return
     */
    public static Boolean getVerificationCode(String phone, Integer type,String reqCode) {
        Boolean flag=false;
        String codeType = getCodeType(type);
        String key = String.format(SmsConstants.Key.RANDCODE, codeType, phone);
        String code = RedisUtil.get(key);

        if(StringUtils.isNotBlank(code) && code.equals(reqCode)){
            RedisUtil.del(key);
            flag=true;
        }
        return flag;
    }


    public static String getCodeType(Integer type) {
        String codeType = null;
        for (SmsConstants.smsType smsType : SmsConstants.smsType.values()) {
            if (smsType.getCode() == type) {
                codeType = smsType.getValue();
                break;
            }
        }
        return codeType;
    }


    /**
     * 生成验证码
     *
     * @return
     */
    public static String randomCode() {
        return "" + (new Random().nextInt(899999) + 100000);
    }


}
