package com.onelive.common.model.req.pay;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author 就不告诉你
 * @version V1.0
 * @ClassName: PayTypeBackUpReq
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @date 创建时间：2021/4/16 19:31
 */
@Data
@ApiModel("支付类型更新req")
public class PayTypeBackUpReq {

    @ApiModelProperty("支付类型Id")
    private Long payTypeId;

    @ApiModelProperty("支付类型名称")
    private String payTypeName;

    @ApiModelProperty("是否禁用：0-否、1-是")
    private Boolean isEnable;

    @ApiModelProperty("是否热门：0-否、1-是")
    private Boolean isHot;

    @ApiModelProperty("支付类型图标url")
    private String iconUrl;



}
