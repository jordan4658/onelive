package com.onelive.common.model.req.risk;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @author 就不告诉你
 * @version V1.0
 * @ClassName: RiskAuditQueryReq
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @date 创建时间：2021/6/04 16:40
 */
@Data
@ApiModel
public class RiskAuditQueryReq {


    @ApiModelProperty("分公司")
    private String branch;

    @ApiModelProperty("审核状态: 0-未审核、1-锁定、2-通过、3-拒绝")
    private Integer status;

    @ApiModelProperty("取款次数")
    private Integer times;

    @ApiModelProperty("层级: 0-默认层级、1-土豪")
    private Integer layer;

    @ApiModelProperty("会员账号")
    private String account;

    @ApiModelProperty("金额下限")
    private BigDecimal minAmount;

    @ApiModelProperty("金额上限")
    private BigDecimal maxAmount;

    @ApiModelProperty("风控人")
    private String operator;

    @ApiModelProperty("户主")
    private String bankAccountName;

}
