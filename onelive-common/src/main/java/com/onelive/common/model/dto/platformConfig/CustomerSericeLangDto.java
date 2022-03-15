package com.onelive.common.model.dto.platformConfig;

import java.io.Serializable;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "配置 --- 客户服务")
public class CustomerSericeLangDto implements Serializable {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty("语言标识")
	private String lang;

	@ApiModelProperty("内容")
    private String context;

}
