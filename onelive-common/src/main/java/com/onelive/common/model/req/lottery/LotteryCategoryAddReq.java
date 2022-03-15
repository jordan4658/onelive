package com.onelive.common.model.req.lottery;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value = "彩种分类新增请求类")
public class LotteryCategoryAddReq {

    @ApiModelProperty(value = "分类编号",required = true)
    private Integer categoryId;
    @ApiModelProperty(value = "名称",required = true)
    private String name;
    @ApiModelProperty(value = "别名",required = true)
    private String alias;
    @ApiModelProperty(value = "排序",required = true)
    private Integer sort;
    @ApiModelProperty(value = "玩法级别",required = true)
    private Integer level;
    @ApiModelProperty(value = "是否开售",required = true)
    private Integer isWork;

}
