package com.onelive.common.model.req.lottery;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
@ApiModel(value = "彩种分类保存请求类")
public class LotteryCategorySaveReq {

    @ApiModelProperty(value = "主键ID, 更新时传入")
    private Integer id;
    @ApiModelProperty(value = "分类编号[必填]",required = true)
    private Integer categoryId;
    @ApiModelProperty(value = "排序[必填]",required = true)
    private Integer sort;
    @ApiModelProperty(value = "玩法级别[必填]",required = true)
    private Integer level;
    @ApiModelProperty(value = "是否开售[必填]",required = true)
    private Integer isWork;
    @ApiModelProperty(value = "多语言列表[必填]",required = true)
    private List<LotteryCategoryLangReq> langList;

}
