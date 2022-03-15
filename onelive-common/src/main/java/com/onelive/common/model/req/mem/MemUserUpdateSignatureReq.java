package com.onelive.common.model.req.mem;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@ApiModel
public class MemUserUpdateSignatureReq implements Serializable {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "新的签名[必填]",required = true)
	private String signature;
}
