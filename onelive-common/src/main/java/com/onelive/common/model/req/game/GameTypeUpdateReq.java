package com.onelive.common.model.req.game;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * <p>
 * 家族表req类
 * </p>
 *
 */
@Data
@ApiModel
public class GameTypeUpdateReq{

    @ApiModelProperty("主键")
    private Long id;
    
	@ApiModelProperty("类型名称")
    private String typeName;

     @ApiModelProperty("状态 0显示1隐藏")
    private Boolean isHide;

     @ApiModelProperty("语言")
    private String lang;

    @ApiModelProperty("所属地区id")
    private Long countryId;

}
