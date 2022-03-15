package com.onelive.common.model.req.pay;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;


@Data
@ApiModel
public class PayShortcutOptionsAddReq {

    @ApiModelProperty("支付方式ID")
    private Long payWayId;

    @ApiModelProperty("多个值使用逗号分割开")
    private String shortcutOptionsContent;


}
