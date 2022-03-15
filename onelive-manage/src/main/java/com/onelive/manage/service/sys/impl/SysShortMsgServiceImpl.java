package com.onelive.manage.service.sys.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.onelive.common.enums.SendTypeEnum;
import com.onelive.common.enums.ShortMsgStatusEnum;
import com.onelive.common.model.req.sys.ShortMsgReq;
import com.onelive.common.model.vo.sys.ShortMsgVO;
import com.onelive.common.mybatis.entity.SysShortMsg;
import com.onelive.common.mybatis.mapper.master.sys.SysShortMsgMapper;
import com.onelive.common.mybatis.mapper.slave.sys.SlaveSysShortMsgMapper;
import com.onelive.common.utils.others.DateUtils;
import com.onelive.manage.service.sys.SysShortMsgService;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 短信记录表 服务实现类
 * </p>
 *
 * @author fl-sport
 * @since 2021-04-05
 */
@Service
public class SysShortMsgServiceImpl extends ServiceImpl<SysShortMsgMapper, SysShortMsg> implements SysShortMsgService {

    @Resource
    private SlaveSysShortMsgMapper slaveSysShortMsgMapper;

    @Override
    public List<ShortMsgVO> queryShortMsgList(ShortMsgReq req) {
        List<ShortMsgVO> voList = new ArrayList<>();

        QueryWrapper<SysShortMsg> queryWrapper = new QueryWrapper<>();

        if (req.getMsgType() != null) {
            queryWrapper.lambda().eq(SysShortMsg::getMsgType, req.getMsgType());
        }
        if (req.getMasStatus() != null) {
            queryWrapper.lambda().eq(SysShortMsg::getMasStatus, req.getMasStatus());
        }
        if (StringUtils.isNotBlank(req.getMobilePhone())) {
            queryWrapper.lambda().like(SysShortMsg::getMobilePhone, req.getMobilePhone());
        }
        if (StringUtils.isNotBlank(req.getAreaCode())) {
            queryWrapper.lambda().eq(SysShortMsg::getAreaCode, req.getAreaCode());
        }
        if (StringUtils.isNotBlank(req.getSendIp())) {
            queryWrapper.lambda().like(SysShortMsg::getSendIp, req.getSendIp());
        }
        if (req.getSendDateStart() != null) {
            queryWrapper.lambda().gt(SysShortMsg::getSendDate, DateUtils.parseDate(req.getSendDateStart()));
        }
        if (req.getSendDateEnd() != null) {
            queryWrapper.lambda().lt(SysShortMsg::getSendDate, DateUtils.parseDate(req.getSendDateEnd()));
        }
        if (StringUtils.isNotBlank(req.getUserAccount())) {
        	queryWrapper.lambda().like(SysShortMsg::getUserAccount, req.getUserAccount());
        }
        if (StringUtils.isNotBlank(req.getBusinessName())) {
        	queryWrapper.lambda().like(SysShortMsg::getBusinessName, req.getBusinessName());
        }
        if (StringUtils.isNotBlank(req.getDevicenName())) {
        	queryWrapper.lambda().like(SysShortMsg::getDeviceName, req.getDevicenName());
        }
        if (StringUtils.isNotBlank(req.getDeviceId())) {
        	queryWrapper.lambda().like(SysShortMsg::getDeviceId, req.getDeviceId());
        }
        queryWrapper.orderByDesc("send_date");

        List<SysShortMsg> queryList = slaveSysShortMsgMapper.selectList(queryWrapper);
        if (CollectionUtils.isNotEmpty(queryList)) {
            voList = queryList.stream().map(a -> {
                ShortMsgVO vo = new ShortMsgVO();
                BeanUtil.copyProperties(a, vo);
                String mobilePhone = vo.getMobilePhone();
//                vo.setMobilePhone(mobilePhone.substring(0, 3) + "****" + mobilePhone.substring(7));
                vo.setMsgTypeInfo(SendTypeEnum.getValueByCode(a.getMsgType()));
                vo.setMasStatusInfo(ShortMsgStatusEnum.getDesByCode(a.getMasStatus()));
                return vo;
            }).collect(Collectors.toList());

        }
        return voList;
    }
}
