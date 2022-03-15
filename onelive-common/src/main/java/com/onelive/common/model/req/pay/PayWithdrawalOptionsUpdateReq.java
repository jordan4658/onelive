package com.onelive.common.model.req.pay;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@Data
@ApiModel
public class PayWithdrawalOptionsUpdateReq extends  PayWithdrawalOptionsAddReq{

    @ApiModelProperty("提现快捷选项ID")
    private Long silverBeanOptionsId;


}
