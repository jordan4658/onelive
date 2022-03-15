package com.onelive.api;


import com.onelive.common.utils.others.SpringUtil;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import javax.annotation.PostConstruct;
import java.util.TimeZone;


@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients(basePackages = "com.**.client")
@EnableSwagger2
@EnableTransactionManagement
@ComponentScan({"com.onelive.*"})
@EnableMongoRepositories(basePackages = "com.onelive.mongodb.repository")
public class ApiApplication {
    public static void main(String[] args) {
        ApplicationContext applicationContext = SpringApplication.run(ApiApplication.class, args);
        SpringUtil.setApplicationContexts(applicationContext);
//        SpringApplication.run(ApiApplication.class, args);
    }


    @PostConstruct
    void started() {
        TimeZone.setDefault(TimeZone.getTimeZone("GMT+8"));
    }
}