package com.onelive.common.model.vo.mem;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 用户状态
 */
@Data
@ApiModel
public class MemUserSetVO {

    /**
     * 是否冻结 0否1是（用户与主播共用）
     */
    @ApiModelProperty("是否冻结 0否1是")
    private Boolean isFrozen;

    /**
     * 是否返点 0否1是
     */
    @ApiModelProperty("是否返点 0否1是")
    private Boolean isCommission;

    /**
     * 是否允许投注 0否1是
     */
    @ApiModelProperty("是否允许投注 0否1是")
    private Boolean isBet;

    /**
     * 是否允许出款 0否1是
     */
    @ApiModelProperty("是否允许出款 0否1是")
    private Boolean isDispensing;


}
