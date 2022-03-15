package com.onelive.common.model.vo.game;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
@ApiModel("游戏分类所需的下拉选项")
public class GameCategorySelectVO {
    //1.第三方游戏分类
    //2.钱包类型
    @ApiModelProperty("第三方游戏分类")
    private List<GameThirdCategoryTypeSelectVO> categoryList;
    @ApiModelProperty("钱包类型")
    private List<GameWalletSelectVO> walletList;

}
