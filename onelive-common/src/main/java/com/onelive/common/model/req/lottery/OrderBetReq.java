package com.onelive.common.model.req.lottery;

import com.onelive.common.model.common.PageReq;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
@ApiModel(value = "投注列表请求类")
public class OrderBetReq extends PageReq {
    @ApiModelProperty(value = "彩种id列表")
    private List<Integer> lotteryIds;
    @ApiModelProperty(value = "排序字段名称：bet_amount:投注金额 | win_amount:中奖金额 | create_time:投注时间")
    private String sortName;
    @ApiModelProperty(value = "排序方式：ASC:顺序 | DESC:倒序")
    private String sortType;
    @ApiModelProperty(value = "状态 WIN:中奖 | NO_WIN:未中奖 | HE:打和 | WAIT:等待开奖")
    private String status;
    @ApiModelProperty(value = "查询日期，格式yyyy-MM-dd，当地时区转时间戳")
    private Long queryDate;

}
