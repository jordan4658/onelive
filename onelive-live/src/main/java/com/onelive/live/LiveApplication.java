package com.onelive.live;


import com.onelive.common.utils.others.SpringUtil;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.annotation.PostConstruct;
import java.util.TimeZone;


@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients(basePackages = "com.**.client")
@EnableTransactionManagement
@ComponentScan({"com.onelive.*"})
public class LiveApplication {
    public static void main(String[] args) {
        ApplicationContext applicationContext = SpringApplication.run(LiveApplication.class, args);
        SpringUtil.setApplicationContexts(applicationContext);
    }

    @PostConstruct
    void started() {
        TimeZone.setDefault(TimeZone.getTimeZone("GMT+8"));
    }

//    // 设置@Value注解取值不到忽略（不报错）
//    @Bean
//    public static PropertySourcesPlaceholderConfigurer placeholderConfigurer() {
//        PropertySourcesPlaceholderConfigurer c = new PropertySourcesPlaceholderConfigurer();
//        c.setIgnoreUnresolvablePlaceholders(true);
//        return c;
//    }
}