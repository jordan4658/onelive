package com.onelive.common.model.vo.sys;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * <p>
 * 广告设置表
 * </p>
 *
 */
@Data
@ApiModel
public class SysAdvAreaListVO {


    @ApiModelProperty("主键")
    private Long id;

    @ApiModelProperty("区域名")
    private String areaName;

    @ApiModelProperty("区域编码")
    private String areaCode;

   
}
