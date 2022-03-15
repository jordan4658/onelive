package com.onelive.api.common.init;


import java.util.Iterator;
import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.onelive.api.service.mem.MemLevelVipService;
import com.onelive.api.service.sys.SysCountryService;
import com.onelive.api.util.ApiBusinessRedisUtils;
import com.onelive.common.mybatis.entity.MemLevelVip;
import com.onelive.common.mybatis.entity.SysCountry;
import com.onelive.common.utils.redis.RedisUtil;

import lombok.extern.slf4j.Slf4j;

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
    private MemLevelVipService memLevelVipService;

    @Autowired
    private RedisTemplate redisTemplate;
    
    @Override
    public void run(String... args) throws Exception {
        RedisUtil.init(redisTemplate);
        //缓存国家信息
        List<SysCountry> list =sysCountryService.getAllCountry();
        if(CollectionUtils.isNotEmpty(list)){
            Iterator<SysCountry> iterator = list.iterator();
            while (iterator.hasNext()){
                SysCountry country = iterator.next();
                ApiBusinessRedisUtils.setCountryInfo(country.getCountryCode(), JSON.toJSONString(country));
                ApiBusinessRedisUtils.setCountryInfo(country.getEnName(),JSON.toJSONString(country));
            }
        }
        //缓存等级信息
        List<MemLevelVip> vips =  memLevelVipService.list();
        if(CollectionUtils.isNotEmpty(vips)){
            Iterator<MemLevelVip> iterator = vips.iterator();
            while (iterator.hasNext()){
                MemLevelVip vip =  iterator.next();
                ApiBusinessRedisUtils.setVipLevelInfo(vip.getLevelWeight(),JSON.toJSONString(vip));
            }
        }

    }
}
