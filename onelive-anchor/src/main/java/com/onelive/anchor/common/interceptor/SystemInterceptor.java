package com.onelive.anchor.common.interceptor;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.onelive.common.annotation.AllowAccess;
import com.onelive.common.annotation.SupperAccess;
import com.onelive.common.base.LocaleMessageSourceService;
import com.onelive.common.constants.business.LoginConstants;
import com.onelive.common.constants.business.RedisKeys;
import com.onelive.common.constants.other.HeaderConstants;
import com.onelive.common.constants.other.SimpleConstant;
import com.onelive.common.constants.other.SymbolConstant;
import com.onelive.common.enums.StatusCode;
import com.onelive.common.exception.BusinessException;
import com.onelive.common.model.dto.login.AppLoginUser;
import com.onelive.common.model.dto.mem.UserStatusDTO;
import com.onelive.common.mybatis.entity.SysCountry;
import com.onelive.common.utils.Login.IPAddressUtil;
import com.onelive.common.utils.Login.LoginInfoUtil;
import com.onelive.common.utils.cache.TokenRedisGuavaCacheUtils;
import com.onelive.common.utils.others.AesUtil;
import com.onelive.common.utils.others.BaseUtil;
import com.onelive.common.utils.others.SpringUtil;
import com.onelive.anchor.util.AnchorBusinessRedisUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * APP登录统一认证
 */
@Component
@Slf4j
public class SystemInterceptor implements HandlerInterceptor {

    //@Value("${fl.secretKey}")
    //private String secretKey;
    @Value("${spring.profiles.active}")
    private String env;
    @Value("${auth.skip}")
    private String skip;
    private static final String errorUri = "/error";



    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws IOException {
        // 如果不是映射到方法直接通过
        if (!(handler instanceof HandlerMethod)) {
            return true;
        }


        //方法注解级拦截器
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        Method method = handlerMethod.getMethod();

        if(StringUtils.isNotBlank(request.getHeader(HeaderConstants.isTest))){
            LoginInfoUtil.setIsTest(true);
        }

        //获取设备类型
        LoginInfoUtil.setDeviceType(IPAddressUtil.getMobileDevice(request));
        //获取设备来源
        LoginInfoUtil.setSource(request.getHeader(HeaderConstants.ONELIVESOURCE));
        //获取IP
        LoginInfoUtil.setIp(IPAddressUtil.getIpAddress(request));
        //获取当前请求的语言
        Locale locale = LocaleContextHolder.getLocale();
        String lang = locale.getLanguage()+SymbolConstant.UNDERLINE+locale.getCountry();
        LoginInfoUtil.setLang(lang);
        //获取当前IP所在区域
        LoginInfoUtil.setArea(IPAddressUtil.getClientAreaEn(LoginInfoUtil.getIp()));
        //获取当前设备唯一标识
        LoginInfoUtil.setDeviceId(request.getHeader(HeaderConstants.ONELIVEDEVICEID));
        log.info("ONELIVEAPPLETYPE:{}",request.getHeader(HeaderConstants.ONELIVEAPPLETYPE));
        //加密解密类型
        LoginInfoUtil.setKeyType(request.getHeader(HeaderConstants.ONELIVEAPPLETYPE));
        //加密解密秘钥
        LoginInfoUtil.setSecretKey(getSecretKey(request.getHeader(HeaderConstants.ONELIVEAPPLETYPE)));
        SupperAccess supperAccess = method.getAnnotation(SupperAccess.class);
        //不走任何校验，直接通过
        if (supperAccess != null) {
            return true;
        }
        String token = request.getHeader(HeaderConstants.AUTHORIZATION);
        String oneLiveSignature = request.getHeader(HeaderConstants.ONELIVESIGNATURE);
        //dev环境跳过验签  sit环境默认万能签是1
        List<String> skipList = StrUtil.splitTrim(skip, ",");
        if (skipList.contains(env) || SimpleConstant.ONE.equals(oneLiveSignature)) {

        } else {
            //立即进行验签
            if (!checkSign(request, token)) {
                BaseUtil.writerResponse(response, StatusCode.UNLEGAL.getCode(), StatusCode.UNLEGAL.getMsg());
                return false;
            }
        }

//
        //判断接口上面是否不需要验证
        AllowAccess allowAccess = method.getAnnotation(AllowAccess.class);
        if (allowAccess != null) {
            return true;
        }
        if (StringUtils.isBlank(token)) {
            throw new BusinessException(StatusCode.UNLOGIN_CODE);
        }
        log.info("请求：" + request.getRequestURL() + " 开始处理，token:{}", token);
        //验证用户信息
        AppLoginUser user = AnchorBusinessRedisUtils.getLoginUserByToken(token);
        if (user == null) {
            throw new BusinessException(StatusCode.UNLOGIN_CODE);
        }

        //获取当前app使用的国家地区
        LoginInfoUtil.setCountryCode(user.getCountryCode());
        SysCountry country = AnchorBusinessRedisUtils.getCountryInfo(user.getCountryCode());
        if(country != null){
            LoginInfoUtil.setCountryId(country.getId());
        }

        //设置当前用户所在国家
        setMemCurrentCountry(user);
        UserStatusDTO dto = AnchorBusinessRedisUtils.getUserStatus(user.getAccno(), user.getId());
        if (dto.getIsFrozen()) {
            throw new BusinessException(StatusCode.LOGIN_FROZEN);
        }
        //传递参数
        request.getSession().setAttribute(LoginConstants.APP_LOGIN_INFO, user);
        LoginInfoUtil.setUserId(user.getId());
        LoginInfoUtil.setUserAccount(user.getUserAccount());
        //保存本次的token
        LoginInfoUtil.setToken(token);
        //token续期
        renewalToken(token, user);
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
    }

