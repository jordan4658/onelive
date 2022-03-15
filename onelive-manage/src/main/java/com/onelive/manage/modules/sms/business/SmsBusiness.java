package com.onelive.manage.modules.sms.business;

import com.github.pagehelper.PageInfo;
import com.onelive.common.model.dto.login.LoginUser;
import com.onelive.common.model.req.sms.SmsAddReq;
import com.onelive.common.model.req.sms.SmsQueryReq;
import com.onelive.common.model.req.sms.SmsUpdateReq;
import com.onelive.common.model.vo.sms.SeeSmsVo;
import com.onelive.common.mybatis.entity.SeeSms;
import com.onelive.common.utils.others.PageInfoUtil;
import com.onelive.manage.service.sms.SeeSmsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Date;

@Component
@Slf4j
public class SmsBusiness {

    @Resource
    private SeeSmsService smsService;

    public PageInfo<SeeSmsVo> list(SmsQueryReq smsQueryReq) {
        PageInfo<SeeSms> pageInfo=smsService.getList(smsQueryReq);
       return PageInfoUtil.pageInfo2DTO(pageInfo, SeeSmsVo.class);
    }

    public void add(SmsAddReq smsAddReq, LoginUser user) {
        SeeSms sms = new SeeSms();
        BeanUtils.copyProperties(smsAddReq, sms);
        sms.setCreateTime(new Date());
        sms.setCreateUser(user.getAccLogin());
        sms.setUpdateTime(new Date());
        sms.setUpdateUser(user.getAccLogin());
        smsService.save(sms);
    }

    public void update(SmsUpdateReq smsUpdateReq, LoginUser user) {
        SeeSms sms = smsService.getById(smsUpdateReq.getId());
        BeanUtils.copyProperties(smsUpdateReq, sms);
        sms.setUpdateTime(new Date());
        sms.setUpdateUser(user.getAccLogin());
        smsService.updateById(sms);
    }

    public SeeSms getById(Long id) {
        return smsService.getById(id);
    }

    public void delete(Long id, LoginUser user) {
        SeeSms sms = smsService.getById(id);
        sms.setIsDelete(true);
        sms.setUpdateTime(new Date());
        sms.setUpdateUser(user.getAccLogin());
        smsService.updateById(sms);
    }

    public void openOrClose(Long id, LoginUser user,Boolean isOpen) {
        SeeSms sms = smsService.getById(id);
        sms.setIsOpen(isOpen);
        sms.setUpdateTime(new Date());
        sms.setUpdateUser(user.getAccLogin());
        smsService.updateById(sms);
    }

}
