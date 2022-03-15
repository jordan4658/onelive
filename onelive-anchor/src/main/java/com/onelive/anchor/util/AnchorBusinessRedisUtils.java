package com.onelive.anchor.util;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.alibaba.fastjson.JSON;
import com.onelive.common.constants.business.RedisKeys;
import com.onelive.common.constants.other.SimpleConstant;
import com.onelive.common.constants.other.SymbolConstant;
import com.onelive.common.enums.RedisTimeEnum;
import com.onelive.common.enums.UserStateEnums;
import com.onelive.common.model.dto.login.AppLoginUser;
import com.onelive.common.model.dto.mem.UserStatusDTO;
import com.onelive.common.model.vo.login.CaptchaVo;
import com.onelive.common.mybatis.entity.MemLevelVip;
import com.onelive.common.mybatis.entity.MemUser;
import com.onelive.common.mybatis.entity.SysCountry;
import com.onelive.common.mybatis.entity.SysParameter;
import com.onelive.common.mybatis.mapper.slave.mem.SlaveMemUserMapper;
import com.onelive.common.utils.Login.LoginInfoUtil;
import com.onelive.common.utils.cache.TokenRedisGuavaCacheUtils;
import com.onelive.common.utils.others.SpringUtil;
import com.onelive.common.utils.redis.RedisUtil;
import com.onelive.common.utils.redis.UserBusinessRedisUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.util.CollectionUtils;

import java.util.*;

/**
 * api端redis业务类
 */
public class AnchorBusinessRedisUtils extends RedisUtil {


    //设置用户token缓存,7天过期
    public static void setAccessToken(MemUser memUser, String acctoken, String jsonStr) {
        String accno = memUser.getAccno();
        setAppToken(acctoken, jsonStr, RedisTimeEnum.GLOBAL.getValue());
        setAppUserInfo(accno, acctoken, RedisTimeEnum.GLOBAL.getValue());
        //根据用户ID保存用户信息,给其他服务用
        set(RedisKeys.SYS_USER_INFO + memUser.getId(), jsonStr, RedisTimeEnum.GLOBAL.getValue());
    }

    //设置用户token缓存  key = TOKEN:APP:{acctoken} value = LoginUser.class
    public static void setAppToken(String acctoken, String jsonStr, Long expire) {
        set(LoginInfoUtil.getMerchantCode() + RedisKeys.APP_TOKEN + acctoken, jsonStr, expire);
    }

    //设置用户token缓存  key = TOKEN:APP:{accLogin} value = acctoken
    public static void setAppUserInfo(String accno, String acctoken, Long expire) {
        set(LoginInfoUtil.getMerchantCode() + RedisKeys.APP_LOGIN_USERINFO + accno + ":" + acctoken, acctoken, expire);
    }


    //根据token获取LoginUser对象
    public static AppLoginUser getLoginUserByToken(String token) {
        String key = LoginInfoUtil.getMerchantCode() + RedisKeys.APP_TOKEN + token;
        String result = get(key);
        if (StringUtils.isBlank(result)) {
            return null;
        }
        return JSONUtil.toBean(result, AppLoginUser.class);
    }

    //删除用户token信息
    public static void deleteToken(String account, String token) {
        String key1 = LoginInfoUtil.getMerchantCode() + RedisKeys.APP_TOKEN + token;
        String key2 = LoginInfoUtil.getMerchantCode() + RedisKeys.APP_LOGIN_USERINFO + account + ":" + token;
        deleteByKeys(key1 + "," + key2);
        return;
    }

    //删除用户之前的所有token
    public static void deleteAllToken(MemUser memUser) {
        String accno = memUser.getAccno();
        Set<String> keys = keys(LoginInfoUtil.getMerchantCode() + RedisKeys.APP_LOGIN_USERINFO + accno);
        if (keys != null && keys.size() > 0) {
            deleteTokens(accno, keys);
        }

        //删除token
        UserBusinessRedisUtils.delAppUserToken(memUser.getId());
    }

