package com.onelive.common.model.req.platform;

import com.onelive.common.model.common.PageReq;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 首页游戏配置列表查询请求参数
 */
@Data
@ApiModel("首页游戏配置列表查询请求参数")
public class GameIndexListReq extends PageReq {

    @ApiModelProperty("国家code 如zh_CN [必填]")
    private String countryCode;

}
