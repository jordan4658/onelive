package com.onelive.common.model.req.sys;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * <p>
 *充值说明addreq
 * </p>
 *
 */
@Data
@ApiModel
public class SysRechargeHelpUpdateReq {

    @ApiModelProperty("充值的主键")
    private Long id;

    @ApiModelProperty("多语言数组")
    private List<SysRechargeHelpUpdateListReq> data;

}
