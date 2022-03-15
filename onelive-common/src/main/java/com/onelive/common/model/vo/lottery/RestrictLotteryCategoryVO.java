package com.onelive.common.model.vo.lottery;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@ApiModel(value = "投注限制彩票大类返回类")
public class RestrictLotteryCategoryVO {

    @ApiModelProperty(value = "彩种类型ID")
    private Integer categoryId;

    @ApiModelProperty(value = "彩种类型名称")
    private String name;

    @ApiModelProperty(value = "彩种列表")
    private List<RestrictLotteryVO> childList = new ArrayList<>();






}
