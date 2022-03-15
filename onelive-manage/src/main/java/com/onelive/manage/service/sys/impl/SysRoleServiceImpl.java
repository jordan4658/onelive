package com.onelive.manage.service.sys.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.onelive.common.model.dto.sys.SysUserRoleDTO;
import com.onelive.common.model.req.sys.SysRoleQueryReq;
import com.onelive.common.mybatis.entity.SysRole;
import com.onelive.common.mybatis.mapper.master.sys.SysRoleMapper;
import com.onelive.common.mybatis.mapper.slave.sys.SlaveSysRoleMapper;
import com.onelive.manage.service.sys.SysRoleService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 后台系统角色 服务实现类
 * </p>
 *
 * @author
 * @since 2021-03-30
 */
@Service
public class SysRoleServiceImpl extends ServiceImpl<SysRoleMapper, SysRole> implements SysRoleService {

    @Resource
    private SlaveSysRoleMapper slaveSysRoleMapper;

    @Override
    public SysRole getRoleByUserId(Long userId) {

        return slaveSysRoleMapper.getRoleByUserId(userId);
    }

    @Override
    public SysRole getRoleByName(String name) {
        LambdaQueryWrapper<SysRole> queryWrapper = Wrappers.<SysRole>lambdaQuery().eq(SysRole::getRoleName, name).last("limit 1 ");
        return slaveSysRoleMapper.selectOne(queryWrapper);
    }

    @Override
    public PageInfo<SysRole> getRoleList(SysRoleQueryReq req) {
        return PageHelper.startPage(req.getPageNum(), req.getPageSize()).doSelectPageInfo(() -> slaveSysRoleMapper.selectList(Wrappers.<SysRole>lambdaQuery()
                .like(StrUtil.isNotBlank(req.getRoleName()), SysRole::getRoleName, req.getRoleName())));
        /*PageHelper.startPage(req.getPageNum(), req.getPageSize());

        List<SysRole> sysRoles = getBaseMapper().selectList(Wrappers.<SysRole>lambdaQuery()
                .eq(StrUtil.isNotBlank(req.getRoleName()), SysRole::getRoleName, req.getRoleName()));
        return new PageInfo<>(sysRoles);*/
    }

    @Override
    public List<SysUserRoleDTO> getUserRoleList() {
        return slaveSysRoleMapper.getUserRoleList();
    }
}
