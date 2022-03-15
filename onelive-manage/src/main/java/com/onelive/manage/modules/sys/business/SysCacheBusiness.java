package com.onelive.manage.modules.sys.business;

import cn.hutool.core.collection.CollectionUtil;

import com.onelive.common.constants.business.RedisCacheableKeys;
import com.onelive.common.enums.StatusCode;
import com.onelive.common.model.common.ResultInfo;
import com.onelive.common.model.req.sys.SysCacheReq;
import com.onelive.common.utils.redis.RedisUtil;
import com.onelive.manage.service.sys.SysParameterService;
import com.onelive.manage.utils.redis.SysBusinessRedisUtils;
import com.onelive.manage.utils.redis.SystemRedisUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.*;

/**
 * @ClassName SysCacheBusiness
 * @Desc 后台缓存管理业务类
 * @Date 2021/4/6 12:02
 */
@Component
@Slf4j
public class SysCacheBusiness {

    private final static String string = "string";
    private final static String list = "list";
    private final static String map = "map";
    

    @Resource
    private SysParameterService sysParameterService;


    /**
     * 缓存查询
     *
     * @param req
     * @return
     */
    public Object listCache(SysCacheReq req) {
        log.info("listCache:{},type:{},field:{},start:{},end:{}...", req.getKey(), req.getType(), req.getField(), req.getStart(), req.getEnd());

        String key = req.getKey();
        String type = req.getType();
        String field = req.getField();
        Long start = req.getStart();
        Long end = req.getEnd();

        Object result = null;
        if (StringUtils.isNotBlank(key) && StringUtils.isNotBlank(type)) {
            key = key.trim();
            switch (type) {
                case string:
                    if (key.contains("*")) {
                        Set indistinctKeys = RedisUtil.keys(key);
                        Map values = new HashMap();
                        Iterator iterator = indistinctKeys.iterator();
                        while (iterator.hasNext()) {
                            Object keyObj = iterator.next();
                            values.put(keyObj, RedisUtil.get(keyObj));
                        }
                        if (CollectionUtil.isNotEmpty(values)) {
                            result = values;
                        }
                    } else {
                        result = RedisUtil.get(key.trim());
                    }
                    break;
                case list:
                    if (null == start || start < 0) {
                        start = 0L;
                    }
                    if (null == end || end < start && end != -1) {
                        end = start + 1;
                    }

                    Long size = RedisUtil.lGetListSize(key);
                    end = end >= size ? size - 1 : end;
                    result = RedisUtil.lGet(key, start, end);
                    break;
                case map:
                    if (StringUtils.isNotEmpty(field)) {
                        Map<String, Object> map = new HashMap<>();
                        for (String fieldKey : field.split(",")) {
                            map.put(fieldKey, RedisUtil.hGet(key, fieldKey.trim()));
                        }
                        result = map;
                    }
                    break;
                default:
                    log.info("listCache:{},不支持的类型:{},field:{},start:{},end:{}...", key, type, field, start, end);
                    break;
            }
        }
        return result;
    }


    /**
     * 缓存的刷新
     */
    public ResultInfo flushCache(String key) {
        if (StringUtils.isBlank(key)) {
            return ResultInfo.getInstance(StatusCode.PARAM_ERROR);
        }
        String[] keyArray = key.split(",");
        Set<String> keys = new HashSet<>();
        for (String item : keyArray) {
            if (StringUtils.isNotEmpty(item)) {
                if (item.contains("*")) {
                    Set indistinctKeys = RedisUtil.keys(item);
                    keys.addAll(indistinctKeys);
                } else {
                    keys.add(item.trim());
                }
            }
        }
        if (CollectionUtil.isNotEmpty(keys)) {
            RedisUtil.delKeys(keys);
        }
        return ResultInfo.ok(true);
    }
    
    /**
     * 删除缓存,前缀删除
     */
    public ResultInfo flushPrefixCache(String key) {
    	if (StringUtils.isBlank(key)) {
    		return ResultInfo.getInstance(StatusCode.PARAM_ERROR);
    	}
    	SysBusinessRedisUtils.dels(key);
    	return ResultInfo.ok(true);
    }

    /**
     * 删除系统参数里面的所有表记录
     *
     * @param type
     * @return
     */
    public ResultInfo delCacheByType(String type) {
        type = StringUtils.isEmpty(type) ? "" : type.trim();
        switch (type) {
            case "SYS_PARAMS":
                SystemRedisUtils.deleteAllSysParameter();
                break;
            case "SYS_BUS_PARAMS":
                SystemRedisUtils.deleteAllSysBusParameter();
                break;
            default:
                log.info("delCacheByType 不支持的类型：{}", type);
                break;
        }
        return ResultInfo.ok(true);
    }


    /**
     * 添加缓存
     *
     * @param key
     * @param value
     * @return
     */
    public ResultInfo addStringCache(String key, String value) {
        if (StringUtils.isBlank(key) || StringUtils.isBlank(value)) {
            return ResultInfo.getInstance(StatusCode.PARAM_ERROR);
        }
        RedisUtil.set(key, value);
        return ResultInfo.ok(true);
    }

}    
    