package com.onelive.websocket.util;


import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class SpringUtils implements ApplicationContextAware{

    private static ApplicationContext applicationContext = null;
    // 非@import显式注入，@Component是必须的，且该类必须与main同包或子包
    // 若非同包或子包，则需手动import 注入，有没有@Component都一样
    // 可复制到Test同包测试

    public static void setApplicationContexts(ApplicationContext applicationContext) throws BeansException {
        setApplicationContext(applicationContext, true);
    }
    
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        if(SpringUtils.applicationContext == null) {
        	SpringUtils.applicationContext = applicationContext;
        }
    }

    public static void setApplicationContext(ApplicationContext applicationContext, boolean init) throws BeansException {
        if (SpringUtils.applicationContext == null) {
        	SpringUtils.applicationContext = applicationContext;
            if (init) {
               // RedisTemplate redisTemplate = (RedisTemplate) getBean("redisTemplate");
               // RedisUtil.init(redisTemplate);
            }
        }
    }

    //获取applicationContext
    public static ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    //通过name获取 Bean.
    public static Object getBean(String name) {
        return getApplicationContext().getBean(name);
    }

    //通过class获取Bean.
    public static <T> T getBean(Class<T> clazz) {
        return getApplicationContext().getBean(clazz);
    }

    //通过name,以及Clazz返回指定的Bean
    public static <T> T getBean(String name, Class<T> clazz) {
        return getApplicationContext().getBean(name, clazz);
    }

    public static <T> Map<String, T> getBeansOfType(Class<T> clazz) {
        return getApplicationContext().getBeansOfType(clazz);
    }

}
