package com.onelive.websocket.util;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 生成随机数工具类
 */
public class RandomUtil {

    public static final String[] ALL_CHAR_ARR = {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z", "0", "1", "2", "3", "4", "5", "6", "7", "8", "9"};
    public static final int ALL_CHAR_ARR_LEN = ALL_CHAR_ARR.length;

    public static String genRandomString(int length) {
        StringBuilder code = new StringBuilder(length);
        Random random = new Random();
        for (int i = 0; i < length; i++) {
            code.append(ALL_CHAR_ARR[random.nextInt(ALL_CHAR_ARR_LEN)]);
        }
        return code.toString();
    }

    /**
     * 生成指定位数的随机数
     *
     * @param length 长度
     * @return
     */
    public static Integer getRandomOne(int length) {
        Double max = Math.pow(10, length);
        Double min = Math.pow(10, length - 1);
        return getRandomOne(min.intValue(), max.intValue());
    }

    /**
     * 生成单个随机数
     * start 和 end 组成一个区间 [start, end) , 生成的随机数将 大于等于 start 并且 小于end
     *
     * @param start 开始数字（包含在内）
     * @param end   结束数字（不包含在内）
     * @return
     */
    public static Integer getRandomOne(int start, int end) {
        // 创建Random对象
        Random random = new Random();
        return random.nextInt(end - start) + start;
    }

    /**
     * 生成【不重复的随机数】集合
     * start 和 end 组成一个区间 [start, end) , 生成的随机数将 大于等于 start 并且 小于end
     *
     * @param count 要生成的随机数个数
     * @param start 开始数字（包含在内）
     * @param end   结束数字（不包含在内）
     * @return
     */
    public static List<Integer> getRandomList(int count, int start, int end) {
        // 1.创建集合容器对象
        List<Integer> list = new ArrayList<>();

        // 2.创建Random对象
        Random r = new Random();

        // 3.循环将得到的随机数进行判断，如果随机数不存在于集合中，则将随机数放入集合中，如果存在，则将随机数丢弃不做操作，进行下一次循环，直到集合长度等于nums
        while (list.size() != count) {
            int num = r.nextInt(end - start) + start;
            if (!list.contains(num)) {
                list.add(num);
            }
        }

        return list;
    }

    /**
     * 生成【不重复的随机数】拼装的字符串
     * start 和 end 组成一个区间 [start, end) , 生成的随机数将大于
     *
     * @param count 要生成的随机数个数
     * @param start 开始数字（包含在内）
     * @param end   结束数字（不包含在内）
     * @return
     */
    public static String getRandomStringNoSame(int count, int start, int end) {
        // 1.创建集合容器对象
        List<Integer> list = new ArrayList<>();

        // 2.创建Random对象
        Random r = new Random();

        // 3.循环将得到的随机数进行判断，如果随机数不存在于集合中，则将随机数放入集合中，如果存在，则将随机数丢弃不做操作，进行下一次循环，直到集合长度等于nums
        while (list.size() != count) {
            int num = r.nextInt(end - start) + start;
            if (!list.contains(num)) {
                list.add(num);
            }
        }
        // 4.排序（该方式只是用与普通类型的排序，只能是顺序）
        //   Collections.sort(list);

        return listToString(list);
    }

    /**
     * 生成【可重复的随机数】拼装的字符串
     * start 和 end 组成一个区间 [start, end) , 生成的随机数将大于
     *
     * @param count 要生成的随机数个数
     * @param start 开始数字（包含在内）
     * @param end   结束数字（不包含在内）
     * @return
     */
    public static String getRandomStringSame(int count, int start, int end) {
        // 1.创建集合容器对象
        List<Integer> list = new ArrayList<>();

        // 2.创建Random对象
        Random r = new Random();

        // 3.循环将得到的随机数进行判断，如果随机数不存在于集合中，则将随机数放入集合中，如果存在，则将随机数丢弃不做操作，进行下一次循环，直到集合长度等于nums
        while (list.size() != count) {
            int num = r.nextInt(end - start) + start;
            list.add(num);
        }

        return listToString(list);
    }

    /**
     * List 集合转为String 每个元素之间用半角逗号","隔开
     *
     * @param list 待转换的集合
     * @return
     */
    private static String listToString(List<Integer> list) {
        // 创建字符串容器对象
        StringBuilder numStr = new StringBuilder();

        Iterator<Integer> iterator = list.iterator();
        while (iterator.hasNext()) {
            Integer num = iterator.next();
            numStr.append(num);
            if (iterator.hasNext()) {
                numStr.append(",");
            }
        }
        return numStr.toString();
    }

    private static Random random = new Random();

    /**
     * 获取北京PK10的公式杀号
     *
     * @return
     */
    public static String getBjpksKillNumber() {
        return getKillNumber(10);
    }

    /**
     * 获取澳洲PK10的公式杀号
     *
     * @return
     */
    public static String getAuspksKillNumber() {
        return getKillNumber(10);
    }

    /**
     * 获取德州PK10的公式杀号
     *
     * @return
     */
    public static String getSpdpksKillNumber() {
        return getKillNumber(10);
    }

    /**
     * 获取德州PK10的公式杀号
     *
     * @return
     */
    public static String getLuckpksKillNumber() {
        return getKillNumber(10);
    }

    /**
     * 获取10分PK10的公式杀号
     *
     * @return
     */
    public static String getTenpksKillNumber() {
        return getKillNumber(10);
    }

    /**
     * 获取5分PK10的公式杀号
     *
     * @return
     */
    public static String getFivepksKillNumber() {
        return getKillNumber(10);
    }

    /**
     * 获取幸运飞艇的公式杀号
     *
     * @return
     */
    public static String getXyftKillNumber() {
        return getKillNumber(10);
    }

    /**
     * 获取六合彩的公式杀号
     *
     * @return
     */
    public static String getLhcKillNumber() {
        return getKillNumber(49);
    }

    /**
     * 获取公式杀号
     *
     * @return
     */
    public static String getKillNumber(int max) {
        StringBuilder killNumber = new StringBuilder();
        for (int i = 0; i < 5; i++) {
            String numStr;
            do {
                int num = random.nextInt(max) + 1;
                numStr = (num < 10 ? "0" : "") + num;
            } while (killNumber.toString().contains(numStr));

            if (i == 0) {
                killNumber.append(numStr);
            } else {
                killNumber.append(",").append(numStr);
            }
        }
        return killNumber.toString();
    }

    /**
     * 生成随机数字
     *
     * @param length
     * @return
     */
    public static String randomNum(int length) {
        // 35是因为数组是从0开始的，26个字母+10个数字
        final int maxNum = 36;
        int i; // 生成的随机数
        int count = 0; // 生成的密码的长度
        char[] str = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9'};
        StringBuffer sb = new StringBuffer("");
        Random r = new Random();
        while (count < length) {
            // 生成随机数，取绝对值，防止生成负数
            i = Math.abs(r.nextInt(maxNum)); // 生成的数最大为36-1

            if (i >= 0 && i < str.length) {
                sb.append(str[i]);
                count++;
            }
        }
        return sb.toString();
    }

    /**
     * 獲取快三隨機數
     *
     * @param length
     * @return
     */
    public static String randomQuickNum(int length) {
        // 35是因为数组是从0开始的，26个字母+10个数字
        final int maxNum = 36;
        int i; // 生成的随机数
        int count = 0; // 生成的密码的长度
        char[] str = {'1', '2', '3', '4', '5', '6'};
        StringBuffer sb = new StringBuffer("");
        Random r = new Random();
        while (count < length) {
            // 生成随机数，取绝对值，防止生成负数
            i = Math.abs(r.nextInt(maxNum)); // 生成的数最大为36-1

            if (i >= 0 && i < str.length) {
                sb.append(str[i]);
                count++;
            }
        }
        return sb.toString();
    }

    /**
     * 生成随机数字和字母
     *
     * @param length
     * @return
     */
    public static String randomString(int length) {
        String val = "";
        Random random = new Random();

        // 参数length，表示生成几位随机数
        for (int i = 0; i < length; i++) {
            String charOrNum = random.nextInt(2) % 2 == 0 ? "char" : "num";
            // 输出字母还是数字
            if ("char".equalsIgnoreCase(charOrNum)) {
                // 输出是大写字母还是小写字母
                int temp = random.nextInt(2) % 2 == 0 ? 65 : 97;
                val += (char) (random.nextInt(26) + temp);
            } else if ("num".equalsIgnoreCase(charOrNum)) {
                val += String.valueOf(random.nextInt(10));
            }
        }
        return val;
    }

    /**
     * 生成uuid(去掉了-)
     *
     * @return
     */
    public static String uuid() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }

    /**
     * 生成：五位随机数 + 当前年月日时分秒 yyMMddHHmmss 共17位
     *
     * @return
     */
    public static String getRandNo() {
        SimpleDateFormat simpleDateFormat;
        simpleDateFormat = new SimpleDateFormat("yyMMddHHmmss");
        Date date = new Date();
        String str = simpleDateFormat.format(date);
        Random random = new Random();
        int rannum = (int) (random.nextDouble() * (99999 - 10000 + 1)) + 10000;// 获取5位随机数
        return rannum + str;// 当前时间
    }

    /**
     * 获取一定长度的随机字符串
     *
     * @param length 指定字符串长度
     * @return 一定长度的字符串
     */
    public static String getRandomStringByLength(int length) {
        String base = "abcdefghijklmnopqrstuvwxyz0123456789";
        Random random = new Random();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < length; i++) {
            int number = random.nextInt(base.length());
            sb.append(base.charAt(number));
        }
        return sb.toString();
    }
}
