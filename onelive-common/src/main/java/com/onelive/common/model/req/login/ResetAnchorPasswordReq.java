package com.onelive.common.model.req.login;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @ClassName : ResetPasswordReq
 * @Description : 重置密码请求参数
 */
@Data
@ApiModel(value = "重置密码请求参数")
public class ResetAnchorPasswordReq {


	@ApiModelProperty("主播的userid，修改自己的密码不必穿")
	private Long userId;
	
    @ApiModelProperty(value = "原密码[必传]", required = true)
    private String password;
    
    @ApiModelProperty(value = "新密码[必传]", required = true)
    private String newPassword;
    
    
}
