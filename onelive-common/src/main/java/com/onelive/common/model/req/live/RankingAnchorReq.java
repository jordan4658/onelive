package com.onelive.common.model.req.live;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value = "排行榜查询")
public class RankingAnchorReq {

	@ApiModelProperty("是否上期：是：查询上一天，上周，月的数据  [必填]")
	private Boolean isPrevious = false;
	   
    @ApiModelProperty("查询类型 1：日 2:周 3：月 4：总  [必填]")
    private Integer type = 1;
    
}