    /**
     * 进行验签操作
     *
     * @param request
     * @param token
     * @return
     */
    private Boolean checkSign(HttpServletRequest request, String token) {
//        //检查是否打开验签开关 0关1开
//        SysParameter sysParameter = sysParameterService.getByCode(SysParamEnum.SIGN_CHECK_SWITCH.getCode());
//        //未打开开关则不验证
//        String uri = request.getRequestURI();
//        if (sysParameter == null || BooleanEnums.FALSE.getCode().toString().equals(sysParameter.getParamValue()) || errorUri.equals(uri)) {
//            return true;
//        }
        String uri = request.getRequestURI();
        //签名随机数
        String flRandom = request.getHeader(HeaderConstants.ONELIVERANDOM);
        //验签随机戳
        String flTimestamp = request.getHeader(HeaderConstants.ONELIVETIMESTAMP);
        //验签url
        String flUrl = request.getHeader(HeaderConstants.ONELIVEURL);
        //最后签名的值
        String oneSignature = request.getHeader(HeaderConstants.ONELIVESIGNATURE);
        //签名类型
        String secretKey = LoginInfoUtil.getSecretKey();
        if(StringUtils.isBlank(secretKey)){
            return false;
        }
        //进行常规验签
        //获取当前请求的最后一个字符串
        String lastUrl = uri.substring(uri.lastIndexOf(Constants.SLASH) + 1);
        //获取token
        token = StringUtils.isBlank(token) ? SymbolConstant.BLANK : token;

        //根据签名规则 onelive-timestamp||onelive-random||Authorization||onelive-url(请求的url最后一个斜杆后面的字符串) 进行匹配
        StringBuilder beforeSign = new StringBuilder();
        beforeSign.append(flTimestamp);
        beforeSign.append(SymbolConstant.STRAIGHT_SLASH);
        beforeSign.append(flRandom);
        beforeSign.append(SymbolConstant.STRAIGHT_SLASH);
        beforeSign.append(token);
        beforeSign.append(SymbolConstant.STRAIGHT_SLASH);
        beforeSign.append(lastUrl);
        String sign = AesUtil.aesEncrypt(beforeSign.toString(), secretKey);

        if (sign.equals(oneSignature)) {
            return true;

//            //7分30秒
//            Integer validSeconds = nonceCacheUtils.timeout / 2;
//            long mytime = System.currentTimeMillis();
//            long diff = (mytime - Long.valueOf(flTimestamp).longValue()) / 1000;
//            if (Math.abs(diff) <= validSeconds) {
//                String nonceKey = RedisKeys.API_REPEAT + flSignature;
//                if (nonceCacheUtils.getNonce(nonceKey) || RedisUtil.exists(nonceKey)) {
//                    log.info("验签重复调用 fl_timestamp:{}, fl_random:{}, fl_url:{},token:{}, fl_signature:{},beforeSign:{},afterSign:{}", flTimestamp, flRandom, flUrl, token, flSignature, beforeSign.toString(), sign);
//                    return false;
//                }
//                //因为前端设备时间可能存在不同步问题，所以通过时间戳判断会出现问题，暂时由redis来做api防重放,默认15分钟
//                RedisUtil.set(RedisKeys.API_REPEAT + flSignature, 1, nonceCacheUtils.timeout.longValue());
//                nonceCacheUtils.putNonce(RedisKeys.API_REPEAT + flSignature, 1);
//                return true;
//            } else {
//                log.info("验签超时 fl_timestamp:{}, fl_random:{}, fl_url:{},token:{}, fl_signature:{},beforeSign:{},afterSign:{},我的时间:{},相差秒数sign-diff:{}", flTimestamp, flRandom, flUrl, token, flSignature, beforeSign.toString(), sign, mytime, diff);
//                return false;
//            }

//            //验签通过后，判断签名是否在有效期内 todo 默认5秒
//            Integer validSeconds = 5;
//            long mytime = System.currentTimeMillis();
//            long diff = (mytime - Long.valueOf(flTimestamp).longValue()) / 1000;
//            if (-5 <= diff && diff <= validSeconds) {
//                return true;
//            } else {
//                log.info("验签失败 fl_timestamp:{}, fl_random:{}, fl_url:{},token:{}, fl_signature:{},beforeSign:{},afterSign:{},我的时间:{},相差秒数sign-diff:{}", flTimestamp, flRandom, flUrl, token, flSignature, beforeSign.toString(), sign, mytime, diff);
//            }
        } else {
            log.info("验签失败 fl_timestamp:{}, fl_random:{}, fl_url:{},token:{}, fl_signature:{},beforeSign:{},afterSign:{}", flTimestamp, flRandom, flUrl, token, oneSignature, beforeSign.toString(), sign);
        }
        return false;
    }

