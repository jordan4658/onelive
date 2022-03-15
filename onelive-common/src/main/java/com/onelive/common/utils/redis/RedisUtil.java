package com.onelive.common.utils.redis;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.onelive.common.utils.others.StringInnerUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.redis.core.Cursor;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ScanOptions;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * @ClassName RedisUtil
 * @Desc redis 基本工具类
 * @Date 2021/3/15 10:25
 */
@Slf4j
public class RedisUtil {

    public volatile static RedisTemplate redisTemplate;

    public static void init(RedisTemplate redisTemplate) {
        if (null == RedisUtil.redisTemplate && null != redisTemplate) {
            RedisUtil.redisTemplate = redisTemplate;
            log.info("RedisBusinessUtil init:{}", redisTemplate);
        }
    }

    /**
     * 批量删除对应的value
     *
     * @param keys
     */
    public static void remove(final String... keys) {
        for (String key : keys) {
            remove(key);
        }
    }

    public static void remove(final String key) {
        if (exists(key)) {
            redisTemplate.delete(key);
        }
    }

    public static void removeAll(final String key) {
        redisTemplate.delete(redisTemplate.keys(key));
    }

    /**
     * 判断缓存中是否有对应的value
     *
     * @param key
     * @return
     */
    public static boolean exists(final String key) {
        return redisTemplate.hasKey(key);
    }

    /**
     * 更新key对应的失效时间
     *
     * @param key
     * @param expireTime
     * @return
     */
    public static boolean updateExp(final String key, Long expireTime) {
        if (exists(key)) {
            return redisTemplate.expire(key, expireTime, TimeUnit.SECONDS);
        }
        return false;
    }

    /**
     * 读取缓存
     *
     * @param key
     * @return
     */
    public static <T> T get(Object key) {
        log.info("=========redisTemplate=========:"+ (redisTemplate==null));
        if (null == key || StringUtils.isEmpty(key.toString())) {
            return null;
        }
        return (T) redisTemplate.opsForValue().get(key);
    }

    /**
     * 写入缓存  - 字符串类型
     *
     * @param key
     * @param value
     * @return
     */
    public static void set(final String key, Object value) {
        redisTemplate.opsForValue().set(key, value);
    }

    public static boolean set(final String key, Object value, Long expireTime) {
        boolean result = false;
        try {
            if (null != expireTime && expireTime > 0) {
                redisTemplate.opsForValue().set(key, value, expireTime, TimeUnit.SECONDS);
            }
            result = true;
        } catch (Exception e) {
            log.error("---Redis Set key:{} Object:{} ERROR: {}", key, value, e);
        }
        return result;
    }

    public static boolean setMinute(final String key, Object value, Long expireTime) {
        boolean result = false;
        try {
            if (null != expireTime && expireTime > 0) {
                redisTemplate.opsForValue().set(key, value, expireTime, TimeUnit.MINUTES);
            }
            result = true;
        } catch (Exception e) {
            log.error("---Redis Set key:{} Object:{} ERROR: {}", key, value, e);
        }
        return result;
    }

    public static boolean setHours(final String key, Object value, Long expireTime) {
        boolean result = false;
        try {
            if (null != expireTime && expireTime > 0) {
                redisTemplate.opsForValue().set(key, value, expireTime, TimeUnit.HOURS);
            }
            result = true;
        } catch (Exception e) {
            log.error("---Redis Set key:{} Object:{} ERROR: {}", key, value, e);
        }
        return result;
    }

    public static boolean setDays(final String key, Object value, Long expireTime) {
        boolean result = false;
        try {
            if (null != expireTime && expireTime > 0) {
                redisTemplate.opsForValue().set(key, value, expireTime, TimeUnit.DAYS);
            }
            result = true;
        } catch (Exception e) {
            log.error("---Redis Set key:{} Object:{} ERROR: {}", key, value, e);
        }
        return result;
    }


    /**
     * 写入缓存 - Set集合
     *
     * @param key
     * @param set
     * @return
     */
    public static boolean add(final String key, Set<Object> set, Long expireTime) {
        boolean result = false;
        try {
            if (null != expireTime && expireTime > 0) {
                redisTemplate.opsForSet().add(key, set, expireTime, TimeUnit.SECONDS);
            }
            result = true;
        } catch (Exception e) {
            log.error("---Redis add key:{} Object:{} ERROR: {}", key, set, e);
        }
        return result;
    }


