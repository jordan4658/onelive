package com.onelive.anchor;

import com.onelive.common.utils.others.SpringUtil;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import javax.annotation.PostConstruct;
import java.util.TimeZone;

@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients(basePackages = "com.onelive.common.client")
@EnableSwagger2
@EnableTransactionManagement
@ComponentScan({"com.onelive.*"})
public class AnchorApplication {
    public static void main(String[] args) {
        ApplicationContext applicationContext = SpringApplication.run(AnchorApplication.class, args);
        SpringUtil.setApplicationContexts(applicationContext);
    }


    @PostConstruct
    void started() {
        TimeZone.setDefault(TimeZone.getTimeZone("GMT+8"));
    }
}
