package com.onelive.common.model.vo.platform;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("查询首页游戏配置信息多语言实体类")
public class GameIndexLangVO {
    /**
     * 主键ID
     */
    @ApiModelProperty("主键ID")
    private Long id;
    /**
     * 名称
     */
    @ApiModelProperty("名称")
    private String name;
    /**
     * 图标url
     */
    @ApiModelProperty("图标url")
    private String iconUrl;

    @ApiModelProperty("语言")
    private String lang;
}
