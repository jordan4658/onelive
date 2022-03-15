package com.onelive.common.model.vo.platform;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 用户中心游戏配置信息
 */
@Data
@ApiModel("查询用户中心游戏配置信息实体类")
public class GameUserCenterVO {
    /**
     * 主键ID
     */
    @ApiModelProperty("主键ID")
    private Long id;

    @ApiModelProperty("国家code 如zh_CN")
    private String countryCode;

    /**
     * 名称
     */
    @ApiModelProperty("游戏名称")
    private String name;

    @ApiModelProperty("图标url")
    private String iconUrl;
    /**
     * 关联game_category.category_id
     */
    @ApiModelProperty("游戏分类ID")
    private Integer categoryId;

    @ApiModelProperty("游戏唯一标识")
    private String gameCode;
}
