package com.onelive.common.utils.lottery;


import com.onelive.common.constants.other.SymbolConstant;
import com.onelive.common.enums.FourtyEightEnum;
import com.onelive.common.utils.others.DateInnerUtil;
import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;
import java.util.Date;


/**
 * @ClassName: CaipiaoUtils
 * @Description: 彩票相关计算
 * @author: admin
 * @date: 2019年4月21日 下午2:13:01
 */
public class CaipiaoUtils {

    /**
     * @return String
     * @Title: getAllIsuueNumber
     * @Description: 组织返回前端开奖格式
     * @author admin
     * @date 2019年4月21日下午2:18:38
     */
    public static String getAllIsuueNumber(Integer wan, Integer qian, Integer bai, Integer shi, Integer ge) {
        StringBuffer allNumber = new StringBuffer();
        String allNumberString = allNumber.append(wan).append(qian).append(bai).append(shi).append(ge).toString();
        return allNumberString;
    }

    /**
     * @Title: getNocommaNumber
     * @Description: 去掉开奖号码中的逗号
     * @author HANS
     * @date 2019年7月20日上午11:22:35
     */
    public static String getNocommaNumber(String number) {
        if(StringUtils.isEmpty(number)) {
            return SymbolConstant.BLANK;
        }
        String newNumber = number.replace(",", "");
        return newNumber;
    }

    /**
     * @Title: getNumberTotal
     * @Description: 号码单个值相加合值
     * @author HANS
     * @date 2019年7月20日上午11:33:33
     */
    public static Integer getNumberTotal(String number) {
        Integer total =0;
        if(StringUtils.isEmpty(number)) {
            return 0;
        }
        String[] sigleString = number.split(",");

        if(null == sigleString || sigleString.length < 1) {
            return 0;
        }

        for (String sigleNumber : sigleString) {
            int idint = Integer.parseInt(sigleNumber);
            total += idint;
        }
        return total;
    }

    /**
     * @return Integer
     * @Title: getAllIsuueSum
     * @Description: 开奖号码合值
     * @author admin
     * @date 2019年4月21日下午2:22:37
     */
    public static Integer getAllIsuueSum(Integer wan, Integer qian, Integer bai, Integer shi, Integer ge) {
        Integer sumInteger = 0;

        if (wan != null) {
            sumInteger += wan;
        }

        if (qian != null) {
            sumInteger += qian;
        }

        if (bai != null) {
            sumInteger += bai;
        }

        if (shi != null) {
            sumInteger += shi;
        }

        if (ge != null) {
            sumInteger += ge;
        }
        return sumInteger;
    }

    /**
     * @return String
     * @Title: getNextIdealTime
     * @Description: 获取下期开奖时间
     * @author admin
     * @date 2019年4月21日下午3:36:50
     */
    public static Date nextIssueTime(Date lastTime) {
        Date dateTime;
        String date = DateInnerUtil.format(lastTime, "yyyy-MM-dd");
        String time = DateInnerUtil.format(lastTime, "HH:mm:ss");
        if ("02:00:00".equals(time)) {
            dateTime = DateInnerUtil.parse(date + " 10:20:00", DateInnerUtil.FORMAT_YYYY_MM_DD_HHMMSS);
        } else {
            dateTime = DateInnerUtil.offsetMinute(lastTime, 20);
        }
        return dateTime;
    }


