package com.onelive.common.model.req.lottery;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value = "彩种分类多语言保存请求类")
public class LotteryCategoryLangReq {
    @ApiModelProperty(value = "主键ID, 更新时传入")
    private Long id;

    /**
     * 语言
     */
    @ApiModelProperty(value = "语言[必填]",required = true)
    private String lang;

    /**
     * 分类名称
     */
    @ApiModelProperty(value = "分类名称[必填]",required = true)
    private String name;

    /**
     * 别名
     */
    @ApiModelProperty(value = "别名[必填]",required = true)
    private String alias;
}
