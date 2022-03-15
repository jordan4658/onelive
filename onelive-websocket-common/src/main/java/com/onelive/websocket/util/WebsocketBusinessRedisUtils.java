package com.onelive.websocket.util;

import cn.hutool.core.date.DateUtil;
import cn.hutool.json.JSONUtil;
import com.onelive.websocket.constants.RedisTimeEnum;
import com.onelive.websocket.dto.AppLoginUser;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

/**
 * Websocke端redis业务类
 */
@Slf4j
public class WebsocketBusinessRedisUtils extends RedisUtil {

    //根据token获取LoginUser对象
    public static AppLoginUser getLoginUserByToken(String token) {
        log.info("根据token获取LoginUser对象======token:"+token);
        String key = LoginInfoUtil.getMerchantCode() + RedisKeys.APP_TOKEN + token;
        log.info("redis中获取用户信息的key："+key);
        String result = get(key);
        log.info("redis中用户缓存信息:"+result);
        if (StringUtils.isBlank(result)) {
            return null;
        }
        return JSONUtil.toBean(result, AppLoginUser.class);
    }

    //续期token
    public static void setRenewalToken(String token, String account) {
        //更新redis的过期时间
        updateExp(LoginInfoUtil.getMerchantCode() + RedisKeys.APP_TOKEN + token, RedisTimeEnum.GLOBAL.getValue());
        updateExp(LoginInfoUtil.getMerchantCode() + RedisKeys.APP_LOGIN_USERINFO + account + ":" + token, RedisTimeEnum.GLOBAL.getValue());
        //更新本地服务缓存
        TokenRedisGuavaCacheUtils.putRenewal(token, SimpleConstant.ZERO);
    }

    /**
     * 功能描述: 判断当天日期是否有key值
     *
     * @param: [key, val]
     */
    public static Boolean isExistCurrentDayKey(String key, String val) {
        Boolean result = true;
        String dateStr = DateUtil.today();
        String dateKey = LoginInfoUtil.getMerchantCode() + key + val + SymbolConstant.UNDERLINE + dateStr;
        if (!RedisUtil.exists(dateKey)) {
            //一天后过期
            setDays(dateKey, 0, 1l);
            result = false;
        }
        return result;
    }



}
