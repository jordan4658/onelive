package com.onelive.manage.modules.login.business;

import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import com.onelive.common.constants.business.CommonConstants;
import com.onelive.common.exception.BusinessException;
import com.onelive.common.model.dto.login.LoginUser;
import com.onelive.common.model.req.login.BackLoginReq;
import com.onelive.common.mybatis.entity.SysLog;
import com.onelive.common.mybatis.entity.SysUser;
import com.onelive.manage.service.login.LoginService;
import com.onelive.manage.service.sys.SysLogService;
import com.onelive.manage.service.sys.SysUserService;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Date;

/**
 * @author lorenzo
 * @Description: 登录业务
 * @date 2021/3/30
 */
@Component
public class LoginBusiness {

    @Resource
    private LoginService loginService;
    @Resource
    private SysUserService sysUserService;
    @Resource
    private SysLogService sysLogService;
//    @Resource
//    private SysWhiteListService sysWhiteListService;

    public LoginUser doLogin(BackLoginReq req, String clintipadd, String serverIp, Long vercode) {
        if (StrUtil.isEmpty(req.getAccLogin())) {
            throw new BusinessException("账号不能为空");
        }
        if (StrUtil.isEmpty(req.getPassword())) {
            throw new BusinessException("密码不能为空");
        }

        SysUser login = this.sysUserService.selectByAccLogin(req.getAccLogin());
        if (null == login) {
            throw new BusinessException("账号或密码错误");
        }
        if (!req.getPassword().equals(login.getPassword())) {
            throw new BusinessException("账号或密码错误");
        }
        if (!req.getAccLogin().equals(login.getAccLogin())) {
            throw new BusinessException("账号或密码错误");
        }
        if (CommonConstants.STATUS_NO.equals(login.getAccStatus())) {
            throw new BusinessException("账号已禁用");
        }
//
//        //判断是否是ip白名单
//        if (!sysWhiteListService.checkIsWhiteIp(clintipadd)) {
//            throw new BusinessException(StatusCode.IP_ERROR.getCode(), StatusCode.IP_ERROR.getMsg());
//        }

        LoginUser org = loginService.doLogin(login);
        // 更新用户登陆次数
        SysUser loginParam = new SysUser();
        loginParam.setAccLogin(login.getAccLogin());
        loginParam.setLastLoginDate(new Date());
        loginParam.setClientIp(clintipadd);
        loginParam.setUserId(login.getUserId());
        sysUserService.updateById(loginParam);

        // 系统登录日志
        SysLog sysLog = new SysLog();
        sysLog.setUsername(login.getAccLogin());
        sysLog.setDescription("后台用户登录");
        sysLog.setLogType("INFO");
        sysLog.setMethod("/manage/login");
        sysLog.setParams(JSON.toJSONString(req));
        sysLog.setRequestIp(clintipadd);
        sysLogService.save(sysLog);

        return org;
    }
}
