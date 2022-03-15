package com.onelive.common.model.req.pay;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
@ApiModel
public class PayWithdrawalOptionsIsEnableReq {

    @ApiModelProperty("提现快捷选项ID")
    private List<Long> silverBeanOptionsIds;


    @ApiModelProperty("是否启用：-0-否、1-是")
    private Boolean isEnable;
}
