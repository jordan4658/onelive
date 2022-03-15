package com.onelive.job.init;


import com.onelive.common.utils.redis.RedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

/**
 * @Classname MyStartupRunner
 * @Description 初始化参数
 */
@Component
@Slf4j
public class StartupRunner implements CommandLineRunner {

    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public void run(String... args) throws Exception {
        //初始化Redis工具类
        RedisUtil.init(redisTemplate);
    }
}
