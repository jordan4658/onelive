package com.onelive.mongodb.util;

import cn.hutool.core.bean.BeanUtil;
import org.springframework.data.mongodb.core.query.Update;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * mongodb查询条件构建工具类
 */
public class MongoUtil {

    public static Update updateOf(Object object) {
        Update update = new Update();
        Map<String, Object> items = BeanUtil.beanToMap(object, false, true);
        for (Map.Entry<String, Object> entry: items.entrySet()) {
            update.set(entry.getKey(), entry.getValue());
        }
        return update;
    }

    public static Update updateOf(Object object, String... ignoreKeys) {
        Update update = new Update();
        List<String> keys = Arrays.asList(ignoreKeys);
        Map<String, Object> items = BeanUtil.beanToMap(object, false, true);
        for (Map.Entry<String, Object> entry: items.entrySet()) {
            if (!keys.contains(entry.getKey())) {
                update.set(entry.getKey(), entry.getValue());
            }
        }
        return update;
    }
}
