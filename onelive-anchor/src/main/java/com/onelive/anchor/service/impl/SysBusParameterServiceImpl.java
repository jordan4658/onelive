package com.onelive.anchor.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.onelive.anchor.service.SysBusParameterService;
import com.onelive.common.mybatis.entity.SysBusParameter;
import com.onelive.common.mybatis.mapper.master.sys.SysBusParameterMapper;
import com.onelive.common.mybatis.mapper.slave.sys.SlaveSysBusParameterMapper;
import com.onelive.common.utils.redis.SysBusinessRedisUtils;

/**
 * <p>
 * 业务参数 服务实现类
 * </p>
 *
 * @author ${author}
 * @since 2021-04-06
 */
@Service
public class SysBusParameterServiceImpl extends ServiceImpl<SysBusParameterMapper, SysBusParameter> implements SysBusParameterService {

    @Resource
    private SlaveSysBusParameterMapper slaveSysBusParameterMapper;

    @Override
    public List<SysBusParameter> getByParentCode(String pcode) {
        List<SysBusParameter> list = SysBusinessRedisUtils.getSysBusParameterList(pcode);
        if (CollectionUtils.isNotEmpty(list)) { return list; }

        QueryWrapper<SysBusParameter> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(SysBusParameter::getPParamCode, pcode);
        queryWrapper.lambda().eq(SysBusParameter::getStatus, 0);
        queryWrapper.lambda().eq(SysBusParameter::getIsDelete, false);
        list = slaveSysBusParameterMapper.selectList(queryWrapper);
        if (CollectionUtils.isNotEmpty(list)) {
            SysBusinessRedisUtils.setSysBusParameterList(list, pcode);
        }
        return list;
    }

    @Override
    public SysBusParameter getByCode(String code) {
        SysBusParameter param = SysBusinessRedisUtils.getSysBusParameter(code);
        if (param != null) {return param;}

        QueryWrapper<SysBusParameter> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(SysBusParameter::getParamCode, code);
        queryWrapper.lambda().eq(SysBusParameter::getStatus, 0);
        queryWrapper.lambda().eq(SysBusParameter::getIsDelete, false).last("limit 1 ");
        param = slaveSysBusParameterMapper.selectOne(queryWrapper);
        if (param != null) {
            SysBusinessRedisUtils.setSysBusParameter(param);
        }
        return param;
    }


}
