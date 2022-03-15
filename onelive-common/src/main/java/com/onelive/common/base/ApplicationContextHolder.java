package com.onelive.common.base;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * ApplictionContext工具
 * @author chenzh27
 * @version 2017年12月6日 上午11:53:31
 */
@Component
public class ApplicationContextHolder implements ApplicationContextAware {
    static ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        ApplicationContextHolder.applicationContext = applicationContext;
    }

    public static <T> T getBean(Class<T> t) {
        return applicationContext.getBean(t);
    }

    public static <T> Map<String, T> getBeansOfType(Class<T> t) {
        return applicationContext.getBeansOfType(t);
    }

    public static Object getBean(String name) {
        return applicationContext.getBean(name);
    }
}
