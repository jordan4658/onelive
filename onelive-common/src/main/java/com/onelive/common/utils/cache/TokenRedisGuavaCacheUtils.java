package com.onelive.common.utils.cache;

import cn.hutool.core.date.DateUtil;
import com.onelive.common.constants.other.SymbolConstant;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import lombok.extern.slf4j.Slf4j;
import net.logstash.logback.encoder.org.apache.commons.lang3.StringUtils;
import org.apache.commons.collections4.CollectionUtils;

import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * app或者后台的token续期缓存工具类
 */
@Slf4j
public class TokenRedisGuavaCacheUtils {

    /**
     * @desction: 用于检查每天APP的token是否已经续期redis, 有效期是一天
     */
    private static Cache<String, String> appRedisRenewalCache;
    /**
     * @desction: 用于检查manage的token是否已经续期redis, 后台管理token有效期是2小时，但缓存换成1小时续期一次
     */
    private static Cache<String, String> manageRedisRenewalCache;

    static {
        appRedisRenewalCache = CacheBuilder.newBuilder().maximumSize(100000)
                .expireAfterWrite(1, TimeUnit.DAYS).build();
        manageRedisRenewalCache = CacheBuilder.newBuilder().maximumSize(100000)
                .expireAfterWrite(1, TimeUnit.HOURS).build();
    }

    /**
     * @desction: 获取缓存
     */
    public static String getRenewal(String key) {
        return StringUtils.isNotBlank(key) ? appRedisRenewalCache.getIfPresent(renewalKey(key)) : null;
    }

    /**
     * @desction: 放入缓存
     */
    public static void putRenewal(String key, String value) {
        if (StringUtils.isNotBlank(key) && StringUtils.isNotBlank(value)) {
            appRedisRenewalCache.put(renewalKey(key), value);
        }
    }

    /**
     * @desction: 移除缓存
     */
    public static void removeRenewal(String key) {
        if (StringUtils.isNotBlank(key)) {
            appRedisRenewalCache.invalidate(renewalKey(key));
        }
    }

    /**
     * @desction: 批量删除缓存
     */
    public static void removeRenewal(List<String> keys) {
        if (CollectionUtils.isNotEmpty(keys)) {
            List<String> newKeys = keys.stream().map(aa -> {
                String change = renewalKey(aa);
                return change;
            }).collect(Collectors.toList());
            appRedisRenewalCache.invalidateAll(newKeys);
        }
    }

    /**
     * 续期key值，key值是 {当天日期}:{token}
     *
     * @param key
     * @return
     */
    private static String renewalKey(String key) {
        return DateUtil.today() + SymbolConstant.COLON + key;
    }


    /**
     * @desction: 获取后台管理缓存
     */
    public static String getManage(String key) {
        return StringUtils.isNotBlank(key) ? manageRedisRenewalCache.getIfPresent(key) : null;
    }

    /**
     * @desction: 放入后台管理缓存
     */
    public static void putManage(String key, String value) {
        if (StringUtils.isNotBlank(key) && StringUtils.isNotBlank(value)) {
            appRedisRenewalCache.put(key, value);
        }
    }


}
