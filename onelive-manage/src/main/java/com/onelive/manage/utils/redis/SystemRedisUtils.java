package com.onelive.manage.utils.redis;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.onelive.common.constants.business.LotteryRedisKeys;
import com.onelive.common.constants.business.RedisKeys;
import com.onelive.common.enums.SysParamEnum;
import com.onelive.common.exception.BusinessException;
import com.onelive.common.model.dto.login.LoginUser;
import com.onelive.common.model.dto.lottery.LotteryInfoDTO;
import com.onelive.common.mybatis.entity.*;
import com.onelive.common.utils.pay.RandomUtil;
import com.onelive.common.utils.redis.RedisUtil;
import com.onelive.manage.common.constant.SystemRedisKeys;
import com.onelive.manage.service.sys.SysParameterService;
import com.onelive.manage.utils.encrypt.MD5;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.*;

/**
 * @ClassName SystemRedisUtils
 * @Desc 系统redis缓存工具类
 * @Date 2021/3/26 10:09
 */
public class SystemRedisUtils extends RedisUtil {


    /**
     * @param token
     * @param user
     * @param validTime
     */
    public static void setLoginUserJson(String token, LoginUser user, Long validTime) {
        set(SystemRedisKeys.TOKEN + token, JSON.toJSONString(user), validTime);
    }

    /**
     * @param token
     * @return
     */
    public static String getLoginUserJson(String token) {
        return get(SystemRedisKeys.TOKEN + token);
    }


    /**
     * @param token
     * @param token
     * @param validTime
     */
    public static void setLoginToken(Long userId, String token, Long validTime) {
        set(SystemRedisKeys.LOGIN_USERINFO + userId, token, validTime);
    }

    /**
     * @param userId
     * @return
     */
    public static String getLoginToken(String userId) {
        return get(SystemRedisKeys.LOGIN_USERINFO + userId);
    }


    /**
     * 生成token
     *
     * @param userSessionKey
     * @param loginUser
     * @param sysParamService
     * @return
     */
    public static String createToken(String userSessionKey, LoginUser loginUser, SysParameterService sysParamService) {
        String token;
        if (loginUser == null) {
            return null;
        }
        String seckey = userSessionKey + RandomUtil.uuid();
        token = MD5.md5(seckey, "UTF-8");
        loginUser.setAccToken(token);
        // 重新设置sessionid 相关值
        SysParameter bs = sysParamService.getByCode(SysParamEnum.SESSION_TIME_BACK.name());
        if (bs == null || StrUtil.isEmpty(bs.getParamValue())) {
            throw new BusinessException("系统参数(session_time_back)异常");
        }
        Long userSessionIdOutTime = (Long.parseLong(bs.getParamValue())) * 60;
        // 通过sessionid 的 获取 缓存对象
        setLoginToken(loginUser.getUserId(), token, userSessionIdOutTime);
        setLoginUserJson(token, loginUser, userSessionIdOutTime);

        return token;
    }

    public static List<Long> getParentFuncIdListNode(Long funcId) {
        String key = SystemRedisKeys.SYS_FUNCTION + funcId + "getParentFuncIdListNode";
        if (hasKey(key)) {
            String json = get(key);
            return JSON.parseArray(json, Long.class);
        }
        return null;
    }

    public static void setParentFuncIdListNode(Long funcId, List<Long> list) {
        String jsonString = JSON.toJSONString(list);
        String key = SystemRedisKeys.SYS_FUNCTION + funcId + "getParentFuncIdListNode";
        set(key, jsonString, 60L);
    }

    /** 获取角色对应的功能ID列表缓存 */
    public static List<Long> getRoleFunIdList(Long roleId) {
        String key = SystemRedisKeys.SYS_ROLE_FUNC + roleId.toString();
        if (hasKey(key)) {
            String json = get(key);
            return JSON.parseArray(json, Long.class);
        }
        return null;
    }

    /** 设置角色对应的功能ID列表缓存 */
    public static void setRoleFunIdList(Long roleId, List<Long> roleFuncIdList) {
        String key = SystemRedisKeys.SYS_ROLE_FUNC + roleId.toString();
        String jsonString = JSON.toJSONString(roleFuncIdList);
        set(key, jsonString, 60L);
    }

    /** 删除角色对应的功能ID列表缓存 */
    public static void delRoleFunIdList(Long roleId) {
        String key = SystemRedisKeys.SYS_ROLE_FUNC + roleId.toString();
        del(key);
    }

