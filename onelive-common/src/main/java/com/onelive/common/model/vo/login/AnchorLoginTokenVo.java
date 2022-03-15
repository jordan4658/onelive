package com.onelive.common.model.vo.login;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel
public class AnchorLoginTokenVo {
	
    @ApiModelProperty(value = "登录token")
    private String acctoken;

    @ApiModelProperty(value = "注册时候的国家code值")
    private String registerCountryCode;
    
    @ApiModelProperty(value = "是否家族长 ，否：主播，是：家族长")
    private Boolean isFamily;
    
}
