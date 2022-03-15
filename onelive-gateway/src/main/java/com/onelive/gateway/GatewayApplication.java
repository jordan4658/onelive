package com.onelive.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;

import javax.annotation.PostConstruct;
import java.util.TimeZone;

/**
 * @ClassName : GatewayApplication
 * @Description :
 * @Author : muyu
 * @Date: 2020-10-01 12:37
 */
@EnableDiscoveryClient
@ComponentScan({ "com.onelive.*"})
@EnableCircuitBreaker // 开启Hystrix的服务降级
@EnableAutoConfiguration
public class GatewayApplication {
    public static void main(String[] args) {
        SpringApplication.run(GatewayApplication.class, args);
    }

    @PostConstruct
    void started() {
        TimeZone.setDefault(TimeZone.getTimeZone("GMT+8"));
    }

}
