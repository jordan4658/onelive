package com.onelive.common.model.req.pay;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author 就不告诉你
 * @version V1.0
 * @ClassName: CancelRechargeReq
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @date 创建时间：2021/4/23 14:28
 */
@Data
@ApiModel
public class CancelRechargeReq {

    @ApiModelProperty(value = "[必填]取消的充值订单号",required = true)
    private String orderNo;

}
