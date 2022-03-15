package com.onelive.common.utils.redis;

import cn.hutool.core.date.DateUtil;
import com.onelive.common.constants.business.RedisKeys;
import com.onelive.common.constants.other.SymbolConstant;
import com.onelive.common.utils.Login.LoginInfoUtil;
import org.apache.commons.lang3.StringUtils;

import java.util.Date;

public class SmsRedisUtils extends RedisUtil {


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
     * 功能描述: 获取手机号最后一次发送时间
     *
     * @param: [phone]
     * @return: java.util.Date
     */
    public static Date getLastSendTime(String phone) {
        Long time = (Long) getValue(LoginInfoUtil.getMerchantCode() + RedisKeys.SEND_MSG + phone);
        if (time == null) {
            return null;
        }
        return DateUtil.date(time);
    }


}
