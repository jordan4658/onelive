package com.onelive.common.model.dto.platform;

import java.io.Serializable;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "悬浮窗多语言传输类")
public class LiveFloatLangDto implements Serializable {

	private static final long serialVersionUID = 1L;

	
	@ApiModelProperty("自增id,编辑时需要传")
	private Long id;
	 
	@ApiModelProperty("礼物表id")
	private Long floatId;

	@ApiModelProperty("语言标识")
	private String lang;

	@ApiModelProperty("图片地址")
    private String imgUrl;

}
