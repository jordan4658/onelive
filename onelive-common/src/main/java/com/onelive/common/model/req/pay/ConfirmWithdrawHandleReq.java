package com.onelive.common.model.req.pay;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author 就不告诉你
 * @version V1.0
 * @ClassName: ConfirmWithdrawHandleReq
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @date 创建时间：2021/4/14 17:55
 */
@Data
@ApiModel
public class ConfirmWithdrawHandleReq {

    @ApiModelProperty(value = "[必填]提现订单Id",required = true)
    private Long orderId;

    @ApiModelProperty(value = "[必填]用户账号",required = true)
    private String account;

    @ApiModelProperty(value = "[必填]订单状态 1-处理中  2-成功  3-失败 4-取消",required = true)
    private Integer orderStatus;

    @ApiModelProperty(value = "[必填]操作说明",required = true)
    private String instructions;
}
