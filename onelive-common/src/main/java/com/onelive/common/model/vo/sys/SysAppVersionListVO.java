package com.onelive.common.model.vo.sys;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * <p>
 * app版本管理ListVO
 * </p>
 */
@Data
@ApiModel
public class SysAppVersionListVO {

	@ApiModelProperty("主键")
    private Long id;

	@ApiModelProperty("app名称")
    private String name;

    @ApiModelProperty("app类型 1客户端, 2主播端")
    private Integer appType;

    @ApiModelProperty("版本号")
    private String showVersion;

    @ApiModelProperty("状态 0待发布 1已发布")
    private Integer status;

    @ApiModelProperty("操作人")
    private String updateUser;
	
	@ApiModelProperty("更新时间")
    private Date updateTime;

}
