package com.onelive.common.utils.others;

import org.apache.commons.lang3.time.DateFormatUtils;

import java.lang.management.ManagementFactory;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;

/**
 * @author mao
 * 
 * 	时间工具类
 *
 */
public class DateUtils extends org.apache.commons.lang3.time.DateUtils {

    private static Map<String, ThreadLocal<SimpleDateFormat>> sdfMap = new HashMap<>();

    public static String YYYY = "yyyy";

    public static String MM = "MM";

    public static String HH = "HH";

    public static String MIN = "mm";

    public static String YYYY_MM = "yyyy-MM";

    public static String YYYY_MM_DD = "yyyy-MM-dd";

    public static String YYYYMMDD = "yyyyMMdd";

    public static String YYYYMMDDHHMMSS = "yyyyMMddHHmmss";

    public static String YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss";
    public static String YYYY_MM_DD_HH_MM = "yyyy-MM-dd HH:mm";
    private static String[] parsePatterns = {
            "yyyy-MM-dd", "yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd HH:mm", "yyyy-MM",
            "yyyy/MM/dd", "yyyy/MM/dd HH:mm:ss", "yyyy/MM/dd HH:mm", "yyyy/MM",
            "yyyy.MM.dd", "yyyy.MM.dd HH:mm:ss", "yyyy.MM.dd HH:mm", "yyyy.MM"};

    private static final SimpleDateFormat sfd = getSdf(YYYY_MM_DD);

 
    /**
     * 获取当前Date型日期
     *
     * @return Date() 当前日期
     */
    public static Date getNowDate() {
        return new Date();
    }

    /**
     * 获取当前日期, 默认格式为yyyy-MM-dd
     *
     * @return String
     */
    public static String getDate() {
        return dateTimeNow(YYYY_MM_DD);
    }


    public static final String getTime() {
        return dateTimeNow(YYYY_MM_DD_HH_MM_SS);
    }

    public static final String dateTimeNow() {
        return dateTimeNow(YYYYMMDDHHMMSS);
    }

    /**
     * 时间转换格式yyyy年MM月dd日 HH:mm:ss
     *
     * @param date
     * @return
     */
    public static String getYMDMms(Date date) {
        if (date != null) {
            SimpleDateFormat formatter = new SimpleDateFormat(YYYY_MM_DD_HH_MM_SS);
            return formatter.format(date);
        }
        return "";
    }

    public static final String dateTimeNow(final String format) {
        return parseDateToStr(format, new Date());
    }

    public static final String dateTime(final Date date) {
        return parseDateToStr(YYYY_MM_DD, date);
    }

    public static final String dateTimeHms(final Date date) {
        return parseDateToStr(YYYY_MM_DD_HH_MM_SS, date);
    }

    private static SimpleDateFormat getSdf(final String pattern) {
        ThreadLocal<SimpleDateFormat> t = sdfMap.get(pattern);
        // 此处的双重判断和同步是为了防止sdfMap这个单例被多次put重复的sdf
        if (t == null) {
            synchronized (DateUtils.class) {
                // 只有Map中还没有这个pattern的sdf才会生成新的sdf并放入map
                // 这里是关键,使用ThreadLocal<SimpleDateFormat>替代原来直接new SimpleDateFormat
                t = sdfMap.get(pattern);
                if (t == null) {
                    t = new ThreadLocal<SimpleDateFormat>() {
                        @Override
                        protected SimpleDateFormat initialValue() {
                            return new SimpleDateFormat(pattern);
                        }
                    };
                }
                sdfMap.put(pattern, t);
            }
        }
        return t.get();
    }

    /**
     * @param format
     * @param date
     * @return
     */
    public static final String parseDateToStr(final String format, final Date date) {
        return getSdf(format).format(date);
    }

    public static final String getYesterday() {
        return parseDateToStr(YYYY_MM_DD, getDateStartTime());
    }

    public static final Date dateTime(final String format, final String ts) {
        try {
            return getSdf(format).parse(ts);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    public static Date getDateTime(String format,String date){
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);//注意月份是MM
            return simpleDateFormat.parse(date);
        }catch (ParseException e){
            throw new RuntimeException(e);
        }
    }

