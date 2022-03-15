package com.onelive.common.service.sms;

import com.cloopen.rest.sdk.BodyType;
import com.cloopen.rest.sdk.CCPRestSmsSDK;
import com.onelive.common.exception.BusinessException;
import com.onelive.common.utils.others.SpringUtil;
import com.onelive.common.utils.others.StringInnerUtils;

import java.util.HashMap;

/**
 * @author lorenzo
 * @Description: 云通信发短信工具类
 * @date 2021/4/3
 */
public class YunTongXinSmsUtil {

    private static final String STATUS_CODE = "statusCode";
    private static final String STATUS_MSG = "statusMsg";
    /**
     * 成功码
     */
    private static final String STATUS_SUCCESS = "000000";

    private static YunTongXinConfig config;

    private static YunTongXinConfig getConfig() {
        if (config == null) {
            config = SpringUtil.getBean(YunTongXinConfig.class);
        }
        return config;
    }

    /**
     * 发送模板短信
     *
     * @param to   目标号码
     * @param args 模板中需要替换的参数,按循序替换
     */
    public static void sendTemplate(String to, String... args) {
        YunTongXinConfig config = getConfig();
        String appId = config.getAppId();
        String sid = config.getSid();
        String token = config.getToken();
        String templateId = config.getTemplateId();
        String serverIp = config.getServerIp();
        String serverPort = config.getServerPort();

        CCPRestSmsSDK sdk = new CCPRestSmsSDK();
        sdk.init(serverIp, serverPort);
        sdk.setAccount(sid, token);
        sdk.setAppId(appId);
        sdk.setBodyType(BodyType.Type_JSON);

        HashMap<String, Object> result = sdk.sendTemplateSMS(to, templateId, args);
        Object statusCode = result.get(STATUS_CODE);
        if (STATUS_SUCCESS.equals(statusCode)) {

        } else {
            Object statusMsg = result.get(STATUS_MSG);
            throw new BusinessException(StringInnerUtils.getWithOutNull(statusCode), StringInnerUtils.getWithOutNull(statusMsg));
        }
    }

}
