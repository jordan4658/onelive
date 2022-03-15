package com.onelive.common.model.req.lottery;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value = "直播游戏更新请求状态类")
public class LotteryCountryUpdateStatusReq {
    @ApiModelProperty(value = "主键id[必填]",required = true)
    private Long id;
    @ApiModelProperty(value = "是否禁用 false否true是[必填]",required = true)
    private Boolean isForbid;

}
