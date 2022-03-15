package com.onelive.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 
 * @ClassName: ResultEnums
 * @Description: 统一返回的代码和说明定义
 */
@Getter
@AllArgsConstructor
public enum ResultEnums {

	SUCCESS(200, "success"), 
	UNLOGINCODE(401,"未登录"),
	ERROR(500, "请求失败"), 
	SYSTEM_ERROR(999, "网络异常"), 
	BUSSINESS_ERROR(550, "业务逻辑错误"),
	PARAM_ERROR(551, "业务参数错误");

	private Integer code;
	private String msg;
}