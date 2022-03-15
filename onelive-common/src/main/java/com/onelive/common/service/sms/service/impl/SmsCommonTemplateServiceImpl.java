package com.onelive.common.service.sms.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.onelive.common.model.req.sms.SmsTemplateQueryReq;
import com.onelive.common.mybatis.entity.SeeSmsTemplate;
import com.onelive.common.mybatis.mapper.master.sms.SeeSmsTemplateMapper;
import com.onelive.common.mybatis.mapper.slave.sms.SlaveSeeSmsTemplateMapper;
import com.onelive.common.service.sms.service.SmsCommonTemplateService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 短信模板表 服务实现类
 * </p>
 *
 * @author ${author}
 * @since 2021-12-21
 */
@Service
public class SmsCommonTemplateServiceImpl extends ServiceImpl<SeeSmsTemplateMapper, SeeSmsTemplate> implements SmsCommonTemplateService {

    @Resource
    private SlaveSeeSmsTemplateMapper slaveSeeSmsTemplateMapper;

    @Override
    public PageInfo<SeeSmsTemplate> getList(SmsTemplateQueryReq smsTemplateQueryReq) {
        QueryWrapper<SeeSmsTemplate> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(SeeSmsTemplate::getIsDelete, false);
        queryWrapper.lambda().eq(SeeSmsTemplate::getIsOpen, smsTemplateQueryReq.getIsOpen());
        PageHelper.startPage(smsTemplateQueryReq.getPageNum(), smsTemplateQueryReq.getPageSize());
        List<SeeSmsTemplate> list = slaveSeeSmsTemplateMapper.selectList(queryWrapper);
        return new PageInfo<>(list);
    }

    @Override
    public SeeSmsTemplate getByTemplateCode(Integer sendType) {
        QueryWrapper<SeeSmsTemplate> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(SeeSmsTemplate::getIsDelete, false);
        queryWrapper.lambda().eq(SeeSmsTemplate::getIsOpen, true);
        queryWrapper.lambda().eq(SeeSmsTemplate::getTemplateCode, sendType);
        queryWrapper.lambda().orderByDesc(SeeSmsTemplate::getCreateTime);
        return slaveSeeSmsTemplateMapper.selectOne(queryWrapper);
    }
}