    //删除用户指定的token信息，用户有多个token同时在线
    public static void deleteTokens(String account, Set<String> tokenKeys) {
        List<String> keys = new ArrayList<>();
        Iterator<String> iterator = tokenKeys.iterator();
        while (iterator.hasNext()) {
            String next = iterator.next();
            String token = StrUtil.removePrefix(next, LoginInfoUtil.getMerchantCode() + RedisKeys.APP_LOGIN_USERINFO + account + ":");
            keys.add(LoginInfoUtil.getMerchantCode() + RedisKeys.APP_TOKEN + token);
            keys.add(LoginInfoUtil.getMerchantCode() + RedisKeys.APP_LOGIN_USERINFO + account + ":" + token);
        }
        if (!CollectionUtils.isEmpty(keys)) {
            deleteByKeys(StringUtils.join(keys, ","));
        }
        return;
    }

    //续期token
    public static void setRenewalToken(String token, AppLoginUser user) {
        //更新redis的过期时间
        String accno = user.getAccno();
        Long userId = user.getId();
        updateExp(LoginInfoUtil.getMerchantCode() + RedisKeys.APP_TOKEN + token, RedisTimeEnum.GLOBAL.getValue());
        updateExp(LoginInfoUtil.getMerchantCode() + RedisKeys.APP_LOGIN_USERINFO + accno + ":" + token, RedisTimeEnum.GLOBAL.getValue());
        updateExp(RedisKeys.APP_LOGIN_USER_TOKEN + userId, RedisTimeEnum.GLOBAL.getValue());
        //更新本地服务缓存
        TokenRedisGuavaCacheUtils.putRenewal(token, SimpleConstant.ZERO);
    }

    /**
     * 删除用户状态标识
     *
     * @param account
     */
    public static void delUserStatus(String account) {
        String frozenStatusKey = LoginInfoUtil.getMerchantCode() + RedisKeys.USER_STATUS + account + ":" + UserStateEnums.FROZENSTATUS.toString();
        String commissionStatusKey = LoginInfoUtil.getMerchantCode() + RedisKeys.USER_STATUS + account + ":" + UserStateEnums.COMMISSIONSTATUS.toString();
        String betStatusKey = LoginInfoUtil.getMerchantCode() + RedisKeys.USER_STATUS + account + ":" + UserStateEnums.BETSTATUS.toString();
        String dispensingStatusKey = LoginInfoUtil.getMerchantCode() + RedisKeys.USER_STATUS + account + ":" + UserStateEnums.DISPENSINGSTATUS.toString();
        String superlivemanageStatusKey = LoginInfoUtil.getMerchantCode() + RedisKeys.USER_STATUS + account + ":" + UserStateEnums.SUPERLIVEMANAGESTATUS.toString();

        redisTemplate.executePipelined(new RedisCallback<Object>() {
            @Override
            public Object doInRedis(RedisConnection redisConnection) throws DataAccessException {
                redisConnection.del(frozenStatusKey.getBytes());
                redisConnection.del(commissionStatusKey.getBytes());
                redisConnection.del(betStatusKey.getBytes());
                redisConnection.del(dispensingStatusKey.getBytes());
                redisConnection.del(superlivemanageStatusKey.getBytes());
                return null;
            }
        });
    }


    /**
     * 设置用户状态标识
     *
     * @param memUser
     */
    public static void setUserStatus(MemUser memUser) {
        String account = memUser.getAccno();
        String frozenStatusKey = LoginInfoUtil.getMerchantCode() + RedisKeys.USER_STATUS + account + ":" + UserStateEnums.FROZENSTATUS.toString();
        String commissionStatusKey = LoginInfoUtil.getMerchantCode() + RedisKeys.USER_STATUS + account + ":" + UserStateEnums.COMMISSIONSTATUS.toString();
        String betStatusKey = LoginInfoUtil.getMerchantCode() + RedisKeys.USER_STATUS + account + ":" + UserStateEnums.BETSTATUS.toString();
        String dispensingStatusKey = LoginInfoUtil.getMerchantCode() + RedisKeys.USER_STATUS + account + ":" + UserStateEnums.DISPENSINGSTATUS.toString();
        String superlivemanageStatusKey = LoginInfoUtil.getMerchantCode() + RedisKeys.USER_STATUS + account + ":" + UserStateEnums.SUPERLIVEMANAGESTATUS.toString();


        redisTemplate.executePipelined(new RedisCallback<Object>() {
            @Override
            public Object doInRedis(RedisConnection redisConnection) throws DataAccessException {
                RedisSerializer serializer = redisTemplate.getValueSerializer();
                redisConnection.set(frozenStatusKey.getBytes(), serializer.serialize(memUser.getIsFrozen()));
                redisConnection.set(commissionStatusKey.getBytes(), serializer.serialize(memUser.getIsCommission()));
                redisConnection.set(betStatusKey.getBytes(), serializer.serialize(memUser.getIsBet()));
                redisConnection.set(dispensingStatusKey.getBytes(), serializer.serialize(memUser.getIsDispensing()));
                redisConnection.set(superlivemanageStatusKey.getBytes(), serializer.serialize(memUser.getIsSuperLiveManage()));
                return null;
            }
        });
    }

