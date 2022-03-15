package com.onelive.common.model.req.platform;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("用户中心游戏配置保存请求参数")
public class GameUserCenterSaveReq {
    /**
     * 主键ID
     */
    @ApiModelProperty("主键ID, 更新时传入")
    private Long id;

    @ApiModelProperty("游戏名称")
    private String name;

    /**
     * 图标url
     */
    @ApiModelProperty("图标url")
    private String iconUrl;

    @ApiModelProperty("国家code 如zh_CN")
    private String countryCode;

    /**
     * 关联game_category.category_id
     */
    @ApiModelProperty("游戏分类ID")
    private Integer categoryId;

    @ApiModelProperty("游戏唯一标识")
    private String gameCode;


}
