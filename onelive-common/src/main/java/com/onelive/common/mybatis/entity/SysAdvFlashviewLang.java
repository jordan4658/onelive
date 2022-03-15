package com.onelive.common.mybatis.entity;

import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 		广告首页轮播语种配置表
 * </p>
 *
 * @author ${author}
 * @since 2021-11-29
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class SysAdvFlashviewLang implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 主键
	 */
	@TableId(value = "id", type = IdType.AUTO)
	private Long id;

	/**
	 * 轮播id
	 */
	private Long flashviewId;
	
	/**
	 * 轮播名称
	 */
	private String flashviewName;

	/**
	 * 语言
	 */
	private String lang;

	/**
	 * 跳转模块
	 */
	private String skipModel;

	/**
	 * 跳转路径
	 */
	private String skipUrl;

	/**
	 * 图片地址
	 */
	private String imgUrl;

	/**
	 * 展示开始时间，服务器时间
	 */
	private Date startDate;

	/**
	 * 展示结束时间，服务器时间
	 */
	private Date endDate;

	/**
	 * 展示开始时间,用户当地时间
	 */
	private Date localStartTime;

	/**
	 * 展示结束时间,用户当地时间
	 */
	private Date localEndTime;

}
