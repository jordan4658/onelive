package com.onelive.common.model.req.sms;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
@ApiModel
public class SmsUpdateReq extends  SmsAddReq {

    @ApiModelProperty("[必填]短信方式ID")
    @NotNull
    private Long  id;

}
