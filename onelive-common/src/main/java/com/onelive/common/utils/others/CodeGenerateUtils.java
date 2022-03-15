/***********************************************************************************************************************
 *
 * Copyright (C) 2013, 2014 by huanju (http://www.yy.com)
 * http://www.yy.com/
 *
 ***********************************************************************************************************************
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 *
 **********************************************************************************************************************/
package com.onelive.common.utils.others;

import java.util.Random;

/**
 * 验证码生成工具类
 */
public final class CodeGenerateUtils {

    /**
     * 4位验证码
     *
     * @return
     */
    public static String randCode(int num) {
        Random random = new Random();
        String str = "";
        for (int i = 0; i < num; i++) {
            int key = random.nextInt(3);
            switch (key) {
                case 0:
                    int code1 = random.nextInt(10);
                    str += code1;
                    break;
                case 1:
                    char code2 = (char) (random.nextInt(26) + 65);
                    str += code2;
                    break;
                case 2:
                    char code3 = (char) (random.nextInt(26) + 97);
                    str += code3;
                    break;
            }
        }
        return str;
    }

    /**
     * 6位数邀请码 大写加数字
     *
     * @return
     */
    public static String inviteCode() {
        int i = 1234567890;
        String s = "qwertyuiopasdfghjklzxcvbnm";
        String S = s.toUpperCase();
        String word = S + i;
        // 获取包含26个字母大写和数字的字符数组
        char[] c = word.toCharArray();

        Random rd = new Random();
        String code = "";
        for (int k = 0; k < 6; k++) {
            // 随机获取数组长度作为索引
            int index = rd.nextInt(c.length);
            // 循环添加到字符串后面
            code += c[index];
        }
        return code;
    }

    /**
     * 获取指定长度的随机字符串, 包含大小写和数字
     * @param len
     * @return
     */
    public static String randomStrCode(int len) {
        int i = 1234567890;
        String s = "qwertyuiopasdfghjklzxcvbnm";
        String S = s.toUpperCase();
        String word = S + i;
        // 获取包含26个字母大写和数字的字符数组
        char[] c = word.toCharArray();

        Random rd = new Random();
        String code = "";
        for (int k = 0; k < len; k++) {
            // 随机获取数组长度作为索引
            int index = rd.nextInt(c.length);
            // 循环添加到字符串后面
            code += c[index];
        }
        return code;
    }


    /**
     * @Title: getSixRandomSmsCode @Description: 产生随机的4六位数 @return String
     * 返回类型 @throws
     */
    public static String getFourRandomSmsCode() {
        Random rad = new Random();
        String resultStr = String.valueOf(rad.nextInt(10000));
        int num = 4;
        if (resultStr.length() != num) {
            return getFourRandomSmsCode();
        }
        return resultStr;
    }

}
