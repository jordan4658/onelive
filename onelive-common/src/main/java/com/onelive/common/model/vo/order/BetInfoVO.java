package com.onelive.common.model.vo.order;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * 下注信息查询实体类
 */
@Data
@ApiModel("下注信息查询实体类")
public class BetInfoVO {
    @ApiModelProperty("注单列表")
    private List<LotteryOrderBetRecordVO> betList;
}
