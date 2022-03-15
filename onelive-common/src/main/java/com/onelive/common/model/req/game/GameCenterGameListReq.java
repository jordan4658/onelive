package com.onelive.common.model.req.game;

import com.onelive.common.model.common.PageReq;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("分类游戏列表查询请求参数")
public class GameCenterGameListReq extends PageReq {

    @ApiModelProperty(value = "分类标签code[必填]",required = true)
    private String code;

//    @ApiModelProperty("分类编号")
//    private Integer categoryId;
}
