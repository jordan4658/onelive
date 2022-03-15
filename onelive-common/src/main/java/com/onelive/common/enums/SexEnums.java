package com.onelive.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 
 * @ClassName: SexEnums
 * @Description: 性别枚举
 */
@Getter
@AllArgsConstructor
public enum SexEnums {

	/**
	 * 保密
	 */
	UNKNOWN(0, "保密"),
	/**
	 * 男
	 */
	MALE(1, "男"),
	/**
	 * 女
	 */
	FEMALE(2,"女");

	private Integer code;
	private String msg;
}