    /**
     * 获取用户状态
     *
     * @param account
     * @param userId
     * @return
     */
    public static UserStatusDTO getUserStatus(String account, Long userId) {
        UserStatusDTO dto = new UserStatusDTO();
        //获取用户信息
        String preKey = LoginInfoUtil.getMerchantCode() + RedisKeys.USER_STATUS + account + ":";

        //1、用户冻结状态
        String frozenStatusKey = preKey + UserStateEnums.FROZENSTATUS.toString();
        //2、用户返点状态
        String commissionStatusKey = preKey + UserStateEnums.COMMISSIONSTATUS.toString();
        //3、用户投注状态
        String betStatusKey = preKey + UserStateEnums.BETSTATUS.toString();
        //4、用户出款状态
        String dispensingStatusKey = preKey + UserStateEnums.DISPENSINGSTATUS.toString();
        //5、直播超级管理员状态
        String superlivemanageStatusKey = preKey + UserStateEnums.SUPERLIVEMANAGESTATUS.toString();
        //6、当前用户所在国家
        String countryKey = preKey + UserStateEnums.COUNTRY.name();

        List<Object> resultList = redisTemplate.executePipelined(new RedisCallback<Object>() {
            @Override
            public Object doInRedis(RedisConnection redisConnection) throws DataAccessException {
                redisConnection.get(frozenStatusKey.getBytes());
                redisConnection.get(commissionStatusKey.getBytes());
                redisConnection.get(betStatusKey.getBytes());
                redisConnection.get(dispensingStatusKey.getBytes());
                redisConnection.get(superlivemanageStatusKey.getBytes());
                redisConnection.get(countryKey.getBytes());
                return null;
            }
        });

        //若不存在或者缺失部分状态
        if (CollectionUtils.isEmpty(resultList) || resultList.stream().anyMatch(m -> m == null)) {
            SlaveMemUserMapper mapper = SpringUtil.getBean(SlaveMemUserMapper.class);
            MemUser memUser = mapper.selectById(userId);
            if (memUser != null) {
                resultList = new ArrayList<>();
                resultList.add(memUser.getIsFrozen());
                resultList.add(memUser.getIsCommission());
                resultList.add(memUser.getIsBet());
                resultList.add(memUser.getIsDispensing());
                resultList.add(memUser.getIsSuperLiveManage());
                resultList.add(memUser.getCountryId());

                set(frozenStatusKey, memUser.getIsFrozen());
                set(commissionStatusKey, memUser.getIsCommission());
                set(betStatusKey, memUser.getIsBet());
                set(dispensingStatusKey, memUser.getIsDispensing());
                set(superlivemanageStatusKey, memUser.getIsSuperLiveManage());
                set(countryKey, memUser.getCountryId());
            }
        }

        if (!CollectionUtils.isEmpty(resultList)) {
            dto.setIsFrozen((Boolean) resultList.get(0));
            dto.setIsCommission((Boolean) resultList.get(1));
            dto.setIsBet((Boolean) resultList.get(2));
            dto.setIsDispensing((Boolean) resultList.get(3));
            dto.setIsSuperLiveManage((Boolean) resultList.get(4));
            Object countryId = resultList.get(5);
            if (countryId != null) {
                dto.setCountryId(Long.valueOf(countryId.toString()));
            }
        }
        return dto;
    }


