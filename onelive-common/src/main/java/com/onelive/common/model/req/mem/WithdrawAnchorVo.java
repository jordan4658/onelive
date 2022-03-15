package com.onelive.common.model.req.mem;

import java.io.Serializable;
import java.math.BigDecimal;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("主播可被提现的金额")
public class WithdrawAnchorVo implements Serializable {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty("主播的userid")
	private Long userId;

	@ApiModelProperty("用户登录账号/主播账号")
	private String userAccount;

	@ApiModelProperty("主播昵称")
	private String nickName;
	
	@ApiModelProperty("主播头像")
	private String avatar;

	@ApiModelProperty("账户可提现（金币/USD）")
	private BigDecimal withdrawAccount;

}
