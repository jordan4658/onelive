package com.onelive.common.model.dto.index;

import java.io.Serializable;
import java.util.Date;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "首页栏目")
public class LiveColumnDto implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 首页栏目ID
	 */
	@ApiModelProperty("首页栏目ID")
	private Integer columnId;

	/**
	 * 商户code值，默认为0
	 */
	@ApiModelProperty("商户code值，默认为0")
	private String merchantCode;

	/**
	 * 栏目名称
	 */
	@ApiModelProperty("栏目名称")
	private String columnName;

	/**
	 * 栏目值
	 */
	@ApiModelProperty("栏目值")
	private String columnValue;

	@ApiModelProperty("创建人")
	private String createdBy;

	/**
	 * 更新人
	 */
	@ApiModelProperty("更新人")
	private String updatedBy;

	/**
	 * 语言标识
	 */
	@ApiModelProperty("语言标识")
	private String lang;

	/**
	 * 创建时间
	 */
	@ApiModelProperty("创建时间")
	private Date createTime;

	/**
	 * 更新时间
	 */
	@ApiModelProperty(" 更新时间")
	private Date updateTime;

	/**
	 * 排序
	 */
	@ApiModelProperty("排序")
	private Integer sortNo;

	/**
	 * 0:关闭 1:开启
	 */
	@ApiModelProperty("0:关闭 1:开启")
	private Boolean isOpen;

	@ApiModelProperty("第几页")
	private Integer pageNum = 1;

	@ApiModelProperty("每页最大页数")
	private Integer pageSize = 10;

}
