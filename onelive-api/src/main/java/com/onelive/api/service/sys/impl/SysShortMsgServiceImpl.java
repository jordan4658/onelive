package com.onelive.api.service.sys.impl;

import cn.hutool.core.date.DateUnit;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.onelive.api.service.sys.SysShortMsgService;
import com.onelive.api.util.ApiBusinessRedisUtils;
import com.onelive.common.config.RabbitConfig;
import com.onelive.common.constants.business.RedisKeys;
import com.onelive.common.enums.EnvEnum;
import com.onelive.common.enums.SendTypeEnum;
import com.onelive.common.enums.SmsStatusEnum;
import com.onelive.common.enums.StatusCode;
import com.onelive.common.exception.BusinessException;
import com.onelive.common.model.dto.sys.SendSmsDTO;
import com.onelive.common.model.dto.sys.SmsCodeDTO;
import com.onelive.common.model.req.login.SmsReq;
import com.onelive.common.mybatis.entity.SysShortMsg;
import com.onelive.common.mybatis.mapper.master.sys.SysShortMsgMapper;
import com.onelive.common.mybatis.mapper.slave.sys.SlaveSysShortMsgMapper;
import com.onelive.common.utils.Login.LoginInfoUtil;
import com.onelive.common.utils.others.CodeGenerateUtils;
import com.onelive.common.utils.others.PhoneUtil;
import com.onelive.common.service.sms.SmsSendUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;

/**
 * <p>
 * 短信记录表 服务实现类
 * </p>
 *
 * @author fl-sport
 * @since 2021-04-05
 */
@Service
@Slf4j
public class SysShortMsgServiceImpl extends ServiceImpl<SysShortMsgMapper, SysShortMsg> implements SysShortMsgService {

    @Resource
    private SlaveSysShortMsgMapper slaveSysShortMsgMapper;

    @Resource
    private RabbitTemplate rabbitTemplate;

    @Resource
    private HttpServletResponse response;
    @Value("${spring.profiles.active}")
    private String env;

    @Override
    public void checkSmsCode(String tel,String areaCode, String smsCode, SendTypeEnum msgType) throws Exception {
        this.checkSmsCode(tel, areaCode,smsCode, msgType.getCode());
        //更新验证码
        UpdateWrapper<SysShortMsg> updateWrapper = new UpdateWrapper<>();
        updateWrapper.lambda().set(SysShortMsg::getMasStatus, SmsStatusEnum.used.getCode())
                .eq(SysShortMsg::getMobilePhone, tel)
                .eq(SysShortMsg::getMasCode, smsCode)
                .eq(SysShortMsg::getMerchantCode,LoginInfoUtil.getMerchantCode());
        this.update(updateWrapper);
    }

    @Override
    public void checkSmsCode(String tel, String areaCode,String smsCode, Integer msgType) throws Exception {
        //验证手机号是否正确
        PhoneUtil.checkPhone(tel, areaCode);

        //TODO 测试用
        if(true){
            return;
        }

        //查询手机验证码信息
        SmsCodeDTO dto = new SmsCodeDTO();
        dto.setTel(tel);
        dto.setSmsCode(smsCode);
        dto.setMasStatus(SmsStatusEnum.success.getCode());
        dto.setMsgType(msgType);
        dto.setMerchantCode(LoginInfoUtil.getMerchantCode());
        SysShortMsg msg = slaveSysShortMsgMapper.selectByCode(dto);
        if (msg == null) {
            throw new BusinessException(StatusCode.SMS_CODE_ERROR);
        }
        //判断手机验证码是否正确
        Date nowDate = new Date();
        if (msg.getValidDate().getTime() < nowDate.getTime()) {
            throw new BusinessException(StatusCode.SMS_CODE_FAILED);
        }
    }

