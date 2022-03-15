package com.onelive.common.model.req.game.third;

import com.onelive.common.model.common.PageReq;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("游戏分类标签列表下拉选项查询请求参数")
public class GameTagSelectListReq {

    @ApiModelProperty("国家地区code 例如:zh_CN [必填]")
    private String countryCode;

}
