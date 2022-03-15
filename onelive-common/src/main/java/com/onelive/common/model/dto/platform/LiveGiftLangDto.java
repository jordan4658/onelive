package com.onelive.common.model.dto.platform;

import java.io.Serializable;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "礼物多语言传输类")
public class LiveGiftLangDto implements Serializable {

	private static final long serialVersionUID = 1L;

	
	@ApiModelProperty("自增id,编辑时需要传")
	private Integer id;
	 
	@ApiModelProperty("礼物表id")
	private Integer giftId;

	@ApiModelProperty("语言标识")
	private String lang;

	@ApiModelProperty("多语言名字")
	private String giftName;

}
