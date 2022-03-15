package com.onelive.common.handler;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import org.apache.ibatis.reflection.MetaObject;

import java.util.Date;

/**
 * MyMetaObjectHandler
 *
 * @author kevin
 * @version 1.0.0
 * @since 2021年10月27日 下午8:01:55
 */
public class MyMetaObjectHandler implements MetaObjectHandler {

    /**
     * 创建时间字段名
     */
    public static final String CREATE_TIME = "createTime";

    /**
     * 删除状态字段名
     */
    public static final String DELETE_STATUS = "deleteStatus";

    /**
     * 更新时间字段名
     */
    public static final String UPDATE_TIME = "updateTime";


    /**
     * insertFill
     *
     * @param metaObject
     * @author kevin
     * @version 1.0.0
     * @since 2021年10月27日 下午8:01:55
     */
    @Override
    public void insertFill(final MetaObject metaObject) {
        final Object createTime = getFieldValByName(MyMetaObjectHandler.CREATE_TIME, metaObject);
        final Object updateTime = getFieldValByName(MyMetaObjectHandler.UPDATE_TIME, metaObject);
        final Object deleteStatus = getFieldValByName(MyMetaObjectHandler.DELETE_STATUS, metaObject);
        final Date date = new Date();
        if (createTime == null) {
            setFieldValByName(MyMetaObjectHandler.CREATE_TIME, date, metaObject);
        }
        if (updateTime == null) {
            setFieldValByName(MyMetaObjectHandler.UPDATE_TIME, date, metaObject);
        }
        if (deleteStatus == null) {
            setFieldValByName(MyMetaObjectHandler.DELETE_STATUS, 0, metaObject);
        }
    }


    /**
     * updateFill
     *
     * @param metaObject
     * @author kevin
     * @version 1.0.0
     * @since 2021年10月27日 下午8:01:55
     */
    @Override
    public void updateFill(final MetaObject metaObject) {
        setFieldValByName(MyMetaObjectHandler.UPDATE_TIME, new Date(), metaObject);
    }
}
