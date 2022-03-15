package com.onelive.common.model.req.live;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
@ApiModel("直播分类游戏信息保存请求参数")
public class LiveGameSaveReq {
    @ApiModelProperty("主键ID, 更新时传入")
    private Long id;

    @ApiModelProperty(value = "标签code[必填]",required = true)
    private String code;

    @ApiModelProperty(value ="游戏分类id[必填]",required = true)
    private Integer categoryId;

    @ApiModelProperty(value ="游戏code[必填]",required = true)
    private String gameCode;

    @ApiModelProperty(value ="是否第三方游戏[必填]",required = true)
    private Boolean isThird;

    @ApiModelProperty(value ="是否显示[必填]",required = true)
    private Boolean isShow;

    @ApiModelProperty(value ="游戏信息多语言列表[必填]",required = true)
    private List<LiveGameLangSaveReq> langList;

}
