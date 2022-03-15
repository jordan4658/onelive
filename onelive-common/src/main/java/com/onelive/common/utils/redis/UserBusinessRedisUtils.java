package com.onelive.common.utils.redis;

import cn.hutool.core.util.StrUtil;
import com.onelive.common.constants.business.RedisKeys;
import com.onelive.common.enums.RedisTimeEnum;
import com.onelive.common.mybatis.entity.MemUser;
import com.onelive.common.utils.Login.LoginInfoUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/**
 * 用户相关的Redis工具类
 */
public class UserBusinessRedisUtils  extends RedisUtil {

    //删除用户token信息
    //删除用户之前的所有token
    public static void deleteAllToken(MemUser user) {
        String accno = user.getAccno();
        Long userId = user.getId();
        Set<String> keys = keys(LoginInfoUtil.getMerchantCode()+RedisKeys.APP_LOGIN_USERINFO + accno);
        if (keys != null && keys.size() > 0) {
            deleteTokens(accno, keys);
        }
        //删除token
        delAppUserToken(userId);
    }


    //删除用户指定的token信息，用户有多个token同时在线
    public static void deleteTokens(String accno, Set<String> tokenKeys) {
        List<String> keys = new ArrayList<>();
        Iterator<String> iterator = tokenKeys.iterator();
        while (iterator.hasNext()) {
            String next = iterator.next();
            String token = StrUtil.removePrefix(next, LoginInfoUtil.getMerchantCode()+RedisKeys.APP_LOGIN_USERINFO + accno + ":");
            keys.add(LoginInfoUtil.getMerchantCode()+RedisKeys.APP_TOKEN + token);
            keys.add(LoginInfoUtil.getMerchantCode()+RedisKeys.APP_LOGIN_USERINFO + accno + ":" + token);
        }
        if (!CollectionUtils.isEmpty(keys)) {
            deleteByKeys(StringUtils.join(keys, ","));
        }
        return;
    }




    /**
     * 保存用户登陆token
     *
     * @param userId
     * @param acctoken
     */
    public static void setAppUserToken(Long userId, String acctoken) {
        String key = RedisKeys.APP_LOGIN_USER_TOKEN + userId;
        set(key, acctoken, RedisTimeEnum.GLOBAL.getValue());
    }

    /**
     * 获取用户登陆token
     *
     * @param userId
     * @return
     */
    public static String getAppUserToken(Long userId) {
        String key = RedisKeys.APP_LOGIN_USER_TOKEN + userId;
        return get(key);
    }

    /**
     * 删除用户token
     *
     * @param userId
     */
    public static void delAppUserToken(Long userId) {
        String key = RedisKeys.APP_LOGIN_USER_TOKEN + userId;
        remove(key);
    }


    /**
     * 更新用户等级缓存
     * @param level
     * @param entity
     */
    public static void setVipLevelInfo(Integer level, String entity) {
        set(RedisKeys.MEM_LEVEL_INFO + level, entity);
    }
}
