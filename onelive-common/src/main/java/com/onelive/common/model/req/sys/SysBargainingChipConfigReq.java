package com.onelive.common.model.req.sys;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @ClassName BargainingChipConfigReq
 * @Desc 平台配置中的筹码配置请求参数
 */
@ApiModel
@Data
public class SysBargainingChipConfigReq {

    @ApiModelProperty(value = "配置项id, 更新时传入")
    private Long id;

    @ApiModelProperty(value = "国家代码[必填]",required = true)
    private String countryCode;

    @ApiModelProperty(value = "筹码1[必填]",required = true)
    private BigDecimal bargainingChip1;

    @ApiModelProperty(value = "筹码2[必填]",required = true)
    private BigDecimal bargainingChip2;

    @ApiModelProperty(value = "筹码3[必填]",required = true)
    private BigDecimal bargainingChip3;

    @ApiModelProperty(value = "筹码4[必填]",required = true)
    private BigDecimal bargainingChip4;

    @ApiModelProperty(value = "筹码5[必填]",required = true)
    private BigDecimal bargainingChip5;

    @ApiModelProperty(value = "筹码6[必填]",required = true)
    private BigDecimal bargainingChip6;

    @ApiModelProperty(value = "筹码7[必填]",required = true)
    private BigDecimal bargainingChip7;

    @ApiModelProperty(value = "是否显示筹码单位 0否, 1是[必填]",required = true)
    private Boolean showUnit;

    @ApiModelProperty(value = "是否启用 0否 1是[必填]",required = true)
    private Boolean status;

    @ApiModelProperty(value = "用户自定义筹码最小值[必填]",required = true)
    private BigDecimal customizeMin;

    @ApiModelProperty(value = "用户自定义筹码最大值[必填]",required = true)
    private BigDecimal customizeMax;

    @ApiModelProperty(value = "下注最小值[必填]",required = true)
    private BigDecimal bargainingChipMin;

    @ApiModelProperty(value = "下注最大值[必填]",required = true)
    private BigDecimal bargainingChipMax;

}    
    