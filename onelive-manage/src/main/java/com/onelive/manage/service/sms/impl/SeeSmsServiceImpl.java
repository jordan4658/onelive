package com.onelive.manage.service.sms.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.onelive.common.model.req.sms.SmsQueryReq;
import com.onelive.common.model.vo.mem.MemUserGroupVO;
import com.onelive.common.mybatis.entity.MemLevel;
import com.onelive.common.mybatis.entity.MemUserGroup;
import com.onelive.common.mybatis.entity.SeeSms;
import com.onelive.common.mybatis.mapper.master.sms.SeeSmsMapper;
import com.onelive.common.mybatis.mapper.slave.sms.SlaveSeeSmsMapper;
import com.onelive.common.utils.others.BeanCopyUtil;
import com.onelive.common.utils.others.StringUtils;
import com.onelive.manage.service.sms.SeeSmsService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 短信方式表 服务实现类
 * </p>
 *
 * @author ${author}
 * @since 2021-12-21
 */
@Service
public class SeeSmsServiceImpl extends ServiceImpl<SeeSmsMapper, SeeSms> implements SeeSmsService {

    @Resource
    private SlaveSeeSmsMapper slaveSeeSmsMapper;

    @Override
    public PageInfo<SeeSms> getList(SmsQueryReq smsQueryReq) {
        QueryWrapper<SeeSms> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(SeeSms::getIsDelete, false);
        if (StringUtils.isNotEmpty(smsQueryReq.getSmsName())) {
            queryWrapper.lambda().like(SeeSms::getSmsName, smsQueryReq.getSmsName());
        }
        if (StringUtils.isNotEmpty(smsQueryReq.getSmsCode())) {
            queryWrapper.lambda().like(SeeSms::getSmsCode, smsQueryReq.getSmsCode());
        }
        if (smsQueryReq.getIsOpen() != null) {
            queryWrapper.lambda().eq(SeeSms::getIsOpen, smsQueryReq.getIsOpen());
        }
        queryWrapper.lambda().orderByAsc(SeeSms::getSortNum);
        PageHelper.startPage(smsQueryReq.getPageNum(), smsQueryReq.getPageSize());
        List<SeeSms> list = slaveSeeSmsMapper.selectList(queryWrapper);
        return new PageInfo<>(list);
    }
}
