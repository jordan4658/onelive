package com.onelive.common.model.req.live;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value = "排行榜查询")
public class RankingReq {

	@ApiModelProperty("是否上期：是：查询上一天，上周，月的数据  [必填]")
	private Boolean isPrevious = false;
	   
    @ApiModelProperty("查询类型 1：日 2:周 3：月 4：总  [必填]")
    private Integer type = 1;
    
    @ApiModelProperty("主播的userId，查询主播榜，土豪榜不必穿")
    private Long userId;
    
   /**
	 * 	是否展示粉丝数
	 */
    private Boolean isShowfans = false;
    
}
