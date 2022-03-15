package com.onelive.common.model.vo.pay;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author 就不告诉你
 * @version V1.0
 * @ClassName: OnlinePayOrderRechargeBackVO
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @date 创建时间：2021/4/20 10:12
 */
@Data
@ApiModel
public class OnlinePayOrderRechargeBackVO {

    @ApiModelProperty("订单id")
    private Long orderId;

    @ApiModelProperty("支付商Id")
    private Long providerId;

    @ApiModelProperty("支付商名称")
    private String providerName;

    @ApiModelProperty("订单类型 1-线上充值 2-线下充值")
    private Integer orderType;

    @ApiModelProperty("订单编号")
    private String orderNo;

    @ApiModelProperty("会员标识号")
    private String account;

    @ApiModelProperty("会员昵称")
    private String nickname;

    @ApiModelProperty("订单总金额")
    private BigDecimal sumAmt;

    @ApiModelProperty("订单状态 1-处理中  2-成功  3-失败 4-取消")
    private Integer orderStatus;

    @ApiModelProperty("失败、取消订单原因")
    private String cancelReason;

    @ApiModelProperty("支付时间")
    private Date payDate;

    @ApiModelProperty("汇款姓名")
    private String payUser;

    @ApiModelProperty("汇款备注")
    private String payNote;

    @ApiModelProperty("创建时间")
    private Date createTime;

    @ApiModelProperty("最后修改人")
    private String updateUser;

    @ApiModelProperty("更新时间")
    private Date updateTime;

}
