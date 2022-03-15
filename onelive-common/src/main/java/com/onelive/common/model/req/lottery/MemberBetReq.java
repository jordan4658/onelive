package com.onelive.common.model.req.lottery;

import com.onelive.common.model.common.PageReq;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author 用户投注请求类
 * @create
 **/
@Data
@ApiModel(value = "用户投注请求类")
public class MemberBetReq extends PageReq {

    @ApiModelProperty(value = "投注开始时间")
    private String startDate;
    @ApiModelProperty(value = "投注结束时间")
    private String endDate;
    @ApiModelProperty(value = "地区Id")
    private Integer countryId;
    @ApiModelProperty(value = "彩种Id")
    private Integer lotteryId;
    @ApiModelProperty(value = "玩法Id")
    private Integer playId;
    @ApiModelProperty(value = "中奖状态")
    private String tbStatus;
    @ApiModelProperty(value = "期号")
    private String issue;
    @ApiModelProperty(value = "订单号")
    private String orderNo;
    @ApiModelProperty(value = "用户账号")
    private String accno;



}

