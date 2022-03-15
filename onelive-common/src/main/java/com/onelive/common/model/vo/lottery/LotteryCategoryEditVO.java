package com.onelive.common.model.vo.lottery;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
@ApiModel(value = "彩种分类编辑返回类")
public class LotteryCategoryEditVO {

    @ApiModelProperty(value = "ID")
    private Integer id;
    @ApiModelProperty(value = "分类编号")
    private Integer categoryId;
    @ApiModelProperty(value = "排序")
    private Integer sort;
    @ApiModelProperty(value = "玩法级别")
    private Integer level;
    @ApiModelProperty(value = "是否开售")
    private Integer isWork;
    @ApiModelProperty(value = "多语言列表")
    private List<LotteryCategoryLangVO> langList;

}