    /**
     * 功能描述: 获取手机号最后一次发送时间
     *
     * @param: [phone]
     * @return: java.util.Date
     */
    public static Date getLastSendTime(String phone) {
        return (Date) getValue(LoginInfoUtil.getMerchantCode() + RedisKeys.SEND_MSG + phone);
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
        set(LoginInfoUtil.getMerchantCode() + RedisKeys.SEND_MSG + phone, date, countDown.longValue() + 1);
    }

    /**
     * 功能描述: 按照日期记录当天操作次数
     *
     * @param: [key, val]
     * @return: intl
     * @date: 2020/7/5 20:07
     */
    public static long limitByCurrentDay(String key, String val) {
        if (StringUtils.isBlank(val)) return 0l;
        String dateStr = DateUtil.today();
        String dateKey = LoginInfoUtil.getMerchantCode() + key + val + SymbolConstant.UNDERLINE + dateStr;
        long value = 0;
        if (exists(dateKey)) {
            value = incr(dateKey, 1);
        } else {
            value = incr(dateKey, 1);
            expireDay(dateKey, 1);
        }
        return value;
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

    /**
     * 功能描述: 判断当天日期是否有key值
     *
     * @param: [key, val]
     */
    public static Boolean isExistCurrentDayKey(String key, String val) {
        Boolean result = true;
        String dateStr = DateUtil.today();
        String dateKey = LoginInfoUtil.getMerchantCode() + key + val + SymbolConstant.UNDERLINE + dateStr;
        if (!RedisUtil.exists(dateKey)) {
            //一天后过期
            setDays(dateKey, 0, 1l);
            result = false;
        }
        return result;
    }

    /**
     * 设置图片验证码标识
     *
     * @param vo
     */
    public static void setCaptchaKey(CaptchaVo vo) {
        setMinute(LoginInfoUtil.getMerchantCode() + RedisKeys.CAPTCHA_KEY + vo.getCaptchaKey(), vo.getImgCode(), 5l);
    }

    /**
     * 获取图片验证码标识
     *
     * @param captchaKey
     * @return
     */
    public static String getCaptchaKey(String captchaKey) {
        return (String) getValue(LoginInfoUtil.getMerchantCode() + RedisKeys.CAPTCHA_KEY + captchaKey);
    }

    /**
     * 设置国家映射表信息
     *
     * @param countryCode
     * @param entity
     */
    public static void setCountryInfo(String countryCode, String entity) {
        set(LoginInfoUtil.getMerchantCode() + RedisKeys.SYS_COUNTRY_INFO + countryCode, entity);
    }


    /**
     * 获取国家信息表
     *
     * @param countryCode
     * @return
     */
    public static SysCountry getCountryInfo(String countryCode) {
        if (StringUtils.isBlank(countryCode)) {
            return null;
        }
        String country = get(LoginInfoUtil.getMerchantCode() + RedisKeys.SYS_COUNTRY_INFO + countryCode);
        if (StringUtils.isBlank(country)) {
            return null;
        }
        return JSONUtil.toBean(country, SysCountry.class);
    }



    /**
     * 设置等级映射表信息
     *
     * @param level
     * @param entity
     */
    public static void setVipLevelInfo(Integer level, String entity) {
        set(RedisKeys.MEM_LEVEL_INFO + level, entity);
    }

    /**
     * 获取等级映射信息表
     *
     * @param level
     * @return
     */
    public static MemLevelVip getVipLevelInfo(Integer level) {
        String entity = get(RedisKeys.MEM_LEVEL_INFO + level);
        if (StringUtils.isBlank(entity)) {
            return null;
        }
        return JSONUtil.toBean(entity, MemLevelVip.class);
    }


    /**
     * 设置当前用户所在国家
     *
     * @param userId
     */
    public static void setMemCurrentArea(Long userId, String area) {
        if (StringUtils.isNotBlank(area)) {
            set(LoginInfoUtil.getMerchantCode() + RedisKeys.MEM_CURRENT_AREA + userId, area, RedisTimeEnum.ONEDAY.getValue());
        }
    }

    /**
     * 获取用户所在地区（国家）
     *
     * @param userId
     * @return
     */
    public static String getMemCurrentArea(Long userId) {
        String area = get(LoginInfoUtil.getMerchantCode() + RedisKeys.MEM_CURRENT_AREA + userId);
        if (StringUtils.isBlank(area)) {
            return "";
        }
        return area;
    }

    /**
     * 设置登录保护，一小时内连续输错5次
     *
     * @param account
     */
    public static void setLoginProtect(String account, Integer loginNum) {
        set(LoginInfoUtil.getMerchantCode() + RedisKeys.LOGIN_PROTECT + account, loginNum, RedisTimeEnum.ONEHOUR.getValue());
    }

    /**
     * 获取登录保护
     *
     * @param account
     * @return
     */
    public static Integer getLoginProtect(String account) {
        Integer num = get(LoginInfoUtil.getMerchantCode() + RedisKeys.LOGIN_PROTECT + account);
        if (num == null) {
            num = 0;
        }
        return num;
    }

    /**
     * 设置系统参数
     *
     * @param key
     * @param entity
     */
    public static void setSysParameter(String key, SysParameter entity) {
        if (StringUtils.isNotBlank(key)) {
            set(RedisKeys.SYS_PARAMETER_CODE + key.toUpperCase(), JSON.toJSONString(entity));
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
        String entity = get(RedisKeys.SYS_PARAMETER_CODE + key.toUpperCase());
        if (StringUtils.isBlank(entity)) return null;
        return JSONUtil.toBean(entity, SysParameter.class);
    }


    /**
     * 设置游客设备标识
     *
     * @param userId
     * @param deviceId
     */
    public static void setLoginTourist(String deviceId, Long userId) {
        String key = LoginInfoUtil.getMerchantCode() + RedisKeys.LOGIN_TOURIST;
        set(key + deviceId, String.valueOf(userId));
    }

    /**
     * 获取设备对应的游客登录信息
     *
     * @param deviceId
     * @return
     */
    public static Long getLoginTourist(String deviceId) {
        String key = LoginInfoUtil.getMerchantCode() + RedisKeys.LOGIN_TOURIST;
        String value = get(key + deviceId);
        return StrUtil.isBlank(value) ? null : Long.valueOf(value);
    }


    /**
     * 设置游客设备标识对应的国家编号
     *
     * @param countryCode
     * @param deviceId
     */
    public static void setTouristCountryCode(String deviceId, String countryCode) {
        String key = LoginInfoUtil.getMerchantCode() + RedisKeys.LOGIN_TOURIST_COUNTRY_CODE + deviceId;
        set(key, countryCode);
    }

    /**
     * 获取游客设备标识对应的国家编号
     *
     * @param deviceId
     * @return
     */
    public static String getTouristCountryCode(String deviceId) {
        String key = LoginInfoUtil.getMerchantCode() + RedisKeys.LOGIN_TOURIST_COUNTRY_CODE + deviceId;
        return get(key);
    }


    /**
     * 移除游客的国家代码
     *
     * @param deviceId
     */
    public static void removeTouristCountryCode(String deviceId) {
        String key = LoginInfoUtil.getMerchantCode() + RedisKeys.LOGIN_TOURIST_COUNTRY_CODE + deviceId;
        remove(key);
    }


    /**
     * 设置游客绑定手机号
     *
     * @param deviceId
     */
    public static void setVisitorPhone(String deviceId) {
        String key = LoginInfoUtil.getMerchantCode() + RedisKeys.LOGIN_TOURIST_IS_PHONE + deviceId;
        set(key, 1);
    }

    /**
     * 游客是否已绑定手机号
     *
     * @param deviceId
     * @return
     */
    public static Boolean getVisitorPhone(String deviceId) {
        String key = LoginInfoUtil.getMerchantCode() + RedisKeys.LOGIN_TOURIST_IS_PHONE + deviceId;
        if (get(key) != null) return true;
        return false;
    }


    /**
     * 获取多语言列表
     *
     * @return
     */
    public static List<String> getLangList() {
        List<String> langList = get(RedisKeys.SYS_LANG_LIST);
        if (langList == null) {
            langList = new LinkedList<>();
        }
        return langList;
    }


}
