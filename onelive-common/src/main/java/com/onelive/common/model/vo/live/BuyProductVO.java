package com.onelive.common.model.vo.live;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel
public class BuyProductVO {

    @ApiModelProperty("观看剩余时长-单位：秒")
    private Long outTime = -1L;

    @ApiModelProperty("购买是否成功，false-否，true-是")
    private Boolean flag;

}
