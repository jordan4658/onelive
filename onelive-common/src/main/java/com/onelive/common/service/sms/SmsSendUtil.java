package com.onelive.common.service.sms;

import com.onelive.common.enums.SmsThirdEnum;
import com.onelive.common.enums.StatusCode;
import com.onelive.common.exception.BusinessException;
import com.onelive.common.model.vo.sms.SmsResultVo;
import lombok.extern.slf4j.Slf4j;

/**
 * @ClassName SmsSendUtil
 * @Desc 短信发送统一工具类
 * @Date 2021/4/3 17:34
 */

@Slf4j
public class SmsSendUtil {

    /**
     * 默认5分钟有效
     */
    public static final Integer validRange = 5;


    /**
     * 方式短信
     * @param areacode 国家区号
     * @param mobilePhone 手机号码
     * @param sendCode    验证码
     * @param validTime   有效时间（单位分钟）
     * @param smsType     第三方供应商标识，具体类型参考枚举类 SmsThirdEnum
     * @return
     */
    public static SmsResultVo send(String areacode,String mobilePhone, String sendCode, String validTime, String smsType) {
        SmsResultVo vo = new SmsResultVo();
        vo.setSmsType(smsType);
        try {
            if (SmsThirdEnum.YunTongXin.getCode().equals(smsType)) {
                YunTongXinSmsUtil.sendTemplate(mobilePhone, sendCode, validTime);
            }
        } catch (BusinessException e) {
            log.error("SmsSendUtil.send,报错:{},手机号：{}", e.getMessage(), mobilePhone, e);
            vo.setCode(e.getCode().toString());
            vo.setMsg(e.getMessage());
        } catch (Exception e) {
            log.error("SmsSendUtil.send,失败:{},手机号：{}", e.getMessage(), mobilePhone, e);
            vo.setCode(StatusCode.SYSTEM_ERROR.getCode().toString());
            vo.setMsg(e.getMessage());
        }
        return vo;
    }





}    
    