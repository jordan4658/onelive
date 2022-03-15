package com.onelive.api.service.sys;

import com.baomidou.mybatisplus.extension.service.IService;
import com.onelive.common.enums.SendTypeEnum;
import com.onelive.common.model.req.login.SmsReq;
import com.onelive.common.mybatis.entity.SysShortMsg;

/**
 * <p>
 * 短信记录表 服务类
 * </p>
 *
 * @author fl-sport
 * @since 2021-04-05
 */
public interface SysShortMsgService extends IService<SysShortMsg> {

    /**
     * 验证短信验证码是否正确
     *
     * @param tel     手机号
     * @param areaCode  区号
     * @param smsCode 验证码
     * @param msgType 短信类型
     * @throws Exception
     */
    void checkSmsCode(String tel,String areaCode, String smsCode, SendTypeEnum msgType) throws Exception;

    /**
     * 验证短信验证码是否正确
     *
     * @param tel     手机号
     * @param smsCode 验证码
     * @param msgType 短信类型
     * @throws Exception
     */
    void checkSmsCode(String tel, String areaCode,String smsCode, Integer msgType) throws Exception;

    /**
     * 功能描述: 发送短信
     *
     * @param: SmsReq
     * @return: 剩下倒计时长，单位秒
     */
    long sendSmsCode(SmsReq req);
}
