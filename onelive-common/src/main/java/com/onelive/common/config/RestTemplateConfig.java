package com.onelive.common.config;


import okhttp3.ConnectionPool;
import okhttp3.OkHttpClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.OkHttp3ClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.TimeUnit;

/**
 * RestTemplate配置类
 */
@Configuration
public class RestTemplateConfig {

    @Bean
    public ConnectionPool connectionPool(){
        // 设置连接池参数，最大空闲连接数200，空闲连接存活时间10s
        return new ConnectionPool(200, 10 , TimeUnit.SECONDS);
    }

    @Bean
    public OkHttpClient okHttpClient(){
        return new OkHttpClient.Builder().retryOnConnectionFailure(false).connectionPool(connectionPool())
                .connectTimeout(3, TimeUnit.SECONDS)
                .readTimeout(3, TimeUnit.HOURS)
                .writeTimeout(3, TimeUnit.SECONDS).build();
    }

    @Bean
    public ClientHttpRequestFactory clientHttpRequestFactory(){
        return new OkHttp3ClientHttpRequestFactory(okHttpClient());
    }

    /**
     * rest模板
     * @return
     */
    @Bean
    public RestTemplate restTemplate(ClientHttpRequestFactory clientHttpRequestFactory) {
        // boot中可使用RestTemplateBuilder.build创建
        RestTemplate restTemplate = new RestTemplate();
        // 配置请求工厂
        restTemplate.setRequestFactory(clientHttpRequestFactory);
        return restTemplate;
    }

}