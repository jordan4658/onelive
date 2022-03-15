package com.onelive.common.model.req.pay;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @author 就不告诉你
 * @version V1.0
 * @ClassName: WithdrawReq
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @date 创建时间：2021/4/14 9:41
 */
@Data
@ApiModel("提现请求req")
public class WithdrawReq {

    @ApiModelProperty(value = "[必填]提现金币数量",required = true)
    private BigDecimal price;

    @ApiModelProperty(value = "[必填]提现银行卡id",required = true)
    private Long BankAccid;

//    @ApiModelProperty(value = "[必填]支付密码",required = true)
//    private String payPassword;
//
//    @ApiModelProperty(value = "[必填]手机短信验证码",required = true)
//    private String smsCode;


}
