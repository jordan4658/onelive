package com.onelive.manage.service.login.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.onelive.common.constants.business.CommonConstants;
import com.onelive.common.exception.BusinessException;
import com.onelive.common.model.dto.login.LoginUser;
import com.onelive.common.model.dto.sys.SysFunctionForRoleDTO;
import com.onelive.common.model.vo.sys.SysFunctionVO;
import com.onelive.common.mybatis.entity.SysFunction;
import com.onelive.common.mybatis.entity.SysRole;
import com.onelive.common.mybatis.entity.SysUser;
import com.onelive.manage.service.login.LoginService;
import com.onelive.manage.service.sys.*;
import com.onelive.manage.utils.other.SysFunctionUtil;
import com.onelive.manage.utils.redis.SystemRedisUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class LoginServiceImpl implements LoginService {


    @Resource
    private SysRoleFuncService sysRolefuncService;

    @Resource
    private SysRoleService sysRoleInfoService;

    @Resource
    private SysFunctionService sysFunctionService;

    @Resource
    private SysFuncInterfaceService sysFuncinterfaceService;

    @Resource
    private SysParameterService sysParamService;


    @Value("${userSessionKey:}")
    protected String userSessionKey;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public LoginUser doLogin(SysUser sysUser) {
        
        LoginUser loginUser = new LoginUser();
        BeanUtils.copyProperties(sysUser, loginUser);

        // 返回后台登录角色权限 把角色 存入对象
        SysRole sysRoleInfo = sysRoleInfoService.getRoleByUserId(sysUser.getUserId());
        if (sysRoleInfo == null || sysRoleInfo.getRoleStatus().equals(CommonConstants.STATUS_NO)) {
            throw new BusinessException("无角色权限，不能访问该系统");
        }

        loginUser.setRoleId(sysRoleInfo.getRoleId());

        loginUser.setRoleName(sysRoleInfo.getRoleName());

        String acctoken = SystemRedisUtils.createToken(userSessionKey, loginUser, sysParamService);

        if (StrUtil.isBlank(acctoken)) {
            throw new BusinessException("系统异常，请稍后重试");
        }

        loginUser.setAccToken(acctoken);
        //admin账号默认全部权限
        if("admin".equalsIgnoreCase(sysUser.getAccLogin())) {
        	 loginUser.setFunctions(getAllFunctions());
        }else {
        	loginUser.setFunctions(getRoleFunctions(sysRoleInfo.getRoleId()));
		}
        loginUser.setCountryCode(sysUser.getCountryCode());
        
        return loginUser;
    }

    /** 根据角色id获取功能菜单树 */
    private List<SysFunctionVO> getRoleFunctions(Long roleId) {
        if (roleId == null) {
            return Collections.emptyList();
        }
        List<SysFunctionForRoleDTO> roleFunctionList = this.getSysFunctionForRole(roleId);
        if (CollectionUtils.isNotEmpty(roleFunctionList)) {

            // 接口权限缓存(暂时用不到)
            /*List<Long> functionIdList = roleFunctionList.stream().map(SysFunction::getFuncId).filter(Objects::nonNull).collect(Collectors.toList());
            setRoleInterfaceCache(sysRoleInfo.getRoleId(), functionIdList);*/

            /* dto转vo */
            List<SysFunctionVO> collect = roleFunctionList.stream().map(o -> {
                SysFunctionVO df = new SysFunctionVO();
                df.setParentFuncId(o.getParentFuncId());
                df.setFuncId(o.getFuncId());
                df.setFuncName(o.getFuncName());
                df.setFuncType(o.getFuncType());
                df.setFuncUrl(o.getFuncUrl());
                return df;
            }).collect(Collectors.toList());

            /* list转树装结构 */
            return SysFunctionUtil.listToTree(collect);
        }
        return Collections.emptyList();
    }
    
    /**
     * 获取所有菜单（超级管理员用）
     */
    private  List<SysFunctionVO> getAllFunctions() {
    	List<SysFunction> funs =  sysFunctionService.getSysFunctionTree();
    	List<SysFunctionVO> collect = funs.stream().map(o -> {
            SysFunctionVO df = new SysFunctionVO();
            df.setParentFuncId(o.getParentFuncId());
            df.setFuncId(o.getFuncId());
            df.setFuncName(o.getFuncName());
            df.setFuncType(o.getFuncType());
            df.setFuncUrl(o.getFuncUrl());
            return df;
        }).collect(Collectors.toList());

        /* list转树装结构 */
        return SysFunctionUtil.listToTree(collect);
    }

    /** 获取角色对应的功能列表 */
    private List<SysFunctionForRoleDTO> getSysFunctionForRole(Long roleId) {
        // 获取角色 对应 的 节点
        List<Long> roleFunIdList = sysRolefuncService.getRoleFunIdList(roleId);
        // 存在 就向上 查询 每个 节点 的 父节点
        /*Set<Long> set = new HashSet<>();
        for (Long funId : roleFunIdList) {
            List<Long> parentFunIdListNode = sysFunctionService.getParentFunIdListNode(funId);
            set.addAll(parentFunIdListNode);
        }*/
        // 再查询出所有的 节点
        // List<Long> allFunId = new ArrayList<>(set);
        List<SysFunctionForRoleDTO> dataList = sysFunctionService.getSysFunctionAllList(roleFunIdList);

        dataList.forEach(o -> {
            o.setCheckbox(1);
            o.setRoleId(roleId);
        });
        return dataList;
    }

    // 接口权限缓存逻辑
    private void setRoleInterfaceCache(Long roleId, List<Long> functionIdList) {
        if (!SystemRedisUtils.hasRoleInterface(roleId)) {
            // 设置角色 对于的 模块 对应的 接口

            List<String> interfaceUrls = sysFuncinterfaceService.getInterfaceUrlsByRole(functionIdList);
            SystemRedisUtils.setRoleInterface(roleId, interfaceUrls);
        } else {
            List<String> roleInterface = SystemRedisUtils.getRoleInterface(roleId);
            if (roleInterface == null || roleInterface.isEmpty()) {
                // 设置角色 对于的 模块 对于的 接口

                List<String> interfaceUrls = sysFuncinterfaceService.getInterfaceUrlsByRole(functionIdList);
                SystemRedisUtils.setRoleInterface(roleId, interfaceUrls);
            }
        }
    }

}
