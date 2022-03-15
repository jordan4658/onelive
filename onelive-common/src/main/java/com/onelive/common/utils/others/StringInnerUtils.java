package com.onelive.common.utils.others;

import com.onelive.common.constants.other.SymbolConstant;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

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
     * 字符串处理，若为null，会转成""
     *
     * @param val
     * @return
     */
    public static String getWithOutNull(Object val) {
        if (val == null) return SymbolConstant.BLANK;
        return val.toString();
    }

    /**
     * 删除所有的空格
     * @param str
     * @return
     */
    public static String decodeHtml(String str) {
        if (str == null) return SymbolConstant.BLANK;
        return str.replaceAll("\\\\r|\\\\n|\\\\", "");
    }


}    
    