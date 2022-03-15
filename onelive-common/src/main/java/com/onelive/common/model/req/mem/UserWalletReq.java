package com.onelive.common.model.req.mem;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel
public class UserWalletReq {

    @ApiModelProperty("钱包类型：1-平台钱包，其他的稍后补齐")
    private Integer walletType;
}
