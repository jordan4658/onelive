package com.onelive.common.model.vo.game.third;

import com.onelive.common.model.vo.game.GameCategorySelectListVO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
@ApiModel("游戏中平台和分类下拉选项列表")
public class GamePlatformAndCategoryVO {
    /**
     * 第三方平台名称
     */
    @ApiModelProperty("第三方平台名称")
    private String name;

    /**
     * 平台代码
     */
    @ApiModelProperty("平台代码")
    private String platformCode;

    @ApiModelProperty("平台分类列表")
    private List<GameCategorySelectListVO> categoryList;
}
