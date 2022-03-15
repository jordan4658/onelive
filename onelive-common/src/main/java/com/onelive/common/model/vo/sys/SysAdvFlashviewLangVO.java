package com.onelive.common.model.vo.sys;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value = "轮播图多语言,新增,修改,查询传输类")
public class SysAdvFlashviewLangVO implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 主键
	 */
	@ApiModelProperty("主键")
	private Long id;

	/**
	 * 轮播id
	 */
	@ApiModelProperty("轮播id")
	private Long flashviewId;

	/**
	 * 轮播名称
	 */
	@ApiModelProperty("轮播名称")
	private String flashviewName;

	/**
	 * 语言
	 */
	@ApiModelProperty("语言")
	private String lang;

	/**
	 * 跳转模块
	 */
	@ApiModelProperty("跳转模块")
	private String skipModel;

	/**
	 * 跳转路径
	 */
	@ApiModelProperty("跳转路径")
	private String skipUrl;

	/**
	 * 图片地址
	 */
	@ApiModelProperty(" 图片地址")
	private String imgUrl;

	/**
	 * 展示开始时间，服务器时间
	 */
	@ApiModelProperty("展示开始时间，服务器时间")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	private Date startDate;

	/**
	 * 展示结束时间，服务器时间
	 */
	@ApiModelProperty("展示结束时间，服务器时间")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	private Date endDate;

	/**
	 * 展示开始时间,用户当地时间
	 */
	@ApiModelProperty("展示开始时间,用户当地时间")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	private Date localStarTime;

	/**
	 * 展示结束时间,用户当地时间
	 */
	@ApiModelProperty("展示结束时间,用户当地时间")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	private Date localEndTime;

}
