package com.onelive.manage.service.sys.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.onelive.common.mybatis.entity.SysRoleFunc;
import com.onelive.common.mybatis.mapper.master.sys.SysRoleFuncMapper;
import com.onelive.common.mybatis.mapper.slave.sys.SlaveSysRoleFuncMapper;
import com.onelive.manage.service.sys.SysRoleFuncService;
import com.onelive.manage.utils.redis.SystemRedisUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 角色拥有功能 服务实现类
 * </p>
 *
 * @author
 * @since 2021-03-30
 */
@Service
public class SysRoleFuncServiceImpl extends ServiceImpl<SysRoleFuncMapper, SysRoleFunc> implements SysRoleFuncService {

    @Resource
    private SlaveSysRoleFuncMapper slaveSysRoleFuncMapper;

    @Override
    public List<Long> getRoleFunIdList(Long roleId) {
        List<Long> roleFuncIdList = SystemRedisUtils.getRoleFunIdList(roleId);
        if (roleFuncIdList == null) {
            roleFuncIdList = slaveSysRoleFuncMapper.getRoleFuncIdList(roleId);
            SystemRedisUtils.setRoleFunIdList(roleId, roleFuncIdList);
        }
        return roleFuncIdList;
    }

    @Override
    public void delRoleFunction(Long roleId) {
        // 角色权限更改之后需要及时更新角色与功能ID列表缓存
        SystemRedisUtils.delRoleFunIdList(roleId);
        getBaseMapper().update(null, Wrappers.<SysRoleFunc>lambdaUpdate()
                .set(SysRoleFunc::getIsDelete, true)
                .eq(SysRoleFunc::getRoleId, roleId)
        );
    }
}
