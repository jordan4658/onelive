package com.onelive.common.model.vo.mem;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

@Data
@ApiModel
public class MemWalletVO {

    @ApiModelProperty("用户钱包id")
    private Long walletId;

    @ApiModelProperty("钱包类型：1-平台钱包、2-OBG游戏钱包、")
    private Integer walletType;

    @ApiModelProperty("用户id")
    private Long userId;

    @ApiModelProperty("会员账号")
    private String account;

    @ApiModelProperty("钱包名称")
    private String walletName;

    @ApiModelProperty("金币（钱包余额，可以提现）")
    private BigDecimal amount;

    @ApiModelProperty("银豆（礼物使用，不可提现）")
    private BigDecimal silverBean;

    @ApiModelProperty("剩余打码量，打码量为0才能进行提现")
    private BigDecimal accountDml;

    @ApiModelProperty("累计打码量")
    private BigDecimal sumAccountDml;

    @ApiModelProperty("累计提现金额")
    private BigDecimal sumWithdrawAmount;

    @ApiModelProperty("总充值金额")
    private BigDecimal sumRechargeAmount;

    @ApiModelProperty("是否允许提现：0-否，1-是")
    private Boolean isWithdrawal;

    @ApiModelProperty("最大充值金额")
    private BigDecimal payMax;

    @ApiModelProperty("首次充值金额")
    private BigDecimal payFirst;

    @ApiModelProperty("充值总次数")
    private Integer payNum;

    @ApiModelProperty("最大提现金额")
    private BigDecimal withdrawalMax;

    @ApiModelProperty("首次提现金额")
    private BigDecimal withdrawalFirst;

    @ApiModelProperty("提现总次数")
    private Integer withdrawalNum;

    @ApiModelProperty("游戏钱包专用")
    private Integer gameWallet;

    @ApiModelProperty("金币单位")
    private String shortcutOptionsUnit;

    @ApiModelProperty("提现汇率")
    private String txExChange;

    @ApiModelProperty("充值汇率")
    private String czExChange;

    @ApiModelProperty("实际支付币种单位")
    private String currencyUint;

}
