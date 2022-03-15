package com.onelive.common.model.dto.platform;

import java.io.Serializable;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value = "关注主播传输类")
public class MemFocusUserRemindDto implements Serializable {

    private static final long serialVersionUID=1L;

    @ApiModelProperty("主播id[必填]")
    private Long focusId;
    
    @ApiModelProperty("是否提醒 true:是")
	private Boolean isRemind;
    
}
