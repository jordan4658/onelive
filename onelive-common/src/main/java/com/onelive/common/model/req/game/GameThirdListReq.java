package com.onelive.common.model.req.game;

import com.onelive.common.model.common.PageReq;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 第三方游戏列表查询参数
 */
@Data
@ApiModel("第三方游戏列表查询参数")
public class GameThirdListReq extends PageReq {

    @ApiModelProperty(value = "分类编号")
    private Integer categoryId;

    @ApiModelProperty(value = "游戏名称")
    private String name;
}
