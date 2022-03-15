package com.onelive.common.model.req.lottery;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value = "彩种玩法赔率多语言实体类")
public class LotteryPlayOddsLangReq {
    @ApiModelProperty(value = "主键ID, 更新时传入")
    private Long id;

    @ApiModelProperty(value = "语言[必填]",required = true)
    private String lang;

    @ApiModelProperty(value = "选项名称[必填]",required = true)
    private String oddsName;
}
