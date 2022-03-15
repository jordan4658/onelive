package com.onelive.common.utils.cache;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import org.apache.commons.lang3.StringUtils;

import java.util.concurrent.TimeUnit;

/**
 * @ClassName nonceCacheUtils
 * @Desc 签名本地缓存
 * @Date 2021/4/28 18:26
 */
public class nonceCacheUtils {

    public static Integer timeout = 900;

    /**
     * @desction: 检查Im体育是否有初始化过账号
     */
    private static Cache<String, Integer> nonceCache;

    static {
        nonceCache = CacheBuilder.newBuilder().maximumSize(100000)
                .expireAfterWrite(timeout, TimeUnit.SECONDS).build();

    }

    /**
     * @desction: 获取签名
     */
    public static Boolean getNonce(String key) {
        if (StringUtils.isNotBlank(key) && nonceCache.getIfPresent(key) != null) {
            return true;
        }
        return false;
    }

    /**
     * @desction: 设置签名
     */
    public static void putNonce(String key, Integer value) {
        if (key != null) {
            nonceCache.put(key, value);
        }
    }

    /**
     * @desction: 移除签名
     */
    public static void removeNonce(String key) {
        if (key != null) {
            nonceCache.invalidate(key);
        }
    }

}    
    