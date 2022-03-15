package com.onelive.common.constants.sms;

import lombok.Getter;

public class SmsConstants {

    public interface Key {
        public static final String RANDCODE = "KSMSService:randcode:%s:%s";
    }


    @Getter
    public enum smsCode {
        DXB_CODE("短信宝", "dxb"),
        VERIFY3_CODE("Verify3", "Verify3");

        private String name;
        private String value;

        smsCode(String name, String value) {
            this.name = name;
            this.value = value;
        }
    }

    @Getter
    public enum smsType {
        REGISTER("注册", 1, "REGISTER"),
        UPDATE_PASSWORD("更新密码", 2, "UPDATE_PASSWORD"),
        BINDING_PHONE("绑定手机", 3, "BINDING_PHONE"),
        RETRIEVE_PASSWORD("找回密码", 4, "RETRIEVE_PASSWORD");

        private String name;
        private Integer code;
        private String value;

        smsType(String name, Integer code, String value) {
            this.name = name;
            this.code = code;
            this.value = value;
        }
    }
}
