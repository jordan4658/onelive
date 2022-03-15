package com.onelive.common.model.req.sms;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel
public class SmsTemplateUpdateReq {

    @ApiModelProperty("[必填]短信模板ID")
    private Long id;

    @ApiModelProperty("[必填]短信模板内容")
    private String templateContent = "";

    @ApiModelProperty("[必填]短信模版标识")
    private String templateCode;

    @ApiModelProperty("[必填]排序号")
    private Integer sortNum = 0;

    @ApiModelProperty("[必填]是否开启：true-是、false-否")
    private Boolean isOpen = true;

//    @ApiModelProperty("创建人")
//    private String createdUser;
//
//    @ApiModelProperty("创建时间")
//    private Date createdTime = new Timestamp(System.currentTimeMillis());
//
//    @ApiModelProperty("最后更新人")
//    private String updateUser;
//
//    @ApiModelProperty("最后更新时间")
//    private Date updateTime = new Timestamp(System.currentTimeMillis());
}
