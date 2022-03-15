package com.onelive.common.model.dto.lottery;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * 彩票中奖公告配置内容
 */
@Data
public class LotteryNoticeConfigDTO {

    @ApiModelProperty("开启状态, 0关 1开")
    private Integer status;

    @ApiModelProperty("公告类型 1:直播间公告, 2:世界公告")
    private Integer type;

    @ApiModelProperty("排名前N名用户")
    private Integer count;

    @ApiModelProperty("每条公告展示时间, 单位:秒")
    private Integer showTime;

    @ApiModelProperty("中奖金额大于等于")
    private BigDecimal amount;

    @ApiModelProperty("彩票ID, 关联lotteryId")
    private List<Integer> lotteryIdList;

}
