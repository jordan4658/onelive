package com.onelive.manage.modules.permission.business;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageInfo;
import com.onelive.common.constants.business.CommonConstants;
import com.onelive.common.exception.BusinessException;
import com.onelive.common.model.dto.login.LoginUser;
import com.onelive.common.model.dto.sys.SysFunctionByRollAllDTO;
import com.onelive.common.model.dto.sys.SysFunctionDTO;
import com.onelive.common.model.dto.sys.SysUserDTO;
import com.onelive.common.model.dto.sys.SysUserRoleDTO;
import com.onelive.common.model.req.sys.*;

import com.onelive.common.model.vo.sys.*;
import com.onelive.common.mybatis.entity.*;
import com.onelive.common.utils.others.PageInfoUtil;
import com.onelive.manage.service.sys.*;
import com.onelive.manage.utils.other.SysFunctionUtil;
import com.onelive.manage.utils.redis.SystemRedisUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author lorenzo
 * @Description: 权限业务类
 * @date 2021/3/31
 */
@Component
@Slf4j
public class PermissionBusiness {

    @Resource
    private SysUserService sysUserService;

    @Resource
    private SysUserRoleService sysUserRoleService;

    @Resource
    private SysRoleService sysRoleService;

    @Resource
    private SysFunctionService sysFunctionService;

    @Resource
    private SysRoleFuncService sysRoleFuncService;

    /**
     * 保存系统用户
     *
     * @param req
     * @param admin
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public void saveSysUser(SysUserReq req, LoginUser admin) {
        if (StrUtil.isBlank(req.getAccLogin()) || StrUtil.isBlank(req.getPhone())
                || StrUtil.isBlank(req.getPassword()) || StrUtil.isBlank(req.getUserName())
                || StrUtil.isBlank(req.getCountryCode())
        ) {
            throw new BusinessException("账号,手机号,密码,昵称,国家不能为空");
        }
        // 账号判断重复
        SysUser sysUser = sysUserService.selectByAccLogin(req.getAccLogin());
        if (sysUser != null) {
            throw new BusinessException("105", "账号重复");
        }
        // 手机号判断重复
        SysUser byPhoneNo = sysUserService.selectByPhoneNo(req.getPhone());
        if (byPhoneNo != null) {
            throw new BusinessException("109", "手机号重复");
        }

        SysUser user = new SysUser();
        BeanUtil.copyProperties(req, user);
        user.setCreateUser(admin.getAccLogin());
        user.setAccStatus(CommonConstants.STATUS_YES);
        user.setPasswordMd5(req.getPassword());
        sysUserService.save(user);
        // 新增角色权限
        if (req.getRoleId() != null) {
            SysUserRole sur = new SysUserRole();
            sur.setUserId(user.getUserId());
            sur.setRoleId(req.getRoleId());
            sur.setCreateUser(admin.getAccLogin());
            sysUserRoleService.save(sur);
        }
    }

    /**
     * 修改系统用户
     *
     * @param req
     * @param admin
     */
    @Transactional(rollbackFor = Exception.class)
    public void updateSysUser(SysUserUpdateReq req, LoginUser admin) {
        SysUser sysUser = sysUserService.getById(req.getUserId());
        if (sysUser == null) {
            throw new BusinessException("102", "用户不存在");
        }

        /*if (StrUtil.isNotBlank(req.getPhone())) {
            SysUser byPhoneNo = sysUserService.selectByPhoneNo(req.getPhone());
            if (byPhoneNo != null && byPhoneNo.getUserId().equals(sysUser.getUserId())) {
                throw new BusinessException("109", "手机号重复");
            }
        }*/
        SysUser update = new SysUser();
        BeanUtil.copyProperties(req, update);
        sysUserService.updateById(update);

        // 更新角色
        sysUserRoleService.delUserRoleByUserId(sysUser.getUserId());
        if (req.getRoleId() != null) {
            SysUserRole sur = new SysUserRole();
            sur.setUserId(sysUser.getUserId());
            sur.setRoleId(req.getRoleId());
            sur.setCreateUser(admin.getAccLogin());
            sysUserRoleService.save(sur);
        }

    }