    public static boolean setField(String key, String field, Object value, Long expireTime) {
        boolean result = false;
        try {
            if (null != expireTime && expireTime > 0) {
                redisTemplate.opsForHash().put(key, expireTime, TimeUnit.SECONDS);
            }
            result = true;
        } catch (Exception e) {
            log.error("---Redis setField key:{} field:{} object:{} ERROR: {}", key, field, value, e);
        }
        return result;
    }

    public static Object getField(String key, String field) {
        return redisTemplate.opsForHash().get(key, field);
    }

    public static Long removeField(String key, String... fields) {
        Long cnt = 0L;
        for (String field : fields) {
            cnt += removeField(key, field);
        }
        return cnt;
    }

    public static Long removeField(String key, String field) {
        return redisTemplate.opsForHash().delete(key, field);
    }

    public static boolean existsField(String key, String field) {
        return redisTemplate.opsForHash().hasKey(key, field);
    }

    public static void incrField(String key, String field, Long delta) {
        redisTemplate.opsForHash().increment(key, field, delta);
    }

    public static void incrField(String key, String field) {
        redisTemplate.opsForHash().increment(key, field, 1L);
    }


    //=============================common============================

    /**
     * 指定缓存失效时间
     *
     * @param key  键
     * @param time 时间(秒)
     * @return
     */
    public static boolean expire(String key, long time) {
        try {
            if (time > 0) {
                redisTemplate.expire(key, time, TimeUnit.SECONDS);
            }
            return true;
        } catch (Exception e) {
            log.error("expire for key:{},time:{} occur error:{}", key, time, e);
            return false;
        }
    }

    /**
     * 指定缓存失效时间
     *
     * @param key  键
     * @param time 时间(分钟)
     * @return
     */
    public static boolean expireMin(String key, long time) {
        try {
            if (time > 0) {
                redisTemplate.expire(key, time, TimeUnit.MINUTES);
            }
            return true;
        } catch (Exception e) {
            log.error("expire for key:{},time:{} occur error:{}", key, time, e);
            return false;
        }
    }

    /**
     * 指定缓存失效时间
     *
     * @param key  键
     * @param time 时间(小时)
     * @return
     */
    public static boolean expireHours(String key, long time) {
        try {
            if (time > 0) {
                redisTemplate.expire(key, time, TimeUnit.HOURS);
            }
            return true;
        } catch (Exception e) {
            log.error("expire for key:{},time:{} occur error:{}", key, time, e);
            return false;
        }
    }

    /**
     * 指定缓存失效时间
     *
     * @param key  键
     * @param time 时间(天)
     * @return
     */
    public static boolean expireDay(String key, long time) {
        try {
            if (time > 0) {
                redisTemplate.expire(key, time, TimeUnit.DAYS);
            }
            return true;
        } catch (Exception e) {
            log.error("expire for key:{},time:{} occur error:{}", key, time, e);
            return false;
        }
    }


    /**
     * 根据key 获取过期时间
     *
     * @param key 键 不能为null
     * @return 时间(秒) 返回0代表为永久有效 失效时间为负数，说明该主键未设置失效时间（失效时间默认为-1）
     */
    public static long getExpire(String key) {
        return redisTemplate.getExpire(key, TimeUnit.SECONDS);
    }

    /**
     * 判断key是否存在
     *
     * @param key 键
     * @return true 存在 false 不存在
     */
    public static boolean hasKey(String key) {
        try {
            return redisTemplate.hasKey(key);
        } catch (Exception e) {
            log.error("hasKey for key:{}, occur error:{}", key, e);
            return false;
        }
    }

    /**
     * 删除缓存
     *
     * @param keys 可以传一个值 或多个
     */
    public static void del(String... keys) {
        if (keys != null && keys.length > 0) {
            if (keys.length == 1) {
                redisTemplate.delete(keys[0]);
            } else {
                redisTemplate.delete(CollectionUtils.arrayToList(keys));
            }
            log.info("delete redis cache for keys:{} success.", Arrays.toString(keys));
        }
    }
    
