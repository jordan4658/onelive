package com.onelive.common.model.req.mem;

import java.io.Serializable;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("家族行创建主播的传输类")
public class FamilyCreateAnchorReq implements Serializable {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty("登录密码")
	private String password;

	@ApiModelProperty("用户登录账号/主播账号")
	private String userAccount;

}