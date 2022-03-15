package com.onelive.common.model.vo.report;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @ClassName UserReportDetailVO
 * @Desc 会员报表明细列表展示类
 * @Date 2021/4/13 16:14
 */
@Data
@ApiModel
public class UserReportDetailVO {

    @ApiModelProperty("帐变类型名称")
    private String changeTypeName;

    @ApiModelProperty("帐变类型code值")
    private Integer changeType;

    @ApiModelProperty("金币")
    private BigDecimal tradeAmount;

    @ApiModelProperty("银豆")
    private BigDecimal tradeSilver;

    @ApiModelProperty("打码量")
    private BigDecimal tradeDml;

    @ApiModelProperty("创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;


}
    