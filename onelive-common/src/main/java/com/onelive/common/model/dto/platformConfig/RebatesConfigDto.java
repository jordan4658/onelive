package com.onelive.common.model.dto.platformConfig;

import java.io.Serializable;
import java.math.BigDecimal;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "平台配置 --- 返点比例")
public class RebatesConfigDto implements Serializable {

	private static final long serialVersionUID = 1L;

    @ApiModelProperty("游戏平台id")
    private Long gameCategoryId;
    
    @ApiModelProperty("返点比例")
    private BigDecimal rebates;

}
