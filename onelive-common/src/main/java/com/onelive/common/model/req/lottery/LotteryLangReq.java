package com.onelive.common.model.req.lottery;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value = "保存彩种(多语言)请求类")
public class LotteryLangReq {
    @ApiModelProperty(value = "主键ID, 更新时传入")
    private Long id;

    /**
     * 语言
     */
    @ApiModelProperty(value = "语言[必填]",required = true)
    private String lang;

    /**
     * 彩种名称
     */
    @ApiModelProperty(value = "彩种名称[必填]",required = true)
    private String lotteryName;
}
