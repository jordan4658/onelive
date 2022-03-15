package com.onelive.common.model.vo.lottery;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value = "彩种分类多语言实体类")
public class LotteryCategoryLangVO {
    @ApiModelProperty(value = "主键ID")
    private Long id;

    /**
     * 语言
     */
    @ApiModelProperty(value = "语言")
    private String lang;

    /**
     * 分类名称
     */
    @ApiModelProperty(value = "分类名称")
    private String name;

    /**
     * 别名
     */
    @ApiModelProperty(value = "别名")
    private String alias;
}
