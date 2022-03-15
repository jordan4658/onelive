package com.onelive.common.model.req.util;

import io.swagger.annotations.ApiModelProperty;

import java.lang.reflect.Field;

/**
 * @ClassName ReqUtil
 * @Description wk
 * @Author wk
 * @Date 2021/6/3 18:31
 **/
public class ReqUtil {
    /**
     * 参数非空校验
     * 检测@ApiModelProperty注解包含required的所有属性
     *
     * @param obj
     * @return 注解属性为空反回 当前直接为空的字符串提示
     * @throws Throwable
     */
    public static String checkApiModelProperty(Object obj) {
        try {
            Field[] fields = obj.getClass().getDeclaredFields();
            //我这里是需要循环获取对象中的属性判断是否有注解，有再另做处理
            for (int i = 0; i < fields.length; i++) {
                Field field = fields[i];
                //获取到有注解的属性
                if (null != field.getAnnotation(ApiModelProperty.class)) {
                    //通过注解里的属性名获取到注解属性的值，如下获取注解ApiModelProperty中的required值:
                    boolean required = field.getAnnotation(ApiModelProperty.class).required();
                    if (true == required) {
                        //打开私有访问
                        field.setAccessible(true);
                        //获取属性值
                        Object value = field.get(obj);
                        if (null == value) {
                            return "参数 " + field.getName() + " 为空！";
                        }
                    }
                }
            }
            return null;
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            return null;
        }
    }
}
