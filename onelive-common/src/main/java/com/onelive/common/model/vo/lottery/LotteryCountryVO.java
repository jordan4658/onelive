package com.onelive.common.model.vo.lottery;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value = "直播游戏返回类")
public class LotteryCountryVO {

    @ApiModelProperty(value = "直播游戏ID")
    private Long id;

    @ApiModelProperty(value = "游戏名称")
    private String name;

    @ApiModelProperty(value = "开始时间")
    private String startTime;

    @ApiModelProperty(value = "结束时间")
    private String endTime;

    @ApiModelProperty(value = "每期间隔时间(分)")
    private Integer interval;

    @ApiModelProperty(value = "封盘时间（秒）")
    private Integer closingTime;

    @ApiModelProperty(value = "期数")
    private Integer issueNum;

    @ApiModelProperty(value = "是否禁用 0否1是")
    private Boolean isForbid;

    @ApiModelProperty(value = "图片地址")
    private String iconUrl;

}

