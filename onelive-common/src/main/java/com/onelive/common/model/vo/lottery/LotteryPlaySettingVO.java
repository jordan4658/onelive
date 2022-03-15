package com.onelive.common.model.vo.lottery;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
@ApiModel(value = "玩法规则返回类")
public class LotteryPlaySettingVO {

    @ApiModelProperty(value = "玩法设置id")
    private Integer id;

    @ApiModelProperty(value = "彩种分类Id")
    private Integer cateId;

    @ApiModelProperty(value = "玩法ID")
    private Integer playId;

    @ApiModelProperty(value = "玩法规则Tag编号")
    private Integer playTagId;

    @ApiModelProperty(value = "多语言列表")
    private List<LotteryPlaySettingLangVO> langList;

}
