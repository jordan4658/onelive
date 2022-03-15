package com.onelive.common.utils.others;

import java.util.Random;

/**
 * @ClassName PasswordGeneratorUtils
 * @Desc 密码随机生成唯
 */
public class PasswordGeneratorUtils {


    /**
     * 8位密码
     *
     * @return
     */
    public static String code() {
        int i = 1234567890;
        String s = "qwertyuiopasdfghjklzxcvbnm";
        String word = s + i;
        // 获取包含26个字母和数字的字符数组
        char[] c = word.toCharArray();

        Random rd = new Random();
        String code = "";
        for (int k = 0; k < 8; k++) {
            // 随机获取数组长度作为索引
            int index = rd.nextInt(c.length);
            // 循环添加到字符串后面
            code += c[index];
        }
        return code;
    }


}    
    