    /**
     * 续期token，本地缓存和redis缓存，都只针对当天有效，当天只会续期一次
     *
     * @param token
     * @param user
     */
    private void renewalToken(String token, AppLoginUser user) {
        //redis续期
        try {
            //1、检查本地服务器是否有续期记录
            String renewalRecord = TokenRedisGuavaCacheUtils.getRenewal(token);
            if (renewalRecord == null) {
                //2、检查redis是否有续期记录
                if (AnchorBusinessRedisUtils.isExistCurrentDayKey(RedisKeys.APP_ACTIVETOKEN, token)) {
                    //4、更新本地服务缓存
                    TokenRedisGuavaCacheUtils.putRenewal(token, SimpleConstant.ZERO);
                } else {
                    //3、若本地缓存和redis同时没有续期记录，则进行续期
                    AnchorBusinessRedisUtils.setRenewalToken(token, user);
                    //TODO 临时加入，后期去掉
                    AnchorBusinessRedisUtils.isExistCurrentDayKey(RedisKeys.APP_ACTIVETOKEN_ACCOUNT, user.getAccno());
                }
            }
        } catch (Exception e) {
            log.error("redis续期报错 ", e);
        }
    }

    /**
     * 设置当前用户所在国家
     * @param user
     */
    private void setMemCurrentCountry(AppLoginUser user){
//        LocaleMessageSourceService localeMessageSourceService = SpringUtil.getBean(LocaleMessageSourceService.class);
//        //获取当前用户所在地区
//        if(StringUtils.isNotBlank(LoginInfoUtil.getArea())){
//            SysCountry sysCountry =  ApiBusinessRedisUtils.getCountryInfo(LoginInfoUtil.getArea());
//            if(sysCountry != null){
//                ApiBusinessRedisUtils.setMemCurrentArea(user.getId(),localeMessageSourceService.getMessage(user.getRegisterCountryCode()));
//            }else{
//                ApiBusinessRedisUtils.setMemCurrentArea(user.getId(),LoginInfoUtil.getArea());
//            }
//        }else{
//            //若获取不了当前用户所在国家，则默认取注册时候的国家
//            ApiBusinessRedisUtils.setMemCurrentArea(user.getId(),localeMessageSourceService.getMessage(user.getRegisterCountryCode()));
//        }

        LocaleMessageSourceService localeMessageSourceService = SpringUtil.getBean(LocaleMessageSourceService.class);
        //获取当前用户所在地区
        if(StringUtils.isNotBlank(LoginInfoUtil.getArea())){
            AnchorBusinessRedisUtils.setMemCurrentArea(user.getId(),LoginInfoUtil.getArea());
        }else{
            //若获取不了当前用户所在国家，则默认取注册时候的国家
            AnchorBusinessRedisUtils.setMemCurrentArea(user.getId(),localeMessageSourceService.getMessage(user.getRegisterCountryCode()));
        }
    }


    /**
     * 获取秘钥key值,先写死，后读取配置文件 TODO
     * @param key
     * @return
     */
    private String getSecretKey(String key){
        if(StringUtils.isBlank(key)){
            return null;
        }
        List<String> keyList = new ArrayList<>();
        keyList.add("c1kgVioySoUVimtw");
        keyList.add("f2kgVioykoURWmtg");
        keyList.add("e3kgGioydoURWmtf");
        keyList.add("b9rtFfoydoURnjtp");
        keyList.add("t6rtTfmcdoURnjth");

        if("1".equals(key)){
            return keyList.get(0);
        }

        if("2".equals(key)){
            return keyList.get(1);
        }

        if("3".equals(key)){
            return keyList.get(2);
        }

        if("4".equals(key)){
            return keyList.get(3);
        }
        return keyList.get(4);

       //return "c1kgVioySoUVimtw";

    }


}
