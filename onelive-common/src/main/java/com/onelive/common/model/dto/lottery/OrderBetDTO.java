package com.onelive.common.model.dto.lottery;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
@ApiModel(value = "投注列表实体类")
public class OrderBetDTO {

    @ApiModelProperty(value = "用户id")
    private Integer userId;
    @ApiModelProperty(value = "语言标识")
    private String lang;
    @ApiModelProperty(value = "彩种id列表")
    private List<Integer> lotteryIds;
    @ApiModelProperty(value = "分页号")
    Integer pageNo = 1;
    @ApiModelProperty(value = "分页数目")
    Integer pageSize = 10;
    @ApiModelProperty(value = "排序列名")
    private String sortName;
    @ApiModelProperty(value = "排序方式，desc降序，asc升序")
    private String sortType;
    @ApiModelProperty(value = "投注类型，默认是彩票")
    private String status = "Lottery";
    @ApiModelProperty(value = "时间，年月日")
    private Date date;
    @ApiModelProperty(value = "彩票id")
    private Integer lotteryId;

}
