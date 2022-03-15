package com.onelive.common.model.req.lottery;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
@ApiModel(value = "保存彩种请求类")
public class LotterySaveReq {

    @ApiModelProperty(value = "彩种id, 更新时传入")
    private Integer id;

    @ApiModelProperty(value = "彩票分类id[必填]",required = true)
    private Integer categoryId;

    @ApiModelProperty(value = "彩种编号[必填]",required = true)
    private Integer lotteryId;

    @ApiModelProperty(value = "每天/年开奖期数[必填]",required = true)
    private Integer startlottoTimes;

    @ApiModelProperty(value = "封盘倒计时(秒)[必填]",required = true)
    private Integer endTime;

    @ApiModelProperty(value = "是否开售[必填]",required = true)
    private Integer isWork;

    @ApiModelProperty(value = "排序[必填]",required = true)
    private Integer sort;

    @ApiModelProperty(value = "多语言列表[必填]",required = true)
    List<LotteryLangReq> langList;

}
