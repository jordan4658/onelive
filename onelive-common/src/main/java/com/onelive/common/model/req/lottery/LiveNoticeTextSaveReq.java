package com.onelive.common.model.req.lottery;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
@ApiModel("直播间中奖公告配置请求实体类")
public class LiveNoticeTextSaveReq {
    @ApiModelProperty("主键ID, 更新时传入")
    private Long id;

    /**
     * 最小金额
     */
    @ApiModelProperty("最小金额[必填]")
    private BigDecimal minAmount;

    /**
     * 最大金额
     */
    @ApiModelProperty("最小金额[必填]")
    private BigDecimal maxAmount;

    /**
     * 是否显示 0否1是
     */
    @ApiModelProperty("是否显示[必填]")
    private Boolean isShow;

    @ApiModelProperty("多语言列表[必填]")
    private List<LiveNoticeTextLangSaveReq> langList;
}
