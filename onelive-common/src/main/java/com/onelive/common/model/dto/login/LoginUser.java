package com.onelive.common.model.dto.login;


import com.onelive.common.model.vo.sys.SysFunctionVO;
import lombok.Data;

import java.util.List;


/**
 * @ClassName LoginUser
 * @Desc 后台登录人基本信息，
 * @Date 2021/3/16 10:26
 */
@Data
public class LoginUser {
    /**
     * 登录人token
     */
    private String accToken;
    /**
     * 用户id
     */
    private Long userId;
    /**
     * 角色id
     */
    private Long roleId;
    /**
     * 角色名称
     */
    private String roleName;
    /**
     * 拥有方法
     */
    private List<SysFunctionVO> functions;

    /**
     * 账号名称
     */
    private String accLogin;

    /**
     * 国家sys_country.country_code
     */
    private String countryCode;

}    
    