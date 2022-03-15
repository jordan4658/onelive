package com.onelive.common.model.vo.mem;

import java.io.Serializable;
import java.math.BigDecimal;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel
public class FamilyCanWithdrawVO implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@ApiModelProperty("账户可提现（金币/USD）")
	private BigDecimal withdrawAccount;
	
	@ApiModelProperty("主播账户未提现（金币/USD）")
	private BigDecimal withdrawAnchor;

	public FamilyCanWithdrawVO(BigDecimal withdrawAccount, BigDecimal withdrawAnchor) {
		super();
		this.withdrawAccount = withdrawAccount;
		this.withdrawAnchor = withdrawAnchor;
	}
}
