package com.onelive.common.utils.Login;

import cn.hutool.core.util.StrUtil;
import com.onelive.common.constants.sys.LangConstants;
import lombok.Data;

/**
 * @ClassName : LoginInfoUtil
 * @Description : 获取前端发起请求的一些基本信息
 */
public class LoginInfoUtil implements AutoCloseable{

    public static final ThreadLocal<LoginInfo> infoHolder = ThreadLocal.<LoginInfo>withInitial(() -> new LoginInfo());

    @Override
    public void close() throws Exception {
        infoHolder.remove();
    }



    @Data
    public static class LoginInfo {
        /**
         * 获取ip
         */
        private String ip;
        /**
         * 请求来源 ios、android、pc
         */
        private String source;
        /**
         * 获取设备类型 例如华为、魅族
         */
        private String deviceType;
        /**
         * 获取每个设备的唯一标识
         */
        private String deviceId;
        
        /**
         * 获取每个设备的唯一标识
         */
        private String devices;

        /**
         * 商户code值，默认值为0
         */
        private String merchantCode = "0";

        /**
         * 获取语言标识，如vi_VN
         */
        private String lang;

        /**
         * 切换的国家id TODO 默认值后面去掉
         */
        private Long countryId =1l;

        /**
         * 切换的国家code值
         */
        private String countryCode;

        /**
         * 获取当前ip所在区域
         */
        private String area;

        /**
         * 当前登录人id
         */
        private Long userId;

        /**
         * 用户唯一标识
         */
        private String accno;

        /**
         * 后台登录人账号
         */
        private String userAccount;
        /**
         * 邀请码
         */
        private String inviteCode;

        /**
         * 加密解密类型 1,2,3,4,5
         */
        private String keyType;

        /**
         * 加密解密秘钥
         */
        private String secretKey;

        /**
         * 调试，不走加密
         */
        private Boolean isTest = false;

        /**
         * 是否feign远程服务, 如果是就不走加密
         */
        private Boolean isFeign = false;
        /**
         * 记录本次token, 给用户下注时远程服务使用
         */
        private String token;
        
        /**
         * 用户类型：0-普通用户 、1-游客用户、2-主播、3-家族长
         */
        private Integer userType;

    }


    public static void setAccno(String accno) {
        infoHolder.get().setAccno(accno);
    }

    public static String getAccno() {
        return infoHolder.get().getAccno();
    }

    public static void setIp(String ip) {
        infoHolder.get().setIp(ip);
    }

    public static String getIp() {
        return infoHolder.get().getIp();
    }

    public static void setSource(String source) {
        infoHolder.get().setSource(source);
    }

    public static String getSource() {
        return infoHolder.get().getSource();
    }

    public static void setDeviceType(String type) {
        infoHolder.get().setDeviceType(type);
    }

    public static String getDeviceType() {
        return infoHolder.get().getDeviceType();
    }

    public static void setDeviceId(String deviceId) {
        infoHolder.get().setDeviceId(deviceId);
    }

    public static String getDeviceId() {
        return infoHolder.get().getDeviceId();
    }
    
    public static void setDevices(String devices) {
    	infoHolder.get().setDevices(devices);
    }
    
    public static String getDevices() {
    	return infoHolder.get().getDevices();
    }

    public static void setMerchantCode(String merchantCode) {
        infoHolder.get().setMerchantCode(merchantCode);
    }

    public static String getMerchantCode() {
        return infoHolder.get().getMerchantCode();
    }

    public static void setLang(String lang) {
        infoHolder.get().setLang(lang);
    }

    public static String getLang() {
        return infoHolder.get().getLang();
    }

    public static void setArea(String area) {
        infoHolder.get().setArea(area);
    }

    public static String getArea() {
        return infoHolder.get().getArea();
    }

    public static void setCountryId(Long countryId){
        infoHolder.get().setCountryId(countryId);
    }

    public static Long getCountryId(){
       return infoHolder.get().getCountryId();
    }

    public static void setUserId(Long userId){
        infoHolder.get().setUserId(userId);
    }

    public static Long getUserId(){
        return infoHolder.get().getUserId();
    }

    public static void setUserAccount(String userAccount){
        infoHolder.get().setUserAccount(userAccount);
    }

    public static String getUserAccount(){
        return infoHolder.get().getUserAccount();
    }

    public static void setInviteCode(String inviteCode){
        infoHolder.get().setInviteCode(inviteCode);
    }

    public static String getInviteCode(){
        return infoHolder.get().getInviteCode();
    }

    public static void setCountryCode(String countryCode){
        infoHolder.get().setCountryCode(countryCode);
    }

    public static String getCountryCode(){
        String countryCode = infoHolder.get().getCountryCode();
        if(StrUtil.isBlank(countryCode)){
            countryCode = LangConstants.LANG_VN;
        }
        return countryCode;
    }

    public static void setKeyType(String keyType){
        infoHolder.get().setKeyType(keyType);
    }

    public static String getKeyType(){
        return infoHolder.get().getKeyType();
    }

    public static void setIsTest(Boolean keyType){
        infoHolder.get().setIsTest(keyType);
    }

    public static Boolean getIsTest(){
        return infoHolder.get().getIsTest();
    }

    public static void setSecretKey(String secretKey){
        infoHolder.get().setSecretKey(secretKey);
    }

    public static String getSecretKey(){
        return infoHolder.get().getSecretKey();
    }

    public static void remove(){
        infoHolder.remove();
    }

    public static void setIsFeign(Boolean isFeign){
        infoHolder.get().setIsFeign(isFeign);
    }

    public static Boolean getIsFeign(){
        return infoHolder.get().getIsFeign();
    }
    public static void setToken(String token){
        infoHolder.get().setToken(token);
    }

    public static String getToken(){
        return infoHolder.get().getToken();
    }
    
    public static void setUserType(Integer userType){
    	infoHolder.get().setUserType(userType);
    }
    
    public static Integer getUserType(){
    	return infoHolder.get().getUserType();
    }

}
