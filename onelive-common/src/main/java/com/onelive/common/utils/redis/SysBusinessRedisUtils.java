package com.onelive.common.utils.redis;

import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.onelive.common.constants.business.RedisKeys;
import com.onelive.common.mybatis.entity.SysBusParameter;
import com.onelive.common.mybatis.entity.SysCountry;
import com.onelive.common.mybatis.entity.SysParameter;
import com.onelive.common.mybatis.entity.SysUser;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * @author lorenzo
 * @Description: 系统业务Redis工具
 * @date 2021/4/6
 */
public class SysBusinessRedisUtils extends RedisUtil {


    /** 设置业务参数 */
    public static void setSysBusParameter(SysBusParameter parameter) {
        if (parameter == null || StrUtil.isBlank(parameter.getParamCode())) {
            return;
        }
        String key = RedisKeys.SYS_BUS_PARAMETER_CODE + parameter.getParamCode();
        String jsonString = JSON.toJSONString(parameter);
        set(key, jsonString);
    }

    /** 获取业务参数 */
    public static SysBusParameter getSysBusParameter(String code) {
        if (StrUtil.isBlank(code)) {
            return null;
        }
        String key = RedisKeys.SYS_BUS_PARAMETER_CODE + code;
        String jsonString = get(key);
        if (StrUtil.isBlank(jsonString)) {
            return null;
        }
        return JSON.parseObject(jsonString, SysBusParameter.class);
    }


    /** 删除业务参数 */
    public static void delSysBusParameter(String code) {
        if (StrUtil.isBlank(code)) {
            return;
        }
        String key = RedisKeys.SYS_BUS_PARAMETER_CODE + code;
        del(key);
    }


    /** 设置业务参数list */
    public static void setSysBusParameterList(List<SysBusParameter> list, String pcode) {
        if (CollectionUtils.isEmpty(list)) {
            return;
        }
        String key = RedisKeys.SYS_BUS_PARAMETER_CODE + pcode;
        String jsonString = JSON.toJSONString(list);
        set(key, jsonString);
    }


    /** 获取业务参数list */
    public static List<SysBusParameter> getSysBusParameterList(String pCode) {
        if (StrUtil.isBlank(pCode)) {
            return null;
        }
        String key = RedisKeys.SYS_BUS_PARAMETER_CODE + pCode;
        String jsonString = get(key);
        if (StrUtil.isBlank(jsonString)) {
            return null;
        }
        JSONArray array = JSON.parseArray(jsonString);
        List<SysBusParameter> list = JSONObject.parseArray(array.toJSONString(), SysBusParameter.class);
        return list;
    }


    /** 删除多个业务参数 */
    public static void delSysBusParameters(String... code) {
        if (code == null && code.length == 0) {
            return;
        }
        for (int i = 0; i < code.length; i++) {
            code[i] = RedisKeys.SYS_BUS_PARAMETER_CODE + code[i];
        }
        del(code);
    }

    /**
     * 删除所有业务系统参数缓存
     */
    public static void deleteAllSysBusParameter() {
        Set<String> paramKeys = RedisUtil.keys(RedisKeys.SYS_BUS_PARAMETER_CODE);
        if (paramKeys != null && paramKeys.size() > 0) {
            deleteByKeys(StringUtils.join(paramKeys, ","));
        }
    }


    /**
     * 获取系统参数
     *
     * @param key
     * @return
     */
    public static SysParameter getSysParameter(String key) {
        if (null == key) {
            return null;
        }
        SysParameter sysParameter = JSONObject.parseObject(get(RedisKeys.SYS_PARAMETER_CODE + key), SysParameter.class);
        return sysParameter;
    }

    /**
     * 增加系统参数
     *
     * @param info
     */
    public static void addSysParameter(SysParameter info) {
        if (null == info || StringUtils.isEmpty(info.getParamCode())) {
            return;
        }
        set(RedisKeys.SYS_PARAMETER_CODE + info.getParamCode().toUpperCase(), JSON.toJSONString(info));
    }


    /**
     * 删除系统参数
     *
     * @param code
     */
    public static void deleteSysParameter(String code) {
        if (StringUtils.isNotBlank(code)) {
            code = code.trim().toUpperCase();
            del(RedisKeys.SYS_PARAMETER_CODE + code);
        }
    }

    /**
     * 删除所有系统参数缓存
     */
    public static void deleteAllSysParameter() {
        Set<String> paramKeys = RedisUtil.keys(RedisKeys.SYS_PARAMETER_CODE);
        if (paramKeys != null && paramKeys.size() > 0) {
            deleteByKeys(StringUtils.join(paramKeys, ","));
        }
    }


    /**
     * 功能描述: 获取手机号最后一次发送时间
     *
     * @param: [phone]
     * @return: java.util.Date
     */
    public static Date getLastSendTime(String phone) {
        return (Date) getValue(RedisKeys.SEND_MSG + phone);
    }

    /**
     * 功能描述: 设置手机号最新发送时间
     *
     * @param: phone 手机号码
     * @param: date 发送时间
     * @param: countDown 有效时间
     * @return: void
     */
    public static void setLastSendTime(String phone, Date date, Long countDown) {
        set(RedisKeys.SEND_MSG + phone, date, countDown.longValue() + 1);
    }

    /** 设置根据手机查询的系统用户缓存 */
    public static void setSysUserByPhone(String phone, SysUser sysUser) {
        String key = RedisKeys.SYS_USER_BY_PHONE + phone;
        String jsonString = JSON.toJSONString(sysUser);
        set(key, jsonString, 60L * 60L); // 1 hour
    }

    /** 获取根据手机查询的系统用户缓存 */
    public static SysUser getSysUserByPhone(String phone) {
        String key = RedisKeys.SYS_USER_BY_PHONE + phone;
        String jsonString = get(key);
        if (StrUtil.isBlank(jsonString)) {
            return null;
        }
        return JSON.parseObject(jsonString, SysUser.class);
    }

    /** 删除根据手机号码查询的系统用户缓存*/
    public static void delSysUserByPhone(String phone) {
        del(RedisKeys.SYS_USER_BY_PHONE + phone);
    }


    /**
     * 清除杀号缓存
     */
    public static void deleteAllKillConfig() {
        String keyPrefix = "kill*";
        Set<String> keys = redisTemplate.keys(keyPrefix);
        if (keys.size() > 0) {
            redisTemplate.delete(keys);
        }
    }



    /**
     * 获取国家信息表
     * @param countryCode
     * @return
     */
    public static SysCountry getCountryInfo(String countryCode) {
        String country = get(RedisKeys.SYS_COUNTRY_INFO+countryCode);
        if (StringUtils.isBlank(country)) {
            return null;
        }
        return JSONUtil.toBean(country, SysCountry.class);
    }



    /**
     * 设置所有国家列表
     *
     * @param list
     */
    public static void setCountryList(List<SysCountry> list) {
        if (org.apache.commons.collections4.CollectionUtils.isEmpty(list)) {
            return;
        }
        String key = RedisKeys.SYS_COUNTRY_LIST;
        String jsonString = JSON.toJSONString(list);
        set(key, jsonString);
    }


    /**
     * 获取所有国家列表信息
     *
     * @return
     */
    public static List<SysCountry> getCountryList() {
        String key = RedisKeys.SYS_COUNTRY_LIST;
        String jsonString = get(key);
        if (StrUtil.isBlank(jsonString)) {
            return null;
        }
        JSONArray array = JSON.parseArray(jsonString);
        if (array == null) return null;
        List<SysCountry> list = JSONObject.parseArray(array.toJSONString(), SysCountry.class);
        return list;
    }


}
