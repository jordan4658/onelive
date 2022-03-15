package com.onelive.common.model.req.mem;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

@Data
@ApiModel
public class UserWalletChangeReq {

    @ApiModelProperty(value = "金额",required = true)
    private BigDecimal amount;

    @ApiModelProperty(value = "划转的钱包类型（转出：需要转出的钱包类型  转入：需要转入的钱包类型）",required = true)
    private Integer walletType;

    @ApiModelProperty(value = "操作类型：0=转入、1=转出",required = true)
    private Integer optionType;


}
