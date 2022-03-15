package com.onelive.common.config;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.ClusterServersConfig;
import org.redisson.config.Config;
import org.redisson.config.SingleServerConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @description: 分布式锁框架
 * @author: muyu
 * @create: 2020-06-26 13:43
 **/
@Configuration
@Slf4j
public class RedissonConfig {

    @Value("${spring.redis.host:}")
    private String host;

    @Value("${spring.redis.port:}")
    private String port;

    @Value("${spring.redis.password:}")
    private String password;

    @Value("${spring.redis.cluster.nodes:}")
    private String nodes;

    @Bean
    public RedissonClient redissonClient() {
        Config config = new Config();

        //集群
        if (StringUtils.isNotBlank(nodes)) {
            ClusterServersConfig serverConfig = config.useClusterServers();
            serverConfig.setScanInterval(2000); // 集群状态扫描间隔时间，单位是毫秒
            if (StringUtils.isNotEmpty(nodes)) {
                String str[] = nodes.split(",");
                for (int i = 0; i < str.length; i++) {
                    log.info("redis addr : [{}]", str[i]);
                    if (StringUtils.isNotEmpty(str[i])) {
                        serverConfig.addNodeAddress("redis://" + str[i].trim());//// use "rediss://" for SSL connection
                    }
                }
            } else {
                log.error("REDIS集群地址为空,系统退出");
                System.exit(0);
            }
        }
        //单机
        else {
            SingleServerConfig serverConfig = config.useSingleServer();
            serverConfig.setAddress("redis://" + host + ":" + port);
            if (StringUtils.isNotEmpty(password)) {
                serverConfig.setPassword(password);
            }
        }
        return Redisson.create(config);

    }
}
