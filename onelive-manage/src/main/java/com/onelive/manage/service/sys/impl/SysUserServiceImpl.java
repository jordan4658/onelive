package com.onelive.manage.service.sys.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.onelive.common.model.dto.sys.SysUserDTO;
import com.onelive.common.model.req.sys.SysUserQueryReq;
import com.onelive.common.mybatis.entity.SysUser;
import com.onelive.common.mybatis.mapper.master.sys.SysUserMapper;
import com.onelive.common.mybatis.mapper.slave.sys.SlaveSysUserMapper;
import com.onelive.manage.service.sys.SysUserService;
import com.onelive.manage.utils.redis.SystemRedisUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * <p>
 * 后台系统用户信息 服务实现类
 * </p>
 *
 * @author
 * @since 2021-03-30
 */
@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements SysUserService {

    @Resource
    private SlaveSysUserMapper slaveSysUserMapper;

    @Override
    public PageInfo<SysUserDTO> getUserDtoList(SysUserQueryReq query) {
        return PageHelper.startPage(query.getPageNum(), query.getPageSize()).doSelectPageInfo(() -> slaveSysUserMapper.getDtoList(query.getUserName()));
    }

    @Override
    public SysUser selectByAccLogin(String accLogin) {
        SysUser sysUser = SystemRedisUtils.getSysUserByAccLogin(accLogin);
        if (sysUser == null) {
            LambdaQueryWrapper<SysUser> queryWrapper = Wrappers.<SysUser>lambdaQuery()
                    .eq(SysUser::getAccLogin, accLogin)
                    .eq(SysUser::getIsDelete, false).last("limit 1 ");
            sysUser = slaveSysUserMapper.selectOne(queryWrapper);
            SystemRedisUtils.setSysUserByAccLogin(accLogin, sysUser);
        }
        return sysUser;
    }

    @Override
    public SysUser selectByPhoneNo(String phone) {
        SysUser sysUserByPhone = SystemRedisUtils.getSysUserByPhone(phone);
        if (sysUserByPhone == null) {
            LambdaQueryWrapper<SysUser> queryWrapper = Wrappers.<SysUser>lambdaQuery()
                    .eq(SysUser::getPhone, phone).eq(SysUser::getIsDelete, false).last("limit 1 ");
            sysUserByPhone = slaveSysUserMapper.selectOne(queryWrapper);
            SystemRedisUtils.setSysUserByPhone(phone, sysUserByPhone);
        }
        return sysUserByPhone;
    }
}
