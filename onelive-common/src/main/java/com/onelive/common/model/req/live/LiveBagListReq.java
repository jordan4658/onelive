package com.onelive.common.model.req.live;

import com.onelive.common.model.common.PageReq;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 背包列表查询请求参数
 */
@Data
@ApiModel(value = "背包列表查询请求参数")
public class LiveBagListReq extends PageReq {

    /**
     * 背包物品名称
     */
    @ApiModelProperty("背包物品名称")
    private String bagName;

    /**
     * 地区
     */
    @ApiModelProperty("地区ID")
    private String countryId;


}
