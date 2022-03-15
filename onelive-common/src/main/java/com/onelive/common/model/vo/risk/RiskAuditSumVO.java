package com.onelive.common.model.vo.risk;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @author 就不告诉你
 * @version V1.0
 * @ClassName: RiskAuditPageVO
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @date 创建时间：2021/6/4 22:37
 */
@Data
@ApiModel
public class RiskAuditSumVO {

    @ApiModelProperty("提出额度总计")
    private BigDecimal sumProposedQuota;

    @ApiModelProperty("出款金额总计")
    private BigDecimal sumOutAmount;

    @ApiModelProperty("优惠金额总计")
    private BigDecimal sumDiscAmount;

    @ApiModelProperty("总计笔数")
    private BigDecimal totalCount;

}
