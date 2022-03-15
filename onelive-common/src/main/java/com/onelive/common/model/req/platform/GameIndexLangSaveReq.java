package com.onelive.common.model.req.platform;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("首页游戏配置多语言保存请求参数")
public class GameIndexLangSaveReq {
    /**
     * 主键ID
     */
    @ApiModelProperty("主键ID, 更新时传入")
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
