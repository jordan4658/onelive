package com.onelive.common.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 自定义注解 标识请求类 加密解密
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface AopEncryption {

    /**
     * 执行时 解密
     */
    boolean decrypt() default true;

    /**
     * 返回时 加密
     */
    boolean encryption() default true;

    /**
     * 参数类型
     * @return
     */
    Class<?> [] paramType() default {};
}