    public static SysUser getSysUserByAccLogin(String accLogin) {
        String key = SystemRedisKeys.SYSTEM_USER + "selectByAccLogin" + accLogin;
        if (hasKey(key)) {
            String json = get(key);
            return JSON.parseObject(json, SysUser.class);
        }
        return null;
    }

    public static void setSysUserByAccLogin(String accLogin, SysUser user) {
        set(accLogin, JSON.toJSONString(user));
    }

    public static boolean hasRoleInterface(Long roleId) {
        return hasKey(SystemRedisKeys.ROLE_INTERFACES + roleId);
    }

    public static List<String> getRoleInterface(Long roleId) {
        String key = SystemRedisKeys.ROLE_INTERFACES + roleId;
        if (hasKey(key)) {
            String json = get(key);
            return JSON.parseArray(json, String.class);
        }
        return null;
    }

    public static void setRoleInterface(Long roleId, List<String> interfaceUrls) {
        String key = SystemRedisKeys.ROLE_INTERFACES + roleId;
        String jsonString = JSON.toJSONString(interfaceUrls);
        set(key, jsonString);
    }


    /**
     * 判断是否是系统IP白名单
     *
     * @param key
     * @return
     */
    public static Boolean isExistsWhiteIp(String key) {
        if (StringUtils.isBlank(key)) {
            return false;
        }
        return exists(SystemRedisKeys.IP_WHITE + key);
    }

    /**
     * 添加系统IP白名单
     *
     * @param ip
     */
    public static void addWhiteIp(String ip) {
        if (StringUtils.isBlank(ip)) return;

        set(SystemRedisKeys.IP_WHITE + ip, true);
    }


