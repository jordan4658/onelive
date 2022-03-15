package com.onelive.common.utils.others;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;

import java.util.Date;
import java.util.TimeZone;

/**
 * @author lorenzo
 * @Description: 下注日志时间工具类
 * @date 2021/4/20
 */
public class BetLogTimeUtils {

    private static final String TIME_FORMAT_STYLE = "yyyy-MM-dd HH:mm:ss +08:00";
    private static final String SQL_DATE_FORMAT_STYLE = "yyyy-MM-dd HH:mm:ss";

    private static final Integer HOUR = 1000 * 60 * 60;

    public static DateTime parse(String timeStamp) {
        if (StrUtil.isBlank(timeStamp)) {
            return null;
        }
        return DateUtil.parse(timeStamp, TIME_FORMAT_STYLE);
    }

    public static int getUTCOffset() {
        int rawOffset = TimeZone.getDefault().getRawOffset();
        return rawOffset / HOUR;
    }

    public static DateTime getNow(int utc) {
        return getUTCTime(utc, new Date());
    }

    public static DateTime getUTCTime(int utc, Date dateTime) {
        int utcOffset = BetLogTimeUtils.getUTCOffset();
        return DateUtil.offsetHour(dateTime, utc - utcOffset);
    }

    public static String formatUTCtime(Long timestamp) {
        return formatUTCtime(getUTCOffset(), timestamp);
    }

    public static String formatUTCtime(int utc, Long timestamp) {
        return formatUTCtime(utc, new Date(timestamp));
    }

    public static String formatUTCtime(int utc, Date dateTime) {
        DateTime date = getUTCTime(utc, dateTime);
        return formatSqlTime(date);
    }

    public static String formatSqlTime(Long timestamp) {
        return formatSqlTime(new Date(timestamp));
    }

    public static String formatSqlTime(Date dateTime) {
        return DateUtil.format(dateTime, SQL_DATE_FORMAT_STYLE);
    }

}
