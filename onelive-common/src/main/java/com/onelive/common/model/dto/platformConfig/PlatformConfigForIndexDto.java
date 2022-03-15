package com.onelive.common.model.dto.platformConfig;

import java.io.Serializable;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value = "平台配置 -- 平台配置")
public class PlatformConfigForIndexDto implements Serializable {

    private static final long serialVersionUID=1L;

    @ApiModelProperty("语言标识")
	private String lang;

	@ApiModelProperty("内容")
    private String context;
    
    @ApiModelProperty("平台名")
    private String name;
    
    @ApiModelProperty("是否维护中")
    private Boolean isMaintained;
    
    @ApiModelProperty("是否进入首页")
    private Boolean isIndex;
    
    @ApiModelProperty("是否开放注册")
    private Boolean isOpenRegister;

}
