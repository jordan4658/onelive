package com.onelive.common.model.dto.platformConfig;

import java.io.Serializable;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "平台配置 --- 昵称过滤")
public class NickNameFiterDto implements Serializable {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty("内容")
    private String context;

}
