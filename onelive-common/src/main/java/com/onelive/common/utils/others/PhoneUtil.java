package com.onelive.common.utils.others;

import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.onelive.common.constants.business.LoginConstants;
import com.onelive.common.enums.StatusCode;
import com.onelive.common.exception.BusinessException;
import lombok.extern.slf4j.Slf4j;

/**
 * @ClassName : PhoneUtil
 * @Description : 手机号工具类
 */
@Slf4j
public class PhoneUtil {
    private static final PhoneNumberUtil phoneNumberUtil = PhoneNumberUtil.getInstance();

    /**
     * 功能描述: 验证是否是正确的手机号
     *
     * @param tel  电话号码
     * @param area 区号
     * @auther: muyu
     * @return: void
     * @date: 2020/8/31 13:26
     */
    public static void checkPhone(String tel, String area){

        if(!area.matches(LoginConstants.PHONE_AREA_REGEX) || !tel.matches(LoginConstants.PHONE_REGEX)){
            throw new BusinessException(StatusCode.PHONE_VALID);
        }
        /*int code = Integer.parseInt(area);
        long phone = Long.parseLong(tel);
        Phonenumber.PhoneNumber pn = new Phonenumber.PhoneNumber();
        pn.setCountryCode(code);
        pn.setNationalNumber(phone);
        if (!phoneNumberUtil.isValidNumber(pn)) {
            throw new BusinessException(StatusCode.PHONE_VALID);
        }*/
    }


}
