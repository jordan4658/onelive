package com.onelive.common.model.vo.index;

import java.io.Serializable;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel
public class LiveColumnVO implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 栏目名称
	 */
	@ApiModelProperty(value = "栏目名称")
	private String columnName;

	/**
	 * 栏目code : 关注，推荐，附近
	 */
	@ApiModelProperty(value = "栏目code")
	private String columnCode;

	/**
	 * 语言标识
	 */
	@ApiModelProperty(value = "语言标识")
	private String lang;

	/**
	 * 排序
	 */
	@ApiModelProperty(value = "排序号")
	private Integer sortNo;

}
