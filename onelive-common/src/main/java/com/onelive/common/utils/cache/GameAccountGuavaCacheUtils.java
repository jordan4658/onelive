package com.onelive.common.utils.cache;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;

/**
 * 判断第三游戏是否已创建账号缓存工具类
 */
@Slf4j
public class GameAccountGuavaCacheUtils {

    /**
     * @desction: 检查Im体育是否有初始化过账号
     */
    private static Cache<Long, String> accountImCache;

    static {
        accountImCache = CacheBuilder.newBuilder().maximumSize(100000)
                .expireAfterWrite(30, TimeUnit.DAYS).build();

    }

    /**
     * @desction: 获取账号
     */
    public static String getImAccount(Long key) {
        return key != null ? accountImCache.getIfPresent(key) : null;
    }

    /**
     * @desction: 设置账号
     */
    public static void putImAccount(Long key, String value) {
        if (key != null) {
            accountImCache.put(key, value);
        }
    }

    /**
     * @desction: 移除账号
     */
    public static void removeImAccount(Long key) {
        if (key != null) {
            accountImCache.invalidate(key);
        }
    }


}
