package com.onelive.common.model.req.pay;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author 就不告诉你
 * @version V1.0
 * @ClassName: PayThreeProviderUpBackReq
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @date 创建时间：2021/4/17 17:13
 */
@Data
@ApiModel
public class PayThreeProviderUpStatusBackReq {

    @ApiModelProperty("支付商Id")
    private Long providerId;

    @ApiModelProperty("状态：1-启用，2-禁用")
    private Integer status;


}
