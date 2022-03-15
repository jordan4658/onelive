package com.onelive.common.model.req.pay;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author 就不告诉你
 * @version V1.0
 * @ClassName: PayWayBackDelReq
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @date 创建时间：2021/4/17 11:56
 */
@Data
@ApiModel
public class PayWayBackUpStatusReq {

    @ApiModelProperty(value = "[必填]支付方式id",required = true)
    private Long payWayId;

    @ApiModelProperty(value = "[必填]状态：1-启用，2-禁用",required = true)
    private Integer status;
}
