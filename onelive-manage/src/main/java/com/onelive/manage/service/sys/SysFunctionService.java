package com.onelive.manage.service.sys;


import com.baomidou.mybatisplus.extension.service.IService;
import com.onelive.common.model.dto.sys.SysFunctionByRollAllDTO;
import com.onelive.common.model.dto.sys.SysFunctionDTO;
import com.onelive.common.model.dto.sys.SysFunctionForRoleDTO;
import com.onelive.common.mybatis.entity.SysFunction;

import java.util.List;

/**
 * <p>
 * 后台系统(运营后台)功能 服务类
 * </p>
 *
 * @author
 * @since 2021-03-30
 */
public interface SysFunctionService extends IService<SysFunction> {

    /**
     * 向上 查询 每个 节点 的 父节点
     *
     * @param funcId
     * @return
     */
    List<Long> getParentFunIdListNode(Long funcId);

    /**
     * 获取权限列表
     *
     * @param allFuncId
     * @return
     */
    List<SysFunctionForRoleDTO> getSysFunctionAllList(List<Long> allFuncId);

    /**
     * 获取角色的权限列表树
     *
     * @param roleId
     * @return
     */
    List<SysFunctionByRollAllDTO> getSysFunctionTreeByRoleAll(Long roleId);


    /**
     * 权限功能树
     *
     * @param funId
     * @return
     */
    List<SysFunctionDTO> getSysFunctionTreeByFunId(Long funId);

    /**
     * 权限列表树
     *
     * @return
     */
    List<SysFunction> getSysFunctionTree();

    /**
     * 按照功能名称查询
     *
     * @param funcName
     * @return
     */
    SysFunction selectByFunctionName(String funcName);
}