    /**
     * 删除缓存,前缀删除
     *
     * @param keys 可以传一个值 或多个
     */
    public static void dels(String key) {
    	if (StringUtils.isNotEmpty(key)) {
    		Set<String> keys = redisTemplate.keys(key + "*");
            redisTemplate.delete(keys);
    		log.info("delete redis cache for keys:{} success.", key);
    	}
    }


    /**
     * 删除缓存
     *
     * @param keys 可以传一个值 或多个
     */
    public static void del(List<String> keys) {
        redisTemplate.delete(keys);
    }


    /**
     * 删除指定key的缓存，多个key间以逗号分隔
     *
     * @param keys
     */
    public static void deleteByKeys(String keys) {
        if (StringUtils.isEmpty(keys)) {
            return;
        }
        List<String> idList = StringInnerUtils.splitStringList(keys);
        redisTemplate.delete(idList);
        log.info("deleteByKeys:{} success.", keys);
    }

    //============================String=============================

    /**
     * 普通缓存获取
     *
     * @param key 键
     * @return 值
     */
    public static Object getValue(String key) {
        return key == null ? null : redisTemplate.opsForValue().get(key);
    }

    /**
     * 普通缓存放入(最长时间)
     *
     * @param key   键
     * @param value 值
     * @return true成功 false失败
     */
    public static boolean setValue(String key, Object value) {
        try {
            redisTemplate.opsForValue().set(key, value);
            return true;
        } catch (Exception e) {
            log.error("setValue for key:{},value:{} occur error:{}", key, value, e);
            return false;
        }

    }

    /**
     * 普通缓存放入并设置时间
     *
     * @param key   键
     * @param value 值
     * @param time  时间(秒) time要大于0 如果time小于等于0 将设置无限期
     * @return true成功 false 失败
     */
    public static boolean setValue(String key, Object value, long time) {
        try {
            if (time > 0) {
                redisTemplate.opsForValue().set(key, value, time, TimeUnit.SECONDS);
            } else {
                setValue(key, value);
            }
            return true;
        } catch (Exception e) {
            log.error("setValue for key:{},value:{},time:{} occur error:{}", key, value, time, e);
            return false;
        }
    }

    /**
     * 递增 此时value值必须为int类型 否则报错
     *
     * @param key   键
     * @param delta 要增加几(大于0)
     * @return
     */
    public static long incr(String key, long delta) {
        if (delta < 0) {
            throw new RuntimeException("递增因子必须大于0");
        }
        return redisTemplate.opsForValue().increment(key, delta);
    }

    /**
     * 递减
     *
     * @param key   键
     * @param delta 要减少几(小于0)
     * @return
     */
    public static long decr(String key, long delta) {
        if (delta < 0) {
            throw new RuntimeException("递减因子必须大于0");
        }
        return redisTemplate.opsForValue().increment(key, -delta);
    }

    //================================Map=================================

    /**
     * HashGet
     *
     * @param key  键 不能为null
     * @param item 项 不能为null
     * @return 值
     */
    public static Object hGet(String key, String item) {
        return redisTemplate.opsForHash().get(key, item);
    }

    /**
     *	 获取hashKey的长度
     *
     * @param key 键
     * @return map的size
     */
    public static Long hMGetSize(String key) {
    	return redisTemplate.opsForHash().size(key);
    }
    
    /**
     * 获取hashKey对应的所有键值
     *
     * @param key 键
     * @return 对应的多个键值
     */
    public static Map<String, Object> hMGet(String key) {
        return redisTemplate.opsForHash().entries(key);
    }

    /**
     * HashSet
     *
     * @param key 键
     * @param map 对应多个键值
     * @return true 成功 false 失败
     */
    public static boolean hSet(String key, Map<Object, Object> map) {
        try {
            redisTemplate.opsForHash().putAll(key, map);
            return true;
        } catch (Exception e) {
            log.error("hSet for key:{},map:{} occur error:{}", key, JSON.toJSONString(map), e);
            return false;
        }
    }
    

