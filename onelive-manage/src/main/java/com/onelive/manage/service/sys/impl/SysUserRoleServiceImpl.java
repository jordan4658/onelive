package com.onelive.manage.service.sys.impl;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.onelive.common.mybatis.entity.SysUserRole;
import com.onelive.common.mybatis.mapper.master.sys.SysUserRoleMapper;
import com.onelive.manage.service.sys.SysUserRoleService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * <p>
 * 后台用户角色关系 服务实现类
 * </p>
 *
 * @author
 * @since 2021-03-30
 */
@Service
public class SysUserRoleServiceImpl extends ServiceImpl<SysUserRoleMapper, SysUserRole> implements SysUserRoleService {

    @Resource
    private SysUserRoleMapper sysUserRoleMapper;

    @Override
    public void delUserRoleByUserId(Long userId) {
        sysUserRoleMapper.update(null, Wrappers.<SysUserRole>lambdaUpdate()
                .set(SysUserRole::getIsDelete, true)
                .eq(SysUserRole::getUserId, userId));
    }

    @Override
    public void deleteRoleRef(Long roleId) {
        LambdaUpdateWrapper<SysUserRole> updateWrapper = new UpdateWrapper<SysUserRole>().lambda()
                .set(SysUserRole::getIsDelete, true)
                .eq(SysUserRole::getRoleId, roleId);
        sysUserRoleMapper.update(null, updateWrapper);
    }
}
