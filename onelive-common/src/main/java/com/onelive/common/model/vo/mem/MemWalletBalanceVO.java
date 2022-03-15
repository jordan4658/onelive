package com.onelive.common.model.vo.mem;

import java.math.BigDecimal;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel
public class MemWalletBalanceVO {

    @ApiModelProperty("金币（钱包余额，可以提现）")
    private BigDecimal amount;

    @ApiModelProperty("银豆（礼物使用，不可提现）")
    private BigDecimal silverBean;
}
