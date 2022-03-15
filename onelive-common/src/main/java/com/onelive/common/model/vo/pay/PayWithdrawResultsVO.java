package com.onelive.common.model.vo.pay;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author 就不告诉你
 * @version V1.0
 * @ClassName: PayWithdrawResultsVO
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @date 创建时间：2021/4/30 18:22
 */
@ApiModel
@Data
public class PayWithdrawResultsVO {

    @ApiModelProperty("提现金额")
    private BigDecimal withdrawAmt;

    @ApiModelProperty("银行名称")
    private String bankName;

    @ApiModelProperty("到账银行卡尾号")
    private String bankTail;

    @ApiModelProperty("提现订单状态:1-处理中  2-成功  3-失败 4-取消")
    private Integer orderStatus;

    @ApiModelProperty("预计到账时间")
    private Date endTime;

    @ApiModelProperty("取消、失败 原因")
    private String cause;



}
