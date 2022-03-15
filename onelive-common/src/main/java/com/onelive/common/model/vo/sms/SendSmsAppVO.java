package com.onelive.common.model.vo.sms;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel
public class SendSmsAppVO {

    @ApiModelProperty("下次短信发送间隔时间 单位：秒")
    private long  countDown;
}
