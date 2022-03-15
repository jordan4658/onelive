package com.onelive.common.model.vo.lottery;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value = "查询彩种玩法多语言实体类")
public class LotteryPlayLangVO {
    @ApiModelProperty(value = "主键ID, 更新时传入")
    private Long id;

    /**
     * 语言
     */
    @ApiModelProperty(value = "语言")
    private String lang;

    /**
     * 玩法名称
     */
    @ApiModelProperty(value = "玩法名称")
    private String playName;
}