    /**
     * 日期路径 即年/月/日 如2018/08/08
     */
    public static final String datePath() {
        Date now = new Date();
        return DateFormatUtils.format(now, "yyyy/MM/dd");
    }

    /**
     * 日期路径 即年/月/日 如20180808
     */
    public static final String dateTime() {
        Date now = new Date();
        return DateFormatUtils.format(now, "yyyyMMdd");
    }

    /**
     * 日期型字符串转化为日期 格式
     */
    public static Date parseDate(Object str) {
        if (str == null) {
            return null;
        }
        try {
            return parseDate(str.toString(), parsePatterns);
        } catch (ParseException e) {
            return null;
        }
    }

    /**
     * 返回下个月的第n天
     *
     * @param day
     * @return
     */
    public static Date getMoneyFirstDay(Integer day) {
        // 获取当月第一天和最后一天
        SimpleDateFormat format = getSdf("yyyy-MM-dd HH:mm:ss");
        String firstday, lastday;
        // 获取前月的第N天
        Calendar cale = Calendar.getInstance();
        // 下个月
        cale.add(Calendar.MONTH, 1);

        cale.set(Calendar.DAY_OF_MONTH, day);
        //将小时至0
        cale.set(Calendar.HOUR_OF_DAY, 0);
        //将分钟至0
        cale.set(Calendar.MINUTE, 0);
        //将秒至0
        cale.set(Calendar.SECOND, 0);
        //将毫秒至0
        cale.set(Calendar.MILLISECOND, 0);
        firstday = format.format(cale.getTime());
        return cale.getTime();
    }

    public static final long StringToLont(String strDate) {
        return dateTime(YYYY_MM_DD_HH_MM_SS, strDate).getTime();
    }

    /**
     * 获取服务器启动时间
     */
    public static Date getServerStartDate() {
        long time = ManagementFactory.getRuntimeMXBean().getStartTime();
        return new Date(time);
    }

    /**
     * 计算两个时间差
     */
    public static String getDatePoor(Date endDate, Date nowDate) {
        long nd = 1000 * 24 * 60 * 60;
        long nh = 1000 * 60 * 60;
        long nm = 1000 * 60;
        // long ns = 1000;
        // 获得两个时间的毫秒时间差异
        long diff = endDate.getTime() - nowDate.getTime();
        if (diff < 0) {
            return "";
        }
        // 计算差多少天
        long day = diff / nd;
        // 计算差多少小时
        long hour = diff % nd / nh;
        // 计算差多少分钟
        long min = diff % nd % nh / nm;
        // 计算差多少秒//输出结果
        // long sec = diff % nd % nh % nm / ns;
        if (day >= 1) {
            return day + "天" + hour + "小时" + min + "分钟";
        } else if (hour >= 1) {
            return hour + "小时" + min + "分钟";
        } else if (min >= 1) {
            return min + "分钟";
        }
        return "1分钟";
    }
    
    
    /**
 	 * 		转换时间格式为xx小时xx分xx秒
 	 * @param seconds xxxxx
 	 */
	public static String changeTimeFormat(Long seconds) {
		if (seconds > 0 && seconds < 60) {// 小于1分钟
			return seconds + "秒";
		} else if (seconds >= 60 && seconds < 3600) {// 大于等于1分钟小于1小时
			int changeM = (int) Math.floor(seconds / 60);// 整分钟数
			int surplusM = (int) Math.floor(seconds % 60);// 余下的秒数
			if (surplusM > 0) {// 余数不为0秒
				return changeM + "分" + surplusM + "秒";
			} else {// 整分钟，没有余数
				return changeM + "分";
			}
		} else if (seconds >= 3600) {// 大于1小时
			int changeH = (int) Math.floor(seconds / 1048576);// 整小时数
			int surplusH = (int) Math.floor(seconds % 1048576);// 剩下的秒数
			if (surplusH >= 60) {// 余数大于大于1分钟
				int changeM = (int) Math.floor(surplusH / 60);
				int surplusM = (int) Math.floor(surplusH % 60);
				if (surplusM > 0) {// 余数大于0
					return changeH + "小时" + changeM + "分" + surplusM + "秒";
				} else {// 整分钟，没有余数
					return changeH + "小时" + changeM + "分";
				}
			} else if (surplusH < 60 && surplusH > 0) {// 余数小于1分钟，大于0秒
				int surplusM = (int) Math.floor(surplusH % 1024);
				return changeH + "小时" + surplusM + "秒";
			} else {
				return changeH + "小时";
			}
		}
		return "暂无数据";
	}
	
