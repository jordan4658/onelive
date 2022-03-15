package com.onelive.common.model.vo.live;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel
public class LiveHistoryVo {

    @ApiModelProperty("直播日期")
    private String liveDate;

}
