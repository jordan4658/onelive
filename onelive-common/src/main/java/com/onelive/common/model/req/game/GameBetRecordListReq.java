package com.onelive.common.model.req.game;

import com.onelive.common.model.common.PageReq;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("第三方游戏-游戏注单记录列表请求参数")
public class GameBetRecordListReq extends PageReq {
    @ApiModelProperty(value = "游戏分类ID, 关联game_category.category_id[必填]",required = true)
    private Integer categoryId;
    @ApiModelProperty(value ="开始时间[必填]",required = true)
    private String startTime;
    @ApiModelProperty(value ="结束时间[必填]",required = true)
    private String endTime;
}
