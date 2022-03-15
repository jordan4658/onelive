package com.onelive.common.model.vo.live;

import java.io.Serializable;
import java.util.Date;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 直播基础配置传输类
 */
@Data
@ApiModel(value = "直播基础配置")
public class LiveConfigVo implements Serializable {

	private static final long serialVersionUID = 1L;

    @ApiModelProperty("数据id")
	private Long paramId;

    @ApiModelProperty("参数代码")
	private String paramCode;

	/**
	 * 参数名称
	 */
    @ApiModelProperty("参数名称")
	private String paramName;

	/**
	 * 参数说明
	 */
    @ApiModelProperty("参数说明")
	private String remark;

	/**
	 * 参数值
	 */
    @ApiModelProperty("参数值")
	private String paramValue;

}
