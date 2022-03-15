package com.onelive.common.model.req.pay;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author 就不告诉你
 * @version V1.0
 * @ClassName: CancelWithdrawReq
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @date 创建时间：2021/4/23 16:34
 */
@Data
@ApiModel("取消-提现请求req")
public class CancelWithdrawReqFeign {

    @ApiModelProperty(value = "[必填]用户ID",required = true)
    private Long userId;

    @ApiModelProperty(value = "[必填]提现订单号",required = true)
    private String withdrawOrderNo;

    @ApiModelProperty(value = "[必填]取消说明",required = true)
    private String cancelExplain;


}
