package com.onelive.common.service.sms;

import com.onelive.common.utils.http.HttpClient_Fh_Util;
import com.onelive.common.utils.others.SecurityUtils;

import java.util.Map;

public class VnSmsUtils {

    public static void main(String[] args) {
        System.out.println(SecurityUtils.MD5("GTKPEK9XM"));
        String url ="http://api.abenla.com/api/SendSms?loginName=ABM7DNN&sign="+SecurityUtils.MD5("GTKPEK9XM")+"&serviceTypeId=30&phoneNumber=84363106964&message=666666&brandName=Verify3&callBack=false&smsGuid=1";
        String result= HttpClient_Fh_Util.doGetRequest(url,null,null,null);
        System.out.println("result:"+result);

    }

}
