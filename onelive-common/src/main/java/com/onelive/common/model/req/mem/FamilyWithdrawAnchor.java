package com.onelive.common.model.req.mem;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
@ApiModel
public class FamilyWithdrawAnchor implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "用户userId")
    private Long userId;
    
    @ApiModelProperty(value = "提款金额，可以为空，输入时赋值")
    BigDecimal withdrawMoney;
    

}
