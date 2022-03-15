package com.onelive.common.model.vo.lottery;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@Data
@ApiModel(value = "彩种管理返回类")
public class LotteryVO {

    @ApiModelProperty(value = "ID")
    private Integer id;
    @ApiModelProperty(value = "彩种分类Id")
    private Integer categoryId;
    @ApiModelProperty(value = "彩种编号")
    private Integer lotteryId;
    @ApiModelProperty(value = "彩票类型")
    private String categoryName;
    @ApiModelProperty(value = "名称")
    private String name;
    @ApiModelProperty(value = "周期期数")
    private Integer startlottoTimes;
    @ApiModelProperty(value = "排序")
    private Integer sort;
    @ApiModelProperty(value = "是否开售")
    private Integer isWork;
    @ApiModelProperty(value = "封盘时间,单位秒")
    private Integer endTime;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @ApiModelProperty(value = "创建时间")
    private Date createTime;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @ApiModelProperty(value = "更新时间")
    private Date updateTime;

}
