package com.onelive.common.model.req.live;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 背包物品保存多语言请求参数
 */
@Data
@ApiModel("背包物品保存多语言请求参数")
public class LiveBagLangSaveReq {
    @ApiModelProperty("更新时传入ID")
    private Long id;

    /**
     * 语言
     */
    @ApiModelProperty(value = "语言[必填]",required = true)
    private String lang;

    /**
     * 背包物品名称
     */
    @ApiModelProperty(value = "背包物品名称[必填]",required = true)
    private String bagName;

}
