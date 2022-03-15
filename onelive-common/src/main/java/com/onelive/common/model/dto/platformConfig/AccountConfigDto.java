package com.onelive.common.model.dto.platformConfig;

import java.io.Serializable;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "平台配置 --- 账户配置")
public class AccountConfigDto implements Serializable {

	private static final long serialVersionUID = 1L;
    
	@ApiModelProperty("预设用户分层")
    private String defaultHierarchy;
	
	@ApiModelProperty("设置头像")
	private Integer avatarLevel;
	
	@ApiModelProperty("开放注册区号")
	private String phoneAreaCode;

}
