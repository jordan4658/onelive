package com.onelive.common.model.vo.mem;

import java.math.BigDecimal;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel
public class WithdrawIndexVO {

    @ApiModelProperty("会员银行卡id")
    private Long bankAccid;

    @ApiModelProperty("银行账号")
    private String bankAccountNo;

    @ApiModelProperty("银行名称标识符 如ICBC")
    private String bankCode;

    @ApiModelProperty("银行名")
    private String bankName;

    @ApiModelProperty("银行卡logo地址")
    private String bankLogo;

    @ApiModelProperty("当前可提现的余额")
    private BigDecimal balance;
    
    @ApiModelProperty("实际到账的金额")
    private BigDecimal ratioBalance;

    @ApiModelProperty("礼物抽成比例")
	private BigDecimal giftRatio;

    @ApiModelProperty("账户名")
    private String account;
    
    @ApiModelProperty(value = "开户人姓名")
    private String bankAccountName;

}
