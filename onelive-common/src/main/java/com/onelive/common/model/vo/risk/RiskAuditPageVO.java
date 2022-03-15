package com.onelive.common.model.vo.risk;

import com.github.pagehelper.PageInfo;
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
public class RiskAuditPageVO {

    @ApiModelProperty("提出额度小计")
    private BigDecimal sumPageProposedQuota;

    @ApiModelProperty("出款金额小计")
    private BigDecimal sumPageOutAmount;

    @ApiModelProperty("优惠金额小计")
    private BigDecimal sumPageDiscAmount;

    private RiskAuditSumVO riskAuditSumVO;

    private PageInfo<RiskAuditVO> pageInfo;

}
