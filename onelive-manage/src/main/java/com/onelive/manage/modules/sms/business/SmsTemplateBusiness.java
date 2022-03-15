package com.onelive.manage.modules.sms.business;

import com.github.pagehelper.PageInfo;
import com.onelive.common.exception.BusinessException;
import com.onelive.common.model.dto.login.LoginUser;
import com.onelive.common.model.req.sms.SmsTemplateAddReq;
import com.onelive.common.model.req.sms.SmsTemplateQueryReq;
import com.onelive.common.model.req.sms.SmsTemplateUpdateReq;
import com.onelive.common.model.vo.sms.SmsTemplateVo;
import com.onelive.common.mybatis.entity.SeeSmsTemplate;
import com.onelive.common.utils.others.PageInfoUtil;
import com.onelive.common.utils.others.StringUtils;
import com.onelive.manage.service.sms.SeeSmsTemplateService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
@Slf4j
public class SmsTemplateBusiness {

    @Autowired
    private SeeSmsTemplateService seeSmsTemplateService;

    public PageInfo<SmsTemplateVo> list(SmsTemplateQueryReq smsTemplateQueryReq) {
        PageInfo<SeeSmsTemplate> pageInfo = seeSmsTemplateService.getList(smsTemplateQueryReq);
        return PageInfoUtil.pageInfo2DTO(pageInfo, SmsTemplateVo.class);
    }

    public SeeSmsTemplate getById(Long id) {
        return seeSmsTemplateService.getById(id);
    }

    public void add(SmsTemplateAddReq smsTemplateAddReq, LoginUser user) {
        if(StringUtils.isEmpty(smsTemplateAddReq.getTemplateContent())){
            throw  new BusinessException("模板内容为空！");
        }
        if(StringUtils.isEmpty(smsTemplateAddReq.getTemplateCode())){
            throw  new BusinessException("模板标识code为空！");
        }
        if(smsTemplateAddReq.getSortNum()==null){
            throw  new BusinessException("排序好为空！");
        }
        SeeSmsTemplate template = new SeeSmsTemplate();
        BeanUtils.copyProperties(smsTemplateAddReq, template);
        template.setCreateTime(new Date());
        template.setCreateUser(user.getAccLogin());
        template.setUpdateTime(new Date());
        template.setUpdateUser(user.getAccLogin());
        seeSmsTemplateService.save(template);
    }

    public void update(SmsTemplateUpdateReq smsTemplateUpdateReq, LoginUser user) {
        if(smsTemplateUpdateReq.getId()==null){
            throw  new BusinessException("Id为空！");
        }
        if(StringUtils.isEmpty(smsTemplateUpdateReq.getTemplateContent())){
            throw  new BusinessException("模板内容为空！");
        }
        if(StringUtils.isEmpty(smsTemplateUpdateReq.getTemplateCode())){
            throw  new BusinessException("模板标识code为空！");
        }
        if(smsTemplateUpdateReq.getSortNum()==null){
            throw  new BusinessException("排序好为空！");
        }
        SeeSmsTemplate template = seeSmsTemplateService.getById(smsTemplateUpdateReq.getId());
        BeanUtils.copyProperties(smsTemplateUpdateReq, template);
        template.setUpdateTime(new Date());
        template.setUpdateUser(user.getAccLogin());
        seeSmsTemplateService.updateById(template);
    }

    public void delete(Long id, LoginUser user) {
        SeeSmsTemplate template = seeSmsTemplateService.getById(id);
        template.setIsDelete(true);
        template.setUpdateTime(new Date());
        template.setUpdateUser(user.getAccLogin());
        seeSmsTemplateService.updateById(template);
    }

    public void openOrClose(Long id, LoginUser loginAdmin,Boolean isOpen) {
        SeeSmsTemplate smsTemplate = seeSmsTemplateService.getById(id);
        smsTemplate.setIsOpen(isOpen);
        smsTemplate.setUpdateTime(new Date());
        smsTemplate.setUpdateUser(loginAdmin.getAccLogin());
        seeSmsTemplateService.updateById(smsTemplate);
    }

}
