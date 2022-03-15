package com.onelive.common.model.vo.pay;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


@Data
@ApiModel
public class PayBankVO {

    @ApiModelProperty("支付商id")
    private Long providerId;

    @ApiModelProperty("银行名称标识符 如ICBC")
    private String bankName;

    @ApiModelProperty("银行卡账号")
    private String bankAccountNo;

    @ApiModelProperty("银行开户名称")
    private String bankAccountName;

    @ApiModelProperty("银行开户行地址")
    private String bankAddress;

}
