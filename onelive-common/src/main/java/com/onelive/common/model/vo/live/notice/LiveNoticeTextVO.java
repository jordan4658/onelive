package com.onelive.common.model.vo.live.notice;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
@ApiModel("直播间中奖公告配置信息实体类")
public class LiveNoticeTextVO {
    @ApiModelProperty("主键ID")
    private Long id;
    /**
     * 最小金额
     */
    @ApiModelProperty("最小金额")
    private BigDecimal minAmount;
    /**
     * 最大金额
     */
    @ApiModelProperty("最大金额")
    private BigDecimal maxAmount;
    /**
     * 中文内容
     */
    @ApiModelProperty("中文内容")
    private String text;
    /**
     * 是否显示 0否1是
     */
    @ApiModelProperty("是否显示")
    private Boolean isShow;

    @ApiModelProperty("多语言列表")
    private List<LiveNoticeTextLangVO> langList;
}
