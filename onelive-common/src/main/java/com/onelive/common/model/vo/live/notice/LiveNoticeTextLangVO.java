package com.onelive.common.model.vo.live.notice;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("直播间中奖公告配置信息多语言实体类")
public class LiveNoticeTextLangVO {
    @ApiModelProperty("主键ID")
    private Long id;

    @ApiModelProperty("语言")
    private String lang;

    @ApiModelProperty("文案内容")
    private String text;

}
