package com.onelive.common.model.req.lottery;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value = "彩种玩法多语言实体类")
public class LotteryPlayLangReq {
    @ApiModelProperty(value = "主键ID, 更新时传入")
    private Long id;

    /**
     * 语言
     */
    @ApiModelProperty(value = "语言[必填]",required = true)
    private String lang;

    /**
     * 玩法名称
     */
    @ApiModelProperty(value = "玩法名称[必填]",required = true)
    private String playName;
}
