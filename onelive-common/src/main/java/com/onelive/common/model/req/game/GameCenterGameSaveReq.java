package com.onelive.common.model.req.game;

import com.onelive.common.utils.upload.AWSS3Util;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
@ApiModel("分类游戏信息保存请求参数")
public class GameCenterGameSaveReq {
    @ApiModelProperty("主键ID, 更新时传入")
    private Long id;

    @ApiModelProperty("平台代码")
    private String platformCode;

    @ApiModelProperty("标签唯一标识")
    private String code;

    @ApiModelProperty(value ="游戏分类id,关联game_category.category_id[必填]",required = true)
    private Integer categoryId;

    @ApiModelProperty("游戏唯一标识")
    private String gameCode;

    @ApiModelProperty(value ="图片地址[必填]",required = true)
    private String iconUrl;

    @ApiModelProperty(value ="是否显示[必填]",required = true)
    private Boolean isShow;

    @ApiModelProperty(value ="游戏信息多语言列表[必填]",required = true)
    private List<GameCenterGameLangSaveReq> langList;

    public void setIconUrl(String iconUrl) {
        this.iconUrl = AWSS3Util.getRelativeUrl(iconUrl);
    }
}
