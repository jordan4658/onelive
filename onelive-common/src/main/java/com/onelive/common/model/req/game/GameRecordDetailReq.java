package com.onelive.common.model.req.game;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 查询游戏记录详情请求参数
 */
@Data
@ApiModel
public class GameRecordDetailReq implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "查询日期 1今天,2昨天[必填]",required = true)
    private Integer queryDate;

    @ApiModelProperty(value = "游戏ID[必填]",required = true)
    private String gameId;

    @ApiModelProperty(value = "游戏分类[必填]",required = true)
    private String gameType;

}
