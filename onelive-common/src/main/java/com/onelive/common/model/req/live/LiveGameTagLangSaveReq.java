package com.onelive.common.model.req.live;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("直播游戏分类标签多语言保存请求参数")
public class LiveGameTagLangSaveReq {
    @ApiModelProperty("主键ID, 更新时传入")
    private Long id;

    /**
     * 语言
     */
    @ApiModelProperty(value = "语言[必填]",required = true)
    private String lang;

    /**
     * 标签名称
     */
    @ApiModelProperty(value = "标签名称[必填]",required = true)
    private String name;
}
