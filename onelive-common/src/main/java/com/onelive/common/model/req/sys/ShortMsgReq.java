package com.onelive.common.model.req.sys;

import java.util.Date;

import com.onelive.common.model.common.PageReq;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @ClassName ShortMsgVO
 * @Desc 短信请求类
 * @Date 2021/4/5 19:14
 */
@Data
@ApiModel
public class ShortMsgReq extends PageReq {

    @ApiModelProperty("短信类型")
    private Integer msgType;

    @ApiModelProperty("手机号码")
    private String mobilePhone;

    @ApiModelProperty("短信状态码")
    private Integer masStatus;
    
    @ApiModelProperty("发送时间")
    private String sendDateStart;
    
    @ApiModelProperty("发送时间")
    private String sendDateEnd;

    @ApiModelProperty("发送的ip")
    private String sendIp;
    
    @ApiModelProperty("区号")
    private String areaCode;
    
    @ApiModelProperty("服务商名")
    private String businessName;
 
    @ApiModelProperty("设备名")
    private String devicenName;
    
    @ApiModelProperty("设备码")
    private String deviceId;
    
    @ApiModelProperty("用户账号")
    private String userAccount;


}    
    