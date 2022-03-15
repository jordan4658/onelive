package com.onelive.common.model.req.lottery;

import com.onelive.common.model.common.PageReq;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value = "彩种列表查询请求类")
public class LotteryQueryReq extends PageReq {
    @ApiModelProperty(value = "彩种大类id[必填]",required = true)
    private Integer cateId;
    @ApiModelProperty(value = "彩种ID[必填]",required = true)
    private Integer lotteryId;

}
