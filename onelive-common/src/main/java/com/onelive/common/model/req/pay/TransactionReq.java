package com.onelive.common.model.req.pay;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@Data
@ApiModel
public class TransactionReq {

    @ApiModelProperty("当前页最后一条的数据的创建日期")
    private Long startDate;

    @ApiModelProperty("条数-默认：10")
    private Integer pageSize = 10;

    @ApiModelProperty("交易类型：（根据，交易记录类型接口数据传递）")
    private Integer transactionType;

    @ApiModelProperty("搜索条件-开始日期")
    private Long queryDate;



}
