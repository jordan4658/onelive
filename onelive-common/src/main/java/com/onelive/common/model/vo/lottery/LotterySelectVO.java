package com.onelive.common.model.vo.lottery;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@Data
@ApiModel(value = "彩种选择列表实体类")
public class LotterySelectVO {
    @ApiModelProperty(value = "ID")
    private Integer id;

    @ApiModelProperty(value = "彩种编号")
    private Integer lotteryId;

    @ApiModelProperty(value = "名称")
    private String name;
}
