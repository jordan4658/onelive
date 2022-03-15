package com.onelive.common.model.vo.lottery;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
@ApiModel(value = "彩种玩法返回类")
public class LotteryPlayVO {

    @ApiModelProperty(value = "主键ID")
    private Integer id;

    @ApiModelProperty(value = "彩种分类Id")
    private Integer categoryId;

    @ApiModelProperty(value = "彩种编号")
    private Integer lotteryId;

    @ApiModelProperty(value = "父级id")
    private Integer parentId;

    @ApiModelProperty(value = "层级")
    private Integer level;

    @ApiModelProperty(value = "玩法规则Tag编号")
    private Integer playTagId;

    @ApiModelProperty(value = "排序")
    private Integer sort;

    @ApiModelProperty(value = "取值区间")
    private String section;

    @ApiModelProperty(value = "玩法节点")
    private String tree;

    @ApiModelProperty(value = "多语言列表")
    List<LotteryPlayLangVO> langList;

}
