package com.onelive.common.model.dto.platformConfig;

import java.io.Serializable;
import java.util.Date;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value = "平台配置")
public class PlatformConfigAllDto implements Serializable {

    private static final long serialVersionUID=1L;

    @ApiModelProperty("配置名")
    private String configName;
    
    @ApiModelProperty("更新人")
    private String updateUser;
    
    @ApiModelProperty("更新时间")
    private Date updateTime;
    
    @ApiModelProperty("配置code")
    private String confiCode;
    
    @ApiModelProperty("配置对象，具体内容")
    private String context;
    

    @ApiModelProperty("平台配置 --- 账户配置")
    private AccountConfigDto accountConfigDto;
    
    @ApiModelProperty("配置 --- 客户服务")
    private CustomerSericeLangDto customerSericeLangDto;

    @ApiModelProperty("平台配置 --- 昵称过滤")
    private NickNameFiterDto nickNameFiterDto;
    
    @ApiModelProperty("平台配置 -- 平台配置")
    private PlatformConfigDto platformConfigDto;
    
    @ApiModelProperty("平台配置 -- 平台配置多语言")
    private PlatformConfigLangDto platformConfigLangDto;
    
    @ApiModelProperty("平台配置 --- 返点比例")
    private RebatesConfigDto rebatesConfigDto;
    
    

}
