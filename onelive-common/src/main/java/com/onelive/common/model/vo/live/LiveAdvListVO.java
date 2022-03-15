package com.onelive.common.model.vo.live;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 直播首页栏位广告listVO
 */
@Data
@ApiModel
public class LiveAdvListVO {
	
	@ApiModelProperty("ID")
	private Long id;

	@ApiModelProperty("名称")
	private String advName;
	
	@ApiModelProperty("封面图")
	private String advImg;  
	
	@ApiModelProperty("跳转模块  phone-原生手机跳转（原生手机跳转地址由移动端提供后录入）  h5-原生H5跳转  url-浏览器URL跳转")
    private String skipModel;
	
    @ApiModelProperty("跳转路径")
    private String skipUrl;

	@ApiModelProperty("广告类型，1一级 2二级 ")
    private Integer type = 1;

	@ApiModelProperty("二级广告动态参数，json格式")
    private String params;

}