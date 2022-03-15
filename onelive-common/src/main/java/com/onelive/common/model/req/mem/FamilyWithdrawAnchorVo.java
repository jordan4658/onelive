package com.onelive.common.model.req.mem;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("家族长提现页面")
public class FamilyWithdrawAnchorVo implements Serializable {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty("主播可被提现的金额")
	List<WithdrawAnchorVo> withdrawAnchorList;

	@ApiModelProperty("账户可提现（金币/USD）")
	private BigDecimal withdrawAccount;

}
