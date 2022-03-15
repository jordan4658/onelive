package com.onelive.common.model.vo.operate;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 开奖记录
 */
@Data
@ApiModel
public class LotterySGReocrdVO {
    @ApiModelProperty("游戏名称")
    private String name;
    @ApiModelProperty("游戏代名")
    private String game;
    @ApiModelProperty("期号")
    private String issue;
    @ApiModelProperty("开奖号码")
    private String number;
    @ApiModelProperty("开奖状态")
    private String openStatus;
    @ApiModelProperty("封盘时间")
    private String fpTime;
    @ApiModelProperty("开奖时间")
    private String time;
}
