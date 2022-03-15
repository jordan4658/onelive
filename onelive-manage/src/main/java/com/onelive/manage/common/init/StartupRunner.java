package com.onelive.manage.common.init;


import com.onelive.common.utils.redis.RedisUtil;
import com.onelive.manage.service.sys.SysCountryService;
import com.onelive.manage.utils.redis.SysBusinessRedisUtils;
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
    private SysCountryService sysCountryService;
    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public void run(String... args) throws Exception {
        RedisUtil.init(redisTemplate);
        //缓存国家信息
        SysBusinessRedisUtils.initCountryInfo(sysCountryService);
    }
}