    /**
     * 删除用户
     * @param userId
     * @param admin
     */
    public void deleteSysUser(Long userId, LoginUser admin) {
        SysUser user = new SysUser();
        user.setUserId(userId);
        user.setIsDelete(true);
        sysUserService.updateById(user);
        // 删除用户与角色的关系
        sysUserRoleService.delUserRoleByUserId(userId);
        log.info("删除用户操作人: {}", admin);
    }

    /**
     * 查询系统列表分页
     *
     * @param query
     * @return
     */
    public PageInfo<SysUserListVO> getSysUserList(SysUserQueryReq query) {
        PageInfo<SysUserDTO> userDtoList = sysUserService.getUserDtoList(query);
        return PageInfoUtil.pageInfo2DTO(userDtoList, SysUserListVO.class);
    }

    /**
     * 查询系统用户详细信息
     *
     * @param userId
     * @param admin
     * @return
     */
    public SysUserDetailVO getSysUserDetail(Long userId, LoginUser admin) {
        if (userId == null) {
            throw new BusinessException("101", "唯一标识为空");
        }
        SysUser sysUser = sysUserService.getById(userId);
        if (sysUser == null) {
            throw new BusinessException("102", "用户不存在");
        }
        SysUserDetailVO detailVO = new SysUserDetailVO();
        BeanUtil.copyProperties(sysUser, detailVO);

        // 查询是否存在角色
        SysRole role = sysRoleService.getRoleByUserId(userId);

        if (role != null && CommonConstants.STATUS_YES.equals(role.getRoleStatus())) {
            detailVO.setRoleId(role.getRoleId());
        }

        return detailVO;
    }

    /**
     * 修改状态
     *
     * @param req
     * @param admin
     */
    public void updateStatus(SysUserStatusReq req, LoginUser admin) {
        SysUser byId = sysUserService.getById(req.getUserId());
        if (byId == null) {
            throw new BusinessException("101", "用户不存在");
        }
        SysUser update = new SysUser();
        update.setUserId(req.getUserId());
        update.setAccStatus(req.getStatus());
        update.setUpdateUser(admin.getAccLogin());
        sysUserService.updateById(update);
    }

    /**
     * 获取角色所属的用户列表
     *
     * @param roleId
     * @return
     */
    public SysRoleUserListVO getRoleUserList(Long roleId) {
        List<SysUserRoleDTO> userRoleList = sysRoleService.getUserRoleList();
        SysRoleUserListVO vo = new SysRoleUserListVO();
        if (roleId == null) {
            return vo;
        }
        for (SysUserRoleDTO dto : userRoleList) {
            SysUserRoleVO item = new SysUserRoleVO();
            BeanUtil.copyProperties(dto, item);
            if (roleId.equals(dto.getRoleId())) {
                vo.getOn().add(item);
            } else if (dto.getRoleId() == null){
                vo.getOut().add(item);
            }
        }
        return vo;
    }

    /**
     * 新增角色
     *
     * @param req
     * @param admin
     */
    public void saveRole(SysRoleReq req, LoginUser admin) {
        if (StrUtil.isBlank(req.getRoleName())) {
            throw new BusinessException("101", "角色名称为空");
        }
        // 检查角色名是否重复
        SysRole roleByName = sysRoleService.getRoleByName(req.getRoleName());
        if (roleByName != null) {
            throw new BusinessException("102", "角色名称重复");
        }
        SysRole role = new SysRole();
        role.setRoleStatus(CommonConstants.STATUS_YES);
        BeanUtil.copyProperties(req, role);

        sysRoleService.save(role);
    }

    /**
     * 编辑角色
     *
     * @param req
     * @param admin
     */
    public void updateRole(SysRoleUpdateReq req, LoginUser admin) {
        if (req.getRoleId() == null) {
            throw new BusinessException("101", "角色id不能为空");
        }
        if (StrUtil.isNotBlank(req.getRoleName())) {
            // 检查角色名是否重复
            SysRole roleByName = sysRoleService.getRoleByName(req.getRoleName());
            if (roleByName != null && roleByName.getRoleId() != req.getRoleId()) {
                throw new BusinessException("102", "角色名称重复");
            }
        }
        SysRole update = new SysRole();
        BeanUtil.copyProperties(req, update);
        sysRoleService.updateById(update);
    }