    @Override
    public long sendSmsCode(SmsReq req) {
        Date nowDate = new Date();
        //系统配置短信条数限制 TODO  后期考虑写到配置表
        Integer limit = 50;

        // 系统配置短信倒计时 单位秒 默认两分钟 TODO 后期考虑写到配置表
        Integer countDown = 120;

        //系统配置短信有效时间 单位秒 默认5分钟 TODO  后期考虑写到配置表
        Integer validTime = SmsSendUtil.validRange * 60;

        //2、判断最新一条手机验证码是否过期
        Date sendDate = ApiBusinessRedisUtils.getLastSendTime(req.getAreaCode()+req.getMobilePhone() + req.getSendType());
        if (sendDate != null) {
            long seconds = DateUtil.between(DateUtil.offsetSecond(sendDate, countDown), nowDate, DateUnit.SECOND);
            if (seconds > 0) {
                return seconds;
            }
        }

        //1、发送验证码
        // 根据地区 调短信发送接口
        try {
            PhoneUtil.checkPhone(req.getMobilePhone(),req.getAreaCode());
        } catch (Exception e) {
            log.error("验证手机号码出错", e);
            throw new BusinessException(StatusCode.SMS_PHONE_FAILED);
        }

        //2、ip限制次数
        String ip = LoginInfoUtil.getIp();
        long ipCount = ApiBusinessRedisUtils.limitByCurrentDay(RedisKeys.IP_PHONE, ip);
        if (ipCount > limit) {
            throw new BusinessException(StatusCode.SMS_PHONE_LIMIT);
        }

        //3、判断手机验证码是否超过当天条数
        ipCount = ApiBusinessRedisUtils.limitByCurrentDay(RedisKeys.IP_PHONE,req.getAreaCode() + req.getMobilePhone());
        if (ipCount > limit) {
            throw new BusinessException(StatusCode.SMS_PHONE_LIMIT);
        }

        //4、随机生成4位验证码
        String smsCode = CodeGenerateUtils.getFourRandomSmsCode();
        StringBuilder masBody = new StringBuilder();
        String smsTemplate = "【飞狐科技】您的验证码为{}，请于{}分钟内正确输入，如非本人操作，请忽略此短信。";
        masBody.append(StrUtil.format(smsTemplate, smsCode, SmsSendUtil.validRange));

        //5、保存到数据库
        SysShortMsg sysShortMsg = new SysShortMsg();
        sysShortMsg.setAreaCode(req.getAreaCode());
        sysShortMsg.setMasBody(masBody.toString());
        sysShortMsg.setMobilePhone(req.getMobilePhone());
        sysShortMsg.setMasCode(smsCode);
        sysShortMsg.setSendDate(nowDate);
        sysShortMsg.setValidDate(DateUtil.offsetSecond(nowDate, validTime));
        sysShortMsg.setMsgType(req.getSendType());
        sysShortMsg.setMasStatus(SmsStatusEnum.success.getCode());
        sysShortMsg.setSendIp(ip);
        sysShortMsg.setUserAccount(LoginInfoUtil.getUserAccount());
        sysShortMsg.setDeviceId(LoginInfoUtil.getDeviceId());
        sysShortMsg.setDeviceName(LoginInfoUtil.getDevices());
        
//        sysShortMsg.setBusinessName(businessName);
        
        this.baseMapper.insert(sysShortMsg);

        //6、写入redis缓存
        ApiBusinessRedisUtils.setLastSendTime(req.getAreaCode()+req.getMobilePhone() + req.getSendType(), nowDate, Long.valueOf(countDown));

        //7、消息队列异步发送手机验证码
        SendSmsDTO dto = new SendSmsDTO();
        dto.setSmsCode(smsCode);
        dto.setTel(req.getMobilePhone());
        dto.setId(sysShortMsg.getId());
        dto.setAreaCode(req.getAreaCode());
        rabbitTemplate.convertAndSend(RabbitConfig.SMS_EXCHANGE_TOPIC, "sms.message", dto);

        //如果是测试环境的话，返回
        if (EnvEnum.sit.name().equals(env) || EnvEnum.dev.name().equals(env)) {
            response.setHeader("tempCode", smsCode);
        }
        log.info("发送短信成功,{}", JSONObject.toJSONString(sysShortMsg));

        return countDown;
    }
}