    /**
     * @return String
     * @Title: mathNextIssueTimeForNow
     * @Description: 计算当前时间的下期开奖时间,  (20分钟一次开奖)
     * @author admin
     * @date 2019年4月21日下午6:06:22
     */
    public static String mathNextIssueTimeForNow() {
        Date thisTime = new Date();
        String thisDateYearAndTime = DateInnerUtil.format(thisTime, DateInnerUtil.FORMAT_YYYY_MM_DD_HHMMSS);
        String thisDateYear = DateInnerUtil.format(thisTime, DateInnerUtil.FORMAT_YYYY_MM_DD);
        // 是否大于2点
        String startTimeString = thisDateYear + " 02:00:00";
        long startFlag = DateInnerUtil.timeReduce(thisDateYearAndTime, startTimeString);
        // 是否小于10：20
        String endTimeString = thisDateYear + " 10:20:00";
        long endFlag = DateInnerUtil.timeReduce(endTimeString, thisDateYearAndTime);

        // 02:00:00  到  10:20:00 之前的时间，下期时间都是 当日10:20:00
        if (startFlag > 0 && endFlag > 0) {
            String nextIssueTime = thisDateYear + " 10:20:00";
            return nextIssueTime;
        }
        // 取20分钟后时间
        String thisDatetime = DateInnerUtil.format(thisTime, DateInnerUtil.TIME_PATTERN);
        String hourString = thisDatetime.substring(0, 2);//时
        String minString = thisDatetime.substring(3, 5);//分
        String secondString = thisDatetime.substring(6, 8);//秒
        // 按照数值类型比较
        Integer hour = Integer.valueOf(hourString);
        Integer minuter = Integer.valueOf(minString);
        Integer second = Integer.valueOf(secondString);

        if (second > 57) {
            minuter++;
        }

        if (minuter >= 0 && minuter < 20) {
            String nextIssueTime = thisDateYear + " " + hourString + ":20:00";
            return nextIssueTime;
        }

        if (minuter >= 20 && minuter < 40) {
            String nextIssueTime = thisDateYear + " " + hourString + ":40:00";
            return nextIssueTime;
        }

        if (minuter >= 40 && minuter <= 59) {
            hour++;
            String nextIssueTime = thisDateYear + " " + hour + ":00:00";
            return nextIssueTime;
        }
        return null;
    }

    /**
     * @return String
     * @Title: getNextIssueNumber
     * @Description: 获取下期期号 ,  (20分钟一次开奖)
     * @author admin
     * @date 2019年4月21日下午3:28:16
     */
    public static String getNextIssueNumber(String nextIssueTime) {
        Date thisDate = DateInnerUtil.parse(nextIssueTime, DateInnerUtil.FORMAT_YYYY_MM_DD_HHMMSS);
        String thisDateYear = DateInnerUtil.format(thisDate, DateInnerUtil.FORMAT_YYYYMMDD);
        String thisDatetime = DateInnerUtil.format(thisDate, DateInnerUtil.TIME_PATTERN);
        // 获取时分秒
        String hourString = thisDatetime.substring(0, 2);//时
        String minString = thisDatetime.substring(3, 5);//分
        StringBuffer issueKeyBuffer = new StringBuffer();
        issueKeyBuffer.append(hourString).append(minString);
        // 查找出今日顺序号
        String issueId = FourtyEightEnum.getIssueId(issueKeyBuffer.toString());
        // 返回下期期号
        StringBuffer issueNumberBuffer = new StringBuffer();
        issueNumberBuffer.append(thisDateYear).append(issueId);
        return issueNumberBuffer.toString();
    }

    /**
     * 计算赔率
     * @param totalCount
     * @param winCount
     * @param divisor
     * @return
     */
    public static String getLotteryPlayOdds(String totalCount, String winCount, Double divisor) {
        // 分割字符串（总注数可能有多个，以/分隔）
        String[] totalStr = totalCount.split("/");
        StringBuilder odds = new StringBuilder();
        // 遍历总注数
        for (int i = 0; i < totalStr.length; i++) {
            if (i > 0) {
                odds.append("/");
            }
            double total = Double.parseDouble(totalStr[i]);
            // 计算赔率
            double odd = total * 1.0 / Double.parseDouble(winCount) * divisor;

            BigDecimal bg = new BigDecimal(odd);
            double dd = bg.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
            odds.append(Double.toString(dd));
        }
        return odds.toString();
    }

    /**
     * 计算赔率
     * @param totalCount
     * @param winCount
     * @param divisor
     * @return
     */
    public static String getLotteryPlayOddsNew(String totalCount, String winCount, Double divisor) {
        // 分割字符串（总注数可能有多个，以/分隔）
        String[] totalStr = totalCount.split("/");
        StringBuilder odds = new StringBuilder();
        // 遍历总注数
        for (int i = 0; i < totalStr.length; i++) {
            if (i > 0) {
                odds.append("/");
            }
            double total = Double.parseDouble(totalStr[i]);
            // 计算赔率
            double odd = total * 1.0 / Double.parseDouble(winCount) * divisor;

            BigDecimal bg = new BigDecimal(odd);
            double dd = bg.setScale(3, BigDecimal.ROUND_HALF_UP).doubleValue();
            odds.append(Double.toString(dd));
        }
        return odds.toString();
    }


}
