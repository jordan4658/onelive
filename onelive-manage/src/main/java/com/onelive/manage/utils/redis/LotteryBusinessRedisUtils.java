package com.onelive.manage.utils.redis;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.onelive.common.constants.business.LotteryRedisKeys;
import com.onelive.common.model.dto.lottery.LotteryInfoDTO;
import com.onelive.common.model.vo.lottery.LotteryPlayExVo;
import com.onelive.common.model.vo.lottery.LotteryPlayOddsExVo;
import com.onelive.common.mybatis.entity.*;
import com.onelive.common.utils.redis.RedisUtil;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * 彩票相关的Redis工具类
 */
public class LotteryBusinessRedisUtils extends RedisUtil {



    /**
     * 获取彩票大类列表
     * @return
     */
    public static List<LotteryCategory> getLotteryCategoryListByLang(String lang){
        String key = LotteryRedisKeys.LOTTERY_CATEGORY_LIST_LANG_KEY+lang;
        String jsonString = get(key);
        if (StrUtil.isBlank(jsonString)) {
            return null;
        }
        JSONArray array = JSON.parseArray(jsonString);
        List<LotteryCategory> list = JSONObject.parseArray(array.toJSONString(), LotteryCategory.class);
        return list;
    }

    /**
     * 设置彩票大类列表
     */
    public static void setLotteryCategoryListWithLang(List<LotteryCategory> list,String lang){
        if(CollectionUtils.isNotEmpty(list)){
            String key =  LotteryRedisKeys.LOTTERY_CATEGORY_LIST_LANG_KEY+lang;
            String jsonString = JSON.toJSONString(list);
            set(key,jsonString);
        }
    }

    /**
     * 添加彩票大类分类map
     * @param lotteryCategoryList
     */
    public static void setLotteryCategoryMap(List<LotteryCategory> lotteryCategoryList,String lang){
        String key  = LotteryRedisKeys.LOTTERY_CATEGORY_MAP_LANG_KEY+lang;
        Map<Object, Object> lotteryCategoryMap = new HashMap<>();
        if(CollectionUtils.isNotEmpty(lotteryCategoryList)){
            Iterator<LotteryCategory> iterator = lotteryCategoryList.iterator();
            while (iterator.hasNext()){
                LotteryCategory category = iterator.next();
                lotteryCategoryMap.put(category.getCategoryId().toString(), JSON.toJSONString(category));
            }
            hSet(key,lotteryCategoryMap);
        }
    }

    /**
     * 获取彩票大类分类map
     * @return
     */
    public static Map<Integer, LotteryCategory> getLotteryCategoryMapWithLang(String lang){
        Map<Integer, LotteryCategory> map = new HashMap<>();
        String key  = LotteryRedisKeys.LOTTERY_CATEGORY_MAP_LANG_KEY+lang;
        Map<String, Object>  resultMap = hMGet(key);
        if(MapUtil.isNotEmpty(resultMap)){
            Iterator<Map.Entry<String,Object>> entryIterator = resultMap.entrySet().iterator();
            while (entryIterator.hasNext()){
                Map.Entry<String,Object> entry = entryIterator.next();
                LotteryCategory entity = JSONObject.parseObject(entry.getValue().toString(), LotteryCategory.class);
                map.put(Integer.valueOf(entry.getKey()),entity);
            }
        }
        return map;
    }






    /**
     * 获取全部彩种子类列表
     * @param
     * @return
     */
    public static List<Lottery> getAllLotteryWithLang(String lang){
        String key = LotteryRedisKeys.LOTTERY_LIST_LANG_KEY+lang;
        String jsonString = get(key);
        if (StrUtil.isBlank(jsonString)) {
            return null;
        }
        JSONArray array = JSON.parseArray(jsonString);
        return JSONObject.parseArray(array.toJSONString(), Lottery.class);
    }

    /**
     * 添加全部彩种子类列表
     * @param list
     */
    public static void setAllLotteryWithLang(List<Lottery> list,String lang){
        String key = LotteryRedisKeys.LOTTERY_LIST_LANG_KEY+lang;
        if(CollectionUtils.isNotEmpty(list)){
            String listStr = JSONObject.toJSONString(list);
            set(key,listStr);
        }
    }

    /**
     * 添加所有彩票map
     * @param lotteryMap
     */
    public static void setLotteryMapWithLang( Map<Integer, Lottery> lotteryMap,String lang){
        String key  = LotteryRedisKeys.LOTTERY_MAP_LANG_KEY+lang;
        if(MapUtil.isNotEmpty(lotteryMap)){
            Map<Object, Object> lotterySetMap = new HashMap<>();
            Iterator<Map.Entry<Integer, Lottery>> iterator = lotteryMap.entrySet().iterator();
            while (iterator.hasNext()){
                Map.Entry<Integer, Lottery> entry = iterator.next();
                lotterySetMap.put(entry.getKey().toString(),JSON.toJSONString(entry.getValue()));
            }
            hSet(key,lotterySetMap);
        }
    }



    /**
     * 获取所有彩种玩法集合key
     * @return
     */
    public static List<LotteryPlayExVo> getLotteryPlayListWithLang(String lang){
        String key = LotteryRedisKeys.LOTTERY_PLAY_LIST_LANG_KEY+lang;
        String jsonString = get(key);
        if (StrUtil.isBlank(jsonString)) {
            return null;
        }
        JSONArray array = JSON.parseArray(jsonString);
        return JSONObject.parseArray(array.toJSONString(), LotteryPlayExVo.class);
    }

