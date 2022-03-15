package com.onelive.common.model.vo.pay;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @author 就不告诉你
 * @version V1.0
 * @ClassName: PayHandleFinanceAmtBackVO
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @date 创建时间：2021/4/19 11:05
 */
@Data
@ApiModel
public class PayHandleFinanceAmtBackVO {


    @ApiModelProperty("会员账号")
    private String account;

    @ApiModelProperty("会员昵称")
    private String nickname;

    @ApiModelProperty("会员唯一标识")
    private String accno;

    @ApiModelProperty("用户类型：0-普通用户 、1-游客用户、2-主播、3-家族长")
    private String userType;

    @ApiModelProperty("会员金币余额")
    private BigDecimal walletAmount;

    @ApiModelProperty("会员银豆余额")
    private BigDecimal walletSilverBean;

    @ApiModelProperty("会员所需打码量")
    private BigDecimal accountDml;

    @ApiModelProperty("累计已打码量")
    private BigDecimal sumAccountDml;

    @ApiModelProperty("累计充值金额")
    private BigDecimal sumRechargeAmount;

    @ApiModelProperty("累计提现金额")
    private BigDecimal sumWithdrawAmount;




}
