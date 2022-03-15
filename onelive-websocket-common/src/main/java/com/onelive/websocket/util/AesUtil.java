package com.onelive.websocket.util;

import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sun.misc.BASE64Decoder;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.spec.SecretKeySpec;
import java.util.Date;

/**
 * @ClassName : AesUtil
 * @Description : ios、安卓、h5、fluter各端端通用加密工具类
 * @Author : muyu
 * @Date: 2020-10-04 15:17
 * 参考地址：
 * IOS 与 安卓 https://blog.csdn.net/Kevindongkun/article/details/92832373?utm_medium=distribute.pc_relevant.none-task-blog-BlogCommendFromMachineLearnPai2-1.nonecase&depth_1-utm_source=distribute.pc_relevant.none-task-blog-BlogCommendFromMachineLearnPai2-1.nonecase
 * vue https://www.cnblogs.com/libo0125ok/p/9224121.html
 * fluter https://www.jianshu.com/p/7d18a12afbe6
 */
public class AesUtil {
    private static Logger logger = LoggerFactory.getLogger(AesUtil.class);

    // 加密模式ECB  填充方式PKCS5Padding
    private static final String ALGORITHMSTR = "AES/ECB/PKCS5Padding";


    //data加密
    public static byte[] aesEncryptToBytes(String content, String encryptKey)  throws Exception {
        KeyGenerator kgen = KeyGenerator.getInstance("AES");
        kgen.init(128);    //密钥长度 128
        Cipher cipher = Cipher.getInstance(ALGORITHMSTR);
        cipher.init(Cipher.ENCRYPT_MODE, new SecretKeySpec(encryptKey.getBytes(), "AES"));
        return cipher.doFinal(content.getBytes("utf-8"));
    }

    //data解密
    public static String aesDecryptByBytes(byte[] encryptBytes, String decryptKey) throws Exception {
        KeyGenerator kgen = KeyGenerator.getInstance("AES");
        kgen.init(128);
        Cipher cipher = Cipher.getInstance(ALGORITHMSTR);
        cipher.init(Cipher.DECRYPT_MODE, new SecretKeySpec(decryptKey.getBytes(), "AES"));
        byte[] decryptBytes = cipher.doFinal(encryptBytes);

        return new String(decryptBytes,"utf-8");
    }

    //字节加密
    public static String base64Encode(byte[] bytes){
        return Base64.encodeBase64String(bytes);
    }

    //字节解密
    public static byte[] base64Decode(String base64Code) throws Exception{
        return new BASE64Decoder().decodeBuffer(base64Code);
    }
    //字符串加密
    public static String aesEncrypt(String content, String encryptKey) {
        try {
            return base64Encode(aesEncryptToBytes(content, encryptKey));
        }catch (Exception e){
            logger.error("AES加密失败",e);
        }
        return null;
    }

    //字符串解密
    public static String aesDecrypt(String encryptStr, String decryptKey) {
        try {
            return aesDecryptByBytes(base64Decode(encryptStr), decryptKey);
        }catch (Exception e){
            logger.error("AES解密密失败",e);
        }
        return null;
    }

    public static void main(String[] args) {
        Date now = new Date();
        System.out.println(now.getTime());


       // String checkString = "hqlive||123||1601798284995";
        String checkString = "1614419729000||7F02E158-F2D6-40DB-B896-32DE4C4A266B||||testRedis";
       // logger.info("checkString-->{}",checkString);
        String keySignature =AesUtil.aesEncrypt(checkString,"c1kgVioySoUVimtw");
        System.out.println(keySignature);

//        String aa = "13jwtQ3cceeZnIiuRYmWvp6PW44fZKMggzcbtG5ofRO8t1ljIGt3TLREvCKCGtlqODcBhri6jethwuonkX2Z4ZEKcJFn1JmY/gsL0PpiQeg=";
//        keySignature =   AesUtil.aesDecrypt(aa,"c1kgVioySoUVimtw");
//        System.out.println(keySignature);

    }
}
