package com.onelive.third.common.init;


import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * @Classname MyStartupRunner
 * @Description 初始化参数
 */
@Component
@Slf4j
public class StartupRunner implements CommandLineRunner {

    @Override
    public void run(String... args) throws Exception {
       //初始化本地缓存或者其他信息

    }
}
