package com.onelive.common.model.vo.lottery;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value = "查询彩种玩法赔率多语言实体类")
public class LotteryPlayOddsLangVO {
    @ApiModelProperty(value = "主键ID, 更新时传入")
    private Long id;

    @ApiModelProperty(value = "语言")
    private String lang;

    @ApiModelProperty(value = "选项名称")
    private String oddsName;
}
