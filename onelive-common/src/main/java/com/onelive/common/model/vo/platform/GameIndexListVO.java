package com.onelive.common.model.vo.platform;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 首页游戏配置列表查询实体类
 */
@Data
@ApiModel("首页游戏配置列表查询实体类")
public class GameIndexListVO {
    @ApiModelProperty("主键ID")
    private Long id;

    @ApiModelProperty("名称")
    private String name;
}
