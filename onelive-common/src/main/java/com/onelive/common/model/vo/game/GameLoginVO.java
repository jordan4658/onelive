package com.onelive.common.model.vo.game;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 第三方游戏登陆结果
 */
@Data
@ApiModel("第三方游戏登陆结果")
public class GameLoginVO {

    @ApiModelProperty("游戏进入地址")
    private String url;

}
