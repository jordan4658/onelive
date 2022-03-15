package com.onelive.common.model.vo.mem;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 用户中心游戏配置列表查询实体类
 */
@Data
@ApiModel("客户端用户中心游戏配置列表查询实体类")
public class AppGameUserCenterListVO {
    @ApiModelProperty("主键ID")
    private Long id;

    /**
     * 图标url
     */
    @ApiModelProperty("图标url")
    private String iconUrl;

    /**
     * 关联game_category.category_id
     */
    @ApiModelProperty("游戏分类ID")
    private Integer categoryId;

    /**
     * 关联game_third.game_code
     */
    @ApiModelProperty("游戏code")
    private String gameCode;

}
