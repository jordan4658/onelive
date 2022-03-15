package com.onelive.common.model.req.sys;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Description: 首页轮播查询请求类
 */
@Data
@ApiModel
public class SysAdvFlashviewQueryReq {

    @ApiModelProperty("广告code： 热门： hot 首页(推荐):index 游戏：game")
    private String flashviewCode;


}
