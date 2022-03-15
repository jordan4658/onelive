package com.onelive.api.common.interceptor;

import com.onelive.common.utils.Login.LoginInfoUtil;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * Feign远程服务拦截器
 */
@Slf4j
@Component
public class FeignClientInterceptor implements RequestInterceptor {
    @Override
    public void apply(RequestTemplate requestTemplate) {
        //设置Feign远程服务不走加密流程, 如果要移除isTest字段,请添加新字段,确保服务不走加密流程
        LoginInfoUtil.setIsFeign(true);
        //把lang设置进请求头
    }
}
