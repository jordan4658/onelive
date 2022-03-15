package com.onelive.common.model.req.pay;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author 就不告诉你
 * @version V1.0
 * @ClassName: BankCallBackVO
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @date 创建时间：2021/4/14 15:08
 */
@Data
@ApiModel
public class OfflineRechargeHandleReq {

    @ApiModelProperty(value = "[必填]订单id",required = true)
    private Long orderId;

    @ApiModelProperty(value = "[必填]会员账号",required = true)
    private String account;

    @ApiModelProperty(value = "[必填]订单状态 1-处理中  2-成功  3-失败 4-取消",required = true)
    private Integer orderStatus;

    @ApiModelProperty(value = "[必填]操作说明",required = true)
    private String instructions;

}
