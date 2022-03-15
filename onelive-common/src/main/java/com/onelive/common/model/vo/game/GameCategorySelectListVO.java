package com.onelive.common.model.vo.game;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("第三方游戏分类列表-用于选择实体类")
public class GameCategorySelectListVO {
    /**
     * 分类名称
     */
    @ApiModelProperty("分类名称")
    private String name;

    /**
     * 分类ID
     */
    @ApiModelProperty("分类编号")
    private Integer categoryId;
}
