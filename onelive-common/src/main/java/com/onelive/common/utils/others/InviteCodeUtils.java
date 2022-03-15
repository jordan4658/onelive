package com.onelive.common.utils.others;

import java.util.Arrays;
import java.util.Random;

/**
 * @ClassName InviteCodeUtils
 * @Desc 生成唯一邀请码
 * @Date 2021/4/7 18:57
 */
public class InviteCodeUtils {
    /**
     * 随机字符串
     */
    private static final char[] CHARS = new char[]{'F', 'L', 'G', 'W', '5', 'X', 'C', '3',
            '9', 'Z', 'M', '6', '7', 'Y', 'R', 'T', '2', 'H', 'S', '8', 'D', 'V', 'E', 'J', '4', 'K',
            'Q', 'P', 'U', 'A', 'N', 'B'};

    private final static int CHARS_LENGTH = 32;
    /**
     * 邀请码长度
     */
    private final static int CODE_LENGTH = 6;

    /**
     * 随机数据
     */
    private final static long SLAT = 1234561L;

    /**
     * PRIME1 与 CHARS 的长度 L互质，可保证 ( id * PRIME1) % L 在 [0,L)上均匀分布
     */
    private final static int PRIME1 = 3;

    /**
     * PRIME2 与 CODE_LENGTH 互质，可保证 ( index * PRIME2) % CODE_LENGTH  在 [0，CODE_LENGTH）上均匀分布
     */
    private final static int PRIME2 = 11;

    /**
     * 生成邀请码
     *
     * @param id 唯一的id主键
     * @return code
     */
    public static String gen(Long id) {
        //补位
        id = id * PRIME1 + SLAT;
        //将 id 转换成32进制的值
        long[] b = new long[CODE_LENGTH];
        //32进制数
        b[0] = id;
        for (int i = 0; i < CODE_LENGTH - 1; i++) {
            b[i + 1] = b[i] / CHARS_LENGTH;
            //按位扩散
            b[i] = (b[i] + i * b[0]) % CHARS_LENGTH;
        }
        b[5] = (b[0] + b[1] + b[2] + b[3] + b[4]) * PRIME1 % CHARS_LENGTH;

        //进行混淆
        long[] codeIndexArray = new long[CODE_LENGTH];
        for (int i = 0; i < CODE_LENGTH; i++) {
            codeIndexArray[i] = b[i * PRIME2 % CODE_LENGTH];
        }

        StringBuilder buffer = new StringBuilder();
        Arrays.stream(codeIndexArray).boxed().map(Long::intValue).map(t -> CHARS[t]).forEach(buffer::append);
        return buffer.toString();
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
     * 指定长度的随机字符串
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
     * 生成10位唯一标识账号(小写和数字)
     *
     * @return
     */
    public static String accountCode() {
        //生成账号前两位英文
        String s = "qwertyuiopasdfghjklzxcvbnm";
        String word = s.toUpperCase();
        // 获取包含26个字母大写和数字的字符数组
        char[] c = word.toCharArray();
        Random rd = new Random();
        String code = "";
        for (int k = 0; k < 2; k++) {
            // 随机获取数组长度作为索引
            int index = rd.nextInt(c.length);
            // 循环添加到字符串后面
            code += c[index];
        }
        //生成账号后8位数字
        String after = randCode(8);
        code = code + after;
        return code.toLowerCase();
    }


    public static String nickName() {
        String pre = "U";

        int i = 1234567890;
        String s = "qwertyuiopasdfghjklzxcvbnm";
        String S = s.toUpperCase();
        String word = S + i;
        // 获取包含26个字母大写和数字的字符数组
        char[] c = word.toCharArray();

        Random rd = new Random();
        String code = "";
        for (int k = 0; k < 7; k++) {
            // 随机获取数组长度作为索引
            int index = rd.nextInt(c.length);
            // 循环添加到字符串后面
            code += c[index];
        }
        return pre + code;
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
    