package com.onelive.common.model.req.operate;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 开奖记录请求参数
 */
@Data
@ApiModel
public class LotterySGRecordReq {

    /**
     * 指定游戏
     */
    @ApiModelProperty("指定游戏")
    private String game;
    /**
     * 指定期号
     */
    @ApiModelProperty("指定期号")
    private String issue;
    /**
     * 开始时间
     */
    @ApiModelProperty("开始时间")
    private String startTime;

    /**
     * 结束时间
     */
    @ApiModelProperty("结束时间")
    private String endTime;

    /**
     * 页码
     */
    @ApiModelProperty("页码")
    private Integer pageNum = 1;

    /**
     * 每页数量
     */
    @ApiModelProperty("每页数量")
    private Integer pageSize = 10;

}
