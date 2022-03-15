package com.onelive.common.model.vo.lottery;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@ApiModel(value = "彩种类型下拉-彩种名称下拉返回类")
public class CategorySelectChildVO {

    @ApiModelProperty(value = "彩种大类ID")
    private Integer categoryId;

    @ApiModelProperty(value = "彩票ID")
    private Integer lotteryId;

    @ApiModelProperty(value = "彩票名称")
    private String name;

}
