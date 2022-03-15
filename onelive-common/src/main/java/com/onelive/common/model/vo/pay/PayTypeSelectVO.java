package com.onelive.common.model.vo.pay;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Classname MemFlowRestrictReq
 * @Des 支付类型下拉框
 * @Author 凯文
 * @Date 2021/5/1416:05
 */
@Data
@ApiModel
public class PayTypeSelectVO {
    @ApiModelProperty("支付类型code")
    private Integer payTypeCode;

    @ApiModelProperty("支付类型名称")
    private String payTypeName;
}
