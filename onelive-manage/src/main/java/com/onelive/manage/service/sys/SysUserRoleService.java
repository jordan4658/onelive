package com.onelive.manage.service.sys;


import com.baomidou.mybatisplus.extension.service.IService;
import com.onelive.common.mybatis.entity.SysUserRole;

/**
 * <p>
 * 后台用户角色关系 服务类
 * </p>
 *
 * @author
 * @since 2021-03-30
 */
public interface SysUserRoleService extends IService<SysUserRole> {

    void delUserRoleByUserId(Long userId);

    /**
     * 删除角色与用户的关系
     * @param roleId
     */
    void deleteRoleRef(Long roleId);

}
