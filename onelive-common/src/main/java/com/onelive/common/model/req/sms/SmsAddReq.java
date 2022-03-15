package com.onelive.common.model.req.sms;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@ApiModel
@Validated
public class SmsAddReq {

    @NotBlank
    @ApiModelProperty("[必填]短信方式名称")
    private String  smsName;

    @NotBlank
    @ApiModelProperty("[必填]apikey(或者账号)")
    private String apiKey="";

    @NotBlank
    @ApiModelProperty("[必填]秘钥(或者密码)")
    private String secretKey="";

    @NotBlank
    @ApiModelProperty("[必填]发送短信的UrL")
    private String sendUrl;

    @ApiModelProperty("[必填]查询短信余额url")
    private String queryUrl;

    @NotNull
    @ApiModelProperty("[必填]短信发送有效时间-单位：分钟")
    private Integer validTime;

    @NotBlank
    @ApiModelProperty("[必填]短信方式标识")
    private String smsCode;

    @NotNull
    @ApiModelProperty("[必填]排序号")
    private Integer sortNum;

    @ApiModelProperty("[必填]是否开启：true-是、false-否")
    private Boolean isOpen=true;

//    @ApiModelProperty("是否删除：true-是、false-否")
//    private Boolean isDelete=false;
}
