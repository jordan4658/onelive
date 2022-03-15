package com.onelive.common.model.vo.mem;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 查询职业列表返回的实体类
 */
@Data
@ApiModel
public class MemUserOccupationListVO {
    /**
     * 职业代码
     */
    @ApiModelProperty("职业代码")
    private String occupationCode;

    /**
     * 职业名称
     */
    @ApiModelProperty("职业名称")
    private String occupationName;
}
