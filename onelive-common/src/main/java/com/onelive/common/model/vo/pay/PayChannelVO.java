package com.onelive.common.model.vo.pay;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel
public class PayChannelVO {

    @ApiModelProperty("通道名称")
    private String channelName;

    @ApiModelProperty("通道编码")
    private String channelCode;
}
