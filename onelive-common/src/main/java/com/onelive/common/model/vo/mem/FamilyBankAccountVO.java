package com.onelive.common.model.vo.mem;

import java.util.List;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel
public class FamilyBankAccountVO {

    @ApiModelProperty("会员银行卡id")
    private List<MemBankAccountVO> memBankAccountList;

    @ApiModelProperty("家族长还可以绑定银行卡数量")
    private Integer canBindCount;


}
