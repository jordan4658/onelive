package com.onelive.manage.service.sys.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.onelive.common.enums.SysParamEnum;
import com.onelive.common.exception.BusinessException;
import com.onelive.common.model.vo.sys.SysParameterListVO;
import com.onelive.common.mybatis.entity.SysParameter;
import com.onelive.common.mybatis.mapper.master.sys.SysParametersMapper;
import com.onelive.common.utils.others.BeanCopyUtil;
import com.onelive.manage.service.sys.SysParameterService;
import com.onelive.manage.utils.redis.SystemRedisUtils;

import cn.hutool.core.bean.BeanUtil;
import lombok.extern.slf4j.Slf4j;

/**
 * <p>
 * 系统参数 服务实现类
 * </p>
 *
 * @author
 * @since 2021-03-30
 */
@Service
@Slf4j
public class SysParameterServiceImpl extends ServiceImpl<SysParametersMapper, SysParameter> implements SysParameterService {


    @Override
    public SysParameter getByCode(SysParamEnum sysParamEnum) throws BusinessException {
        return getByCode(null == sysParamEnum ? null : sysParamEnum.getCode());
    }

    @Override
    public SysParameter getByCode(String code) {
        SysParameter sysParameter = SystemRedisUtils.getSysParameter(code);
        if (sysParameter == null) {
            // 写入缓存,读主库
            sysParameter = this.baseMapper.selectByCode(code);
            SystemRedisUtils.addSysParameter(sysParameter);
        }
        return sysParameter;
    }

    @Override
    public SysParameter getById(Long id) {
        return this.baseMapper.selectById(id);
    }

    @Override
    public void updateSysParameter(SysParameter parameter) {
        SysParameter origin = this.getById(parameter.getParamId());
        if (origin == null) {
            return;
        }
        //更新数据库
        UpdateWrapper<SysParameter> updateWrapper = new UpdateWrapper<>();
        updateWrapper.lambda().set(StringUtils.isNotBlank(parameter.getParamCode()), SysParameter::getParamCode, parameter.getParamCode())
                .set(StringUtils.isNotBlank(parameter.getParamName()), SysParameter::getParamName, parameter.getParamName())
                .set(StringUtils.isNotBlank(parameter.getParamValue()), SysParameter::getParamValue, parameter.getParamValue())
                .set(parameter.getParamStatus() != null, SysParameter::getParamStatus, parameter.getParamStatus())
                .set(StringUtils.isNotBlank(parameter.getRemark()), SysParameter::getRemark, parameter.getRemark())
                .eq(SysParameter::getParamId, parameter.getParamId());
        this.update(updateWrapper);

        //删除以前的缓存
        SystemRedisUtils.deleteSysParameter(origin.getParamCode());
    }

    @Override
    public List<SysParameterListVO> getList(String paramCode) {
        List<SysParameterListVO> voList = new ArrayList<>();

        QueryWrapper<SysParameter> queryWrapper = new QueryWrapper<>();
        if (StringUtils.isNotBlank(paramCode)) {
            queryWrapper.lambda().eq(SysParameter::getParamCode, paramCode);
        }
        queryWrapper.lambda().eq(SysParameter::getIsDelete, false);

        List<SysParameter> list = this.list(queryWrapper);
        if (CollectionUtils.isNotEmpty(list)) {
            voList = list.stream().map(a -> {
                SysParameterListVO vo = new SysParameterListVO();
                BeanUtil.copyProperties(a, vo);
                return vo;
            }).collect(Collectors.toList());
        }
        return voList;
    }
    
    @Override
    public List<SysParameterListVO> getListByType(String type) {
    	QueryWrapper<SysParameter> queryWrapper = new QueryWrapper<>();
    	if (StringUtils.isNotBlank(type)) {
    		queryWrapper.lambda().eq(SysParameter::getParamType, type);
    	}
    	queryWrapper.lambda().eq(SysParameter::getIsDelete, false);
    	
    	List<SysParameter> list = this.list(queryWrapper);
    	
    	return BeanCopyUtil.copyCollection(list, SysParameterListVO.class);
    }

    @Override
    public void updateDeleteStatus(Long id) {
        SysParameter origin = this.getById(id);
        if (origin == null) {
            return;
        }
        //更新数据库
        UpdateWrapper<SysParameter> updateWrapper = new UpdateWrapper<>();
        updateWrapper.lambda().set(SysParameter::getIsDelete, true)
                .eq(SysParameter::getParamId, id);
        this.baseMapper.update(null, updateWrapper);
        //删除以前的缓存
        SystemRedisUtils.deleteSysParameter(origin.getParamCode());
    }


}
