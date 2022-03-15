package com.onelive.common.model.dto.platform;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value = "分享管理传输类")
public class PlatformShareConfigDto implements Serializable {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty("主键id")
	private Long id;
	
	@ApiModelProperty("对语言对象")
	private List<PlatformShareConfigLangDto> platformShareConfigLangList;

	@ApiModelProperty("分享类型")
	private String shareType;

	@ApiModelProperty("每次分享后的奖励金额")
	private BigDecimal award;

	@ApiModelProperty("每award_hour小时可以领取award_times次")
	private Integer awardHour;

	@ApiModelProperty("每award_hour效时可以领取的次数")
	private Integer awardTimes;

	@ApiModelProperty("适用国家id,多个逗号分隔,空即:所有")
	private String useCountry;

	@ApiModelProperty("是否展示 0：否")
	private Boolean isShow;

	@ApiModelProperty("修改人(后台操作人登录账号)")
	private String updateUser;

	@ApiModelProperty("修改时间")
	private Date updateTime;

	@ApiModelProperty("商户code值，默认值为0")
	private String merchantCode;

}