    /**
     * 删除系统IP白名单
     *
     * @param ip
     */
    public static void delWhiteIp(String ip) {
        if (StringUtils.isNotBlank(ip)) {
            del(SystemRedisKeys.IP_WHITE + ip);
        }
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
     * 删除所有业务系统参数缓存
     */
    public static void deleteAllSysBusParameter() {
        Set<String> paramKeys = RedisUtil.keys(RedisKeys.SYS_BUS_PARAMETER_CODE);
        if (paramKeys != null && paramKeys.size() > 0) {
            deleteByKeys(StringUtils.join(paramKeys, ","));
        }
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



    /////////////////////////////////////////业务方法/////////////////////////////////////////

    /**
     * 迁移代码-------删除彩种类缓存
     */
    public static void deleteLotteryCaches() {

        List<String> keys = new ArrayList<>();
        keys.add(LotteryRedisKeys.LOTTERY_CATEGORY_LIST_KEY);
        keys.add(LotteryRedisKeys.LOTTERY_CATEGORY_MAP_KEY);
        keys.add(LotteryRedisKeys.LOTTERY_LIST_KEY);
        keys.add(LotteryRedisKeys.LOTTERY_MAP_KEY);
        keys.add(LotteryRedisKeys.LOTTERY_ALL_LIST_KEY);
        keys.add(LotteryRedisKeys.LOTTERY_ALL_MAP_KEY);
        keys.add(LotteryRedisKeys.LOTTERY_PLAY_LIST_KEY);
        keys.add(LotteryRedisKeys.LOTTERY_PLAY_MAP_KEY);
        keys.add(LotteryRedisKeys.LOTTERY_PLAY_SETTING_ALL_DATA);
        keys.add(LotteryRedisKeys.LOTTERY_PLAY_ODDS_ALL_DATA);
        keys.add(LotteryRedisKeys.LOTTERY_FAVORITE_USER_DATA_DEFAULT);
        keys.add(LotteryRedisKeys.LOTTERY_FAVORITE_DEFAULT);
        keys.add(LotteryRedisKeys.LOTTERY_ALL_INNER_LIST);


        //删除彩种压缩包缓存
        keys.add(SysParamEnum.LOTTERY_VERSION_ZIP_URL.getCode());
        keys.add(SysParamEnum.LOTTERY_VERSION_ZIP_URL.getCode() + LotteryRedisKeys.SYSTEM_INFO_VALUE_SUFFIX);

        del(keys);


        //带国际化的彩种信息
        String lottery_category_list_lang_key = LotteryRedisKeys.LOTTERY_CATEGORY_LIST_LANG_KEY+"*";
        String lottery_category_map_lang_key = LotteryRedisKeys.LOTTERY_CATEGORY_MAP_LANG_KEY+"*";
        String lottery_all_info_lang = LotteryRedisKeys.LOTTERY_ALL_INFO_LANG+"*";
        String lottery_list_lang_key = LotteryRedisKeys.LOTTERY_LIST_LANG_KEY+"*";
        String lottery_map_lang_key = LotteryRedisKeys.LOTTERY_MAP_LANG_KEY+"*";
        String lottery_play_list_lang_key = LotteryRedisKeys.LOTTERY_PLAY_LIST_LANG_KEY+"*";
        String lottery_play_setting_all_data_lang = LotteryRedisKeys.LOTTERY_PLAY_SETTING_ALL_DATA_LANG+"*";
        String lottery_play_odds_all_data_lang = LotteryRedisKeys.LOTTERY_PLAY_ODDS_ALL_DATA_LANG+"*";

        Set categoryListSet = redisTemplate.keys(lottery_category_list_lang_key);
        Set categoryMapSet = redisTemplate.keys(lottery_category_map_lang_key);
        Set allInfoSet = redisTemplate.keys(lottery_all_info_lang);
        Set lotteryListSet = redisTemplate.keys(lottery_list_lang_key);
        Set lotteryMapSet = redisTemplate.keys(lottery_map_lang_key);
        Set playListSet = redisTemplate.keys(lottery_play_list_lang_key);
        Set playSettingAllDataSet = redisTemplate.keys(lottery_play_setting_all_data_lang);
        Set playOddsAllDataSet = redisTemplate.keys(lottery_play_odds_all_data_lang);


        String favoriteUser = LotteryRedisKeys.LOTTERY_FAVORITE_USER_PREFIX + "*";
        String favoriteUserData = LotteryRedisKeys.LOTTERY_FAVORITE_USER_DATA_PREFIX + "*";
        String lotteryAllInfo = LotteryRedisKeys.LOTTERY_ALL_INFO + "*";
        String lotteryInfo = LotteryRedisKeys.LOTTERY_KEY + "*";
        String oddsSetting = LotteryRedisKeys.ODDS_LIST_SETTING_KEY +"*";

        Set<String> favoriteUserSet = redisTemplate.keys(favoriteUser);
        Set<String> favoriteUserDataSet = redisTemplate.keys(favoriteUserData);
        Set<String> lotteryAllInfoSet = redisTemplate.keys(lotteryAllInfo);
        Set<String> lotteryInfoSet = redisTemplate.keys(lotteryInfo);
        Set<String> oddsSettingSet = redisTemplate.keys(oddsSetting);

        List<String> keySet = new ArrayList<>();
        keySet.addAll(favoriteUserSet);
        keySet.addAll(favoriteUserDataSet);
        keySet.addAll(lotteryAllInfoSet);
        keySet.addAll(lotteryInfoSet);
        keySet.addAll(oddsSettingSet);

        //多语言相关
        keySet.addAll(categoryListSet);
        keySet.addAll(categoryMapSet);
        keySet.addAll(allInfoSet);
        keySet.addAll(lotteryListSet);
        keySet.addAll(lotteryMapSet);
        keySet.addAll(playListSet);
        keySet.addAll(playSettingAllDataSet);
        keySet.addAll(playOddsAllDataSet);

        if (keySet.size() > 0) {
            del(keySet);
        }
    }



    /**
     * 缓存彩种及赔率所有信息
     *
     * @param type
     * @param list
     */
    public static void addLotteryAllInfo(String type, List<LotteryInfoDTO> list) {
        if (CollectionUtil.isEmpty(list)) {
            return;
        }
        String key = LotteryRedisKeys.LOTTERY_ALL_INFO;
        if (StringUtils.isNotEmpty(type)) {
            key = key + "_" + type;
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
    public static List<LotteryInfoDTO> getLotteryAllInfo(String type) {
        String key = LotteryRedisKeys.LOTTERY_ALL_INFO;
        if (StringUtils.isNotEmpty(type)) {
            key = key + "_" + type;
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
     * 获取彩票大类列表
     * @return
     */
    public static List<LotteryCategory> getLotteryCategoryList(){
        String key = LotteryRedisKeys.LOTTERY_CATEGORY_LIST_KEY;
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
    public static void addLotteryCategoryList(List<LotteryCategory> list){
        if(CollectionUtils.isNotEmpty(list)){
            String key =  LotteryRedisKeys.LOTTERY_CATEGORY_LIST_KEY;
            String jsonString = JSON.toJSONString(list);
            set(key,jsonString);
        }
    }


    /**
     * 获取彩票大类分类map
     * @return
     */
    public static Map<Integer, LotteryCategory> getLotteryCategoryMap(){
        Map<Integer, LotteryCategory> map = new HashMap<>();
        String key =  LotteryRedisKeys.LOTTERY_CATEGORY_MAP_KEY;
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
     * 添加彩票大类分类map
     * @param lotteryCategoryList
     */
    public static void addLotteryCategoryMap(List<LotteryCategory> lotteryCategoryList){
        String key  = LotteryRedisKeys.LOTTERY_CATEGORY_MAP_KEY;
        Map<Object, Object> lotteryCategoryMap = new HashMap<>();
        if(CollectionUtils.isNotEmpty(lotteryCategoryList)){
             Iterator<LotteryCategory> iterator = lotteryCategoryList.iterator();
             while (iterator.hasNext()){
                 LotteryCategory category = iterator.next();
                 lotteryCategoryMap.put(category.getCategoryId().toString(),JSON.toJSONString(category));
             }
            hSet(key,lotteryCategoryMap);
        }
    }


    /**
     * 获取全部彩种子类列表
     * @param
     * @return
     */
    public static List<Lottery> getAllLottery( ){
        String key = LotteryRedisKeys.LOTTERY_LIST_KEY;
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
    public static void addAllLottery(List<Lottery> list){
        String key = LotteryRedisKeys.LOTTERY_LIST_KEY;
        if(CollectionUtils.isNotEmpty(list)){
            String listStr = JSONObject.toJSONString(list);
            set(key,listStr);
        }
    }





    /**
     * 获取所有彩票map
     * @return
     */
    public static Map<Integer, Lottery> getLotteryMap(){
        Map<Integer, Lottery> map = new HashMap<>();
        String key =  LotteryRedisKeys.LOTTERY_MAP_KEY;
        Map<String, Object>  resultMap = hMGet(key);
        if(MapUtil.isNotEmpty(resultMap)){
            Iterator<Map.Entry<String,Object>> entryIterator = resultMap.entrySet().iterator();
            while (entryIterator.hasNext()){
                Map.Entry<String,Object> entry = entryIterator.next();
                Lottery entity = JSONObject.parseObject(entry.getValue().toString(), Lottery.class);
                map.put(Integer.valueOf(entry.getKey()),entity);
            }
        }
        return map;
    }

    /**
     * 添加所有彩票map
     * @param lotteryMap
     */
    public static void addLotteryMap(  Map<Integer, Lottery> lotteryMap){
        String key  = LotteryRedisKeys.LOTTERY_MAP_KEY;
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
     * 查询所有玩法的设置信息
     * @param
     * @return
     */
    public static List<LotteryPlaySetting> getLotteryPlaySettings( ){
        String key = LotteryRedisKeys.LOTTERY_PLAY_SETTING_ALL_DATA;
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
    public static void addLotteryPlaySettings(List<LotteryPlaySetting> list){
        String key = LotteryRedisKeys.LOTTERY_PLAY_SETTING_ALL_DATA;
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
    public static List<LotteryPlayOdds> getLotteryPlayOdds( ){
        String key = LotteryRedisKeys.LOTTERY_PLAY_ODDS_ALL_DATA;
        String jsonString = get(key);
        if (StrUtil.isBlank(jsonString)) {
            return null;
        }
        JSONArray array = JSON.parseArray(jsonString);
        return JSONObject.parseArray(array.toJSONString(), LotteryPlayOdds.class);
    }

    /**
     * 添加所有玩法的设置信息
     * @param list
     */
    public static void addLotteryPlayOdds(List<LotteryPlayOdds> list){
        String key = LotteryRedisKeys.LOTTERY_PLAY_ODDS_ALL_DATA;
        if(CollectionUtils.isNotEmpty(list)){
            String listStr = JSONObject.toJSONString(list);
            set(key,listStr);
        }
    }

    /**
     * 获取所有彩种玩法集合key
     * @return
     */
    public static List<LotteryPlay> getLotteryPlayList(){
        String key = LotteryRedisKeys.LOTTERY_PLAY_LIST_KEY;
        String jsonString = get(key);
        if (StrUtil.isBlank(jsonString)) {
            return null;
        }
        JSONArray array = JSON.parseArray(jsonString);
        return JSONObject.parseArray(array.toJSONString(), LotteryPlay.class);
    }

    /**
     * 添加所有彩种玩法的设置信息
     * @param list
     */
    public static void addLotteryPlayList (List<LotteryPlay> list){
        String key = LotteryRedisKeys.LOTTERY_PLAY_LIST_KEY;
        if(CollectionUtils.isNotEmpty(list)){
            String listStr = JSONObject.toJSONString(list);
            set(key,listStr);
        }
    }


    /**
     * 清空直播间公告方案缓存
     */
    public static void deleteLiveNoticeTextCaches() {
        remove(RedisKeys.SYS_LIVE_NOTICE_TEXT_MAP);
    }
}
    