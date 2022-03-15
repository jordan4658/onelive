package com.onelive.common.model.dto.platform;

import java.io.Serializable;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value = "短信服务商")
public class MsgBusinessDto implements Serializable {

    private static final long serialVersionUID=1L;
    
    @ApiModelProperty("短信服务商 的 code")
    private String code;
    
    @ApiModelProperty("短信服务商名字")
    private String name;

}
