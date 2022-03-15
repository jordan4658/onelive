package com.onelive.common.model.vo.game;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("游戏钱包信息实体类")
public class GameWalletSelectVO {
    @ApiModelProperty("名称")
    private String name;
    @ApiModelProperty("类型")
    private Integer type;
}
