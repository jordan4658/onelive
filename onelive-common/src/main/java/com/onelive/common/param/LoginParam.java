package com.onelive.common.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import java.io.Serializable;

@Data
public class LoginParam implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "请输入账号")
	private String account;
	@ApiModelProperty(value = "请输入密码")
	private String password;
}
