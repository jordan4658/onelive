package com.onelive.common.model.vo.mem;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel
public class MemUserLevelSelectVO {
    private Long id;

    /**
     * 等级权重
     */
    @ApiModelProperty("等级")
    private Integer levelWeight;

    /**
     * 等级名称
     */
    @ApiModelProperty("等级名称")
    private String levelName;

}
