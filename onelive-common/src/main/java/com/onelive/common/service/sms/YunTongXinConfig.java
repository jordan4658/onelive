package com.onelive.common.service.sms;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.io.Serializable;

/**
 * @author lorenzo
 * @Description: 云通信配置
 * @date 2021/4/3
 */
@Data
@Component
@ConfigurationProperties(prefix = "ytx")
public class YunTongXinConfig implements Serializable {

    private static final long serialVersionUID = 1L;
    public String appId;
    public String sid;
    public String token;
    public String templateId;
    public String serverIp;
    public String serverPort;

}
