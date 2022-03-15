package com.onelive.common.base;


import com.onelive.common.constants.business.LoginConstants;
import com.onelive.common.constants.other.HeaderConstants;
import com.onelive.common.model.dto.login.AppLoginUser;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * @ClassName : BaseController
 * @Description : 基本controller类
 * @Author : harden
 * @Date: 2021-10-1411:18
 */
@Controller
@Slf4j
public class BaseController {
    @Resource
    public HttpServletRequest request;

    /**
     * 获取APP用户登录信息
     *
     * @return
     */
    public AppLoginUser getLoginUserAPP() {
        AppLoginUser appLoginUser = (AppLoginUser) request.getSession().getAttribute(LoginConstants.APP_LOGIN_INFO);
//        if (appLoginUser == null && StringUtils.isNotBlank(getToken())) {
//            appLoginUser = ApiBusinessRedisUtils.getLoginUserByToken(getToken());
//        }
        return appLoginUser;
    }

    /**
     * 获取token值
     *
     * @return
     */
    public String getToken() {
        return request.getHeader(HeaderConstants.AUTHORIZATION);
    }


}
