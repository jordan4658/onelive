package com.onelive.common.model.req.risk;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author 就不告诉你
 * @version V1.0
 * @ClassName: RiskAuditQueryReq
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @date 创建时间：2021/6/04 16:40
 */
@Data
@ApiModel
public class RiskAuditUpdateReq {

    @ApiModelProperty("主键")
    private Long id;

    @ApiModelProperty("审核状态: 0-未审核、1-锁定、2-通过、3-拒绝")
    private Integer status;

    @ApiModelProperty("备注")
    private String remark;

}