    /**
     * 删除角色
     * @param roleId
     * @param admin
     */
    @Transactional
    public void deleteRole(Long roleId, LoginUser admin) {
        if (roleId == null) {
            throw new BusinessException("101", "角色ID不能為空");
        }
        // 角色更改之后需要及时更新角色与功能ID列表缓存
        SystemRedisUtils.delRoleFunIdList(roleId);
        // 删除角色与用户的关系
        sysUserRoleService.deleteRoleRef(roleId);
        log.info("删除角色操作人: {}", JSON.toJSONString(admin));
        // 删除角色
        sysRoleService.removeById(roleId);
    }

    /**
     * 角色列表分页
     *
     * @param req
     * @return
     */
    public PageInfo<SysRoleListVO> getRoleList(SysRoleQueryReq req) {
        PageInfo<SysRole> roleList = sysRoleService.getRoleList(req);
        return PageInfoUtil.pageInfo2DTO(roleList, SysRoleListVO.class);
    }

    /**
     * 查询角色模块详情
     *
     * @param roleId
     * @return
     */
    public SysRoleDetailVO detailRoleFunction(Long roleId) {
        SysRole role = sysRoleService.getById(roleId);
        if (role == null) {
            return null;
        }
        List<SysFunctionByRollAllDTO> sysFunctionTreeByRoleAll = sysFunctionService.getSysFunctionTreeByRoleAll(roleId);
        List<SysRoleFunctionVO> collect = sysFunctionTreeByRoleAll.stream().map(br -> {
            SysRoleFunctionVO vo = new SysRoleFunctionVO();
            BeanUtil.copyProperties(br, vo);
            return vo;
        }).collect(Collectors.toList());

        SysRoleDetailVO vo = new SysRoleDetailVO();
        vo.setRoleId(roleId);
        vo.setRoleName(role.getRoleName());
        vo.setFunctionList(SysFunctionUtil.roleFuncListToTree(collect));
        return vo;
    }

    /**
     * 设置角色权限
     *
     * @param req
     * @param admin
     */
    public void setRoleFunction(RoleFunctionReq req, LoginUser admin) {
        if (req.getRoleId() == null || req.getFunIdList().isEmpty()) {
            throw new BusinessException("角色为空或者没有选择功能");
        }
        sysRoleFuncService.delRoleFunction(req.getRoleId());
        List<SysRoleFunc> sysRoleFuncs = req.getFunIdList().stream().map(id -> {
            SysRoleFunc sfc = new SysRoleFunc();
            sfc.setRoleId(req.getRoleId());
            sfc.setIsDelete(false);
            sfc.setCreateUser(admin.getAccLogin());
            sfc.setFuncId(id);
            return sfc;
        }).collect(Collectors.toList());
        for (SysRoleFunc func : sysRoleFuncs) {
            sysRoleFuncService.save(func);
        }
        //MysqlMethod.batchInsert(SysRoleFunc.class, sysRoleFuncs);
        //sysRoleFuncService.saveBatch(sysRoleFuncs);
    }

    /**
     * 新增用户与角色的关系
     *
     * @param req
     * @param admin
     */
    public void addUserRole(SysUserRoleReq req, LoginUser admin) {
        // 检查是否重复
        SysRole roleByUserId = sysRoleService.getRoleByUserId(req.getUserId());
        if (roleByUserId != null) {
            throw new BusinessException("103", "该用户存在角色");
        }
        // 新增用户与角色的关系
        SysUserRole sur = new SysUserRole();
        sur.setCreateUser(admin.getAccLogin());
        sur.setUserId(req.getUserId());
        sur.setRoleId(req.getRoleId());
        sysUserRoleService.save(sur);
    }

    /**
     * 删除用户与角色的关系
     *
     * @param userId
     * @param admin
     */
    public void delUserRole(Long userId, LoginUser admin) {
        sysUserRoleService.delUserRoleByUserId(userId);
    }

