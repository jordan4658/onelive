package com.onelive.common.model.req.lottery;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
@ApiModel(value = "订单下注请求类")
public class OrderReq {
    @ApiModelProperty(value = "直播间房间号[必填]",required = true)
    private String studioNum;

    @ApiModelProperty(value = "购买的期号[必填]",required = true)
    private String issue;

    @ApiModelProperty(value = "彩种id[必填]",required = true)
    private Long lotteryId;

    @ApiModelProperty(value = "用户id,与迁移过来的彩票兼容，后期改成long",hidden = true)
    private Integer userId;

    @ApiModelProperty(value = "国家id",hidden = true)
    private Long countryId;

    @ApiModelProperty(value = "下单来源",hidden = true)
    private String source;

    @ApiModelProperty(value = "订单类型 0代表直播间购彩",hidden = true)
    private Integer buySource = 0;

    @ApiModelProperty(value = "家族id",hidden = true)
    private Long familyId;

//    @ApiModelProperty(value = "内部房间id，转换用",hidden = true)
//    private Long roomId;

    @ApiModelProperty(value = "订单投注记录列表[必填]",required = true)
    private List<OrderBetRecordReq> orderBetList;


}
