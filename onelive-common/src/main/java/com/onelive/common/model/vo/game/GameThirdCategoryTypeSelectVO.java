package com.onelive.common.model.vo.game;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("第三方游戏分类类型实体类")
public class GameThirdCategoryTypeSelectVO {
    @ApiModelProperty("分类名称")
    private String name;
    @ApiModelProperty("类型")
    private String type;
}
