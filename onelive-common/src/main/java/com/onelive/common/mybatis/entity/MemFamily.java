package com.onelive.common.mybatis.entity;

import java.io.Serializable;
import java.math.BigDecimal;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 家族表
 * </p>
 *
 * @author ${author}
 * @since 2021-10-16
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class MemFamily implements Serializable {

	private static final long serialVersionUID = 1L;

	@TableId(value = "id", type = IdType.AUTO)
	private Long id;

	/**
	 * 用户表id
	 */
	private Long userId;

	/**
	 * 家族名
	 */
	private String familyName;

	/**
	 * 礼物抽成比例
	 */
	private BigDecimal giftRatio;

	/**
	 * 商户code值，默认值为0
	 */
	private String merchantCode;

}
