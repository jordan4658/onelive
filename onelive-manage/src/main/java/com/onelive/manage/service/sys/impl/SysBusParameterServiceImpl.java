package com.onelive.manage.service.sys.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.onelive.common.model.req.sys.SysBusParameterQueryReq;
import com.onelive.common.mybatis.entity.SysBusParameter;
import com.onelive.common.mybatis.mapper.master.sys.SysBusParameterMapper;
import com.onelive.common.mybatis.mapper.slave.sys.SlaveSysBusParameterMapper;
import com.onelive.manage.service.sys.SysBusParameterService;
import com.onelive.manage.utils.redis.SysBusinessRedisUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

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

    /**
     * 根据参数code获取业务参数
     *
     * @param code
     * @return
     */
    @Override
    public SysBusParameter getByCode(String code) {
        SysBusParameter sysBusParameter = SysBusinessRedisUtils.getSysBusParameter(code);
        if (sysBusParameter == null) {
            LambdaQueryWrapper<SysBusParameter> queryWrapper = Wrappers.lambdaQuery(SysBusParameter.class)
                    .eq(SysBusParameter::getParamCode, code)
                    .eq(SysBusParameter::getIsDelete, false).last("limit 1 ");
            sysBusParameter = slaveSysBusParameterMapper.selectOne(queryWrapper);
            SysBusinessRedisUtils.setSysBusParameter(sysBusParameter);
        }
        return sysBusParameter;
    }

    /**
     * 根据父级系统参数获取业务参数列表
     *
     * @param pCode
     * @return
     */
    @Override
    public List<SysBusParameter> getChild(String pCode) {
        LambdaQueryWrapper<SysBusParameter> queryWrapper = Wrappers.lambdaQuery(SysBusParameter.class)
                .eq(SysBusParameter::getPParamCode, pCode);
        return slaveSysBusParameterMapper.selectList(queryWrapper);
    }

    /**
     * 系统参数分页查询
     *
     * @param param
     * @return
     */
    @Override
    public PageInfo<SysBusParameter> getList(SysBusParameterQueryReq param) {
        LambdaQueryWrapper<SysBusParameter> wrapper = Wrappers.<SysBusParameter>lambdaQuery()
                .eq(SysBusParameter::getIsDelete, false)
                .like(StrUtil.isNotBlank(param.getParamName()), SysBusParameter::getParamName, param.getParamName())
                .like(StrUtil.isNotBlank(param.getPParamCode()), SysBusParameter::getPParamCode, param.getPParamCode());

        return PageHelper.startPage(param.getPageNum(), param.getPageSize()).doSelectPageInfo(() -> slaveSysBusParameterMapper.selectList(wrapper));
    }

    @Override
    public void deleteParam(Long id, String account) {
        UpdateWrapper<SysBusParameter> wrapper = new UpdateWrapper<>();
        wrapper.lambda().set(SysBusParameter::getIsDelete, true)
                .set(SysBusParameter::getUpdateUser, account)
                .eq(SysBusParameter::getId, id);
        this.update(null, wrapper);
        //清除缓存
        this.delCache(id, null);
    }

    @Override
    public void saveParam(SysBusParameter p) {
        this.save(p);
        //清除缓存
        this.delCache(null, p);
    }

    @Override
    public void updateParam(SysBusParameter p) {
        boolean r = super.updateById(p);
        //清除缓存
        this.delCache(p.getId(), null);
    }

    /**
     * 清除redis缓存
     *
     * @param id
     */
    private void delCache(Long id, SysBusParameter busParameter) {
        if (id != null) {
            busParameter = this.getById(id);
        }
        String code = busParameter.getParamCode();
        String pCode = busParameter.getPParamCode();
        SysBusinessRedisUtils.delSysBusParameters(code, pCode);
    }



}
