package com.onelive.common.model.vo.sys;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * @ClassName ShortMsgVO
 * @Desc 短信展示类
 * @Date 2021/4/5 19:14
 */
@Data
@ApiModel
public class ShortMsgVO {

    @ApiModelProperty("发送类型")
    private String msgTypeInfo;

    @ApiModelProperty("状态")
    private String masStatusInfo;

    @ApiModelProperty("手机号码,只显示后面4位")
    private String mobilePhone;

    @ApiModelProperty("区号")
    private String areaCode;

    @ApiModelProperty("验证码")
    private String masCode;

    @ApiModelProperty("短信内容")
    private String masBody;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @ApiModelProperty("发送时间")
    private Date sendDate;

    @ApiModelProperty("发送的ip")
    private String sendIp;

    @ApiModelProperty("备注")
    private String masRemark;
    
    @ApiModelProperty("用户账号")
    private String userAccount;

    @ApiModelProperty("服务商名")
    private String businessName;
 
    @ApiModelProperty("设备名")
    private String devicenName;
    
    @ApiModelProperty("设备码")
    private String deviceId;

}    
    