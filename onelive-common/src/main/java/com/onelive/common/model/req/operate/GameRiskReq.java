package com.onelive.common.model.req.operate;

import com.onelive.common.model.common.PageReq;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 游戏风控请求参数
 */
@Data
@ApiModel("游戏风控数据列表请求参数")
public class GameRiskReq extends PageReq {

    //开始时间
    @ApiModelProperty("开始时间")
    private String startTime;

    //结束时间
    @ApiModelProperty("结束时间")
    private String endTime;

    //彩种ID
    @ApiModelProperty("彩种ID")
    private Long lotteryId;

    //期号
    @ApiModelProperty("期号")
    private String issue;

}
