package com.onelive.common.model.req.lottery;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
@ApiModel("直播间中奖公告配置多语言请求实体类")
public class LiveNoticeTextLangSaveReq {
    @ApiModelProperty("主键ID, 更新时传入")
    private Long id;

    @ApiModelProperty("语言[必填]")
    private String lang;

    @ApiModelProperty("文案内容[必填]")
    private String text;
}
