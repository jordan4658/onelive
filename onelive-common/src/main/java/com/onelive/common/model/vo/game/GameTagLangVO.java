package com.onelive.common.model.vo.game;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("游戏标签多语言实体类")
public class GameTagLangVO {
    @ApiModelProperty("主键ID, 更新时传入")
    private Long id;

    /**
     * 语言
     */
    @ApiModelProperty("语言")
    private String lang;

    /**
     * 游戏标签名称
     */
    @ApiModelProperty("游戏标签名称")
    private String name;
}
