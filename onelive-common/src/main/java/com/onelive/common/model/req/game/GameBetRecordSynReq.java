package com.onelive.common.model.req.game;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@Data
@ApiModel("第三方游戏-游戏注单记录同步请求参数")
public class GameBetRecordSynReq {
    @ApiModelProperty(value = "游戏分类ID, 关联game_category.category_id[必填]",required = true)
    private Integer categoryId;
    @ApiModelProperty(value ="开始时间[必填]",required = true)
    private Date startTime;
    @ApiModelProperty(value ="结束时间[必填]",required = true)
    private Date endTime;
}
