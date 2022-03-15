package com.onelive.common.utils.others;

import lombok.extern.slf4j.Slf4j;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.UUID;

@Slf4j
public class SecurityUtils {


    /** 定义加密迭代次数 */
    private static final int HASH_ITERATIONS = 3;

    private SecurityUtils() {
    }

    /**
     * @param str  待加密字符串
     * @param salt 加密盐
     * @Author: admin
     * @Description: MD5 + 盐 迭代加密
     * @Version: 1.0.0
     * @Date; 2018/4/25 15:45
     * @return: java.lang.String
     */
    public final static String MD5SaltEncrypt(String str, String salt) {
        String md5 = str + salt;
        for (int i = 0; i < HASH_ITERATIONS; i++) {
            md5 = MD5(md5);
        }
        return md5;
    }

    /**
     * @param str 待加密字符串
     * @Author: admin
     * @Description: MD5 迭代加密
     * @Version: 1.0.0
     * @Date; 2018/4/25 15:45
     * @return: java.lang.String
     */
    public final static String MD5Encrypt(String str) {
        String md5 = str;
        for (int i = 0; i < HASH_ITERATIONS; i++) {
            md5 = MD5(md5);
        }
        return md5;
    }

    /**
     * MD5方法
     *
     * @param string
     * @return String
     */
    public final static String MD5(String string) {
        try {
            return byteArrayToHexString(MessageDigest.getInstance("MD5").digest(string.getBytes()));
        } catch (NoSuchAlgorithmException e) {
            log.error("MD5 encrypt occur error.", e);
        }
        return null;
    }

    /**
     * 哈希方法
     *
     * @param string
     * @return String
     */
    public final static String SHA(String string) {
        try {
            return byteArrayToHexString(MessageDigest.getInstance("SHA").digest(string.getBytes()));
        } catch (NoSuchAlgorithmException e) {
            log.error("SHA encrypt occur error.", e);
        }
        return null;
    }

    /**
     * byteArrayToHexString
     *
     * @param bytes
     * @return
     * @author ruan 2013-7-17
     */
    private final static String byteArrayToHexString(byte[] bytes) {
        StringBuilder buf = new StringBuilder(bytes.length * 2);
        for (int i = 0; i < bytes.length; i++) {
            if (((int) bytes[i] & 0xff) < 0x10) {
                buf.append("0");
            }
            buf.append(Long.toString((int) bytes[i] & 0xff, 16));
        }
        return buf.toString();
    }

    /**
     * @Author: admin
     * @Description: 获取随机盐
     * @Version: 1.0.0
     * @Date; 2018/5/2 17:49
     * @return: java.lang.String
     */
    public final static String getRandomSalt() {
        return UUID.randomUUID().toString().replace("-", "");
    }


}
