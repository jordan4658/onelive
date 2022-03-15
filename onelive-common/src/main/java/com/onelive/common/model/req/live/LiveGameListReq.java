package com.onelive.common.model.req.live;

import com.onelive.common.model.common.PageReq;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("直播分类游戏列表查询请求参数")
public class LiveGameListReq extends PageReq {

    @ApiModelProperty(value = "分类标签code[必填]",required = true)
    private String code;
}
