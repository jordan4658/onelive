package com.onelive.manage.service.sys.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.onelive.common.model.dto.sys.SysFunctionByRollAllDTO;
import com.onelive.common.model.dto.sys.SysFunctionDTO;
import com.onelive.common.model.dto.sys.SysFunctionForRoleDTO;
import com.onelive.common.mybatis.entity.SysFunction;
import com.onelive.common.mybatis.mapper.master.sys.SysFunctionMapper;
import com.onelive.common.mybatis.mapper.slave.sys.SlaveSysFunctionMapper;
import com.onelive.manage.service.sys.SysFunctionService;
import com.onelive.manage.utils.redis.SystemRedisUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 后台系统(运营后台)功能 服务实现类
 * </p>
 *
 * @author
 * @since 2021-03-30
 */
@Service
public class SysFunctionServiceImpl extends ServiceImpl<SysFunctionMapper, SysFunction> implements SysFunctionService {

    @Resource
    private SlaveSysFunctionMapper slaveSysFunctionMapper;

    @Override
    public List<Long> getParentFunIdListNode(Long funcId) {
        List<Long> parentFuncIdListNode = SystemRedisUtils.getParentFuncIdListNode(funcId);
        if (parentFuncIdListNode == null) {
            parentFuncIdListNode = slaveSysFunctionMapper.getParentFuncIdListNode(funcId);
            SystemRedisUtils.setParentFuncIdListNode(funcId, parentFuncIdListNode);
        }
        return parentFuncIdListNode;
    }

    @Override
    public List<SysFunctionForRoleDTO> getSysFunctionAllList(List<Long> allFuncId) {
        return slaveSysFunctionMapper.getSysFunctionList(allFuncId);
    }

    @Override
    public List<SysFunctionByRollAllDTO> getSysFunctionTreeByRoleAll(Long roleId) {
        return slaveSysFunctionMapper.getSysFunctionTreeByRoleAll(roleId);
    }

    @Override
    public List<SysFunctionDTO> getSysFunctionTreeByFunId(Long funId) {
        return slaveSysFunctionMapper.getSysFunctionTreeByFunId(funId);
    }

    @Override
    public List<SysFunction> getSysFunctionTree() {
        return slaveSysFunctionMapper.selectList(
                Wrappers.<SysFunction>lambdaQuery()
                        .eq(SysFunction::getIsDelete, false).orderByAsc(SysFunction::getOrderSeq)
        );
    }

    @Override
    public SysFunction selectByFunctionName(String funcName) {
        return slaveSysFunctionMapper.selectOne(
                Wrappers.<SysFunction>lambdaQuery()
                        .eq(SysFunction::getFuncName, funcName)
                        .eq(SysFunction::getIsDelete, false)
        );
    }
}
