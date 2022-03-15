package com.onelive.common.model.vo.lottery;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value = "彩票历史赛果记录")
public class LotterySgHistoryVO {

    @ApiModelProperty(value = "true可以跳转，false不可以跳转")
    private Boolean value;

}
