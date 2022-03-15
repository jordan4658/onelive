package com.onelive.common.model.req.risk;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * @author 就不告诉你
 * @version V1.0
 * @ClassName: RiskAuditWithdrawReq
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @date 创建时间：2021/6/07 16:40
 */
@Data
@ApiModel
public class RiskAuditWithdrawReq {

    @ApiModelProperty("提现订单号")
    private String withdrawNo;

    @ApiModelProperty("提现状态:1-处理中 2-成功 3-失败 4-取消")
    private Integer withdrawStatus;

    @ApiModelProperty("会员账号")
    private String account;

    @ApiModelProperty("开始时间")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private String startTime;

    @ApiModelProperty("结束时间")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private String endTime;

}
