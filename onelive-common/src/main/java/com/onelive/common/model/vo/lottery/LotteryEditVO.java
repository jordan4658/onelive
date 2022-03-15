package com.onelive.common.model.vo.lottery;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
@ApiModel(value = "彩种信息查询实体类")
public class LotteryEditVO {

    @ApiModelProperty(value = "彩种id")
    private Integer id;

    @ApiModelProperty(value = "彩票分类id")
    private Integer categoryId;

    @ApiModelProperty(value = "彩种编号")
    private Integer lotteryId;

    @ApiModelProperty(value = "每天/年开奖期数")
    private Integer startlottoTimes;

    @ApiModelProperty(value = "封盘倒计时(秒)")
    private Integer endTime;

    @ApiModelProperty(value = "是否开售")
    private Integer isWork;

    @ApiModelProperty(value = "排序")
    private Integer sort;

    @ApiModelProperty(value = "多语言列表")
    List<LotteryLangVO> langList;

}
