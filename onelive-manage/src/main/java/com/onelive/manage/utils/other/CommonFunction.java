package com.onelive.manage.utils.other;


import java.math.BigDecimal;
import java.util.*;

/**
 * 常用静态方法类
 */
public class CommonFunction {

    /**
     * 验证字符串是否为空，为空返回false，否则返回true
     *
     * @param str 字符串
     * @return boolean
     */
    public static boolean isNotNull(String str) {
        return !(str == null || "".equals(str) || str.length() == 0);
    }

    /**
     * 验证一个对象是否为null，为空返回false，否则返回true
     *
     * @param obj 需要验证的对象
     * @return boolean
     */
    public static boolean isNotNull(Object obj) {
        return obj != null;
    }

    /**
     * 返回trim后的字符串
     *
     * @param str 需要做trim操作的字符串
     * @return String
     */
    public static String trim(String str) {
        return isNotNull(str) ? str.trim() : "";
    }

    // 替换字符串中的非法数据，key为替换目标，value为替换值，搜索页面专用
    private static final Map<String, String> filterStrForSearch = new HashMap<String, String>();

    // 初始化替换数据
    static {
        filterStrForSearch.put("<", "");
        filterStrForSearch.put(">", "");
        filterStrForSearch.put("'", "");
        filterStrForSearch.put("\"", "");
        filterStrForSearch.put("+", "");
        filterStrForSearch.put("_", "");
        filterStrForSearch.put("%", "");
        filterStrForSearch.put("\\r", "");
        filterStrForSearch.put("\\n", "");
        filterStrForSearch.put("\\t", "");
    }

    /**
     * 过滤传入对象中的字符串的非法字符
     *
     * @param value
     * @return
     */
    public static String filterString(String value) {
        // 去除空格
        value = trim(value);
        // 循环将当前字符串的值进行替换
        for (Map.Entry<String, String> entry : filterStrForSearch.entrySet()) {
            value = value.replace(entry.getKey(), entry.getValue());
        }
        return value;
    }

    /**
     * 提供精确的加法运算。
     *
     * @param v1 被加数
     * @param v2 加数
     * @return 两个参数的和
     */
    public static double add(double v1, double v2) {
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));
        return b1.add(b2).doubleValue();
    }

    /**
     * 提供精确的减法运算。
     *
     * @param v1 被减数
     * @param v2 减数
     * @return 两个参数的差
     */
    public static double sub(double v1, double v2) {
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));
        return b1.subtract(b2).doubleValue();
    }

    /**
     * 提供精确的乘法运算。
     *
     * @param v1 被乘数
     * @param v2 乘数
     * @return 两个参数的积
     */
    public static double mul(double v1, double v2) {
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));
        return b1.multiply(b2).doubleValue();
    }

    /**
     * @return uuid
     * @Title: getUUID
     * @Description: 得到一个uuid
     */
    public static String getUUID() {
        return UUID.randomUUID().toString().replace("-", "");
    }

}
