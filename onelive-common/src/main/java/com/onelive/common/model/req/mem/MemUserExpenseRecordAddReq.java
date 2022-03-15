package com.onelive.common.model.req.mem;

import com.onelive.common.enums.UserExpenseTypeEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
@ApiModel
public class MemUserExpenseRecordAddReq implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 增加的经验值
     */
    @ApiModelProperty(value = "消费的金额,请输入正数[必填]",required = true)
    private BigDecimal amount;

    /**
     * 类型 1送礼物 2玩游戏
     */
    @ApiModelProperty(value = "类型 1送礼物 2玩游戏[必填]",required = true)
    private UserExpenseTypeEnum type;

    /**
     * 消费的直播间ID, 如果不是在直播间消费的为0
     */
    @ApiModelProperty("消费的直播间ID, 如果不是在直播间消费的不用传")
    private String studioNum;
}
