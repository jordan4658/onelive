package com.onelive.common.model.vo.sys;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @ClassName BargainingChipConfigReq
 * @Desc 平台配置中的筹码配置列表返回数据实体类
 */
@ApiModel
@Data
public class SysBargainingChipConfigListVO {

    @ApiModelProperty(value = "配置项id")
    private Long id;

    @ApiModelProperty(value = "国家代码")
    private String countryCode;

    @ApiModelProperty(value = "筹码1")
    private BigDecimal bargainingChip1;

    @ApiModelProperty(value = "筹码2")
    private BigDecimal bargainingChip2;

    @ApiModelProperty(value = "筹码3")
    private BigDecimal bargainingChip3;

    @ApiModelProperty(value = "筹码4")
    private BigDecimal bargainingChip4;

    @ApiModelProperty(value = "筹码5")
    private BigDecimal bargainingChip5;

    @ApiModelProperty(value = "筹码6")
    private BigDecimal bargainingChip6;

    @ApiModelProperty(value = "筹码7")
    private BigDecimal bargainingChip7;

    @ApiModelProperty(value = "是否显示筹码单位 0否, 1是")
    private Boolean showUnit;

    @ApiModelProperty(value = "是否启用 0否 1是")
    private Boolean status;

    @ApiModelProperty(value = "用户自定义筹码最小值")
    private BigDecimal customizeMin;

    @ApiModelProperty(value = "用户自定义筹码最大值")
    private BigDecimal customizeMax;

    @ApiModelProperty(value = "下注最小值")
    private BigDecimal bargainingChipMin;

    @ApiModelProperty(value = "下注最大值")
    private BigDecimal bargainingChipMax;

}    
    