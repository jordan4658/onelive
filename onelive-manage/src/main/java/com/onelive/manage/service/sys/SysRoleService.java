package com.onelive.manage.service.sys;


import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.PageInfo;
import com.onelive.common.model.dto.sys.SysUserRoleDTO;
import com.onelive.common.model.req.sys.SysRoleQueryReq;
import com.onelive.common.mybatis.entity.SysRole;

import java.util.List;

/**
 * <p>
 * 后台系统角色 服务类
 * </p>
 *
 * @author
 * @since 2021-03-30
 */
public interface SysRoleService extends IService<SysRole> {

    SysRole getRoleByUserId(Long userId);

    SysRole getRoleByName(String name);

    PageInfo<SysRole> getRoleList(SysRoleQueryReq req);

    List<SysUserRoleDTO> getUserRoleList();
}
