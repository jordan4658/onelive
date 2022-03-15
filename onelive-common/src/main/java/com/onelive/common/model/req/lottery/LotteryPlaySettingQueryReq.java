package com.onelive.common.model.req.lottery;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * <p>
 * 彩种玩法规则
 * </p>
 *
 * @author ${author}
 * @since 2021-10-27
 */
@Data
@ApiModel(value = "玩法规则请求类")
public class LotteryPlaySettingQueryReq {

    @ApiModelProperty(value = "规则id[必填]",required = true)
    private Integer id;

}
