package com.onelive.common.model.req.pay;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
@ApiModel
public class PayWithdrawalOptionsAddReq {

    @ApiModelProperty("多个值使用逗号分割开")
    private String optionsContent;



}
