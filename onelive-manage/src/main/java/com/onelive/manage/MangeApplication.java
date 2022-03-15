package com.onelive.manage;

import com.alicp.jetcache.anno.config.EnableCreateCacheAnnotation;
import com.alicp.jetcache.anno.config.EnableMethodCache;
import com.onelive.common.utils.others.SpringUtil;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScans;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import javax.annotation.PostConstruct;
import java.util.TimeZone;

@SpringBootApplication
@EnableSwagger2
@ComponentScans(value = {
        @ComponentScan("com.onelive.common.utils.*"),
        @ComponentScan("com.onelive.common.config.data"),
        @ComponentScan("com.onelive.common.client"),
        @ComponentScan("com.onelive.common.base"),
        @ComponentScan("com.onelive.common.mybatis.util"),
        @ComponentScan("com.onelive.manage.*"),
        @ComponentScan("com.onelive.common.service.*"),
        @ComponentScan("com.onelive.common.exception")

})
@EnableDiscoveryClient
@EnableTransactionManagement
@EnableFeignClients(basePackages = "com.onelive.common.client")
@EnableMethodCache(basePackages = "com.onelive.manage")
@EnableCreateCacheAnnotation
public class MangeApplication {


    public static void main(String[] args) {
        ApplicationContext applicationContext = SpringApplication.run(MangeApplication.class, args);
        SpringUtil.setApplicationContexts(applicationContext);
    }

    @PostConstruct
    void started() {
        TimeZone.setDefault(TimeZone.getTimeZone("GMT+8"));
    }
}
