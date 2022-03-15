package com.onelive.gateway.utils;

import org.springframework.util.CollectionUtils;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @ClassName WhiteUriUtil
 * @Desc 拦截器白名单类
 * @Date 2021/3/24 20:23
 */
public class WhiteUriUtil {

    public static Set<String> whiteUriSet;

    //类加载时候，立即初始化
    static {
        whiteUriSet = new HashSet();
        whiteUriSet.add("/error");
        whiteUriSet.add("/swagger");
        whiteUriSet.add("/api-docs");
        whiteUriSet.add("/login");
    }

    /**
     * 判断是否是白名单
     *
     * @param uri
     * @return
     */
    public static boolean isWhiteUri(String uri) {
        List<String> result = whiteUriSet.stream().filter(a -> uri.contains(a)).collect(Collectors.toList());
        return !CollectionUtils.isEmpty(result);
    }


}    
    