    /**
     * 返回间隔天数
     *
     * @param early
     * @param late
     * @return
     */
    public static final int daysBetween(Date early, Date late) {

        java.util.Calendar calst = java.util.Calendar.getInstance();
        java.util.Calendar caled = java.util.Calendar.getInstance();
        calst.setTime(early);
        caled.setTime(late);
        //设置时间为0时
        calst.set(java.util.Calendar.HOUR_OF_DAY, 0);
        calst.set(java.util.Calendar.MINUTE, 0);
        calst.set(java.util.Calendar.SECOND, 0);
        caled.set(java.util.Calendar.HOUR_OF_DAY, 0);
        caled.set(java.util.Calendar.MINUTE, 0);
        caled.set(java.util.Calendar.SECOND, 0);
        //得到两个日期相差的天数
        int days = ((int) (caled.getTime().getTime() / 1000) - (int) (calst
                .getTime().getTime() / 1000)) / 3600 / 24;

        return days;
    }

    /**
     * 获取前几天，或者后几天
     *
     * @param date
     * @param integer -1 是前1天， +1 是往明天
     * @return
     */
    public static Date gainAroundDate(Date date, Integer integer) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_MONTH, integer);
        date = calendar.getTime();
        return date;
    }

    //Date转换为LocalDateTime
    public static LocalDateTime convertDateToLDT(Date date) {
        return LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault());
    }

    /**
     * 为指定时间按照相应日历字段增加时间
     *
     * @param date  初始时间
     * @param time  要增加的时间 负数为减去
     * @param filed 日历字段 参考Calendar的静态字段
     * @return 修改后的时间
     */
    public static Date addDate(Date date, int time, int filed) {
        Calendar calendar = Calendar.getInstance(Locale.CHINESE);
        calendar.setTime(date);
        calendar.add(filed, time);
        return calendar.getTime();
    }

    /**
     * 给时间加上几个小时,负数即减去
     *
     * @param hour 需要加的时间
     * @return lan
     */
    public static Date addDateHour(Date date, int hour) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.HOUR, hour);// 24小时制
        date = cal.getTime();
        return date;
    }

    /**
     * 获取今日开始时间
     *
     * @return
     */
    public static Date getStartTime() {
        Calendar todayStart = Calendar.getInstance(Locale.CHINESE);
        todayStart.set(Calendar.HOUR_OF_DAY, 0);
        todayStart.set(Calendar.MINUTE, 0);
        todayStart.set(Calendar.SECOND, 0);
        todayStart.set(Calendar.MILLISECOND, 0);
        return todayStart.getTime();
    }
    public  static   Date getCurrentHourStartTime() {
        SimpleDateFormat longHourSdf=new SimpleDateFormat("yyyy-MM-dd HH");
        Date now = new Date();
        try {
            now = longHourSdf.parse(longHourSdf.format(now));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return now;
    }

    public  static Date getCurrentHourEndTime() {
        Date now = new Date();
        SimpleDateFormat longHourSdf=new SimpleDateFormat("yyyy-MM-dd HH");
        SimpleDateFormat longSdf =new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            now = longSdf.parse(longHourSdf.format(now) + ":59:59");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return now;
    }
    /**
     * 获取今日结束时间
     *
     * @return
     */
    public static Date getEndTime() {
        Calendar todayEnd = Calendar.getInstance(Locale.CHINESE);
        todayEnd.set(Calendar.HOUR_OF_DAY, 23);
        todayEnd.set(Calendar.MINUTE, 59);
        todayEnd.set(Calendar.SECOND, 59);
        todayEnd.set(Calendar.MILLISECOND, 999);
        return todayEnd.getTime();
    }

    /**
     * 获取昨日开始时间
     *
     * @return
     */
    public static Date getDateStartTime() {
        Calendar todayStart = Calendar.getInstance(Locale.CHINESE);
        todayStart.set(Calendar.HOUR_OF_DAY, -24);
        todayStart.set(Calendar.MINUTE, 0);
        todayStart.set(Calendar.SECOND, 0);
        todayStart.set(Calendar.MILLISECOND, 0);
        return todayStart.getTime();
    }

    /**
     * 获取昨日结束时间
     *
     * @return
     */
    public static Date getDateEndTime() {
        Calendar todayEnd = Calendar.getInstance(Locale.CHINESE);
        todayEnd.set(Calendar.HOUR_OF_DAY, -1);
        todayEnd.set(Calendar.MINUTE, 59);
        todayEnd.set(Calendar.SECOND, 59);
        todayEnd.set(Calendar.MILLISECOND, 999);
        return todayEnd.getTime();
    }

    /**
     * 获取本月的开始时间
     *
     * @return
     */
    public static Date getMonthStartTime() {
        Calendar todayStart = Calendar.getInstance(Locale.CHINESE);
        int day = todayStart.getActualMinimum(Calendar.DAY_OF_MONTH);
        todayStart.set(Calendar.DAY_OF_MONTH, day);
        todayStart.set(Calendar.HOUR_OF_DAY, 0);
        todayStart.set(Calendar.MINUTE, 0);
        todayStart.set(Calendar.SECOND, 0);
        todayStart.set(Calendar.MILLISECOND, 0);
        return todayStart.getTime();
    }


        /**
         * 获取本周开始时间
         *
         * @return
         */
    public static Date getWeekStartTime() {
        Calendar todayStart = Calendar.getInstance(Locale.CHINESE);
        todayStart.setFirstDayOfWeek(Calendar.MONDAY);
        todayStart.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        todayStart.set(Calendar.HOUR_OF_DAY, 0);
        todayStart.set(Calendar.MINUTE, 0);
        todayStart.set(Calendar.SECOND, 0);
        todayStart.set(Calendar.MILLISECOND, 0);
        return todayStart.getTime();
    }
    

    /** 获取上周开始时间 */
    public static Date getBeginDayOfLastWeek() {
        Date date = new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int dayofweek = cal.get(Calendar.DAY_OF_WEEK);
        if (dayofweek == 1) {
            dayofweek += 7;
        }
        cal.add(Calendar.DATE, 2 - dayofweek - 7);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime();
    }
    

    
    /** 获取上周的结束时间 */
    public static Date getEndDayOfLastWeek() {
        Calendar cal = Calendar.getInstance();
        cal.setTime(getBeginDayOfLastWeek());
        cal.add(Calendar.DAY_OF_WEEK, 7);
        cal.set(Calendar.HOUR_OF_DAY, -1);
        cal.set(Calendar.MINUTE, 59);
        cal.set(Calendar.SECOND, 59);
        cal.set(Calendar.MILLISECOND, 999);
        return cal.getTime();
    }
    
    /**  获取上月的开始时间 */
    public static Date getBeginDayOfLastMonth() {
        Date now = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.set(getYear(now), getMonth(now) - 2, 1);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }
    
    /**
     * 获取上月的结束时间
     * @return
     */
    public static Date getEndDayOfLastMonth() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        calendar.add(Calendar.DATE, -1);
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        return calendar.getTime();
    }


    /**
     * 获取本月开始时间 
     * @param date
     * @return
     */
    public static Date getMonthStartTime(Date date) {
        Calendar todayStart = Calendar.getInstance(Locale.CHINESE);
        todayStart.setTime(date);
        int day = todayStart.getActualMinimum(Calendar.DAY_OF_MONTH);
        todayStart.set(Calendar.DAY_OF_MONTH, day);
        todayStart.set(Calendar.HOUR_OF_DAY, 0);
        todayStart.set(Calendar.MINUTE, 0);
        todayStart.set(Calendar.SECOND, 0);
        todayStart.set(Calendar.MILLISECOND, 0);
        return todayStart.getTime();
    }

    /**
     * 获取上一个月的开始时间
     *
     * @return
     */
    public static Date getUpMonthStartTime() {
        Calendar todayStart = Calendar.getInstance();
        todayStart.setTime(getMonthStartTime());
        todayStart.add(Calendar.MONTH, -1);
        return todayStart.getTime();
    }

    /**
     * 获取本月的第几日开始时间
     *
     * @return
     */
    public static Date getMonthStartTime(Integer day) {
        Calendar todayStart = Calendar.getInstance(Locale.CHINESE);
        todayStart.set(Calendar.DAY_OF_MONTH, day);
        todayStart.set(Calendar.HOUR_OF_DAY, 0);
        todayStart.set(Calendar.MINUTE, 0);
        todayStart.set(Calendar.SECOND, 0);
        todayStart.set(Calendar.MILLISECOND, 0);
        return todayStart.getTime();
    }

    /**
     * 获取指定日期的开始时间
     *
     * @return
     */
    public static Date getStartTime(Date date) {
        Calendar start = Calendar.getInstance(Locale.CHINESE);
        start.setTime(date);
        start.set(Calendar.HOUR_OF_DAY, 0);
        start.set(Calendar.MINUTE, 0);
        start.set(Calendar.SECOND, 0);
        start.set(Calendar.MILLISECOND, 0);
        return start.getTime();
    }

    /**
     * 获取昨天的开始时间, 例如: 2012-12-12 00:00:00
     * @return
     */
    public static String getYesterdayStartTime() {
        Calendar calendar = Calendar.getInstance(Locale.CHINESE);
        calendar.add(Calendar.DAY_OF_MONTH,-1);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        return parseDateToStr(YYYY_MM_DD_HH_MM_SS,calendar.getTime());
    }

    /**
     * 获取昨天的结束时间 例如 2012-12-12 23:59:59
     * @return
     */
    public static String getYesterdayEndTime() {
        Calendar calendar = Calendar.getInstance(Locale.CHINESE);
        calendar.add(Calendar.DAY_OF_MONTH,-1);
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        return parseDateToStr(YYYY_MM_DD_HH_MM_SS,calendar.getTime());
    }


    /**
     * 获取今天的开始时间, 例如: 2012-12-12 00:00:00
     * @return
     */
    public static String getTodayStartTime() {
        Calendar calendar = Calendar.getInstance(Locale.CHINESE);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        return parseDateToStr(YYYY_MM_DD_HH_MM_SS,calendar.getTime());
    }

    /**
     * 获取今天的结束时间 例如 2012-12-12 23:59:59
     * @return
     */
    public static String getTodayEndTime() {
        Calendar calendar = Calendar.getInstance(Locale.CHINESE);
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        return parseDateToStr(YYYY_MM_DD_HH_MM_SS,calendar.getTime());
    }

    /**
     * 获取指定日期结束时间
     *
     * @return
     */
    public static Date timeToEndTime(Date date) {
        Calendar todayEnd = Calendar.getInstance(Locale.CHINESE);
        todayEnd.setTime(date);
        todayEnd.set(Calendar.HOUR_OF_DAY, 23);
        todayEnd.set(Calendar.MINUTE, 59);
        todayEnd.set(Calendar.SECOND, 59);
        todayEnd.set(Calendar.MILLISECOND, 999);
        return todayEnd.getTime();
    }

    /**
     * 获取本月结束时间
     *
     * @return
     */
    public static Date getMonthEndTime() {
        Calendar todayEnd = Calendar.getInstance(Locale.CHINESE);
        int endDay = todayEnd.getActualMaximum(Calendar.DAY_OF_MONTH);
        todayEnd.set(Calendar.DAY_OF_MONTH, endDay);
        todayEnd.set(Calendar.HOUR_OF_DAY, 23);
        todayEnd.set(Calendar.MINUTE, 59);
        todayEnd.set(Calendar.SECOND, 59);
        todayEnd.set(Calendar.MILLISECOND, 999);
        return todayEnd.getTime();
    }
    
    /**
     * 获取本周结束时间
     *
     * @return
     */
    public static Date getWeekEndTime() {
        Calendar todayEnd = Calendar.getInstance(Locale.CHINESE);
        todayEnd.setFirstDayOfWeek(Calendar.MONDAY);
        todayEnd.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        todayEnd.add(Calendar.DATE, 6);
        todayEnd.set(Calendar.HOUR_OF_DAY, 23);
        todayEnd.set(Calendar.MINUTE, 59);
        todayEnd.set(Calendar.SECOND, 59);
        todayEnd.set(Calendar.MILLISECOND, 999);
        return todayEnd.getTime();
    }

    public static Date getMonthEndTime(Date date) {
        Calendar todayEnd = Calendar.getInstance(Locale.CHINESE);
        todayEnd.setTime(date);
        int endDay = todayEnd.getActualMaximum(Calendar.DAY_OF_MONTH);
        todayEnd.set(Calendar.DAY_OF_MONTH, endDay);
        todayEnd.set(Calendar.HOUR_OF_DAY, 23);
        todayEnd.set(Calendar.MINUTE, 59);
        todayEnd.set(Calendar.SECOND, 59);
        todayEnd.set(Calendar.MILLISECOND, 999);
        return todayEnd.getTime();
    }

    /**
     * 获取本月第几天的结束时间
     *
     * @return
     */
    public static Date getMonthEndTime(Integer day) {
        Calendar todayEnd = Calendar.getInstance(Locale.CHINESE);
        todayEnd.set(Calendar.DAY_OF_MONTH, day);
        todayEnd.set(Calendar.HOUR_OF_DAY, 23);
        todayEnd.set(Calendar.MINUTE, 59);
        todayEnd.set(Calendar.SECOND, 59);
        todayEnd.set(Calendar.MILLISECOND, 999);
        return todayEnd.getTime();
    }

    /**
     * 获取指定日期的结束时间
     *
     * @return
     */
    public static Date getEndTime(Date date) {
        Calendar end = Calendar.getInstance(Locale.CHINESE);
        end.setTime(date);
        end.set(Calendar.HOUR_OF_DAY, 23);
        end.set(Calendar.MINUTE, 59);
        end.set(Calendar.SECOND, 59);
        end.set(Calendar.MILLISECOND, 999);
        return end.getTime();
    }


    /**
     * 美东时间转北京时间
     *
     * @return
     */
    public static Date eastUSAToBeijing(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);//date 换成已经已知的Date对象
        cal.add(Calendar.HOUR_OF_DAY, 12);// after 12 hour
        return cal.getTime();
    }

    /**
     * 北京时间转美东时间
     *
     * @return
     */
    public static Date beijingToEastUSA(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);//date 换成已经已知的Date对象
        cal.add(Calendar.HOUR, -12);
        return cal.getTime();
    }

    public static String getMonth() {
        String date = datePath();
        return date.substring(date.indexOf("/") + 1, date.lastIndexOf("/"));
    }

    public static Integer getMonth(Date date) {

        Calendar c1 = Calendar.getInstance();
        c1.setTime(date);
        //在格里高利历和罗马儒略历中一年中的第一个月是 JANUARY，它为 0；最后一个月取决于一年中的月份数。
        //
        //所以这个值的初始值为0，所以我们用它来表示日历月份时需要加1
        return c1.get(Calendar.MONTH) + 1;
    }

    public static Integer getDay(Date date) {

        Calendar c1 = Calendar.getInstance();
        c1.setTime(date);
        //在格里高利历和罗马儒略历中一年中的第一个月是 JANUARY，它为 0；最后一个月取决于一年中的月份数。
        //
        //所以这个值的初始值为0，所以我们用它来表示日历月份时需要加1
        return c1.get(Calendar.DAY_OF_MONTH);
    }

    public static Integer getYear(Date date) {

        Calendar c1 = Calendar.getInstance();
        c1.setTime(date);
        //在格里高利历和罗马儒略历中一年中的第一个月是 JANUARY，它为 0；最后一个月取决于一年中的月份数。
        //
        //所以这个值的初始值为0，所以我们用它来表示日历月份时需要加1
        return c1.get(Calendar.YEAR);
    }

    public static String getLastMonth() {
        Integer currentMonth = Integer.valueOf(getMonth());
        if ((currentMonth.intValue() - 1) <= 0)
            return "12";
        String lastMonth = "00" + (currentMonth - 1);
        return lastMonth.substring(lastMonth.length() - 2);
    }

    /**
     * 处理时间	 * @param oldTime  原时间	 * @param add  增加时间	 * @return	 * @throws ParseException
     */
    public static Date timeAdd(Date oldDate, Integer add) {
        Long addMit = Long.valueOf(add);
        Long oldTime = oldDate.getTime();
        addMit = oldTime + addMit * 60 * 1000;
        Date date2 = new Date(addMit);
        return date2;
    }

    /**
     * 判断时间是不是今天
     *
     * @param date
     * @return 是返回true，不是返回false
     */
    public static boolean isNow(Date date) {
        //当前时间
        Date now = new Date();
        SimpleDateFormat sf = getSdf("yyyyMMdd");
        //获取今天的日期
        String nowDay = sf.format(now);
        //对比的时间
        String day = sf.format(date);
        return day.equals(nowDay);
    }


    /**
     * 获取指定日期结束时间
     *
     * @return
     */
    public static Date timeToBeginTime(Date date) {
        Calendar todayStart = Calendar.getInstance(Locale.CHINESE);
        todayStart.setTime(date);
        todayStart.set(Calendar.HOUR_OF_DAY, 0);
        todayStart.set(Calendar.MINUTE, 0);
        todayStart.set(Calendar.SECOND, 0);
        todayStart.set(Calendar.MILLISECOND, 0);
        return todayStart.getTime();
    }


    /**
     * 返回当前时间到第二天的凌晨的秒数
     *
     * @return
     */
    public static long getSecondsNextEarlyMorning() {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DAY_OF_YEAR, 1);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return (cal.getTimeInMillis() - System.currentTimeMillis()) / 1000;
    }


    /**
     * 获取指定日期之后或之前的天数
     * day:yyyy-MM-dd
     *
     * @param num
     * @return
     */
    public static String getDayLater(String day, int num) throws Exception {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat sfd = getSdf("yyyy-MM-dd");
        calendar.setTime(sfd.parse(day));
        calendar.add(Calendar.DAY_OF_MONTH, num);
        return sfd.format(calendar.getTime());
    }

    /**
     * 给定开始和结束时间，遍历之间的所有日期
     *
     * @param startAt 开始时间，例：2017-04-04
     * @param endAt   结束时间，例：2017-04-11
     * @return 返回日期数组
     */
    public static List<String> queryData(String startAt, String endAt) {
        List<String> dates = new ArrayList<>();
        try {
            Date startDate = sfd.parse(startAt);
            Date endDate = sfd.parse(endAt);
            dates.addAll(queryData(startDate, endDate));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return dates;
    }

    /**
     * 给定开始和结束时间，遍历之间的所有日期
     *
     * @param startAt 开始时间，例：2017-04-04
     * @param endAt   结束时间，例：2017-04-11
     * @return 返回日期数组
     */
    public static List<String> queryData(Date startAt, Date endAt) {
        List<String> dates = new ArrayList<>();
        Calendar start = Calendar.getInstance();
        start.setTime(startAt);
        Calendar end = Calendar.getInstance();
        end.setTime(endAt);
        while (start.before(end) || start.equals(end)) {
            dates.add(sfd.format(start.getTime()));
            start.add(Calendar.DAY_OF_YEAR, 1);
        }
        return dates;
    }

    /**
     * @param date1 <String>
     * @param date2 <String>
     * @return int
     * @throws ParseException
     */
    public static int getMonthSpace(String date1, String date2) {
        try {
            SimpleDateFormat sdf = getSdf("yyyy-MM-dd");

            return getMonthSpace(sdf.parse(date1), sdf.parse(date2));
        } catch (ParseException e) {
            return 0;
        }
    }

    /**
     * @param date1 <Date>
     * @param date2 <Date>
     * @return int
     * @throws ParseException
     */
    public static int getMonthSpace(Date date1, Date date2) {
        int result = 0;

        Calendar c1 = Calendar.getInstance();
        Calendar c2 = Calendar.getInstance();

        c1.setTime(date1);
        c2.setTime(date2);

        result = c2.get(Calendar.MONTH) - c1.get(Calendar.MONTH);

        return Math.abs(result);

    }

    /**
     * 获取某月的最后一天
     */
    public static Date getLastDayOfMonth(int year, int month) {
        Calendar cal = Calendar.getInstance();
        //设置年份
        cal.set(Calendar.YEAR, year);
        //设置月份
        cal.set(Calendar.MONTH, month - 1);
        //获取某月最大天数
        int lastDay = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
        //设置日历中月份的最大天数
        cal.set(Calendar.DAY_OF_MONTH, lastDay);
        //格式化日期
        // SimpleDateFormat sdf =getSdf("yyyy-MM-dd");
        // String lastDayOfMonth = sdf.format(cal.getTime());

        return cal.getTime();
    }

    /**
     * 获取二个时间差之间的月份
     *
     * @param beginDate
     * @param endDate
     * @return
     */
    public static String[] getMonthsArray(Date beginDate, Date endDate) {
        Integer month = DateUtils.getMonthSpace(beginDate, endDate) + 1;
        String[] months = new String[month];
        for (int i = 0; i < month; i++)
            months[i] = String.format("%02d", DateUtils.getMonth(DateUtils.addDate(endDate, -i, Calendar.MONTH)));
        return months;
    }


    /**
     * 判断当前时间是否在[startTime, endTime]区间，注意时间格式要一致
     *
     * @param nowTime   当前时间
     * @param startTime 开始时间
     * @param endTime   结束时间
     */
    public static boolean isEffectiveDate(Date nowTime, Date startTime, Date endTime) {
        if (nowTime.getTime() == startTime.getTime()
                || nowTime.getTime() == endTime.getTime()) {
            return true;
        }
        Calendar date = Calendar.getInstance();
        date.setTime(nowTime);
        Calendar begin = Calendar.getInstance();
        begin.setTime(startTime);
        Calendar end = Calendar.getInstance();
        end.setTime(endTime);
        if (date.after(begin) && date.before(end)) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * @param date
     * @param beforeNum
     * @return 获取指定时间的前后格式化后的月份数，例：
     * Mon Nov 04 12:32:26 SGT 2019 ，-2
     * 返回：2019-09-04 12:32:26
     */
    public static String getNMonthBefore(Date date, Integer beforeNum) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(YYYY_MM_DD_HH_MM_SS);

        Calendar calendar = Calendar.getInstance(); //得到日历
        calendar.setTime(date);//把当前时间赋给日历
        calendar.add(Calendar.MONTH, beforeNum);
        date = calendar.getTime();

        return dateFormat.format(date);
    }
    
    /**
	 * 获得任意时区的时间,指定对应的时区
	 *
	 * @param timeZoneOffset
	 * @return
	 */
	public static Date getByGMT(float timeZoneOffset) {
		if (timeZoneOffset > 13 || timeZoneOffset < -12) {
			timeZoneOffset = 0;
		}
		int newTime = (int) (timeZoneOffset * 60 * 60 * 1000);
		TimeZone timeZone;
		
		String[] ids = TimeZone.getAvailableIDs(newTime);
		if (ids.length == 0) {
			timeZone = TimeZone.getDefault();
		} else {
			timeZone = new SimpleTimeZone(newTime, ids[0]);
		}

		SimpleDateFormat sdf = new SimpleDateFormat(YYYY_MM_DD_HH_MM_SS);
		sdf.setTimeZone(timeZone);
		
		return getDateTime(YYYY_MM_DD_HH_MM_SS, sdf.format(new Date()));
	}
	
	/**
	 * 	获得任意时区对应当前时间的时间,指定对应的时区，和当前时间
	 * 
	 * @param timeZoneOffset
	 * @param date
	 * @return
	 */
	public static Date getByGMT(float timeZoneOffset, Date date) {
		if (timeZoneOffset > 13 || timeZoneOffset < -12) {
			timeZoneOffset = 0;
		}
		int newTime = (int) (timeZoneOffset * 60 * 60 * 1000);
		TimeZone timeZone;
		
		String[] ids = TimeZone.getAvailableIDs(newTime);
		if (ids.length == 0) {
			timeZone = TimeZone.getDefault();
		} else {
			timeZone = new SimpleTimeZone(newTime, ids[0]);
		}
		
		SimpleDateFormat sdf = new SimpleDateFormat(YYYY_MM_DD_HH_MM_SS);
		sdf.setTimeZone(timeZone);
		
		return getDateTime(YYYY_MM_DD_HH_MM_SS, sdf.format(date));
	}

}
