package com.onelive.common.model.vo.game.category;

import com.onelive.common.model.vo.game.GameThirdCategoryTypeSelectVO;
import com.onelive.common.model.vo.game.GameWalletSelectVO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
@ApiModel("第三方游戏分类下拉选项内容实体类")
public class GamePlatformSelectVO {

    @ApiModelProperty("第三方平台名称")
    private String platformName;
    @ApiModelProperty("第三方平台代码")
    private String platformCode;

    @ApiModelProperty("第三方分类列表")
    private List<GameThirdCategoryTypeSelectVO> categoryList;

    @ApiModelProperty("分类钱包列表")
    private List<GameWalletSelectVO> walletList;

}
