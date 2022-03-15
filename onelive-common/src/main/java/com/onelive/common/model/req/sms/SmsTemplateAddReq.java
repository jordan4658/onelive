package com.onelive.common.model.req.sms;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel
public class SmsTemplateAddReq {

    @ApiModelProperty("[必填]短信模板内容")
    private String templateContent = "";

    @ApiModelProperty("[必填]短信模版标识:1-注册、2-更新密码、3-绑定手机、4-找回密码")
    private String templateCode;

    @ApiModelProperty("[必填]排序号")
    private Integer sortNum = 0;

    @ApiModelProperty("[必填]是否开启：true-是、false-否")
    private Boolean isOpen = true;

    @ApiModelProperty("[必填]是否删除：true-是、false-否")
    private Boolean isDelete = false;

}
