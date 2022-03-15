package com.onelive.common.model.vo.lottery;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@Data
@ApiModel(value = "彩种分类返回类")
public class LotteryCategoryVO {

    @ApiModelProperty(value = "ID")
    private Integer id;
    @ApiModelProperty(value = "分类编号")
    private Integer categoryId;
    @ApiModelProperty(value = "名称")
    private String name;
    @ApiModelProperty(value = "别名")
    private String alias;
    @ApiModelProperty(value = "排序")
    private Integer sort;
    @ApiModelProperty(value = "玩法级别")
    private Integer level;
    @ApiModelProperty(value = "是否开售")
    private Integer isWork;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @ApiModelProperty(value = "创建时间")
    private Date createTime;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @ApiModelProperty(value = "更新时间")
    private Date updateTime;

}
