package com.onelive.common.model.vo.pay;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author 就不告诉你
 * @version V1.0
 * @ClassName: PayWithdrawResultVO
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @date 创建时间：2021/5/3 16:03
 */
@Data
@ApiModel
public class PayWithdrawResultVO {

    @ApiModelProperty("提现订单号")
    private String withdrawOrderNo;
}
