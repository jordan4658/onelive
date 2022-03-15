package com.onelive.common.utils.pay;

import com.alibaba.fastjson.JSONObject;
import com.ob.internal.common.util.Md5Util;
import com.onelive.common.utils.others.SecurityUtils;
import lombok.extern.slf4j.Slf4j;
import net.sf.jsqlparser.expression.StringValue;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.*;

/**
 * @version V1.0
 * @ClassName: SignMd5Utils
 * @date 创建时间：2021/4/10 19:19
 */
@Slf4j
public class SignMd5Utils {


    /**
     * 地雷支付 的签名方法  签名的key 放在参数尾部
     *
     * @param packageParams 参与签名的参数
     * @param signKey       签名key例如： key=123
     * @return
     */
    public static String commonSign(Map<String, String> packageParams, String signKey) {
        StringBuilder sign = getSignUtils(packageParams);
        if (StringUtils.isNotBlank(signKey)) {
            sign.append("&").append(signKey);
        }
        System.out.println("sign====" + sign.toString());
        return MD5Hex(sign.toString(), true);
    }

    public static String commonSignToKg(Map<String, Object> packageParams, String signKey) {
        StringBuilder sign = getKgSignUtils(packageParams);
        if (StringUtils.isNotBlank(signKey)) {
            sign.append(signKey);
        }
        System.out.println("sign====" + sign.toString());
        return SecurityUtils.MD5(sign.toString());
    }


    /**
     * KG支付 的签名方法  签名的key 放在参数尾部
     *
     * @param packageParams 参与签名的参数
     * @param signKey       签名key例如： key=123
     * @return
     */
    public static String commonSignByObject(Map<String, Object> packageParams, String signKey) {
        StringBuilder sign = getKgSignUtils(packageParams);
        if (StringUtils.isNotBlank(signKey)) {
            sign.append(signKey);
        }
        System.out.println("sign====" + sign.toString());
        return MD5Hex(sign.toString(), false);
    }


    /**
     * 生成待签名字符串
     *
     * @param map
     * @return
     */
    public static StringBuilder getKgSignUtils(Map<String, Object> map) {
        try {
            List<Map.Entry<String, Object>> infoIds = new ArrayList<Map.Entry<String, Object>>(map.entrySet());
            // 对所有传入参数按照字段名的 ASCII 码从小到大排序（字典序）
            Collections.sort(infoIds, new Comparator<Map.Entry<String, Object>>() {
                public int compare(Map.Entry<String, Object> o1, Map.Entry<String, Object> o2) {
                    return (o1.getKey()).toString().compareTo(o2.getKey());
                }
            });
            // 构造签名键值对的格式
            StringBuilder sb = new StringBuilder();
            for (Map.Entry<String, Object> item : infoIds) {
                if (item.getKey() != null || item.getKey() != "") {
                    String key = item.getKey();
                    String val = String.valueOf(item.getValue());
                    if (!"sign".equals(key)) {
                        sb.append(val);
                    }
                }
            }
            return sb;
        } catch (Exception e) {
            return null;
        }

    }


    /**
     * TreeMap集合默认按照ASCII码从小到大（字典序）排序（直接使用），并生成 get请求 参数的键值对 例如：xxx=yyyy&zzz=pppp&。。。。。。
     *
     * @param map
     * @return
     */
    public static StringBuilder getSignUtils(Map<String, String> map) {
        try {
            // 构造签名键值对的格式
            StringBuilder sb = new StringBuilder();
            for (Map.Entry<String, String> item : map.entrySet()) {
                if (StringUtils.isNotBlank(item.getKey()) && StringUtils.isNotBlank(item.getValue())) {
                    String key = item.getKey();
                    String val = item.getValue();
                    sb.append(key + "=" + val + "&");
                }
            }

            return sb.deleteCharAt(sb.length() - 1);
        } catch (Exception e) {
            log.error("TreeMap集合按照ASCII码从小到大（字典序）排序------报错：" + e.getMessage());
            return null;
        }

    }

    /**
     * TreeMap集合默认按照ASCII码从小到大（字典序）排序（直接使用），并生成 get请求 参数的键值对 例如：xxx=yyyy&zzz=pppp&。。。。。。
     *
     * @param map
     * @return
     */
    public static StringBuilder getSignUtilsByObject(Map<String, Object> map) {
        try {
            // 构造签名键值对的格式
            StringBuilder sb = new StringBuilder();
            for (Map.Entry<String, Object> item : map.entrySet()) {
                if (StringUtils.isNotBlank(item.getKey()) && StringUtils.isNotBlank(String.valueOf(item.getValue()))) {
                    String key = item.getKey();
                    String val = String.valueOf(item.getValue());
                    sb.append(key + "=" + val + "&");
                }
            }

            return sb.deleteCharAt(sb.length() - 1);
        } catch (Exception e) {
            log.error("TreeMap集合按照ASCII码从小到大（字典序）排序------报错：" + e.getMessage());
            return null;
        }

    }

    /**
     * [str, isUpperCase 是否转 大写  true：大写，false：小写]
     *
     * @param str
     * @param isUpperCase
     * @return
     */
    public static String MD5Hex(String str, boolean isUpperCase) {
        if (isUpperCase) {
            str = DigestUtils.md5Hex(str).toUpperCase();
        } else {
            str = DigestUtils.md5Hex(str).toLowerCase();
        }
        return str;
    }

    public static void main(String[] args) {
        /**
         * income=58500
         * &payOrderId=P01202106042214340545320
         * &amount=60000
         * &mchId=20000086
         * &productId=8028
         * &mchOrderNo=CZVX0273826723478464
         * &paySuccTime=1622817201000
         * &sign=A378595A578679C1320E13970F97BFDE
         * &channelOrderNo=1400818261326561280
         * &backType=2
         * &param1=
         * &param2=
         * &appId=a145bbc0eff040dc836457c46a408cbc
         * &status=2
         */
        Map<String, String> map = new TreeMap<>();
        map.put("income", "58500");
        map.put("payOrderId", "P01202106042214340545320");
        map.put("amount", "60000");
        map.put("mchId", "20000086");
        map.put("productId", "8028");
        map.put("mchOrderNo", "CZVX0273826723478464");
        map.put("paySuccTime", "1622817201000");
        map.put("channelOrderNo", "1400818261326561280");
        map.put("backType", "2");
        map.put("appId", "a145bbc0eff040dc836457c46a408cbc");
        map.put("status", "2");
        String sign = SignMd5Utils.commonSign(map, "key=NCUZUDGQHWN4SDRBINF0UVWILDRFT5TGU2K2JJDHI4F7XE2DCHPWDRMTXBQGI5VESSKA3OBGNW30TSPSLKOXKPNH8IQTPHEVZEXK52VWVPBUH2OUKW1K7YEU5DRALUK9");
        map.put("sing", sign);
        System.out.println(JSONObject.toJSONString(map));

    }

}
