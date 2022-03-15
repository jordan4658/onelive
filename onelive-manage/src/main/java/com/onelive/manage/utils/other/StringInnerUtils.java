package com.onelive.manage.utils.other;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @ClassName StringCommonUtils
 * @Desc 项目内部字符串工具类，与第三方工具类区分开来
 * @Date 2021/3/13 20:05
 */
public class StringInnerUtils {


    public static List<String> splitStringList(String str) {
        return splitStringList(str, ",");
    }

    public static List<String> splitStringList(String str, String separate) {
        if (StringUtils.isBlank(str)) {
            return null;
        }
        String[] arr = str.split(separate);
        List<String> list = new ArrayList<>();
        for (String item : arr) {
            list.add(item);
        }
        return list;
    }

    /**
     * 功能：判断字符串是否为数字
     * @param str
     * @return
     */
    public static boolean isNumeric(String str) {
        Pattern pattern = Pattern.compile("[0-9]*");
        Matcher isNum = pattern.matcher(str);
        if (isNum.matches()) {
            return true;
        } else {
            return false;
        }
    }
}    
    