    /**
     * 获取模块功能列表
     *
     * @return
     */
    public List<SysFunctionVO> getSysFunctionTree() {
        List<SysFunction> sysFunctionTree = sysFunctionService.getSysFunctionTree();
        List<SysFunctionVO> collect = sysFunctionTree.stream().map(sf -> {
            SysFunctionVO vo = new SysFunctionVO();
            BeanUtil.copyProperties(sf, vo);
            return vo;
        }).collect(Collectors.toList());
        // List转树装结构逻辑
        return SysFunctionUtil.listToTree(collect);
    }

    /**
     * 获取功能模块选择树
     *
     * @param funId
     * @return
     */
    public List<SysFunctionVO> getSelectFunctionTree(Long funId) {
        SysFunction byId = sysFunctionService.getById(funId);
        if (byId == null) {
            return null;
        }
        List<SysFunctionDTO> sysFunctionTreeByFunId = sysFunctionService.getSysFunctionTreeByFunId(funId);
        List<SysFunctionVO> collect = sysFunctionTreeByFunId.stream().map(dto -> {
            SysFunctionVO vo = new SysFunctionVO();
            BeanUtil.copyProperties(dto, vo);
            return vo;
        }).collect(Collectors.toList());
        // List转树装结构逻辑
        return SysFunctionUtil.listToTree(collect);
    }

    /**
     * 功能新增
     *
     * @param req
     * @param admin
     */
    public void saveFunction(SysFunctionReq req, LoginUser admin) {
        if (StrUtil.isBlank(req.getFuncName())) {
            throw new BusinessException("101", "模块名称为空");
        }
        if (StrUtil.isBlank(req.getOfSystem())) {
            throw new BusinessException("102", "所属系统为空");
        }
        if (StrUtil.isBlank(req.getFuncType())) {
            throw new BusinessException("103", "模块类型为空");
        }
        if (StrUtil.isBlank(req.getFuncUrl())) {
            throw new BusinessException("104", "功能/url为空");
        }
        // 检查是否重名
        SysFunction sysFunction = sysFunctionService.selectByFunctionName(req.getFuncName());
        if (sysFunction != null) {
            throw new BusinessException("105", "名称重复");
        }
        SysFunction sf = new SysFunction();
        BeanUtil.copyProperties(req, sf);
        sf.setFuncStatus(CommonConstants.STATUS_YES);
        sysFunctionService.save(sf);
    }

    /**
     * 修改功能
     *
     * @param req
     * @param admin
     */
    public void updateFunction(SysFunctionUpdateReq req, LoginUser admin) {
        if (req.getFuncId() == null) {
            throw new BusinessException("101", "功能模块id为空");
        }

        if (req.getParentFuncId() != null) {
            if (req.getParentFuncId().equals(req.getFuncId())) {
                throw new BusinessException("1110", "父节点不能是自己");
            }
        }

        // 检查是否重名
        SysFunction sysFunction = sysFunctionService.selectByFunctionName(req.getFuncName());
        if (sysFunction != null) {
            throw new BusinessException("105", "名称重复");
        }

        SysFunction sf = new SysFunction();
        BeanUtil.copyProperties(req, sf);
//        sf.setUpdateUser(admin.getAccLogin());
        sysFunctionService.updateById(sf);
    }

    /**
     * 切换功能状态
     *
     * @param req
     * @param admin
     */
    public void switchFunctionStatus(SysFunctionStatusReq req, LoginUser admin) {
        if (req.getFuncId() == null) {
            throw new BusinessException(101, "功能模块id为空");
        }
        SysFunction sf = new SysFunction();
        BeanUtil.copyProperties(req, sf);
        sf.setUpdateUser(admin.getAccLogin());
        sysFunctionService.updateById(sf);
    }

    /**
     * 删除功能
     *
     * @param funcId
     * @param admin
     */
    public void delFunction(Long funcId, LoginUser admin) {
        SysFunction sf = new SysFunction();
        sf.setFuncId(funcId);
        sf.setIsDelete(true);
//        sf.setUpdateUser(admin.getAccLogin());
        sysFunctionService.updateById(sf);
    }

}
