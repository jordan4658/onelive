package com.onelive.common.model.req.mem;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@ApiModel
public class MemUserSetStatusReq implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 用户id
     */
    @ApiModelProperty(value = "用户id[必填]",required = true)
    private Long id;
    /**
     * 是否冻结 0否1是（用户与主播共用）
     */
    @ApiModelProperty(value = "是否冻结 0否1是[必填]",required = true)
    private Boolean isFrozen;

    /**
     * 是否返点 0否1是
     */
    @ApiModelProperty(value = "是否返点 0否1是[必填]",required = true)
    private Boolean isCommission;

    /**
     * 是否允许投注 0否1是
     */
    @ApiModelProperty(value = "是否允许投注 0否1是[必填]",required = true)
    private Boolean isBet;

    /**
     * 是否允许出款 0否1是
     */
    @ApiModelProperty(value = "是否允许出款 0否1是[必填]",required = true)
    private Boolean isDispensing;


}
