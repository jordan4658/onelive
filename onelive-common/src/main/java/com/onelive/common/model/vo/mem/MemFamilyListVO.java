package com.onelive.common.model.vo.mem;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * <p>
 * 家族表查询传输类
 * </p>
 *
 */
@Data
@ApiModel
public class MemFamilyListVO {

	@ApiModelProperty("家族主键")
	private Long id;
	
	@ApiModelProperty("家族用户表id")
	private Long userId;

	@ApiModelProperty("注册的手机号")
	private String mobilePhone;

	@ApiModelProperty("注册的区号")
	private String registerAreaCode;

	@ApiModelProperty("家族名")
	private String familyName;

	@ApiModelProperty("家族账号")
	private String userAccount;

	@ApiModelProperty("账号状态")
	private Boolean isFrozen;

	@ApiModelProperty("礼物抽成比例")
	private BigDecimal giftRatio;

	@ApiModelProperty("总资产")
	private BigDecimal generalAssets = new BigDecimal("0.00");

	@ApiModelProperty("备注")
	private String remark;

	@ApiModelProperty("所属地区Code")
	private String registerCountryCode;
	
	@ApiModelProperty("创建时间")
	private String registerTime;
	
	@ApiModelProperty("主播数量")
	private Integer anchorCount = 0;

}
