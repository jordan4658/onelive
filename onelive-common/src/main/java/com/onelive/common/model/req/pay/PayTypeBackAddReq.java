package com.onelive.common.model.req.pay;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author 就不告诉你
 * @version V1.0
 * @ClassName: PayTypeBackAddReq
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @date 创建时间：2021/4/16 19:22
 */
@Data
@ApiModel("支付类型新增Req")
public class PayTypeBackAddReq {

    @ApiModelProperty("支付类型code：1-momo、2-微信、3-银联")
    private Integer payTypeCode;

    @ApiModelProperty("支付类型名称")
    private String payTypeName;

    @ApiModelProperty("是否热门：0-否、1-是")
    private Boolean isHot;

    @ApiModelProperty("支付类型图标url")
    private String iconUrl;



}
