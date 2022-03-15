package com.onelive.manage.service.sys;


import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.PageInfo;
import com.onelive.common.model.dto.sys.SysUserDTO;
import com.onelive.common.model.req.sys.SysUserQueryReq;
import com.onelive.common.mybatis.entity.SysUser;

/**
 * <p>
 * 后台系统用户信息 服务类
 * </p>
 *
 * @author
 * @since 2021-03-30
 */
public interface SysUserService extends IService<SysUser> {

    /**
     * 查询系统用户dto分页
     *
     * @param query
     * @return
     */
    PageInfo<SysUserDTO> getUserDtoList(SysUserQueryReq query);

    /**
     * 按照登录账号查询
     *
     * @param accLogin
     * @return
     */
    SysUser selectByAccLogin(String accLogin);

    /**
     * @param phone
     * @return
     */
    SysUser selectByPhoneNo(String phone);
}
