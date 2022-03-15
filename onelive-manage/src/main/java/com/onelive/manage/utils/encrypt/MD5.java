package com.onelive.manage.utils.encrypt;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @description 数据加密
 * @date 2011.05.27
 */
public class MD5 {
    public MD5() {
    }

    private static final char[] DIGITS = {'0', '1', '2', '3', '4', '5', '6',
            '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};

    /**
     * 对字符串进行MD5加密
     *
     * @param text 明文
     * @return 密文
     */
    public static String md5(String text, String charsetName) {
        MessageDigest msgDigest = null;
        try {
            msgDigest = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException(
                    "System doesn't support MD5 algorithm.");
        }
        try {
            if (charsetName == null) {
                msgDigest.update(text.getBytes());
            } else {
                msgDigest.update(text.getBytes(charsetName));
            }
        } catch (UnsupportedEncodingException e) {
            throw new IllegalStateException(
                    "System doesn't support your  EncodingException.");
        }

        byte[] bytes = msgDigest.digest();
        String md5Str = new String(encodeHex(bytes));
        return md5Str;
    }

    /**
     * 对字符串进行MD5加密
     *
     * @param text 需要加密的字符串
     * @return 返回加密后的字符串
     * @about version ：1.00
     * @auther : wangjun
     * @Description ：
     */
    public static String md5(String text) {
        return md5(text, null);
    }

    private static char[] encodeHex(byte[] data) {
        int l = data.length;
        char[] out = new char[l << 1];
        for (int i = 0, j = 0; i < l; i++) {
            out[j++] = DIGITS[(0xF0 & data[i]) >>> 4];
            out[j++] = DIGITS[0x0F & data[i]];
        }
        return out;
    }
	
	
	/*public static void main(String[] args) {
		System.out.println(md5("LOGINNUM18191090817"));
	}*/
}
