package com.onelive.common.model.req.sms;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel
public class SmsQueryReq  {

    @ApiModelProperty("[必填]短信方式名称")
    private String smsName;

    @ApiModelProperty("[必填]是否开启：true-是、false-否")
    private Boolean isOpen;

    @ApiModelProperty("[必填]短信标识")
    private String smsCode;

    @ApiModelProperty("[必填]第几页")
    private Integer pageNum = 1;

    @ApiModelProperty("[必填]每页最大页数")
    private Integer pageSize = 10;
}
