package com.onelive.manage.service.login;


import com.onelive.common.model.dto.login.LoginUser;
import com.onelive.common.mybatis.entity.SysUser;

public interface LoginService {

    LoginUser doLogin(SysUser login);

}
