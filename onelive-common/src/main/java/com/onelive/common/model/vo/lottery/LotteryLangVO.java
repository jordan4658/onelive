package com.onelive.common.model.vo.lottery;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value = "彩种(多语言)实体类")
public class LotteryLangVO {
    @ApiModelProperty(value = "主键ID")
    private Long id;

    /**
     * 语言
     */
    @ApiModelProperty(value = "语言")
    private String lang;

    /**
     * 彩种名称
     */
    @ApiModelProperty(value = "彩种名称")
    private String lotteryName;
}
