package com.onelive.manage.common.intercepptor;

import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSONObject;
import com.onelive.common.annotation.AllowAccess;
import com.onelive.common.constants.business.LoginConstants;
import com.onelive.common.constants.business.MemConstants;
import com.onelive.common.constants.other.HeaderConstants;
import com.onelive.common.constants.other.SimpleConstant;
import com.onelive.common.constants.other.SymbolConstant;
import com.onelive.common.enums.RedisTimeEnum;
import com.onelive.common.enums.SysParamEnum;
import com.onelive.common.model.dto.login.LoginUser;
import com.onelive.common.mybatis.entity.SysParameter;
import com.onelive.common.utils.Login.IPAddressUtil;
import com.onelive.common.utils.Login.LoginInfoUtil;
import com.onelive.common.utils.cache.TokenRedisGuavaCacheUtils;
import com.onelive.common.utils.others.BaseUtil;
import com.onelive.common.utils.redis.RedisUtil;
import com.onelive.manage.service.sys.SysParameterService;
import com.onelive.manage.utils.other.WhiteUriUtil;
import com.onelive.manage.utils.redis.SystemRedisUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.util.Locale;

/**
 * @ClassName SystemInterceptor
 * @Desc 拦截器
 * @Date 2021/3/15 10:17
 */
@Component
@Slf4j
public class SystemInterceptor extends HandlerInterceptorAdapter {

    @Resource
    private SysParameterService sysParamService;
//    @Resource
//    private SysWhiteListService sysWhiteListService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String uri = BaseUtil.getRequestUri(request);
        //登录令牌
        String acctoken = request.getHeader(HeaderConstants.AUTHORIZATION);
        // 如果header中没有acctoken 则去取获取参数
        if (StrUtil.isBlank(acctoken)) {
            acctoken = request.getParameter(HeaderConstants.AUTHORIZATION);
        }
//
//        //获取用户ip，且判断是否是ip白名单
//        String userIP = BaseUtil.getIpAddress(request);
//        log.info("{} 请求开始 uri:{}, userIP:{}", acctoken, uri, userIP);
//        if (!sysWhiteListService.checkIsWhiteIp(userIP)) {
//            BaseUtil.writerResponse(response, StatusCode.IP_ERROR);
//            return false;
//        }
        //获取IP
        LoginInfoUtil.setIp(IPAddressUtil.getIpAddress(request));


        //获取当前请求的语言
        Locale locale = LocaleContextHolder.getLocale();
        String lang = locale.getLanguage()+ SymbolConstant.UNDERLINE+locale.getCountry();
        LoginInfoUtil.setLang(lang);

        //判断方法是否是白名单方法
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        Method method = handlerMethod.getMethod();
        if (method.getAnnotation(AllowAccess.class) != null || WhiteUriUtil.isWhiteUri(uri)) {
            return super.preHandle(request, response, handler);
        } else if (StrUtil.isEmpty(acctoken)) {
            BaseUtil.writer401Response(response);
            return false;
        }

        String jsonstr = SystemRedisUtils.getLoginUserJson(acctoken);
        if (null == jsonstr) {
            log.info("获取redis中的acctoken:{}失败,导致用户未登录或已过期", acctoken);
            BaseUtil.writer401Response(response);
            return false;
        }

        Long sessiontime = RedisTimeEnum.TWOHOUR.getValue();

        SysParameter sp = this.sysParamService.getByCode(SysParamEnum.SESSION_TIME_BACK.name());
        if (sp != null) {
            sessiontime = Long.parseLong(sp.getParamValue()) * 60;
        }
        LoginUser user = null;
        try {
            user = JSONObject.parseObject(jsonstr, LoginUser.class);
        } catch (Exception e) {
            log.error("{}:{} parse LoginUser:{} occur error.", acctoken, uri, jsonstr, e);
            BaseUtil.writer401Response(response);
            return false;
        }

        String loginToken = SystemRedisUtils.getLoginToken(user.getUserId().toString());
        // 后台管理，统一是单点登录验证逻辑

        if (!acctoken.equals(loginToken)) {
            RedisUtil.del(acctoken);
            log.info("{}:{} 账号已在别处登录，您已被迫下线！", acctoken, uri);
            BaseUtil.writer401Response(response, "您的帐号已在另一个设备上登录");
            return false;
        }

        request.getSession().setAttribute(LoginConstants.ADMIN_LOGIN_INFO, user);
        //传递参数
        request.setAttribute(MemConstants.ADMIN_ATTR_USER_ID, user.getUserId());
        //后台登录账号
        LoginInfoUtil.setUserAccount(user.getAccLogin());
        LoginInfoUtil.setCountryCode(user.getCountryCode());
        // token续期逻辑
        renewalToken(loginToken, user, sessiontime);
        return super.preHandle(request, response, handler);
    }

    /**
     * @param request
     * @param response
     * @param handler
     * @param ex
     * @throws Exception
     */
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
    }


    /**
     * 续期token，本地缓存和redis缓存，每一小时续期一次
     */
    private void renewalToken(String loginToken, LoginUser user, Long sessiontime) {
        //redis续期
        try {
            //1、检查本地服务器是否有续期记录
            String renewalRecord = TokenRedisGuavaCacheUtils.getManage(loginToken);
            if (renewalRecord == null) {
                SystemRedisUtils.setLoginToken(user.getUserId(), loginToken, sessiontime);
                SystemRedisUtils.setLoginUserJson(loginToken, user, sessiontime);
                TokenRedisGuavaCacheUtils.putManage(loginToken, SimpleConstant.ZERO);
            }
        } catch (Exception e) {
            log.error("redis续期报错 ", e);
        }
    }

}
