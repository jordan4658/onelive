package com.onelive.common.model.req.operate;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 新增消息时的子消息
 */
@Data
@ApiModel
public class MessageItemReq {
    @ApiModelProperty("ID, 修改时传")
    private Long id;

    @ApiModelProperty(value = "消息标题[必填]",required = true)
    private String title;

    @ApiModelProperty(value = "消息内容[必填]",required = true)
    private String content;

    @ApiModelProperty(value = "语言标识[必填]",required = true)
    private String lang;

}
