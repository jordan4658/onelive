package com.onelive.common.utils.pay;

import java.math.BigDecimal;

/**
 * @version V1.0
 * @ClassName: PayUtils
 * @date 创建时间：2021/4/12 19:09
 */
public class PayUtils {


    /**
     * 保留三位小数，4位之后的全舍
     *
     * @param money 金额
     * @return
     */
    public static BigDecimal getTradeOffAmount(BigDecimal money) {
        if (null == money || money.compareTo(BigDecimal.ZERO) == 0) {
            return new BigDecimal("0.000");
        }
        return money.setScale(4, BigDecimal.ROUND_DOWN);
    }

    /**
     * 生成订单号
     */
    public static String getOrderNo() {
        return SnowflakeIdWorker.generateShortId();

    }

    /**
     * 验证字符串是否是一个数字
     *
     * @param str
     * @return
     */
    public static Boolean strVerifyNumber(String str) {
        return str.matches("^[-+]?(([0-9]+)(.)?|(.)?)$");
    }
}
