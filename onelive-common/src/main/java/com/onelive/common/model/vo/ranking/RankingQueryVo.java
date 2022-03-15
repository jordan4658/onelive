package com.onelive.common.model.vo.ranking;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 榜单查询参数
 */
@Data
@ApiModel
public class RankingQueryVo {
    @ApiModelProperty("直播间id,如果不传,查询整个平台的贡献")
    private Integer studioId;

    @ApiModelProperty("开始时间")
    private String startTime;

    @ApiModelProperty("结束时间")
    private String endTime;
}
