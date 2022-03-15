package com.onelive.common.model.vo.lottery;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value = "彩种玩法列表返回类")
public class LotteryPlayListVO {

    @ApiModelProperty(value = "玩法ID,也就是方案id")
    private Integer id;
    @ApiModelProperty(value = "玩法名称,也就是方案名称")
    private String showName;
    @ApiModelProperty(value = "玩法设置id，也就是规则id")
    private Integer settingId;
//    @ApiModelProperty(value = "取值区间,列表不展示，带入方案修改")
//    private String section;
//    @ApiModelProperty(value = "排序,列表不展示，带入方案修改")
//    private Integer sort;

}