	/**
	 * 向一张hash表中放入数据,如果不存在将创建
	 * 
	 * @param key   键
	 * @param item  项
	 * @param value 值
	 * @return true 成功 false失败
	 */
	public static boolean hset(String key, String item, Object value) {
		try {
			redisTemplate.opsForHash().put(key, item, value);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

    /**
     * HashSet 并设置时间
     *
     * @param key  键
     * @param map  对应多个键值
     * @param time 时间(秒)
     * @return true成功 false失败
     */
    public static boolean hMSet(String key, Map<String, Object> map, long time) {
        try {
            redisTemplate.opsForHash().putAll(key, map);
            if (time > 0) {
                expire(key, time);
            }
            return true;
        } catch (Exception e) {
            log.error("hMSet for key:{},map:{},time:{} occur error:{}", key, JSON.toJSONString(map), time, e);
            return false;
        }
    }

    /**
     * 向一张hash表中放入数据,如果不存在将创建
     *
     * @param key   键
     * @param item  项
     * @param value 值
     * @return true 成功 false失败
     */
    public static boolean hSet(String key, String item, Object value) {
        try {
            redisTemplate.opsForHash().put(key, item, value);
            return true;
        } catch (Exception e) {
            log.error("hSet for key:{},item:{},value:{} occur error:{}", key, item, value, e);
            return false;
        }
    }

    /**
     * 向一张hash表中放入数据,如果不存在将创建
     *
     * @param key   键
     * @param item  项
     * @param value 值
     * @param time  时间(秒)  注意:如果已存在的hash表有时间,这里将会替换原有的时间
     * @return true 成功 false失败
     */
    public static boolean hSet(String key, String item, Object value, long time) {
        try {
            redisTemplate.opsForHash().put(key, item, value);
            if (time > 0) {
                expire(key, time);
            }
            return true;
        } catch (Exception e) {
            log.error("hSet for key:{},item:{},value:{},time:{} occur error:{}", key, item, value, time, e);
            return false;
        }
    }

    /**
     * 删除hash表中的值
     *
     * @param key  键 不能为null
     * @param item 项 可以使多个 不能为null
     */
    public static void hDel(String key, Object... item) {
        redisTemplate.opsForHash().delete(key, item);
    }

    /**
     * 判断hash表中是否有该项的值
     *
     * @param key  键 不能为null
     * @param item 项 不能为null
     * @return true 存在 false不存在
     */
    public static boolean hHasKey(String key, String item) {
        return redisTemplate.opsForHash().hasKey(key, item);
    }

    /**
     * hash递增 如果不存在,就会创建一个 并把新增后的值返回
     *
     * @param key  键
     * @param item 项
     * @param by   要增加几(大于0)
     * @return
     */
    public static double hIncr(String key, String item, double by) {
        return redisTemplate.opsForHash().increment(key, item, by);
    }

    /**
     * hash递减
     *
     * @param key  键
     * @param item 项
     * @param by   要减少记(小于0)
     * @return
     */
    public static double hDecr(String key, String item, double by) {
        return redisTemplate.opsForHash().increment(key, item, -by);
    }
    
    
    //============================Zset=============================
    /**
     * 	将数据放入zset缓存
     *
     * @param key    键
     * @param value  值
     * @return 成功个数
     */
    public static boolean zSet(String key, Object value, double score) {
        try {
        	return redisTemplate.opsForZSet().add(key , value, score);
        } catch (Exception e) {
            log.error("zSet for key:{},values:{} occur error:{}", key, JSON.toJSONString(value), e);
            return false;
        }
    }
    
    
    /**
     * 	获取zset指定范围内的集合,从大到小
     *
     * @param key
     * @param start
     * @param end
     * @return
     */
    public static LinkedHashSet<String> reverseRangeZset(String key, long start, long end) {
        try {
        	Set reverseRange = redisTemplate.opsForZSet().reverseRange(key, start, end);
        	if (CollectionUtils.isEmpty(reverseRange)) {
        		return null;
        	}
            return new LinkedHashSet(reverseRange);
        } catch (Exception e) {
            log.error("[RedisUtils.reverseRangeZset] [error] [key is {},start is {},end is {}]", key, start, end, e);
            return null;
        }
    }
    
    /**
     * 	查询集合中指定顺序的值， 0 -1 表示获取全部的集合内容  zrange
     *
     * 	返回有序的集合，score小的在前面
     *
     * @param key
     * @param start
     * @param end
     * @return
     */
    public static Set<String> rangeZset(String key, int start, int end) {
    	try {
    		return redisTemplate.opsForZSet().range(key, start, end);
    	} catch (Exception e) {
            log.error("[RedisUtils.rangeZset] [error] [key is {},start is {},end is {}]", key, start, end, e);
            return null;
        }
    }
    
    /**
     * 	删除元素 zrem
     *
     * @param key
     * @param value
     */
    public void zsetRemove(String key, String value) {
    	try {
    		redisTemplate.opsForZSet().remove(key, value);
    	} catch (Exception e) {
    		log.error("[RedisUtils.zsetRemove] [error] [key is {}，value is {}]", key, value, e);
    	}
    }
    
    /**
     * 	获取zset的size
     *
     * @param key
     * @param start
     * @param end
     * @return
     */
    public static Long zsetSize(String key) {
    	try {
    		return redisTemplate.opsForZSet().size(key);
    	} catch (Exception e) {
    		log.error("[RedisUtils.zsetSize] [error] [key is {}]", key, e);
    		return null;
    	}
    }
    
    //============================set=============================

    /**
     * 根据key获取Set中的所有值
     *
     * @param key 键
     * @return
     */
    public static Set<Object> sGet(String key) {
        try {
            return redisTemplate.opsForSet().members(key);
        } catch (Exception e) {
            log.error("sGet for key:{} occur error:{}", key, e);
            return null;
        }
    }

    /**
     * 根据value从一个set中查询,是否存在
     *
     * @param key   键
     * @param value 值
     * @return true 存在 false不存在
     */
    public static boolean sHasKey(String key, Object value) {
        try {
            return redisTemplate.opsForSet().isMember(key, value);
        } catch (Exception e) {
            log.error("sHasKey for key:{},value:{} occur error:{}", key, value, e);
            return false;
        }
    }

    /**
     * 将数据放入set缓存
     *
     * @param key    键
     * @param values 值 可以是多个
     * @return 成功个数
     */
    public static long sSet(String key, Object... values) {
        try {
            long result = redisTemplate.opsForSet().add(key, values);
            return result;
        } catch (Exception e) {
            log.error("sSet for key:{},values:{} occur error:{}", key, JSON.toJSONString(values), e);
            return 0;
        }
    }

    /**
     * 将set数据放入缓存
     *
     * @param key    键
     * @param time   时间(秒)
     * @param values 值 可以是多个
     * @return 成功个数
     */
    public static long sSetAndTime(String key, long time, Object... values) {
        try {
            Long count = redisTemplate.opsForSet().add(key, values);
            if (time > 0) {
                expire(key, time);
            }
            return count;
        } catch (Exception e) {
            log.error("sSetAndTime for key:{},time:{},values:{} occur error:{}", key, time, JSON.toJSONString(values), e);
            return 0;
        }
    }

    /**
     * 获取set缓存的长度
     *
     * @param key 键
     * @return
     */
    public static long sGetSetSize(String key) {
        try {
            return redisTemplate.opsForSet().size(key);
        } catch (Exception e) {
            log.error("sGetSetSize for key:{}, occur error:{}", key, e);
            return 0;
        }
    }

    /**
     * 移除值为value的
     *
     * @param key    键
     * @param values 值 可以是多个
     * @return 移除的个数
     */
    public static long setRemove(String key, Object... values) {
        try {
            return redisTemplate.opsForSet().remove(key, values);
        } catch (Exception e) {
            log.error("setRemove for key:{},values:{} occur error:{}", key, JSON.toJSONString(values), e);
            return 0;
        }
    }
    //===============================list=================================

    /**
     * 获取list缓存的内容
     *
     * @param key   键
     * @param start 开始
     * @param end   结束  0 到 -1代表所有值
     * @return
     */
    public static List<Object> lGet(String key, long start, long end) {
        try {
            return redisTemplate.opsForList().range(key, start, end);
        } catch (Exception e) {
            log.error("lGet for key:{},start:{},end:{} occur error:{}", key, start, end, e);
            return null;
        }
    }

    /**
     * 获取list缓存的长度
     *
     * @param key 键
     * @return
     */
    public static long lGetListSize(String key) {
        try {
            return redisTemplate.opsForList().size(key);
        } catch (Exception e) {
            log.error("lGetListSize for key:{} occur error:{}", key, e);
            return 0;
        }
    }

    /**
     * 通过索引 获取list中的值
     *
     * @param key   键
     * @param index 索引  index>=0时， 0 表头，1 第二个元素，依次类推；index<0时，-1，表尾，-2倒数第二个元素，依次类推
     * @return
     */
    public static Object lGetIndex(String key, long index) {
        try {
            return redisTemplate.opsForList().index(key, index);
        } catch (Exception e) {
            log.error("lGetIndex for key:{},index:{} occur error:{}", key, index, e);
            return null;
        }
    }


    /**
     * 将list放入缓存
     *
     * @param key   键
     * @param value 值
     * @return
     */
    public static boolean lSet(String key, List<Object> value) {
        try {
            redisTemplate.opsForList().leftPushAll(key, value);
            return true;
        } catch (Exception e) {
            log.error("lSet for key:{},value:{} occur error:{}", key, JSON.toJSONString(value), e);
            return false;
        }
    }

    /**
     * 将list放入缓存
     *
     * @param key   键
     * @param value 值
     * @param time  时间(秒)
     * @return
     */
    public static boolean lSet(String key, List<Object> value, long time) {
        try {
            redisTemplate.opsForList().leftPushAll(key, value);
            if (time > 0) {
                expire(key, time);
            }
            return true;
        } catch (Exception e) {
            log.error("lSet for key:{},time:{},value:{} occur error:{}", key, time, JSON.toJSONString(value), e);
            return false;
        }
    }


    /**
     * 将list放入缓存
     *
     * @param key   键
     * @param value 值
     * @return
     */
    public static boolean rSet(String key, List<Object> value) {
        try {
            redisTemplate.opsForList().rightPushAll(key, value);
            return true;
        } catch (Exception e) {
            log.error("rSet for key:{},value:{} occur error:{}", key, JSON.toJSONString(value), e);
            return false;
        }
    }

    /**
     * 将list放入缓存
     *
     * @param key   键
     * @param value 值
     * @param time  时间(秒)
     * @return
     */
    public static boolean rSet(String key, List<Object> value, long time) {
        try {
            redisTemplate.opsForList().rightPushAll(key, value);
            if (time > 0) {
                expire(key, time);
            }
            return true;
        } catch (Exception e) {
            log.error("rSet for key:{},time:{},value:{} occur error:{}", key, time, JSON.toJSONString(value), e);
            return false;
        }
    }


    /**
     * 在列表的最左边塞入一个value
     *
     * @param key
     * @param value
     */
    public static boolean lpushObj(String key, Object value) {
        try {
            redisTemplate.opsForList().leftPush(key, value);
            return true;
        } catch (Exception e) {
            log.error("lpushObj for key:{},value:{} occur error:{}", key, value, e);
            return false;
        }
    }

    /**
     * 在列表的最左边塞入一个value
     *
     * @param key
     * @param value
     */
    public static boolean lpushObj(String key, Object value, long time) {
        try {
            redisTemplate.opsForList().leftPush(key, value);
            if (time > 0) {
                expire(key, time);
            }
            return true;
        } catch (Exception e) {
            log.error("lpushObj for key:{},time:{},value:{} occur error:{}", key, time, value, e);
            return false;
        }

    }

    /**
     * @Description 列表最右边弹出元素，且移除元素
     * @Param [key, value]
     * @Return java.lang.Object
     */
    public static Object rpopObj(String key) {
        try {
            return redisTemplate.opsForList().rightPop(key);
        } catch (Exception e) {
            log.error("rpopObj for key:{} occur error:{}", key, e);
        }
        return null;
    }


    /**
     * 在列表的最右边塞入一个value
     *
     * @param key
     * @param value
     */
    public static boolean rpushObj(String key, Object value) {
        try {
            redisTemplate.opsForList().rightPush(key, value);
            return true;
        } catch (Exception e) {
            log.error("rpushObj for key:{} value:{} occur error:{}", key, value, e);
            return false;
        }
    }

    /**
     * 在列表的最右边塞入一个value
     *
     * @param key
     * @param value
     */
    public static boolean rpushObj(String key, Object value, long time) {
        try {
            redisTemplate.opsForList().rightPush(key, value);
            if (time > 0) {
                expire(key, time);
            }
            return true;
        } catch (Exception e) {
            log.error("rpushObj for key:{} value:{} time:{} occur error:{}", key, value, time, e);
            return false;
        }

    }

    /**
     * @Description 列表最左边弹出元素，且移除元素
     * @Param [key, value]
     * @Return java.lang.Object
     */
    public Object lpopObj(String key, Object value) {
        try {
            return redisTemplate.opsForList().leftPop(key);
        } catch (Exception e) {
            log.error("lpopObj for key:{} value:{} occur error:{}", key, value, e);
        }
        return null;
    }


    /**
     * 根据索引修改list中的某条数据
     *
     * @param key   键
     * @param index 索引
     * @param value 值
     * @return
     */
    public static boolean lUpdateIndex(String key, long index, Object value) {
        try {
            redisTemplate.opsForList().set(key, index, value);
            return true;
        } catch (Exception e) {
            log.error("lUpdateIndex for key:{},index:{},value:{} occur error:{}", key, index, value, e);
            return false;
        }
    }

    /**
     * 移除N个值为value
     *
     * @param key   键
     * @param count 移除多少个
     * @param value 值
     * @return 移除的个数
     */
    public static long lRemove(String key, long count, Object value) {
        try {
            Long remove = redisTemplate.opsForList().remove(key, count, value);
            return remove;
        } catch (Exception e) {
            log.error("lRemove for key:{},count:{},value:{} occur error:{}", key, count, value, e);
            return 0;
        }
    }


    /**
     * 设置指定key的过期时间
     */
    public static void setExpire(String key, Long second) {
        setExpire(key, second, TimeUnit.SECONDS);
    }

    public static void setExpire(String key, Long second, TimeUnit unit) {
        try {
            if (StringUtils.isEmpty(key)) {
                return;
            }
            unit = null == unit ? TimeUnit.SECONDS : unit;
            redisTemplate.expire(key, second, unit);
        } catch (Exception e) {
            log.error("setExpireTime:{} expire:{} occur error.", key, second, e);
        }
    }


    /**
     * 删除多个key
     *
     * @param keys 键集合
     * @return 成功删除的个数
     */
    public static long delKeys(final Collection<String> keys) {
        Long ret = redisTemplate.delete(keys);
        return ret == null ? 0 : ret;
    }
    
	/**
	 * 删除hash表中的值
	 * 
	 * @param key  键 不能为null
	 * @param item 项 可以使多个 不能为null
	 */
	public static void hdel(String key, Object... item) {
		redisTemplate.opsForHash().delete(key, item);
	}


//    /**
//     * 模糊匹配出所有的key
//     *
//     * @param key
//     * @return
//     */
//    public static Set keys(String key) {
//        return redisTemplate.keys(key);
//    }


    /**
     * 获取指定前缀的一系列key
     * 使用scan命令代替keys, Redis是单线程处理，keys命令在KEY数量较多时，
     * 操作效率极低【时间复杂度为O(N)】，该命令一旦执行会严重阻塞线上其它命令的正常请求
     *
     * @param keyPrefix
     * @return
     */
    public static Set<String> keys(String keyPrefix) {
        String realKey = keyPrefix + "*";
        try {
            return (Set<String>) redisTemplate.execute((RedisCallback<Set<String>>) connection -> {
                Set<String> binaryKeys = new HashSet<>();
                Cursor<byte[]> cursor = connection.scan(new ScanOptions.ScanOptionsBuilder().match(realKey).count(Integer.MAX_VALUE).build());
                while (cursor.hasNext()) {
                    binaryKeys.add(new String(cursor.next()));
                }
                return binaryKeys;
            });
        } catch (Throwable e) {
            e.printStackTrace();
        }
        return null;
    }
}
