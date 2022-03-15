package com.onelive.common.model.req.game;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("分类游戏标签多语言保存请求参数")
public class GameCenterGameLangSaveReq {
    @ApiModelProperty(value = "主键ID, 更新时传入")
    private Long id;

    @ApiModelProperty(value = "语言[必填]",required = true)
    private String lang;

    @ApiModelProperty(value = "游戏名称[必填]",required = true)
    private String name;
}
