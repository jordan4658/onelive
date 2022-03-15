//package com.onelive.common.model.dto.platform;
//
//import java.io.Serializable;
//import java.math.BigDecimal;
//
//import io.swagger.annotations.ApiModel;
//import io.swagger.annotations.ApiModelProperty;
//import lombok.Data;
//
//@Data
//@ApiModel(value = "分享管理用户端传输类")
//public class ShareForIndexDto implements Serializable {
//
//	private static final long serialVersionUID = 1L;
//	
//	@ApiModelProperty("分享类型")
//	private String shareType;
//	
//	@ApiModelProperty("分享类型的id")
//	private String lang;
//
//	@ApiModelProperty("分享内容")
//	private String shareContent;
//
//	@ApiModelProperty("分享的标题(Messenger专有)")
//	private String shareTitle;
//
//	@ApiModelProperty("分享的图片相对路径(Messenger专有)")
//	private String sharePic;
//
//	@ApiModelProperty("每次分享后的奖励金额")
//	private BigDecimal award;
//
//	@ApiModelProperty("每award_hour效时可以领取的次数")
//	private Integer awardTimes;
//
//	@ApiModelProperty("适用国家id,多个逗号分隔,空即:所有")
//	private String useCountry;
//
//	@ApiModelProperty("商户code值，默认值为0")
//	private String merchantCode;
//
//}
