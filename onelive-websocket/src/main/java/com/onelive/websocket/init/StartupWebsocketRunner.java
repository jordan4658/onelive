package com.onelive.websocket.init;


import com.onelive.websocket.util.RedisUtil;
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
public class StartupWebsocketRunner implements CommandLineRunner {

    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public void run(String... args) throws Exception {
        RedisUtil.init(redisTemplate);
    }
}
