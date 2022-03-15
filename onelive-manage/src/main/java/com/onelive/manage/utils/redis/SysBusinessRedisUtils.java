package com.onelive.manage.utils.redis;

import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.onelive.common.constants.business.LotteryRedisKeys;
import com.onelive.common.constants.business.RedisKeys;
import com.onelive.common.mybatis.entity.LotteryPlayOdds;
import com.onelive.common.mybatis.entity.SysBusParameter;
import com.onelive.common.mybatis.entity.SysCountry;
import com.onelive.common.utils.redis.RedisUtil;
import com.onelive.manage.service.sys.SysCountryService;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

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
    
    /**
     * 获取指定数字的自增
     *
     * @param key
     * @return
     */
    public static long createStudioNum(String key, Long num) {
        long value = 0;
        if (exists(key)) {
            value = incr(key, 1);
        } else {
            value = incr(key, num);
        }
        return value;
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
     * 设置国家映射表信息
     * @param countryCode
     * @param entity
     */
    public static void setCountryInfo(String countryCode,String entity){
        set(RedisKeys.SYS_COUNTRY_INFO+countryCode,entity);
    }



    /**
     * 保存多语言列表
     * @param langList
     */
    public static void setLangList(List<String> langList){
        set(RedisKeys.SYS_LANG_LIST,langList);
    }

    /**
     * 获取多语言列表
     * @return
     */
    public static List<String> getLangList(){
        List<String> langList = get(RedisKeys.SYS_LANG_LIST);
        if(langList==null){
            langList = new LinkedList<>();
        }
        return langList;
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



    /** 设置赔率配置表list */
    public static void setOddsSettingList(List<LotteryPlayOdds> list, Integer settingId) {
        if (CollectionUtils.isEmpty(list)) {
            return;
        }
        String key = LotteryRedisKeys.ODDS_LIST_SETTING_KEY + settingId;
        String jsonString = JSON.toJSONString(list);
        set(key, jsonString);
    }


    /** 获取赔率配置list */
    public static List<LotteryPlayOdds> getOddsSettingList(Integer settingId) {
        if (settingId == null) {
            return null;
        }
        String key = LotteryRedisKeys.ODDS_LIST_SETTING_KEY + settingId;
        String jsonString = get(key);
        if (StrUtil.isBlank(jsonString)) {
            return null;
        }
        JSONArray array = JSON.parseArray(jsonString);
        List<LotteryPlayOdds> list = JSONObject.parseArray(array.toJSONString(), LotteryPlayOdds.class);
        return list;
    }


    /**
     * 缓存国家信息
     * @param sysCountryService
     */
    public static void initCountryInfo(SysCountryService sysCountryService) {
        List<SysCountry> list =sysCountryService.getAllCountry();
        List<String> langList = new LinkedList<>();
        if(CollectionUtils.isNotEmpty(list)){
            Iterator<SysCountry> iterator = list.iterator();
            while (iterator.hasNext()){
                SysCountry country = iterator.next();
                langList.add(country.getLang());
                SysBusinessRedisUtils.setCountryInfo(country.getCountryCode(), JSON.toJSONString(country));
            }


        }
        //缓存语言列表
        SysBusinessRedisUtils.setLangList(langList);
    }
}