    /**
     * 添加所有彩种玩法的设置信息
     * @param list
     */
    public static void setLotteryPlayListWithLang (List<LotteryPlayExVo> list,String lang){
        String key = LotteryRedisKeys.LOTTERY_PLAY_LIST_LANG_KEY+lang;
        if(CollectionUtils.isNotEmpty(list)){
            String listStr = JSONObject.toJSONString(list);
            set(key,listStr);
        }
    }



    /**
     * 查询所有玩法的设置信息
     * @param
     * @return
     */
    public static List<LotteryPlaySetting> getLotteryPlaySettingsWithLang(String lang){
        String key = LotteryRedisKeys.LOTTERY_PLAY_SETTING_ALL_DATA_LANG+lang;
        String jsonString = get(key);
        if (StrUtil.isBlank(jsonString)) {
            return null;
        }
        JSONArray array = JSON.parseArray(jsonString);
        return JSONObject.parseArray(array.toJSONString(), LotteryPlaySetting.class);
    }

    /**
     * 添加所有玩法的设置信息
     * @param list
     */
    public static void setLotteryPlaySettingsWithLang(List<LotteryPlaySetting> list,String lang){
        String key = LotteryRedisKeys.LOTTERY_PLAY_SETTING_ALL_DATA_LANG+lang;
        if(CollectionUtils.isNotEmpty(list)){
            String listStr = JSONObject.toJSONString(list);
            set(key,listStr);
        }
    }



    /**
     * 查询所有玩法的设置信息
     * @param
     * @return
     */
    public static List<LotteryPlayOddsExVo> getLotteryPlayOddsWithLang(String lang){
        String key = LotteryRedisKeys.LOTTERY_PLAY_ODDS_ALL_DATA_LANG+lang;
        String jsonString = get(key);
        if (StrUtil.isBlank(jsonString)) {
            return null;
        }
        JSONArray array = JSON.parseArray(jsonString);
        return JSONObject.parseArray(array.toJSONString(), LotteryPlayOddsExVo.class);
    }

    /**
     * 保存所有玩法的设置信息
     * @param list
     */
    public static void setLotteryPlayOddsWithLang(List<LotteryPlayOddsExVo> list,String lang){
        String key = LotteryRedisKeys.LOTTERY_PLAY_ODDS_ALL_DATA_LANG+lang;
        if(CollectionUtils.isNotEmpty(list)){
            String listStr = JSONObject.toJSONString(list);
            set(key,listStr);
        }
    }



    /**
     * 缓存彩种及赔率所有信息
     *
     * @param type
     * @param list
     */
    public static void setLotteryAllInfoWithLang(String type, List<LotteryInfoDTO> list, String lang) {
        if (CollectionUtil.isEmpty(list)) {
            return;
        }
        String key = LotteryRedisKeys.LOTTERY_ALL_INFO_LANG+lang;
        if (StringUtils.isNotEmpty(type)) {
            key = key + ":" + type;
        }

        String jsonString = JSON.toJSONString(list);
        set(key,jsonString);
    }


    /**
     * 获取彩种所有信息
     *
     * @param type
     * @return
     */
    public static List<LotteryInfoDTO> getLotteryAllInfoWithLang(String type,String lang) {
        String key = LotteryRedisKeys.LOTTERY_ALL_INFO_LANG+lang;
        if (StringUtils.isNotEmpty(type)) {
            key = key + ":" + type;
        }
        String jsonString = get(key);
        if (StrUtil.isBlank(jsonString)) {
            return null;
        }
        JSONArray array = JSON.parseArray(jsonString);

        List<LotteryInfoDTO> list = JSONObject.parseArray(array.toJSONString(), LotteryInfoDTO.class);
        return list;
    }


    /**
     * 查询所有彩票信息
     * @return
     */
    public static List<Lottery> getLotteryAllInfo() {
        String key = LotteryRedisKeys.LOTTERY_ALL_INFO_LIST;
        String jsonString = get(key);
        if (StrUtil.isBlank(jsonString)) {
            return null;
        }
        JSONArray array = JSON.parseArray(jsonString);
        List<Lottery> list = JSONObject.parseArray(array.toJSONString(), Lottery.class);
        return list;
    }


    /**
     * 保存所有彩票信息
     * @param list
     */
    public static void setLotteryAllInfo(List<Lottery> list) {
        if (CollectionUtil.isEmpty(list)) {
            return;
        }
        String key = LotteryRedisKeys.LOTTERY_ALL_INFO_LIST;
        String jsonString = JSON.toJSONString(list);
        set(key,jsonString,300L);
    }


    /**
     * 查询所有的彩票信息 tableName->name
     * @return
     */
    public static Map<String, Lottery> getLotteryMapInfo() {
        String key = LotteryRedisKeys.LOTTERY_MAP_INFO;
        return get(key);
    }

    /**
     * 保存所有的彩票信息 tableName->name
     * @param map
     */
    public static void setLotteryMapInfo(Map<String, Lottery> map) {
        if (CollectionUtil.isEmpty(map)) {
            return;
        }
        String key = LotteryRedisKeys.LOTTERY_MAP_INFO;
        set(key,map,300L);
    }
}
