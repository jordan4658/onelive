package com.onelive.common.utils.others;

import cn.hutool.core.date.DateUnit;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.date.format.FastDateFormat;
import lombok.extern.slf4j.Slf4j;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @Description 时间工具类，继承hutool
 * @参考地址：https://www.hutool.cn/docs/#/core/%E6%97%A5%E6%9C%9F%E6%97%B6%E9%97%B4/%E6%97%A5%E6%9C%9F%E6%97%B6%E9%97%B4%E5%B7%A5%E5%85%B7-DateUtil
 */
@Slf4j
public class DateInnerUtil extends DateUtil {

    public final static String DATE_YMD_HMS_SS = "yyyy-MM-dd HH:mm:ss.SSS";
    public static final String FORMAT_YYYY_MM_DD_HHMMSS = "yyyy-MM-dd HH:mm:ss";
    public static final String TIME_PATTERN = "HH:mm:ss";
    public static final String FORMAT_YYYYMMDD = "yyyyMMdd";
    public static final String FORMAT_YYYY_MM_DD = "yyyy-MM-dd";
    public static final String FORMAT_YYYY_MM = "yyyy-MM";
    public static final FastDateFormat NORM_DATETIME_MS_FORMAT = FastDateFormat.getInstance("yyyy-MM-dd HH:mm:ss.SSS");
    public static final FastDateFormat NORM_DATETIME_YYYMMDDHHMMSS_FORMAT = FastDateFormat.getInstance("yyyMMddHHmmss");

    /**
     * 获取当前时间的YYYY-MM-DD HH:mm:ss.sss格式
     */
    public static String getTimeSS() {
        return NORM_DATETIME_MS_FORMAT.format(new Date());
    }

    /**
     * 获取当前时间的yyyMMddHHmmss格式
     */
    public static String getTime_yyyMMddHHmmss() {
        return NORM_DATETIME_YYYMMDDHHMMSS_FORMAT.format(new Date());
    }

    /**
     * @param date
     * @param formatType
     * @return
     */
    public static String dateToStr(Date date, String formatType) {
        SimpleDateFormat df = new SimpleDateFormat(formatType);
        return df.format(date);
    }

    /**
     * 两个日期相差天数
     *
     * @param begin
     * @param end
     * @return
     */
    public static Long betweenDay(Date begin, Date end) {
        return DateUtil.between(begin, end, DateUnit.DAY);
    }

    /**
     * 两个日期相差分钟
     *
     * @param begin
     * @param end
     * @return
     */
    public static Long betweenMinute(Date begin, Date end) {
        return DateUtil.between(begin, end, DateUnit.MINUTE);
    }

    /**
     * 两个日期相差秒数
     *
     * @param begin
     * @param end
     * @return
     */
    public static Long betweenSecond(Date begin, Date end) {
        return DateUtil.between(begin, end, DateUnit.SECOND);
    }

    /**
     * 字符串转Date
     *
     * @param strDate
     * @return
     */
    public static Date parseDate(String strDate) {
        if (StringUtils.isEmpty(strDate)) {
            return null;
        }
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            return simpleDateFormat.parse(strDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }
    /**
     * 字符串转Date
     *
     * @param strDate
     * @return
     */
    public static Date parseDateByPattern(String strDate,String format) {
        if (StringUtils.isEmpty(strDate)) {
            return null;
        }
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
        try {
            return simpleDateFormat.parse(strDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * 计算时间差
     *
     * @param time1
     * @param time2
     * @return
     */
    public static long timeReduce(String time1, String time2) {
        DateFormat df = new SimpleDateFormat(FORMAT_YYYY_MM_DD_HHMMSS);
        try {
            Date t1 = df.parse(time1);
            Date t2 = df.parse(time2);
            return (t1.getTime() - t2.getTime()) / 1000l;
        } catch (ParseException e) {
            log.error("parseDate occur error for time1:{}, time2:{} error:{}", time1, time2, e);
        }
        return 0;
    }


}
