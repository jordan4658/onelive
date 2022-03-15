package com.onelive.common.model.req.pay;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @author 就不告诉你
 * @version V1.0
 * @ClassName: RechargerReq
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @date 创建时间：2021/4/8 18:41
 */
@Data
@ApiModel
public class RechargeAddReq {

    @ApiModelProperty(value = "支付渠道id",required = true)
    private Long payWayId;

    @ApiModelProperty(value = "金币数量",required = true)
    private BigDecimal price;

    @ApiModelProperty(value = "入款姓名",required = true)
    private String payName;

    @ApiModelProperty("入款备注")
    private String payNot;

    @ApiModelProperty(value = "支付渠道对应的银行code",required = true)
    private String backCode;

}
