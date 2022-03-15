package com.onelive.manage.service.sys;


import com.baomidou.mybatisplus.extension.service.IService;
import com.onelive.common.mybatis.entity.SysRoleFunc;

import java.util.List;

/**
 * <p>
 * 角色拥有功能 服务类
 * </p>
 *
 * @author
 * @since 2021-03-30
 */
public interface SysRoleFuncService extends IService<SysRoleFunc> {

    /**
     * 获取角色 对应 的 节点
     *
     * @param roleId
     * @return
     */
    List<Long> getRoleFunIdList(Long roleId);

    /**
     * 删除角色对应关系
     *
     * @param roleId
     */
    void delRoleFunction(Long roleId);
}
