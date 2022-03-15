package com.onelive.common.model.dto.platform;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class PlatformShareConfigLangDto implements Serializable {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty("主键id")
	private Long id;

	@ApiModelProperty("分享类型的id")
	private Long shareId;

	@ApiModelProperty("分享类型的id")
	private String lang;

	@ApiModelProperty("分享内容")
	private String shareContent;

	@ApiModelProperty("分享的标题(Messenger专有)")
	private String shareTitle;

	@ApiModelProperty("分享的图片相对路径(Messenger专有)")
	private String sharePic;

}
