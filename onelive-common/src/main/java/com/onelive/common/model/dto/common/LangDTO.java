package com.onelive.common.model.dto.common;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@ApiModel(value = "多语种传输类")
public class LangDTO implements Serializable {
    @ApiModelProperty("用户ID")
    private Long userId;

    @ApiModelProperty("语种")
    private String lang;

}
