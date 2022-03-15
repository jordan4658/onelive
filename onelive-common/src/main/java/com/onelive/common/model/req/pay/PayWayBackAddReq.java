package com.onelive.common.model.req.pay;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @author 就不告诉你
 * @version V1.0
 * @ClassName: PayWayBackAddReq
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @date 创建时间：2021/4/16 17:07
 */
@Data
@ApiModel
public class PayWayBackAddReq {


    @ApiModelProperty(value = "[必填]支付商id",required = true)
    private Long providerId;

    @ApiModelProperty(value = "[必填]支付类型code：1-支付宝、2-微信、3-银联",required = true)
    private Integer payTypeCode;

    @ApiModelProperty(value = "[必填]支付方式的名称",required = true)
    private String payWayName;

    @ApiModelProperty(value = "[必填]国家编码（区分支付方式是那个国家的）",required = true)
    private String countryCode;

    @ApiModelProperty(value = "[必填]支付方式的标识区分是h5还是扫码",required = true)
    private String payWayTag;

    @ApiModelProperty(value = "[必填]赠送类型： 0-不赠送，1-首充赠送，2-每次赠送",required = true)
    private Integer givingType;

    @ApiModelProperty(value = "[必填]赠送比例 %",required = true)
    private BigDecimal payWayGivingRatio;

    @ApiModelProperty(value = "[必填]单笔最低充值金额",required = true)
    private BigDecimal minAmt;

    @ApiModelProperty(value = "[必填]单笔最高充值金额",required = true)
    private BigDecimal maxAmt;

//    @ApiModelProperty(value = "[必填]快捷充值选项，多个使用“,”分隔开",required = true)
//    private String shortcut;

    @ApiModelProperty(value = "[必填]是否允许输入金额：false-否，true-是",required = true)
    private Boolean isInput;

    @ApiModelProperty(value = "[必填]排序字段",required = true)
    private Long sortBy;

}
